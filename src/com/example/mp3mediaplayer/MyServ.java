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
	// 创建一个MediaPlayer对象
	MediaPlayer mp;
	int potionsss ;
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		// 对创建的MediaPlayer对象进行初始化
		mp = new MediaPlayer();
		//获得一个电话管理器对象 
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); 
		//通过这个对象里面的listen进行对电话监听 new MyPhoneListener()是我们在5中定义的对象 
		telephonyManager.listen(new MyPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
	}
	private final class MyPhoneListener extends PhoneStateListener{ 
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {//state是电话的状态 
			case TelephonyManager.CALL_STATE_RINGING://来电 
				if(mp.isPlaying()) {//进行判断是否播放 要是的 获得当前播放的进度
					potionsss = mp.getCurrentPosition();//获得当前播放的进度 并赋给一个成员变量
					mp.stop(); 
					} 
				break;
				case TelephonyManager.CALL_STATE_IDLE://结束通话 
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

	// 播放
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
