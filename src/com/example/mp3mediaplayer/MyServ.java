package com.example.mp3mediaplayer;

import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class MyServ extends Service
{
	// ����һ��MediaPlayer����
	MediaPlayer mp;
	int potionsss ;
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		// �Դ�����MediaPlayer������г�ʼ��
		mp = new MediaPlayer();
		//���һ���绰���������� 
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); 
		//ͨ��������������listen���жԵ绰���� new MyPhoneListener()��������5�ж���Ķ��� 
		telephonyManager.listen(new MyPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
	}
	private final class MyPhoneListener extends PhoneStateListener{ 
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {//state�ǵ绰��״̬ 
			case TelephonyManager.CALL_STATE_RINGING://���� 
				if(mp.isPlaying()) {//�����ж��Ƿ񲥷� Ҫ�ǵ� ��õ�ǰ���ŵĽ���
					potionsss = mp.getCurrentPosition();//��õ�ǰ���ŵĽ��� ������һ����Ա����
					mp.stop(); 
					} 
				break;
				case TelephonyManager.CALL_STATE_IDLE://����ͨ�� 
						if(potionsss>0 ){ 
							mpStart(potionsss); 
							potionsss = 0; 
							}
						break; 
			} 
		}
	}

	public void onStart(Intent intent, int startId)
	{
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		int tag = intent.getIntExtra("MCTAG", 0);
		switch (tag)
		{
		case 1:
			
			mpStart(potionsss);
			break;

		case 2:
			mp.stop();
			break;
		case 3:
			if (mp.isPlaying())
			{
				mp.pause();
			} else
			{
				mp.start();
			}

			break;
		}
	}

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		mp.release();
	}

	// ����
	public void mpStart(int potionsss)
	{
		try
		{
			mp.reset();
			mp.setDataSource("/mnt/sdcard/1.mp3");
			mp.prepare();
			mp.setOnPreparedListener(new PrepareListenerDemo(potionsss));
			mp.start();
		} catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class PrepareListenerDemo implements OnPreparedListener
	{
		private int potion;

		public PrepareListenerDemo(int potionsss)
		{
			potion = potionsss;
		}

		@Override
		public void onPrepared(MediaPlayer mp)
		{
			mp.start();
			if (potion > 0)
			{
				mp.seekTo(potion);
			}
			
		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
	

}
