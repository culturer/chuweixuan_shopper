package com.culturer.guishi_shoper.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.culturer.guishi_shoper.R;
import com.culturer.guishi_shoper.utils.OssUtil;

public class TestActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		OssUtil.init(this);
		OssUtil.upload(
				new OSSProgressCallback<PutObjectRequest>() {
					@Override
					public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
					
					}
				},
				new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
					@Override
					public void onSuccess(PutObjectRequest request, PutObjectResult result) {
					
					}
			
					@Override
					public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
					
					}
		},
		"");
	}
}
