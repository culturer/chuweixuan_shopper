package com.culturer.guishi_shoper.pages.goods;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.culturer.guishi_shoper.R;
import com.culturer.guishi_shoper.cache.BaseCache;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.culturer.guishi_shoper.BaseApplication.HOST;

public class AddGoodsActivity extends AppCompatActivity {
	
	private static final String TAG = "AddGoodsActivity";
	
	private ImageView icon;
	private Spinner type;
	private EditText name;
	private EditText desc;
	private EditText aprice;
	private EditText price;
	private EditText num;
	private Button ok;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_goods);
		init();
	}
	
	private void init(){
		initBaseView();
		initSpinner();
		initImg();
	}
	
	private void initBaseView(){
		icon = findViewById(R.id.icon);
		type = findViewById(R.id.type);
		name = findViewById(R.id.name);
		desc = findViewById(R.id.desc);
		aprice = findViewById(R.id.aprice);
		price = findViewById(R.id.price);
		num = findViewById(R.id.num);
		ok = findViewById(R.id.ok);
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				submit();
			}
		});
	}
	
	private void initImg(){
		icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Album.image(AddGoodsActivity.this) // 选择图片。
						.multipleChoice()
						.camera(true)
						.columnCount(3)
						.selectCount(1)
						.checkedList(new ArrayList<AlbumFile>())
						.filterSize(null)
						.filterMimeType(null)
						.afterFilterVisibility(false) // 显示被过滤掉的文件，但它们是不可用的。
						.onResult(new Action<ArrayList<AlbumFile>>() {
							@Override
							public void onAction(@NonNull ArrayList<AlbumFile> result) {
								Log.i("initIcon", "onAction: "+result.get(0).getPath());
								
							}
							
						})
						.onCancel(new Action<String>() {
							@Override
							public void onAction(@NonNull String result) {
							
							}
						})
						.start();
			}
		});
	}
	
	private void initSpinner(){
		String[] spinnerItems = {"烟酒","零食","饮料","水果"};
		
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,R.layout.storetype_select,spinnerItems);
		spinnerAdapter.setDropDownViewResource(R.layout.storetype_item);
		type.setAdapter(spinnerAdapter);
		type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				Log.i("initSpinner", "onItemSelected: "+spinnerItems[i]);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
			
			}
		});
 	}
	
	private void submit(){
		HttpParams params = new HttpParams();
		params.put("options","addGoods");
		params.put("Name",name.getText().toString());
		params.put("ProductTypeId","0");
		params.put("Desc",desc.getText().toString());
		params.put("APrice",aprice.getText().toString());
		params.put("Price",price.getText().toString());
		params.put("Num",num.getText().toString());
		params.put("Icon","/xasd");
		params.put("StoreId", BaseCache.storeBean.getId());
		
		HttpCallback callback = new HttpCallback() {
			@Override
			public void onSuccess(String t) {
				Log.i(TAG, "onSuccess: "+t);
				try {
					JSONObject jsonObject = new JSONObject(t);
					int status = jsonObject.getInt("status");
					if (status == 200 ){
						Toast.makeText(AddGoodsActivity.this,"新增商品成功！",Toast.LENGTH_LONG).show();
						finish();
					}else {
						Toast.makeText(AddGoodsActivity.this,"新增商品失败，请稍后再试！",Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFailure(VolleyError error) {
				Toast.makeText(AddGoodsActivity.this,"新增商品失败，请稍后再试！",Toast.LENGTH_LONG).show();
				Log.i(TAG, "onFailure: "+error.getMessage());
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
