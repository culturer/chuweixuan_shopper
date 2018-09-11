package com.culturer.guishi_shoper.pages.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.culturer.guishi_shoper.R;
import com.culturer.guishi_shoper.pages.order.order_item.AllFragment;
import com.culturer.guishi_shoper.pages.order.order_item.OkFragment;
import com.culturer.guishi_shoper.pages.order.order_item.WaiteFragment;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	private String mParam1;
	private String mParam2;
	
	private View contentView;
	private TabLayout order_tab;
	private ViewPager order_pager;
	private PagerAdapter pagerAdapter;
	//标签
	List<String> pagerList = new ArrayList<>();
	//内容
	List<Fragment> fragmentList= new ArrayList<>();
	
	public OrderFragment() {
		// Required empty public constructor
	}
	
	public static OrderFragment newInstance(String param1, String param2) {
		OrderFragment fragment = new OrderFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		if (contentView == null){
			contentView = inflater.inflate(R.layout.fragment_order, container, false);
			initBaseView();
			initPager();
		}
		ViewGroup parent = (ViewGroup) contentView.getParent();
		if ( parent!=null ){
			parent.removeView(contentView);
		}
		return contentView;
		
	}
	
	private void initBaseView(){
		order_tab = contentView.findViewById(R.id.order_tab);
		order_pager = contentView.findViewById(R.id.order_pager);
	}
	
	private void initPager(){
		pagerList = new ArrayList<>();
		pagerList.add("待发货");
		pagerList.add("待签收");
		pagerList.add("全部");
		fragmentList = new ArrayList<>();
		fragmentList.add(WaiteFragment.newInstance("",""));
		fragmentList.add(OkFragment.newInstance("",""));
		fragmentList.add(AllFragment.newInstance("",""));
		//MODE_SCROLLABLE可滑动的展示
		//MODE_FIXED固定展示
		order_tab.setTabMode(TabLayout.MODE_FIXED);
/*		优化参考
		if (Cache.productsTypes.size()<10){
			home_tab.setTabMode(TabLayout.MODE_FIXED);
		}else {
			home_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
		}*/
		order_tab.setSelectedTabIndicatorColor(getContext().getColor(R.color.black));
		for (int i=0;i<pagerList.size();i++){
			order_tab.addTab(order_tab.newTab().setText(pagerList.get(i)));
		}
		Log.i("initPager", "fragmentList_size: "+fragmentList.size()+"||pagerList_size："+pagerList.size());
		pagerAdapter = new PagerAdapter(getChildFragmentManager(),fragmentList,pagerList);
		order_pager.setAdapter(pagerAdapter);
		order_tab.setupWithViewPager(order_pager);
	}
	
}
