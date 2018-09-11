package com.culturer.guishi_shoper.pages.sale_after.tuikuan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.culturer.guishi_shoper.R;

public class TuikuanFragment extends Fragment {
	
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	private String mParam1;
	private String mParam2;

	private View contentView;
	private ExpandableListView expandableListView;
	private TuikuanAdapter adapter;
	
	public TuikuanFragment() {
		// Required empty public constructor
	}

	public static TuikuanFragment newInstance(String param1, String param2) {
		TuikuanFragment fragment = new TuikuanFragment();
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
			contentView = inflater.inflate(R.layout.fragment_tuikuan, container, false);
			initBaseView();
			initList();
		}
		ViewGroup parent = (ViewGroup) contentView.getParent();
		if ( parent!=null ){
			parent.removeView(contentView);
		}
		return contentView;
	}
	
	private void initBaseView(){
		expandableListView = contentView.findViewById(R.id.expandableListView);
	}
	
	private void initList(){
		adapter = new TuikuanAdapter(getContext());
		expandableListView.setAdapter(adapter);
	}
}
