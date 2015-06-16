package com.jk.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jk.demo2.MainActivity;
import com.jk.demo2.R;
import com.lidroid.xutils.util.LogUtils;

public class MyAdapter extends BaseAdapter {

	private List<String> list;
	LayoutInflater inflater;

	public MyAdapter(List<String> list, Context ct) {
		this.list = list;
		inflater = LayoutInflater.from(ct);
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
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			LogUtils.i("新建");
			convertView = inflater.inflate(R.layout.item_001, null);
			holder.tView = (TextView) convertView.findViewById(R.id.tv);

			convertView.setTag(holder);
		} else {
			LogUtils.i("复用");
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tView.setText(list.get(position) + position);

		return convertView;

	}

	public final class ViewHolder {
		public TextView tView;
	}
}
