package com.culturer.guishi_shoper.pages.sale_after.tuikuan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.culturer.guishi_shoper.R;

public class TuikuanAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	
	public TuikuanAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public int getGroupCount() {
		return 10;
	}
	
	@Override
	public int getChildrenCount(int i) {
		return 5;
	}
	
	@Override
	public Object getGroup(int i) {
		return null;
	}
	
	@Override
	public Object getChild(int i, int i1) {
		return null;
	}
	
	@Override
	public long getGroupId(int i) {
		return 0;
	}
	
	@Override
	public long getChildId(int i, int i1) {
		return 0;
	}
	
	@Override
	public boolean hasStableIds() {
		return false;
	}
	
	@Override
	public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
		View contentView = LayoutInflater.from(context).inflate(R.layout.tuikuan_group,null);
		return contentView;
	}
	
	@Override
	public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
		View contentView = LayoutInflater.from(context).inflate(R.layout.waite_item,null);
		return contentView;
	}
	
	@Override
	public boolean isChildSelectable(int i, int i1) {
		return false;
	}
}
