package com.culturer.guishi_shoper.pages.order.order_item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.culturer.guishi_shoper.R;

/**
 * Created by Administrator on 2018/7/9 0009.
 */

public class OkAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	
	public OkAdapter(Context context) {
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
		View gruopView = LayoutInflater.from(context).inflate(R.layout.ok_group,null);
		return gruopView;
	}
	
	@Override
	public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
		View childView = LayoutInflater.from(context).inflate(R.layout.ok_item,null);
		
		return childView;
	}
	
	@Override
	public boolean isChildSelectable(int i, int i1) {
		return false;
	}
}
