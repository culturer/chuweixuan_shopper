package com.culturer.guishi_shoper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.culturer.guishi_shoper.pages.advertise.AdvertiseFragment;
import com.culturer.guishi_shoper.pages.goods.GoodsFragment;
import com.culturer.guishi_shoper.pages.order.OrderFragment;
import com.culturer.guishi_shoper.pages.sale_after.SaleAfterFragment;
import com.culturer.guishi_shoper.pages.send.SendFragment;
import com.culturer.guishi_shoper.wedgit.bottom_navigation.BottomNavigationViewHelper;
import com.culturer.guishi_shoper.wedgit.bottom_navigation.MyPagerAdapter;
import com.culturer.guishi_shoper.wedgit.bottom_navigation.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	
	//Android6.0以上动态权限
	//定位需要的权限
	protected String[] needPermissions = {
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.READ_PHONE_STATE,
	};
	private static final int PERMISSON_REQUESTCODE = 0;
	
	private TextView mTextMessage;
	private MyViewPager pager;
	private BottomNavigationView navigation;
	private MenuItem menuItem;
	private MyPagerAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//授权
		checkPermissions(needPermissions);
		//初始化UI
		initBaseView();
		//修改状态栏颜色
		changeTopBackground();
		//初始化页面
		changePager();
		//修改底部导航栏
		changeBottomNavigation();
		
	}
	
	/**
	 * 检查定位权限
	 *
	 * @param needPermissions
	 */
	private void checkPermissions(String[] needPermissions) {
		List<String> needRequestPermissionList = findDeniedPermission(needPermissions);
		if (needRequestPermissionList != null && needRequestPermissionList.size() > 0) {
			ActivityCompat.requestPermissions(this,
					needRequestPermissionList.toArray(new String[needRequestPermissionList.size()]),
					PERMISSON_REQUESTCODE);
		}
	}
	/**
	 * 获取集中需要申请权限的列表
	 *
	 * @param needPermissions
	 * @return
	 */
	private List<String> findDeniedPermission(String[] needPermissions) {
		List<String> needRequestPermissionList = new ArrayList<String>();
		for (String permisson : needPermissions) {
			if (ContextCompat.checkSelfPermission(this, permisson) != PackageManager.PERMISSION_GRANTED
					|| ActivityCompat.shouldShowRequestPermissionRationale(this, permisson)) {
				needRequestPermissionList.add(permisson);
			}
		}
		return needRequestPermissionList;
	}
	
	private void initBaseView(){
		pager = findViewById(R.id.pager);
		mTextMessage = (TextView) findViewById(R.id.message);
		navigation = (BottomNavigationView) findViewById(R.id.navigation);
		
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
	
	//底部导航栏
	private void changeBottomNavigation(){
		BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				switch (item.getItemId()) {
					case R.id.navigation_home:
						mTextMessage.setText(R.string.title_home);
						pager.setCurrentItem(0);
						return true;
					case R.id.navigation_dashboard:
						mTextMessage.setText(R.string.title_dashboard);
						pager.setCurrentItem(1);
						return true;
					case R.id.navigation_notifications:
						mTextMessage.setText(R.string.title_notifications);
						pager.setCurrentItem(2);
						return true;
					case R.id.navigation_saleafter:
						mTextMessage.setText(R.string.title_saleafter);
						pager.setCurrentItem(3);
						return true;
					case R.id.navigation_send:
						mTextMessage.setText(R.string.title_send);
						pager.setCurrentItem(4);
						return true;
				}
				return false;
			}
		};
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		//默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
		BottomNavigationViewHelper.disableShiftMode(navigation);
	}
	
	//修改ViewPager
	private void changePager(){
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(GoodsFragment.newInstance("",""));
		adapter.addFragment(OrderFragment.newInstance("",""));
		adapter.addFragment(AdvertiseFragment.newInstance("",""));
		adapter.addFragment(SaleAfterFragment.newInstance("",""));
		adapter.addFragment(SendFragment.newInstance("",""));
		ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			
			}
			
			@Override
			public void onPageSelected(int position) {
				if (menuItem != null) {
					menuItem.setChecked(false);
				} else {
					navigation.getMenu().getItem(0).setChecked(false);
				}
				menuItem = navigation.getMenu().getItem(position);
				menuItem.setChecked(true);
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
			
			}
		};
		pager.addOnPageChangeListener(pageChangeListener);
		pager.setOffscreenPageLimit(5);
		pager.setAdapter(adapter);
	}
}
