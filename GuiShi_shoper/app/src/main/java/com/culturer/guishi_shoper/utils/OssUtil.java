package com.culturer.guishi_shoper.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.culturer.guishi_shoper.BaseApplication;
import com.vondear.rxtools.RxEncodeTool;

import java.io.IOException;
import java.io.InputStream;

import static com.culturer.guishi_shoper.utils.Encrypt.HmacSHA1Encrypt;

/**
 * Created by Administrator on 2018/7/27 0027.
 */

public class OssUtil {
	
	public static final String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
	// 在移动端建议使用STS方式初始化OSSClient。
	private static OSSCredentialProvider credentialProvider;
	//该配置类如果不设置，会有默认配置，具体可看该类
	private static ClientConfiguration conf;
	private static OSS oss;
	
	public static void init(Context context){
		credentialProvider = new OSSStsTokenCredentialProvider(
				"<StsToken.AccessKeyId>",
				"<StsToken.SecretKeyId>",
				"<StsToken.SecurityToken>"
		);
		conf = new ClientConfiguration();
		//该配置类如果不设置，会有默认配置，具体可看该类
		conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
		conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
		conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
		conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
		oss = new OSSClient(context, endpoint, credentialProvider);
		String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
		credentialProvider = new OSSCustomSignerCredentialProvider() {
			@Override
			public String signContent(String content) {
				// 您需要在这里依照OSS规定的签名算法，实现加签一串字符内容，并把得到的签名传拼接上AccessKeyId后返回
				// 一般实现是，将字符内容post到您的业务服务器，然后返回签名
				// 如果因为某种原因加签失败，描述error信息后，返回nil
				// 以下是用本地算法进行的演示
//				return "OSS " + "LTAIlFW9Sddoh5pV" + ":" + base64(hmac-sha1("Fa391zESQZu5GyXo4tEx1sS5kDm9rT", content));
				try {
					return "OSS " + "LTAIlFW9Sddoh5pV" + ":" + RxEncodeTool.base64Encode2String(HmacSHA1Encrypt("Fa391zESQZu5GyXo4tEx1sS5kDm9rT", content));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "";
			}
		};
		oss = new OSSClient(context, endpoint, credentialProvider);
	}
	
	
	public static void upload(
			OSSProgressCallback<PutObjectRequest> progressCallback,
			OSSCompletedCallback<PutObjectRequest, PutObjectResult> completedCallback,
			String filePath
	){
		//构造上传请求
		PutObjectRequest put = new PutObjectRequest(
				"guishi-photos",
				"",
				filePath
		);
		// 异步上传时可以设置进度回调
		put.setProgressCallback(progressCallback);
		OSSAsyncTask task = oss.asyncPutObject(put,completedCallback);
		// task.cancel(); // 可以取消任务
		 task.waitUntilFinished(); // 可以等待直到任务完成
	}
	
}
