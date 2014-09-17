package android.simple.game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;


public class MainActivity extends Activity {
	private int count=0;//ゲーム開始カウント用
	private Dialog dialog;
	private ScheduledExecutorService executor;
	private Handler handler=new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		countdown();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	//カウントダウン
	public void countdown(){
		count=6;
		dialog = new Dialog(this);
		executor= Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new Runnable() {
			public void run() {
				count--;
				handler.post(new Runnable(){
					public void run(){
						dialog.setCancelable(false);
						if(count!=5)
							dialog.dismiss();
						if(count!=0)
							dialog.setTitle(""+count);
						else
							dialog.setTitle("START!!");
							dialog.show();
					}
				});
				if(count==-1){
					dialog.dismiss();
					executor.shutdown();
				}
			}
		},0,1,TimeUnit.SECONDS);
	}
}
