package cn.edu.cdut.myfirstapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.cdut.myfirstapp.Holder.PersonHolder;
import cn.edu.cdut.myfirstapp.Model.Person;
import cn.edu.cdut.myfirstapp.R;


/**
 * Created by Administrator on 2016/6/2 0002.
 */

public class PersonAdapter extends ArrayAdapter<Person> {

    private int resourceId;

    public PersonAdapter(Context context, int resource, List<Person> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person person = getItem(position);
        PersonHolder personHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId,null);

            personHolder = new PersonHolder();
            personHolder.personImage = (ImageView)convertView.findViewById(R.id.iv_fruit);
            personHolder.personText = (TextView)convertView.findViewById(R.id.tv_fruit);
            convertView.setTag(personHolder);
        }else{
            personHolder = (PersonHolder) convertView.getTag();
        }
        personHolder.personImage.setImageResource(person.getImageId());
        personHolder.personText.setText(person.getName());
        return convertView;
    }
}
