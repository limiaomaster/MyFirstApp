package cn.edu.cdut.myfirstapp.Fragment;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.cdut.myfirstapp.Adapter.ContactListAdapter;
import cn.edu.cdut.myfirstapp.Layout.QuickAlphabeticBar;
import cn.edu.cdut.myfirstapp.Model.ContactBean;
import cn.edu.cdut.myfirstapp.R;

/**
 * Created by Administrator on 2016/6/23 0023.
 */

public class Fragment3 extends Fragment{

    private ListView contactListView;
    private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象
    private QuickAlphabeticBar alphabeticBar; // 快速索引条



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3,null);

        contactListView = (ListView) view.findViewById(R.id.contact_list);
        alphabeticBar = (QuickAlphabeticBar) view.findViewById(R.id.fast_scroller);
        asyncQueryHandler = new MyAsyncQueryHandler(getActivity().getContentResolver());

        init();
        return view;
    }

    /**
     * 初始化数据库查询参数
     */
    private void init() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；
        Log.v("CommonDataKinds.Phone",""+uri);  //  content://com.android.contacts/data/phones
        // 查询的字段
        String[] projection = { ContactsContract.CommonDataKinds.Phone._ID, //  "_id" in BaseColumns
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,    //  "display_name" in ContactsColumns
                ContactsContract.CommonDataKinds.Phone.DATA1,   //  "data1" in DataColumns
                "sort_key",                                                                 //  ContactNameColumns
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,  //  "contact_id" in RawContactsColumns
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,    //"photo_id" in ContactsColumns
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY   //  "lookup" in ContactsColumns
        };
        // 按照sort_key升序查詢
        // token令牌，cookie一个对象，uri，projection返回的列，selection过滤器可包含?，selectionArgs过滤的参数，orderBy排序方式
        asyncQueryHandler.startQuery(0, null, uri, projection, null, null, "sort_key COLLATE LOCALIZED asc");
    }

    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            Log.v("Contacts----","onQueryComplete() 开始处理数据---------------------");
            if (cursor != null && cursor.getCount() > 0) {  //如果找到数据了，游标不为空 并且 找到的行数大于0；

                Map<Integer, ContactBean> contactIdMap = new HashMap<Integer, ContactBean>();
                List<ContactBean> contactBeanList = new ArrayList<ContactBean>();

                cursor.moveToFirst(); // 游标移动到第一项
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    //Log.v("Contacts----","Phone._ID为："+cursor.getString(0));
                    String name = cursor.getString(1);  //获取第二列的数据，给name
                    String number = cursor.getString(2);    //获取第三列的数据，给number
                    String sortKey = cursor.getString(3);
                    int contactId = cursor.getInt(4);
                    Long photoId = cursor.getLong(5);
                    String lookUpKey = cursor.getString(6);

                    if (contactIdMap.containsKey(contactId)) {
                        // 表里有contactId，则无操作
                    } else {
                        // 否则就创建新的联系人对象
                        ContactBean contactBean = new ContactBean();
                        //  为对象的各个成员赋值，形成完整的联系人对象。
                        contactBean.setDesplayName(name);
                        contactBean.setPhoneNum(number);
                        contactBean.setSortKey(sortKey);
                        contactBean.setContactId(contactId);
                        contactBean.setPhotoId(photoId);
                        contactBean.setLookUpKey(lookUpKey);
                        //  把联系人对象添加到list中
                        contactBeanList.add(contactBean);
                        //  contactId和联系人对象添加到contactIdMap里面
                        contactIdMap.put(contactId, contactBean);
                    }
                }

                if (contactBeanList.size() > 0) {
                    setAdapter(contactBeanList);
                }
            }
            super.onQueryComplete(token, cookie, cursor);
            Log.v("Contacts----","onQueryComplete() 数据处理结束---------------------");
        }
    }

    private void setAdapter(List<ContactBean> list) {
        ContactListAdapter adapter = new ContactListAdapter(getActivity(), list , alphabeticBar );
        contactListView.setAdapter(adapter);

        alphabeticBar.init(getActivity());

        alphabeticBar.setListView(contactListView);
        //alphabeticBar.setHight(alphabeticBar.getHeight());
       // Log.v("setAdapter","把快速滚动条的高度设为了："+alphabeticBar.getHeight());
        alphabeticBar.setVisibility(View.VISIBLE);
    }
}
