package han.Chensing.Location;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;

public class Welcome extends Activity
{
	
	boolean isJumped=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		setContentView(R.layout.welcome);
		Handler han=new Handler();
		han.postDelayed(new Runnable(){
			@Override
			public void run(){
				ju();
			}
		},2000);
	}
	
	public void jump(View v){
		ju();
	}
	
	public void ju(){
		if(!isJumped){
			isJumped=true;
			Intent in=new Intent(Welcome.this,MainActivity.class);
			startActivity(in);
			Welcome.this.finish();
		}
	}
}
