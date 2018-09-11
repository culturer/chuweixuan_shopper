package com.culturer.guishi_shoper.pages.goods;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.culturer.guishi_shoper.R;
import com.culturer.guishi_shoper.bean.GoodsBean;
import com.culturer.guishi_shoper.cache.BaseCache;
import com.culturer.guishi_shoper.cache.GoodsCache;
import com.culturer.guishi_shoper.utils.PreferenceUtil;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import static com.culturer.guishi_shoper.BaseApplication.HOST;


public class GoodsFragment extends Fragment {
	
	private static final String TAG = "GoodsFragment" ;

	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	private String mParam1;
	private String mParam2;
	Gson gson = new Gson();
	
	private View conetntView;
	RefreshLayout refreshLayout;
	private ExpandableListView expandableListView;
	private GoodsAdapter adapter;

	public GoodsFragment() {
		// Required empty public constructor
	}
	
	public static GoodsFragment newInstance(String param1, String param2) {
		GoodsFragment fragment = new GoodsFragment();
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
	public View onCreateView(LayoutInflater inflater,
	                         ViewGroup container,
	                         Bundle savedInstanceState) {
		conetntView = inflater.inflate(R.layout.fragment_goods, container, false);
		initBaseView();
		initGoodsList();
		initHeader();
		initRefresh();
		return conetntView;
	}
	
	private void initBaseView(){
		refreshLayout = (RefreshLayout)conetntView.findViewById(R.id.refreshLayout);
		expandableListView = conetntView.findViewById(R.id.expandableListView);
	}
	
	private void initRefresh(){
		refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
				refreshLayout.finishLoadMore(1000);
			}
		});
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(@NonNull RefreshLayout refreshLayout) {
				HttpParams params = new HttpParams();
				params.put("options", "getGoods");
				params.put("storeId", BaseCache.storeBean.getId());
				params.put("size", 10);
				params.put("index",1);
				HttpCallback callback = new HttpCallback() {
					@Override
					public void onSuccess(String t) {
						refreshLayout.finishRefresh();
						Log.i("initGoodsList", "onSuccess: "+t);
						try {
							JSONObject jsonObject = new JSONObject(t);
							int status = jsonObject.getInt("status");
							if ( status == 200 ){
								GoodsCache.goodsBean = gson.fromJson(t,GoodsBean.class);
								PreferenceUtil.commitString("goodsBean",t);
								if (adapter!=null){
									adapter.update(GoodsCache.goodsBean);
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						refreshLayout.finishRefresh();
					}
					
					@Override
					public void onFailure(VolleyError error) {
						refreshLayout.finishRefresh();
						Log.i("initGoodsList", "onFailure: "+error.getMessage());
					}
				};
				new RxVolley.Builder()
						.url(HOST+"/client")
						.httpMethod(RxVolley.Method.POST) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
						.contentType(RxVolley.ContentType.FORM)//default FORM or JSON
						.params(params)
						.shouldCache(false) //default: get true, post false
						.callback(callback)
						.encoding("UTF-8") //default
						.doTask();
			}
		});
	}
	
	private void initGoodsList(){
		
		String strGoods = PreferenceUtil.getString("goodsBean","");
		if (strGoods!=""){
			GoodsCache.goodsBean = gson.fromJson(strGoods,GoodsBean.class);
			adapter = new GoodsAdapter(getContext(),GoodsCache.goodsBean);
			expandableListView.setAdapter(adapter);
		}
		
		expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
				Intent intent = new Intent(getContext(),GoodsActivity.class);
				intent.putExtra("productTypeIndex",i);
				intent.putExtra("productIndex",i1);
				startActivity(intent);
				return true;
			}
		});
		
		HttpParams params = new HttpParams();
		params.put("options", "getGoods");
		params.put("storeId", BaseCache.storeBean.getId());
		params.put("size", 10);
		params.put("index",1);
		HttpCallback callback = new HttpCallback() {
			@Override
			public void onSuccess(String t) {
				Log.i("initGoodsList", "onSuccess: "+t);
				try {
					JSONObject jsonObject = new JSONObject(t);
					int status = jsonObject.getInt("status");
					if ( status == 200 ){
						GoodsCache.goodsBean = gson.fromJson(t,GoodsBean.class);
						PreferenceUtil.commitString("goodsBean",t);
						if (adapter!=null){
							adapter.update(GoodsCache.goodsBean);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(VolleyError error) {
				Log.i("initGoodsList", "onFailure: "+error.getMessage());
			}
		};
		new RxVolley.Builder()
				.url(HOST+"/client")
				.httpMethod(RxVolley.Method.POST) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
				.contentType(RxVolley.ContentType.FORM)//default FORM or JSON
				.params(params)
				.shouldCache(false) //default: get true, post false
				.callback(callback)
				.encoding("UTF-8") //default
				.doTask();
		
	}
	
	private void initHeader(){
		View headerView = LayoutInflater.from(getContext()).inflate(R.layout.goods_header,null);
		expandableListView.addHeaderView(headerView);
		Button addType = headerView.findViewById(R.id.addType);
		Button addGoods = headerView.findViewById(R.id.addGoods);
		addType.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				View contentView = LayoutInflater.from(getContext()).inflate(R.layout.goods_addtype,null);
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				final AlertDialog dialog = builder.setTitle("添加分类")
						.setView(contentView)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								EditText goodsType = contentView.findViewById(R.id.goodsType);
								addGoodsType(goodsType.getText().toString());
							}
						})
						.create();
				dialog.show();
			}
		});
		addGoods.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getContext(),AddGoodsActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void addGoodsType(String goodsType){
		HttpParams params = new HttpParams();
		params.put("options","addProductType");
		params.put("Name",goodsType);
		params.put("StoreId",BaseCache.storeBean.getId());
		HttpCallback callback = new HttpCallback() {
			@Override
			public void onSuccess(String t) {
				Log.i(TAG, "onSuccess: "+t);
				try {
					JSONObject jsonObject = new JSONObject(t);
					int status = jsonObject.getInt("status");
					if (status == 200 ){
						Toast.makeText(getContext(),"添加商品分类成功！",Toast.LENGTH_LONG).show();
						HttpParams params = new HttpParams();
						params.put("options", "getGoods");
						params.put("storeId", BaseCache.storeBean.getId());
						params.put("size", 10);
						params.put("index",1);
						HttpCallback callback = new HttpCallback() {
							@Override
							public void onSuccess(String t) {
								refreshLayout.finishRefresh();
								Log.i("initGoodsList", "onSuccess: "+t);
								try {
									JSONObject jsonObject = new JSONObject(t);
									int status = jsonObject.getInt("status");
									if ( status == 200 ){
										GoodsCache.goodsBean = gson.fromJson(t,GoodsBean.class);
										PreferenceUtil.commitString("goodsBean",t);
										if (adapter!=null){
											adapter.update(GoodsCache.goodsBean);
										}
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
								refreshLayout.finishRefresh();
							}
							
							@Override
							public void onFailure(VolleyError error) {
								refreshLayout.finishRefresh();
								Log.i("initGoodsList", "onFailure: "+error.getMessage());
							}
						};
						new RxVolley.Builder()
								.url(HOST+"/client")
								.httpMethod(RxVolley.Method.POST) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
								.contentType(RxVolley.ContentType.FORM)//default FORM or JSON
								.params(params)
								.shouldCache(false) //default: get true, post false
								.callback(callback)
								.encoding("UTF-8") //default
								.doTask();
					}else {
						Toast.makeText(getContext(),"添加商品分类失败，请稍后再试！",Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(VolleyError error) {
				Log.i(TAG, "onFailure: "+error.getMessage());
				Toast.makeText(getContext(),"添加商品分类失败，请稍后再试！",Toast.LENGTH_LONG).show();
			}
		};
		new RxVolley.Builder()
				.url(HOST+"/shopper")
				.httpMethod(RxVolley.Method.POST) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
				.contentType(RxVolley.ContentType.FORM)//default FORM or JSON
				.params(params)
				.shouldCache(false) //default: get true, post false
				.callback(callback)
				.encoding("UTF-8") //default
				.doTask();
		
	}
}
