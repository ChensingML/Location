package han.Chensing.Location;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.location.*;
import android.content.*;
import java.util.*;
import android.graphics.*;
import android.content.res.*;
import android.graphics.drawable.*;
import android.support.v4.widget.*;

public class MainActivity extends Activity 
{
	boolean menuopend=false;
	static LocationManager lm;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		ActionBar ab=getActionBar();
		ab.setCustomView(R.layout.action);
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayShowHomeEnabled(false);
		ab.show();
        setContentView(R.layout.main);

		lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		nor();
		

		DrawerLayout dl=(DrawerLayout)findViewById(R.id.maindl);
		dl.setDrawerListener(new DrawerLayout.DrawerListener(){
			
			ImageButton imab=(ImageButton)findViewById(R.id.actionimab);
			
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
			}
			@Override
			public void onDrawerOpened(View drawerView) {
				imab.setImageResource(R.drawable.ic_close_black_48dp);
				menuopend=true;
			}
			@Override
			public void onDrawerClosed(View drawerView) {
				imab.setImageResource(R.drawable.ic_menu_black_48dp);
				menuopend=false;
			}
				@Override
			public void onDrawerStateChanged(int newState) {}
			});
    }

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		lm.removeUpdates(ll);
	}
	
	
	public void hsis(View v){
		final View vi=LayoutInflater.from(this).inflate(R.layout.help,null);
		AlertDialog.Builder ab=new AlertDialog.Builder(this);
		ab.setView(vi);
		//sv.setScrollY(100);
		ab.show();
		new Handler().post(new Runnable(){
			@Override
			public void run(){
				ScrollView sv=(ScrollView)vi.findViewById(R.id.helpScrollView);
				sv.smoothScrollTo(0,710);
			}
		});
	}
	
	public void about(View v){
		AlertDialog.Builder ab=new AlertDialog.Builder(this);
		ab.setView(R.layout.about);
		ab.show();
	}
	
	public void help(View v){
		View vi=LayoutInflater.from(this).inflate(R.layout.help,null);
		AlertDialog.Builder ab=new AlertDialog.Builder(this);
		ab.setView(vi);
		ab.show();
	}
	
	public void gtno(View v){
		nor();
	}
	
	public void soc(View v){
		DrawerLayout dl=(DrawerLayout)findViewById(R.id.maindl);
		FrameLayout fl=(FrameLayout)findViewById(R.id.mainFrameLayout);
		if(!menuopend){
			dl.openDrawer(fl);
		}else{
			dl.closeDrawer(fl);
		}
	}
	
	public void ooco(View v){
		showpb();
		ToggleButton tb=(ToggleButton)findViewById(R.id.ooc);
		RadioButton gps=(RadioButton)findViewById(R.id.menuugps);
		EditText upd=(EditText)findViewById(R.id.menutime);
		CheckBox alwu=(CheckBox)findViewById(R.id.menuupdateat);
		if(tb.isChecked()){
			boolean isc;
			Location lo;
			if(gps.isChecked()){
				isc=lm.isProviderEnabled(lm.GPS_PROVIDER);
				lo=lm.getLastKnownLocation(lm.GPS_PROVIDER);
			}else{
				isc=lm.isProviderEnabled(lm.NETWORK_PROVIDER);
				lo=lm.getLastKnownLocation(lm.NETWORK_PROVIDER);
			}
			if(!isc){
				tb.setChecked(false);
				hidepb();
				Toast.makeText(this,"请打开定位服务，并检查定位方式",Toast.LENGTH_LONG).show();
				return;
			}
			if(lo!=null){
				chanlo(lo);
				hidepb();
			}
			
			int timeup;
			String gornet;
			
			if(alwu.isChecked()) timeup=0;
			else timeup=Integer.valueOf(upd.getText().toString());
			
			if(gps.isChecked()) gornet=lm.GPS_PROVIDER;
			else gornet=lm.NETWORK_PROVIDER;
				
			lm.requestLocationUpdates(gornet,timeup,0,ll);
		}else{
			lm.removeUpdates(ll);
			hidepb();
		}
	}
	
	
	public void bjd(View v){
		CheckBox alwu=(CheckBox)findViewById(R.id.menuupdateat);
		EditText upd=(EditText)findViewById(R.id.menutime);
		if(alwu.isChecked())
			upd.setEnabled(false);
		else
			upd.setEnabled(true);
	}
	
	
	
	private void chanlo(Location loc){
		if(loc!=null){
			ArrayList<String> al=new ArrayList<String>();
			double weidu=loc.getLatitude();
			double jindu=loc.getLongitude();
			if(weidu<=0){
				al.add(String.valueOf(weidu+"°S"));//纬度
			}else{
				al.add(String.valueOf(weidu+"°N"));
			}
			if(jindu<=0){
				al.add(String.valueOf(jindu+"°W"));//经度
			}else{
				al.add(String.valueOf(jindu+"°E"));
			}
			al.add("位置精确到 "+loc.getAccuracy()+" M");
			al.add("海拔："+loc.getAltitude()+" M");
			al.add("举止："+loc.getBearing()+"");
			al.add("速度："+loc.getSpeed()+" M/s");
			al.add("时间："+loc.getTime()+"");
			change(al);
		}
	}
	
	private void change(List<String> li){
		ListView lv=(ListView)findViewById(R.id.lv);
		ListAdapter la=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,li);
		lv.setAdapter(la);
	}

	private void showpb(){
		ProgressBar pb=(ProgressBar)findViewById(R.id.actionpb);
		ProgressBar pberr=(ProgressBar)findViewById(R.id.actionerrpb);
		TextView tv=(TextView)findViewById(R.id.actionche);
		pb.setVisibility(View.VISIBLE);
		pberr.setVisibility(View.GONE);
		tv.setVisibility(View.GONE);
	}

	private void showpbAsred(){
		ProgressBar pb=(ProgressBar)findViewById(R.id.actionpb);
		ProgressBar pberr=(ProgressBar)findViewById(R.id.actionerrpb);
		TextView tv=(TextView)findViewById(R.id.actionche);
		pb.setVisibility(View.GONE);
		pberr.setVisibility(View.VISIBLE);
		tv.setVisibility(View.VISIBLE);
	}
	
	private void hidepb(){
		ProgressBar pb=(ProgressBar)findViewById(R.id.actionpb);
		ProgressBar pberr=(ProgressBar)findViewById(R.id.actionerrpb);
		TextView tv=(TextView)findViewById(R.id.actionche);
		pb.setVisibility(View.GONE);
		pberr.setVisibility(View.GONE);
		tv.setVisibility(View.GONE);
	}
	
	private void nor(){
		RadioButton gps=(RadioButton)findViewById(R.id.menuugps);
		RadioButton netw=(RadioButton)findViewById(R.id.menuusenetwork);
		gps.setChecked(true);
		netw.setChecked(false);
		EditText time=(EditText)findViewById(R.id.menutime);
		time.setText("1000");
		time.setEnabled(true);
		CheckBox cb=(CheckBox)findViewById(R.id.menuupdateat);
		cb.setChecked(false);
	}
	
	private LocationListener ll=new LocationListener(){
		@Override
		public void onLocationChanged(Location lo){
			hidepb();
			chanlo(lo);
		}

		@Override
		public void onStatusChanged(String pro,int stat,Bundle ext){}

		@Override
		public void onProviderEnabled(String pro){
			hidepb();
			showpb();
		}

		@Override
		public void onProviderDisabled(String pro){
			showpbAsred();
		}
	};
}
