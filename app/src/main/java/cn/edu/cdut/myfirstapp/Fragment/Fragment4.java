package cn.edu.cdut.myfirstapp.Fragment;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

/**
 * Created by Administrator on 2016/7/26 0026.
 */

public class Fragment4 extends Fragment {
    private ListView callLogListView;
    private AsyncQueryHandler asyncQuery;
    private DialAdapter adapter;
    private List<CallLogBean> callLogs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4,null);

        callLogListView = (ListView) view.findViewById(R.id.call_log_list);

        asyncQuery = new MyAsyncQueryHandler(getActivity().getContentResolver());

        init();
        return view;
    }

    private void init() {
        Uri uri = android.provider.CallLog.Calls.CONTENT_URI;
        // 查询的列
        String[] projection = {
                CallLog.Calls.DATE, // 日期
                CallLog.Calls.NUMBER, // 号码
                CallLog.Calls.TYPE, // 类型
                CallLog.Calls.CACHED_NAME, // 名字
                CallLog.Calls._ID, // id
        };
        asyncQuery.startQuery(0, null, uri, projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
    }

    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                callLogs = new ArrayList<CallLogBean>();
                SimpleDateFormat sfd = new SimpleDateFormat("MM月dd日HH:mm:ss");
                Date date;
                cursor.moveToFirst(); // 游标移动到第一项
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    date = new Date(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)));
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                    String cachedName = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));// 缓存的名称与电话号码，如果它的存在
                    int id = cursor.getInt(cursor.getColumnIndex(CallLog.Calls._ID));

                    CallLogBean callLogBean = new CallLogBean();
                    callLogBean.setId(id);
                    callLogBean.setNumber(number);
                    callLogBean.setName(cachedName);
                    if (null == cachedName || "".equals(cachedName)) {
                        callLogBean.setName("未知联系人");
                    }
                    callLogBean.setType(type);
                    callLogBean.setDate(sfd.format(date));

                    callLogs.add(callLogBean);
                }
                if (callLogs.size() > 0) {
                    setAdapter(callLogs);
                }
            }
            super.onQueryComplete(token, cookie, cursor);
        }
    }

    private void setAdapter(List<CallLogBean> callLogs) {
        adapter = new DialAdapter(getContext(), callLogs);
        callLogListView.setAdapter(adapter);
    }
}
