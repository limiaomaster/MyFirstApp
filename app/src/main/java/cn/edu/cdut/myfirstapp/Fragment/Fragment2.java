package cn.edu.cdut.myfirstapp.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.cdut.myfirstapp.Adapter.PersonAdapter;
import cn.edu.cdut.myfirstapp.Model.Person;
import cn.edu.cdut.myfirstapp.R;


/**
 * Created by Administrator on 2016/5/27 0027.
 */

public class Fragment2 extends Fragment {

    private List<Person> contactsList = new ArrayList<Person>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment2, null);
        readContacts();
        //建一个适配器
        PersonAdapter personAdapter = new PersonAdapter(getActivity(), R.layout.item2, contactsList);

        //找到 listview
        ListView contactsListView = (ListView) view.findViewById(R.id.listview2);

        //为 listview 设置适配器
        contactsListView.setAdapter(personAdapter);



        return view;
    }

    private void readContacts() {
            Cursor cursor = null;
            try {
                cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                while (cursor.moveToNext()) {
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //int numberINT = Integer.parseInt(number,10);
                    //Log.v("readContacts()",numberINT+"");
                    Log.v("readContacts()",number);
                    Person person = new Person(R.drawable.aa,displayName+"\n"+number);
                    contactsList.add(person);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
    }


}
