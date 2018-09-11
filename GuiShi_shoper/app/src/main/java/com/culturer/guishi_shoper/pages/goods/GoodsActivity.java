package com.culturer.guishi_shoper.pages.goods;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.android.phone.mrpc.core.ThreadUtil;
import com.culturer.guishi_shoper.R;
import com.culturer.guishi_shoper.bean.ProductsBean;
import com.culturer.guishi_shoper.cache.GoodsCache;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class GoodsActivity extends AppCompatActivity {
	
	private static final String TAG = "GoodsActivity";
	
	private ImageView icon;
	private TextView name;
	private TextView productType;
	private TextView aPrice;
	private TextView price;
	private TextView num;
	private TextView sellNum;
	private TextView desc;
	private Button ok;
	
	private int productTypeIndex;
	private int productIndex;
	private ProductsBean productsBean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods);
		changeTopBackground();
		init();
	}
	
	private void init(){
		productTypeIndex = getIntent().getIntExtra("productTypeIndex",0);
		productIndex = getIntent().getIntExtra("productIndex",0);
		Log.i(TAG, "productTypeIndex: " + productTypeIndex );
		Log.i(TAG, "productIndex: " + productIndex );
		productsBean = GoodsCache.goodsBean.getDatas().get(productTypeIndex).getProducts().get(productIndex);
		initBaseView();
	}
	
	private void initBaseView(){
		icon = findViewById(R.id.icon);
		name = findViewById(R.id.name);
		productType = findViewById(R.id.productType);
		aPrice = findViewById(R.id.aPrice);
		price = findViewById(R.id.price);
		num = findViewById(R.id.num);
		sellNum = findViewById(R.id.sellNum);
		desc = findViewById(R.id.desc);
		ok = findViewById(R.id.ok);
		initIcon();
		name.setText("商品名称："+productsBean.getName());
		productType.setText("商品分类："+productsBean.getProductTypeId());
		aPrice.setText("进货价：￥"+productsBean.getAPrice());
		price.setText("出售价：￥"+productsBean.getPrice());
		num.setText("库存："+productsBean.getNum());
		sellNum.setText("销量："+productsBean.getSellNum());
		desc.setText(""+productsBean.getDesc());
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			
			}
		});
	}
	
	private void initIcon(){
		icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Album.image(GoodsActivity.this) // 选择图片。
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
}
