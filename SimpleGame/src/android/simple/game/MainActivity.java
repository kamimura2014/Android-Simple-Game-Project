package android.simple.game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private int count=0;//ゲーム開始カウント用
	private Dialog dialog;
	private ImageView countimage;
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
		dialog  = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		countimage = new ImageView(this);
	    dialog.setContentView(countimage);
		executor= Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new Runnable() {
			public void run() {
				count--;
				handler.post(new Runnable(){
					public void run(){
						dialog.setCancelable(false);
						switch(count){
							case 5:
								countimage.setImageResource(R.drawable.count_5);
								break;
							case 4:
								dialog.dismiss();
								countimage.setImageResource(R.drawable.count_4);
								break;

							case 3:
								dialog.dismiss();
								countimage.setImageResource(R.drawable.count_3);
								break;
							case 2:
								dialog.dismiss();
								countimage.setImageResource(R.drawable.count_2);
								break;
							case 1:
								dialog.dismiss();
								countimage.setImageResource(R.drawable.count_1);
								break;
							case 0:
								dialog.dismiss();
								countimage.setImageResource(R.drawable.count_finish);
								break;
						}
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
