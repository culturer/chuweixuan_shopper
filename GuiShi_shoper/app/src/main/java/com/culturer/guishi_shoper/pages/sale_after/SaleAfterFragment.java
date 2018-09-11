package com.culturer.guishi_shoper.pages.sale_after;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.culturer.guishi_shoper.R;
import com.culturer.guishi_shoper.pages.order.PagerAdapter;
import com.culturer.guishi_shoper.pages.order.order_item.AllFragment;
import com.culturer.guishi_shoper.pages.order.order_item.OkFragment;
import com.culturer.guishi_shoper.pages.order.order_item.WaiteFragment;
import com.culturer.guishi_shoper.pages.sale_after.toushu.ToushuFragment;
import com.culturer.guishi_shoper.pages.sale_after.tuikuan.TuikuanFragment;

import java.util.ArrayList;
import java.util.List;


public class SaleAfterFragment extends Fragment {
	
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
	
	public SaleAfterFragment() {
		// Required empty public constructor
	}
	
	public static SaleAfterFragment newInstance(String param1, String param2) {
		SaleAfterFragment fragment = new SaleAfterFragment();
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
			contentView = inflater.inflate(R.layout.fragment_sale_after, container, false);
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
		pagerList.add("退款");
		pagerList.add("投诉");
		fragmentList.add(TuikuanFragment.newInstance("",""));
		fragmentList.add(ToushuFragment.newInstance("",""));
		
	}
	
	private void initPager(){
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
		
		pagerAdapter = new PagerAdapter(getChildFragmentManager(),fragmentList,pagerList);
		order_pager.setAdapter(pagerAdapter);
		order_tab.setupWithViewPager(order_pager);
	}
}
