package com.pascalabs.util.log.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.pascalabs.util.log.R;
import com.pascalabs.util.log.model.BeanLog;
import java.util.ArrayList;

public class LogAdapter
	extends BaseAdapter {

	private ArrayList<BeanLog> items;
	private Context context;

	public LogAdapter(Context context, ArrayList<BeanLog> list) {
		items = new ArrayList<BeanLog>();
		this.context = context;
		this.items = list;
	}


	public ArrayList<BeanLog> getItems() {
		return items;
	}

	public void setItems(ArrayList<BeanLog> items) {
		this.items = items;
	}

	@Override
	public int getCount() {
        try {
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }

	@Override
	public BeanLog getItem(int pos) {
		return items.get(pos);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		holder = new ViewHolder();
		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final Context contextThemeWrapper = context;
			LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

			convertView = localInflater.inflate(R.layout.cell_log, parent, false);
		}
		holder.type = (TextView) convertView.findViewById(R.id.logtype);
		holder.date = (TextView) convertView.findViewById(R.id.logdate);
		holder.event = (TextView) convertView.findViewById(R.id.logevent);

        BeanLog o = getItem(position);
        holder.event.setText(o.getEvent());
		holder.type.setText(o.getType());
        try {
			holder.date.setText(DateUtils.getRelativeTimeSpanString(Long.parseLong(o.getTimestamp())));
        } catch (Exception e) {}

		convertView.setTag(holder);

		return convertView;
	}

	public static class ViewHolder {
		TextView type;
		TextView event;
		TextView date;
	}
}
