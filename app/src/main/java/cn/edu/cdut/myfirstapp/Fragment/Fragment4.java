package cn.edu.cdut.myfirstapp.Fragment;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.cdut.myfirstapp.Adapter.DialAdapter;
import cn.edu.cdut.myfirstapp.Model.CallLogBean;
import cn.edu.cdut.myfirstapp.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2016/7/26 0026.
 */

public class Fragment4 extends Fragment {
    private ListView callLogListView;
    private static AsyncQueryHandler asyncQueryHandler;
    private DialAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4,null);

        callLogListView = (ListView) view.findViewById(R.id.call_log_list);

        asyncQueryHandler = new MyAsyncQueryHandler(getActivity().getContentResolver());

        init();
        return view;
    }

    public static void init() {
        Uri uri = android.provider.CallLog.Calls.CONTENT_URI;

        Log.v("CallLog.Calls",""+uri);  //  content://call_log/calls
        // 查询的列
        String[] projection = {
                CallLog.Calls.DATE, // 日期
                CallLog.Calls.NUMBER, // 号码
                CallLog.Calls.TYPE, // 类型
                CallLog.Calls.CACHED_NAME, // 名字
                CallLog.Calls._ID, // id
                //CallLog.Calls.CACHED_PHOTO_ID,
                //CallLog.Calls.CACHED_LOOKUP_URI
                /*ContactsContract.CommonDataKinds.Phone.PHOTO_ID,    //"photo_id" in ContactsColumns
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,  //  "lookup" in ContactsColumns*/
        };
        asyncQueryHandler.startQuery(0, null, uri, projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        Log.v("Cal Log----","startQuery() 开始查找=====================");
    }

    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            Log.v("Cal Log----","onQueryComplete() 查询结束，开始处理数据---------------------");
            if (cursor != null && cursor.getCount() > 0) {
                List<CallLogBean> callLogs = new ArrayList<CallLogBean>();

                SimpleDateFormat sfd = new SimpleDateFormat("MM月dd日HH:mm:ss");
                Date date;

                cursor.moveToFirst(); // 游标移动到第一项
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);

                    /*date = new Date(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)));
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                    String cachedName = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));// 缓存的名称与电话号码，如果它的存在
                    int id = cursor.getInt(cursor.getColumnIndex(CallLog.Calls._ID));*/

                    date = new Date(cursor.getLong(0));
                    String number = cursor.getString(1);
                    int type = cursor.getInt(2);
                    String cachedName = cursor.getString(3);
                    int id = cursor.getInt(4);

                    int photoId = getPhotoIdByNumber(getContext(),number);

                    String s = getContactIdByNumber(getContext(),number);
                    Log.v("Cal Log----","getContactIdByNumber() 是 "+s);
                    int contactId = Integer.parseInt(s);
                    //Long cachedPhotoId = cursor.getLong(5);
                    //Log.v("Cal Log----","cachedPhotoId 是 "+cachedPhotoId);


                    CallLogBean callLogBean = new CallLogBean();

                    callLogBean.setDate(sfd.format(date));
                    callLogBean.setNumber(number);
                    callLogBean.setType(type);
                    if (null == cachedName || "".equals(cachedName)) {
                        callLogBean.setName("未知联系人");
                    }
                    else callLogBean.setName(cachedName);
                    callLogBean.setId(id);
                    callLogBean.setPhotoId(photoId);
                    callLogBean.setContactId(contactId);

                    callLogs.add(callLogBean);
                }
                if (callLogs.size() > 0) {
                    setAdapter(callLogs);
                }
            }
            super.onQueryComplete(token, cookie, cursor);
            Log.v("Cal Log----","onQueryComplete() 数据处理结束---------------------");
        }
    }


    private static int getPhotoIdByNumber(Context context, String number) {
        int photoId = 0;
        //利用phone_lookup数据表所对应的ContentProvider进行查询
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, number);
        Cursor c = cr.query(uri , new String[]{ContactsContract.PhoneLookup.PHOTO_ID}, null, null, null);
        //如果提供的电话号码确实是有头像的
        if(c.moveToNext()){
            photoId = c.getInt(0);
        }
        c.close();
        return photoId;
    }

    private static String getContactIdByNumber(Context context, String number) {
        Cursor c = null;
        try {
            c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[] {
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    },
                    null,null,null
            );
            if (c != null && c.moveToFirst()) {
                while (!c.isAfterLast()) {

                    if (PhoneNumberUtils.compare(number, c.getString(1))) {
                        return c.getString(0);
                    }
                    c.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getContactId error:", e);
        } finally {
            if (c != null) {c.close();
            }
        }
        return "0";
    }

    private void setAdapter(List<CallLogBean> callLogs) {
        adapter = new DialAdapter(getContext(), callLogs);
        callLogListView.setAdapter(adapter);
    }
}
