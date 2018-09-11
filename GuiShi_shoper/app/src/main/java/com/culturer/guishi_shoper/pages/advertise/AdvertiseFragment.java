package com.culturer.guishi_shoper.pages.advertise;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.culturer.guishi_shoper.CreateStoreActivity;
import com.culturer.guishi_shoper.R;
import com.culturer.guishi_shoper.bean.StoreBean;
import com.culturer.guishi_shoper.bean.TabBean;
import com.culturer.guishi_shoper.cache.BaseCache;
import com.culturer.guishi_shoper.utils.PreferenceUtil;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.culturer.guishi_shoper.BaseApplication.HOST;

public class AdvertiseFragment extends Fragment {
	
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	private String mParam1;
	private String mParam2;
	
	private View contentView;
	private TextView name;
	private EditText desc;
	private EditText desc1;
	private TextView tab1;
	private TextView tab2;
	private TextView tab3;
	private TextView address;
	private RadioButton stop;
	private TextView stop_msg;
	private TextView open_time;
	private TextView tel;
	private Button ok;
	
	public AdvertiseFragment() {
		// Required empty public constructor
	}
	
	public static AdvertiseFragment newInstance(String param1, String param2) {
		AdvertiseFragment fragment = new AdvertiseFragment();
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
		contentView = inflater.inflate(R.layout.fragment_advertise, container, false);
		init();
		return contentView;
	}
	
	private void init(){
		initBaseView();
		initTab();
	}
	
	private void initBaseView(){
		name = contentView.findViewById(R.id.name);
		desc = contentView.findViewById(R.id.desc);
		desc1 = contentView.findViewById(R.id.desc1);
		tab1 = contentView.findViewById(R.id.tab1);
		tab2 = contentView.findViewById(R.id.tab2);
		tab3 = contentView.findViewById(R.id.tab3);
		address = contentView.findViewById(R.id.address);
		stop = contentView.findViewById(R.id.stop);
		stop_msg = contentView.findViewById(R.id.stop_msg);
		open_time = contentView.findViewById(R.id.open_time);
		tel = contentView.findViewById(R.id.tel);
		ok = contentView.findViewById(R.id.ok);
		
		name.setText(BaseCache.storeBean.getName());
		desc.setText(BaseCache.storeBean.getDesc());
		desc1.setText(BaseCache.storeBean.getContent());
		Gson gson = new Gson();
		TabBean tabBean = gson.fromJson(BaseCache.storeBean.getTab(),TabBean.class);
		if ( tabBean!=null && tabBean.getTabs()!=null){
			tab1.setText(tabBean.getTabs().get(0));
			tab2.setText(tabBean.getTabs().get(1));
			tab3.setText(tabBean.getTabs().get(2));
		}
		address.setText(BaseCache.storeBean.getAddress());
		stop.setChecked(BaseCache.storeBean.isIsClose());
		stop_msg.setText(BaseCache.storeBean.getCloseMsg());
		open_time.setText("营业时间："+BaseCache.storeBean.getOpenTime());
		tel.setText("联系电话："+BaseCache.storeBean.getTel());
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				submit();
			}
		});
	}
	private void initTab(){
		tab1.setOnClickListener(view -> {
			View contentView = LayoutInflater.from(getContext()).inflate(R.layout.change_tab,null);
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			final AlertDialog dialog = builder.setTitle("修改标签一")
					.setView(contentView)
					.setPositiveButton("确定", (dialogInterface, i) -> {
						TextView tab_msg = contentView.findViewById(R.id.tab_msg);
						tab1.setText(tab_msg.getText());
					})
					.create();
			dialog.show();
		});
		tab2.setOnClickListener(view -> {
			View contentView = LayoutInflater.from(getContext()).inflate(R.layout.change_tab,null);
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			final AlertDialog dialog = builder.setTitle("修改标签二")
					.setView(contentView)
					.setPositiveButton("确定", (dialogInterface, i) -> {
						TextView tab_msg = contentView.findViewById(R.id.tab_msg);
						tab2.setText(tab_msg.getText());
					})
					.create();
			dialog.show();
		});
		tab3.setOnClickListener(view -> {
			View contentView = LayoutInflater.from(getContext()).inflate(R.layout.change_tab,null);
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			final AlertDialog dialog = builder.setTitle("修改标签三")
					.setView(contentView)
					.setPositiveButton("确定", (dialogInterface, i) -> {
						TextView tab_msg = contentView.findViewById(R.id.tab_msg);
						tab3.setText(tab_msg.getText());
					})
					.create();
			dialog.show();
		});
	}
	
	private void submit(){
		HttpParams params = new HttpParams();
		params.put("options","updateStore");
		params.put("Name",name.getText().toString());
		params.put("Icon",BaseCache.storeBean.getIcon());
		params.put("Id",BaseCache.storeBean.getId());
		params.put("Tel",tel.getText().toString());
		params.put("Desc",desc.getText().toString());
		params.put("Content",desc1.getText().toString());
		TabBean tabBean = new TabBean();
		List<String> tabs = new ArrayList<>();
		tabs.add(tab1.getText().toString());
		tabs.add(tab2.getText().toString());
		tabs.add(tab3.getText().toString());
		tabBean.setTabs(tabs);
		Gson gson = new Gson();
		String strTab = gson.toJson(tabBean);
		params.put("Tab", strTab);
		params.put("Address",address.getText().toString());
		params.put("Position", BaseCache.storeBean.getPosition());
		params.put("UserId", BaseCache.storeBean.getUserId());
		if (stop.isChecked()){
			params.put("IsClose",1);
		}
		params.put("CloseMsg",stop_msg.getText().toString());
		params.put("OpenTime",open_time.getText().toString());
		params.put("IsLock",0);
		params.put("AddTime",BaseCache.storeBean.getAddTime());
		params.put("StoreTypeId",BaseCache.storeBean.getStoreTypeId());
		params.put("CommunityId",BaseCache.storeBean.getCommunityId());
		HttpCallback callback = new HttpCallback() {
			@Override
			public void onSuccess(String t) {
				Log.i("submit", "onSuccess: "+t);
				try {
					JSONObject jsonObject = new JSONObject(t);
					int status = jsonObject.getInt("status");
					if (status == 200){
						String strStore = jsonObject.getString("store");
						PreferenceUtil.commitString("store",strStore);
						Gson gson = new Gson();
						BaseCache.storeBean = gson.fromJson(strStore, StoreBean.class);
						Toast.makeText(getContext(),"修改店铺信息成功！",Toast.LENGTH_LONG).show();
					}else {
						String msg = jsonObject.getString("msg");
						Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFailure(VolleyError error) {
				Log.i("submit", "onFailure: "+error.getMessage());
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
