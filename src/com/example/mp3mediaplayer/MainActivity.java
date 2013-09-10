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
 * android��״̬:ǰ̨״̬ �ɼ�״̬ ����״̬ ��̨״̬ ��״̬
 * 
 */
public class MainActivity extends Activity implements OnClickListener
{

	Button btnStart, btnPause, btnStop;
	//����һ��Intent����  Ϊ����Service
	Intent service;
	//��ͣ��־λ
	Boolean flag = true;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnInit();
		//��ʼ��Intent����
		service = new Intent(MainActivity.this,MyServ.class);
	}

	//���ݲ�ͬ��Btnʵ�ֲ�ͬ�Ĺ���
	public void onClick(View v)
	{

		switch (v.getId())
		{
		case R.id.btnStart:
			service.putExtra("MCTAG", 1);
			startService(service);
			flag = true;
			btnPause.setText("��ͣ");
			break;

		case R.id.btnPause:
			if(flag){
				btnPause.setText("����");
				flag = false;
			}else {
				btnPause.setText("��ͣ");
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
	// btn��ʼ��
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