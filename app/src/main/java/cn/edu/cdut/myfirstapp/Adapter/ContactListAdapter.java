package cn.edu.cdut.myfirstapp.Adapter;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import cn.edu.cdut.myfirstapp.Layout.QuickAlphabeticBar;
import cn.edu.cdut.myfirstapp.Model.ContactBean;
import cn.edu.cdut.myfirstapp.R;


public class ContactListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ContactBean> list;
    private Context ctx; // 上下文


	public ContactListAdapter(Context context, List<ContactBean> list) {
		this.ctx = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		HashMap<String, Integer> alphaIndexer = new HashMap<String, Integer>();
		String[] sections = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			// 得到字母
			String name = getAlpha(list.get(i).getSortKey());
			if (!alphaIndexer.containsKey(name)) {
				alphaIndexer.put(name, i);
			}
		}

		Set<String> sectionLetters = alphaIndexer.keySet();
		ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
		Collections.sort(sectionList);
		sections = new String[sectionList.size()];
		sectionList.toArray(sections);
    }

	public ContactListAdapter(Context context, List<ContactBean> list, QuickAlphabeticBar alpha) {
		this.ctx = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list;

        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        String[] sections = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			// 得到contactBean对象的sortKey成员的首字母，给name
			String name = getAlpha(list.get(i).getSortKey());
            //  如果hashMap中还没有该字母，就把他添加到hashMap中
			if (!hashMap.containsKey(name)) {
				hashMap.put(name, i);
                // 联系人拼音的首字母不一定包含所有26个字母，这样以后，只把包含的那些字母添加到HashMap表的key里面了，
                // 而value为该字母下的第一位联系人在该表的位置。
                //  这样是为了快速滚动条 根据首字母查找联系人用的。
			}
		}

		Set<String> sectionLetters = hashMap.keySet();

		ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
		Collections.sort(sectionList);
		sections = new String[sectionList.size()];
		sectionList.toArray(sections);

		alpha.setAlphaIndexer(hashMap);

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

	public void remove(int position) {
		list.remove(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item3, null);
			holder = new ViewHolder();
			holder.quickContactBadge = (QuickContactBadge) convertView.findViewById(R.id.qcb_img);
			holder.alpha = (TextView) convertView.findViewById(R.id.tv_firstLetter);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.number = (TextView) convertView.findViewById(R.id.tv_number);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ContactBean contactBean = list.get(position);   //从List里根据position得到ContactBean对象

		String name = contactBean.getDesplayName(); //  从ContactBean对象中得到name属性
		String number = contactBean.getPhoneNum();  //  得到number属性

        //  根据从ContactBean对象中得到的各种属性，为holder对象中的属性赋值。
		holder.name.setText(name);
        Log.v("holder.name.setText",name);
		holder.number.setText(number);

        //  为quickContactBage添加Uri，否则点击它不会弹出该联系人卡片。
        //  可以用两种方法：assignContactFromPhone 和 assignContactUri，后者直接，前者还要根据号码再次查找Uri。

		//holder.quickContactBadge.assignContactUri(Contacts.getLookupUri(contactBean.getContactId(), contactBean.getLookUpKey()));
        holder.quickContactBadge.assignContactFromPhone(number,false);

        //获取联系人头像
		if (0 == contactBean.getPhotoId()) {
            Log.v("contactBean.getPhotoId",""+contactBean.getPhotoId());
            holder.quickContactBadge.setImageResource(R.drawable.gg);
		} else {
			Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactBean.getContactId());
            Log.v("Contacts.CONTENT_URI",""+ContactsContract.Contacts.CONTENT_URI); //  content://com.android.contacts/contacts
            Log.v("withAppendedId()",""+uri);
            Log.v("PhotoId is:",""+contactBean.getPhotoId());
			InputStream input = Contacts.openContactPhotoInputStream(ctx.getContentResolver(), uri,true);
			Bitmap contactPhoto = BitmapFactory.decodeStream(input);
			holder.quickContactBadge.setImageBitmap(contactPhoto);
		}
		// 该contactBean对象sortKey的第一个字母
		String currentStr = getAlpha(contactBean.getSortKey());
		// 该contactBean对象前一个对象的sortKey的第一个字母
		String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getSortKey()) : " ";

		if (!previewStr.equals(currentStr)) {
			holder.alpha.setVisibility(View.VISIBLE);
			holder.alpha.setText(getAlpha(contactBean.getSortKey()));//此处可以看到getSortKey()得到的是什么：CAO曹LEI磊
		} else {
			holder.alpha.setVisibility(View.GONE);
		}
		return convertView;
	}

	private static class ViewHolder {
		QuickContactBadge quickContactBadge;
		TextView alpha;
		TextView name;
		TextView number;
	}

	/**
	 * 获取首字母
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);
		// 正则表达式匹配
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase(); // 将小写字母转换为大写
		} else {
			return "#";
		}
	}
}
