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
import android.util.Log;
import android.widget.LinearLayout;

public class MyService extends Service {
	private static final String TAG = "MyService";
	MediaPlayer mp;
	boolean isPause;
	 private int position;
	 String stringExtra ;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG, "onCreate....");
		mp = new MediaPlayer();
		
		 
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        telephonyManager.listen(new MyPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
        
        TelephonyManager telephonyMana = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyMana.listen(new PhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Log.i(TAG, "onStart....");

		int msg = intent.getIntExtra("MSG", 0);
		stringExtra = intent.getStringExtra("etmp3");
		switch (msg) {
		case 1:
			play(position);
			
			break;
		case 2:
			if(mp.isPlaying()){
				mp.pause();
				isPause = true;
			}else{
				if(isPause){
					mp.start();
				}
			}
			break;
		case 3:
			if(mp.isPlaying()){
				mp.stop();
			}
			break;
		}//Ctrl+shift+f
	}

	private void play(int position) {
		try {
			mp.reset();
			mp.setDataSource("/mnt/sdcard/"+stringExtra);
			mp.prepare();
			mp.setOnPreparedListener(new PrepareListener(position));
			isPause = false;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "onDestroy....");
		mp.release();
	}

	
	private final class mh extends PhoneStateListener{

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
		}
		
	}
	
	private final class MyPhoneListener extends PhoneStateListener{
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING://来电
				if(mp.isPlaying()) {
					position = mp.getCurrentPosition();
					mp.stop();
				}
				break;

			case TelephonyManager.CALL_STATE_IDLE:
				if(position>0 ){
					play(position);
					position = 0;
				}
				break;
			}
		}
    }
	private final class PrepareListener implements OnPreparedListener{
		private int position;
		public PrepareListener(int position) {
			this.position = position;
		}

		public void onPrepared(MediaPlayer mp) {
			
			mp.start();//开始播放
			if(position>0) mp.seekTo(position);
		}
	}
}
