package cn.edu.cdut.myfirstapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.cdut.myfirstapp.Holder.ViewHolder;
import cn.edu.cdut.myfirstapp.Model.Fruit;
import cn.edu.cdut.myfirstapp.R;


/**
 * Created by Administrator on 2016/5/30 0030.
 */

public class FruitAdapter extends ArrayAdapter<Fruit>{
    private int resourceId;

    public FruitAdapter(Context context, int textViewResourceId, List<Fruit> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = getItem(position);
        View view;
        ViewHolder viewHolder;
        Log.v("getView()",position+"    "+convertView+" "+position);
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = (ImageView)view.findViewById(R.id.iv_fruit);
            viewHolder.fruitName = (TextView)view.findViewById(R.id.tv_fruit);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());
        return view;
    }
}
