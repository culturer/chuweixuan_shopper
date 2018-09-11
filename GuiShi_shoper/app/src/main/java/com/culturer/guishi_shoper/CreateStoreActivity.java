package com.culturer.guishi_shoper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.culturer.guishi_shoper.BaseApplication.HOST;

public class CreateStoreActivity extends AppCompatActivity implements AMapLocationListener {
	
	private EditText name;
	private Spinner type;
	private EditText desc;
	private EditText desc1;
	private TextView tab1;
	private TextView tab2;
	private TextView tab3;
	private EditText address;
	private Button position;
	private RadioButton stop;
	private EditText stop_msg;
	private EditText open_time;
	private EditText tel;
	private Button ok;
	
	//声明mlocationClient对象
	public AMapLocationClient mlocationClient;
	//声明mLocationOption对象
	public AMapLocationClientOption mLocationOption = null;
	
	private String latitude = "";
	private String longitude = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_store);
		init();
		changeTopBackground();
	}
	
	private void init(){
		initBaseView();
	}
	
	private void initBaseView(){
		name = findViewById(R.id.name);
		type = findViewById(R.id.type);
		desc = findViewById(R.id.desc);
		desc1 = findViewById(R.id.desc1);
		tab1 = findViewById(R.id.tab1);
		tab2 = findViewById(R.id.tab2);
		tab3 = findViewById(R.id.tab3);
		address = findViewById(R.id.address);
		position = findViewById(R.id.position);
		stop = findViewById(R.id.stop);
		stop_msg = findViewById(R.id.stop_msg);
		open_time = findViewById(R.id.open_time);
		tel = findViewById(R.id.tel);
		ok = findViewById(R.id.ok);
		initSpinner();
		initTab();
		position.setOnClickListener(view -> location());
		ok.setOnClickListener(view -> submit());
	}
	
	private void initSpinner(){
		String[] spinnerItems = {"便利店","小吃店","五金店","生鲜店"};
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,R.layout.storetype_select,spinnerItems);
		spinnerAdapter.setDropDownViewResource(R.layout.storetype_item);
		type.setAdapter(spinnerAdapter);
	}
	
	private void submit(){
		HttpParams params = new HttpParams();
		params.put("options","createStore");
		params.put("Name",name.getText().toString());
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
		params.put("Position","{ latitude:"+latitude+",longitude:"+longitude+"}");
		params.put("UserId", BaseCache.user.getId());
		params.put("IsClose",0);
		params.put("CloseMsg",stop_msg.getText().toString());
		params.put("OpenTime",open_time.getText().toString());
		params.put("IsLock",0);
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
						Toast.makeText(CreateStoreActivity.this,"注册店铺成功！",Toast.LENGTH_LONG).show();
						CreateStoreActivity.this.finish();
					}else {
						String msg = jsonObject.getString("msg");
						Toast.makeText(CreateStoreActivity.this,msg,Toast.LENGTH_LONG).show();
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
	
	private void initTab(){
		tab1.setOnClickListener(view -> {
			View contentView = LayoutInflater.from(CreateStoreActivity.this).inflate(R.layout.change_tab,null);
			AlertDialog.Builder builder = new AlertDialog.Builder(CreateStoreActivity.this);
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
			View contentView = LayoutInflater.from(CreateStoreActivity.this).inflate(R.layout.change_tab,null);
			AlertDialog.Builder builder = new AlertDialog.Builder(CreateStoreActivity.this);
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
			View contentView = LayoutInflater.from(CreateStoreActivity.this).inflate(R.layout.change_tab,null);
			AlertDialog.Builder builder = new AlertDialog.Builder(CreateStoreActivity.this);
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
	
	private void location(){
		mlocationClient = new AMapLocationClient(this);
//初始化定位参数
		mLocationOption = new AMapLocationClientOption();
		//设置定位监听
		mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
		mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
		mLocationOption.setInterval(2000);
//设置定位参数
		mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
		mlocationClient.startLocation();
	}
	
	//修改顶部状态栏颜色
	private void changeTopBackground(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
//设置修改状态栏
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//设置状态栏的颜色，和你的app主题或者标题栏颜色设置一致就ok了
			window.setStatusBarColor(getColor(R.color.black));
		}
	}
	
	@Override
	public void onLocationChanged(AMapLocation aMapLocation) {
		if (aMapLocation != null) {
			if (aMapLocation.getErrorCode() == 0) {
				//定位成功回调信息，设置相关消息
				aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
				latitude = "" + aMapLocation.getLatitude();//获取纬度
				longitude = "" + aMapLocation.getLongitude();//获取经度
				aMapLocation.getAccuracy();//获取精度信息
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(aMapLocation.getTime());
				df.format(date);//定位时间
				mlocationClient.disableBackgroundLocation(true);
				Log.i("location", "latitude: "+latitude);
				Log.i("location", "longitude: "+longitude);
			} else {
				//显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
				Log.e("AmapError","location Error, ErrCode:"
						+ aMapLocation.getErrorCode() + ", errInfo:"
						+ aMapLocation.getErrorInfo());
			}
		}
	}
}
