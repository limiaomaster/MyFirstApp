package cn.edu.cdut.myfirstapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import java.util.List;

import cn.edu.cdut.myfirstapp.Model.CallLogBean;
import cn.edu.cdut.myfirstapp.R;

/**
 * Created by Administrator on 2016/7/26 0026.
 */

public class DialAdapter extends BaseAdapter {
    private Context ctx;
    private List<CallLogBean> list;
    private LayoutInflater inflater;

    public DialAdapter(Context context, List<CallLogBean> list) {
        this.ctx = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_record_list_item, null);
            holder = new ViewHolder();
            holder.quickContactBadge = (QuickContactBadge) convertView.findViewById(R.id.qcb_img1);
            holder.call_type = (ImageView) convertView.findViewById(R.id.call_type);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.call_btn = (TextView) convertView.findViewById(R.id.call_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CallLogBean callLogBean = list.get(position);
        switch (callLogBean.getType()) {
            case 1: // 来电:1，
                holder.call_type.setBackgroundResource(R.drawable.ic_calllog_incomming_normal);
                break;
            case 2://   拨出:2,
                holder.call_type.setBackgroundResource(R.drawable.ic_calllog_outgoing_nomal);
                break;
            case 3://   未接:3
                holder.call_type.setBackgroundResource(R.drawable.ic_calllog_missed_normal);
                break;
        }
        holder.name.setText(callLogBean.getName());
        holder.number.setText(callLogBean.getNumber());
        holder.time.setText(callLogBean.getDate());
        //holder.quickContactBadge.assignContactUri(Contacts.getLookupUri(contactBean.getContactId(), contactBean.getLookUpKey()));

        addViewListener(holder.call_btn, callLogBean, position);

        return convertView;
    }

    private static class ViewHolder {
        QuickContactBadge quickContactBadge;
        ImageView call_type;
        TextView name;
        TextView number;
        TextView time;
        TextView call_btn;
    }

    private void addViewListener(View view, final CallLogBean callLog, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + callLog.getNumber());
                //Intent intent = new Intent(Intent.ACTION_CALL, uri); //   直接拨号
                Intent intent = new Intent(Intent.ACTION_DIAL, uri); // 传入拨号程序
                ctx.startActivity(intent);
            }
        });
    }
}
