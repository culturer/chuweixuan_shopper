package com.culturer.guishi_shoper.pages.goods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.culturer.guishi_shoper.R;
import com.culturer.guishi_shoper.bean.GoodsBean;
import com.culturer.guishi_shoper.bean.ProductTypeBean;
import com.culturer.guishi_shoper.bean.ProductsBean;

/**
 * Created by Administrator on 2018/7/9 0009.
 */

public class GoodsAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	private GoodsBean goodsBean;
	public GoodsAdapter(Context context, GoodsBean goodsBean) {
		this.context = context;
		this.goodsBean = goodsBean;
	}
	
	@Override
	public int getGroupCount() {
		return goodsBean.getDatas().size();
	}
	
	@Override
	public int getChildrenCount(int i) {
		return goodsBean.getDatas().get(i).getProducts().size();
	}
	
	@Override
	public ProductTypeBean getGroup(int i) {
		return goodsBean.getDatas().get(i).getProductType();
	}
	
	@Override
	public ProductsBean getChild(int i, int i1) {
		return goodsBean.getDatas().get(i).getProducts().get(i1);
	}
	
	@Override
	public long getGroupId(int i) {
		return i;
	}
	
	@Override
	public long getChildId(int i, int i1) {
		return 0;
	}
	
	@Override
	public boolean hasStableIds() {
		return true;
	}
	
	@Override
	public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
		View goods_group = LayoutInflater.from(context).inflate(R.layout.goods_group,null);
		TextView title = goods_group.findViewById(R.id.title);
		title.setText(getGroup(i).getName());
		return goods_group;
	}
	
	@Override
	public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
		View goods_item = LayoutInflater.from(context).inflate(R.layout.goods_item,null);
		ImageView icon = goods_item.findViewById(R.id.icon);
		TextView name = goods_item.findViewById(R.id.name);
		TextView num = goods_item.findViewById(R.id.num);
		TextView price = goods_item.findViewById(R.id.price);
		TextView desc = goods_item.findViewById(R.id.desc);
		name.setText(getChild(i,i1).getName());
		num.setText("库存:"+getChild(i,i1).getNum());
		price.setText("￥"+getChild(i,i1).getPrice());
		desc.setText(getChild(i,i1).getDesc());
		return goods_item;
	}
	
	@Override
	public boolean isChildSelectable(int i, int i1) {
		return true;
	}
	
	public void update(GoodsBean goodsBean){
		this.goodsBean = goodsBean;
		notifyDataSetChanged();
	}
}
