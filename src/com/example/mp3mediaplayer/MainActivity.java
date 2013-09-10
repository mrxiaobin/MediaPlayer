package com.example.mp3mediaplayer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * android的状态:前台状态 可见状态 服务状态 后台状态 空状态
 * 
 */
public class MainActivity extends Activity implements OnClickListener
{

	Button btnStart, btnPause, btnStop;
	//定义一个Intent对象  为启动Service
	Intent service;
	//暂停标志位
	Boolean flag = true;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnInit();
		//初始化Intent对象
		service = new Intent(MainActivity.this,MyServ.class);
	}

	//根据不同的Btn实现不同的功能
	public void onClick(View v)
	{

		switch (v.getId())
		{
		case R.id.btnStart:
			service.putExtra("MCTAG", 1);
			startService(service);
			flag = true;
			btnPause.setText("暂停");
			break;

		case R.id.btnPause:
			if(flag){
				btnPause.setText("继续");
				flag = false;
			}else {
				btnPause.setText("暂停");
				flag = true;
			}
			service.putExtra("MCTAG", 3);
			startService(service);
			break;
		case R.id.btnStop:
			service.putExtra("MCTAG", 2);
			startService(service);
			stopService(service);
			break;
		}
	}
	// btn初始化
	public void btnInit()
	{
		btnStart = (Button) findViewById(R.id.btnStart);
		btnPause = (Button) findViewById(R.id.btnPause);
		btnStop = (Button) findViewById(R.id.btnStop);

		btnStart.setOnClickListener(this);
		btnPause.setOnClickListener(this);
		btnStop.setOnClickListener(this);
	}

}