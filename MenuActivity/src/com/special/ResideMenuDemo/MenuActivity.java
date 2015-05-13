package com.special.ResideMenuDemo;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.text.AlteredCharSequence;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.LogUtil.log;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{

    public ResideMenu resideMenu;
    private MenuActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemMypackage;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemSignOut;
    private ResideMenuItem itemAnalysis;
    private ResideMenuItem itemSkin;
    private ResideMenuItem itemShare;
    
    String mTitleStr="haha title:";  
    int mCount= 0;  
    /**
     * Called when the activity is first created.
     */
    public PendingIntent getDefalutIntent(int flags){  
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);  
        return pendingIntent;  
    }  
    void mySleep(long sec)  
    {  
        try{  
            Thread.sleep(sec);  
        }  
        catch(InterruptedException e)  
        {  
            e.printStackTrace();  
        }  
    }  
      
    void myAction()  
    {  
    	AvosDatabase avosDatabase=new AvosDatabase();
    	int num1=Packages.Num1;
    	int num2=Packages.Num2;
        avosDatabase.countDatabase(1);
        avosDatabase.countDatabase(2);
        Toast.makeText(this, String.valueOf(num1), Toast.LENGTH_SHORT).show();
        if(num1!=Packages.Num1){
        	NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        	int icon = R.drawable.icon;
        	CharSequence tickerText = "1KM";
        	long when = System.currentTimeMillis();
            Notification notification = new Notification(icon, tickerText, when);
        	NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        	//log.e("hahah",String.valueOf(num1));
        	Context context = getApplicationContext();
        	CharSequence contentTitle = "你有一个新包裹啦";
        	CharSequence contentText = "点我查看详细内容";
        	Intent notificationIntent = new Intent(this, MenuActivity.class);
        	PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);
        	notification.setLatestEventInfo(context, contentTitle, contentText,contentIntent);
        	notification.defaults|=Notification.DEFAULT_ALL;
        	 nm.notify(1, notification);
        	avosDatabase.getDatabase(1);
        }
        if(num2!=Packages.Num2){
        	avosDatabase.getDatabase(2);
        }
        
    }  
    Handler  hd = new Handler (){  
        public void handleMessage (Message msg)  
        {  
            super.handleMessage(msg);     
                myAction();                      
                //mySleep(10000);  
               // Message m= new Message();   
               // hd.sendMessage(m);   
              
        }  
    };  
    Thread th = new Thread(){  
        public  void run ()  
        {  
            Message m= new Message();
            while(true){
            mySleep(10000);   
            hd.sendMessage(m);  
            }
        }  
    };  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new HomeFragment());
        long mpid=Thread.currentThread().getId();  
        th.start();  
        
        AvosDatabase avosDatabase=new AvosDatabase();
        avosDatabase.getDatabase(1);
        avosDatabase.getDatabase(2);
        
        SharedPreferences sharedPreferences=getSharedPreferences("Package", Context.MODE_PRIVATE);
        int num1=sharedPreferences.getInt("NUM1", 0);
        avosDatabase.countDatabase(1);
        
        if(Packages.Num1!=num1){
        	
        	NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        	Notification n = new Notification();
        	
        	n.icon=R.drawable.icon;
        	n.tickerText="你有新包裹啦!";
        	n.when = System.currentTimeMillis();   
               
             nm.notify(1, n);  
        	
        	
        	Editor editor=sharedPreferences.edit();
        	editor.putInt("NUM1", Packages.Num1);
        	editor.commit();
        }
   
    }
    public Context getContext(){
    	return this;
    }
    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        switch (TurnControl.background_ID) {
		case "1":
	        resideMenu.setBackground(R.drawable.menu_background1);
			break;
		case "2":
	        resideMenu.setBackground(R.drawable.menu_background3);
			break;
		case "3":			
	        resideMenu.setBackground(R.drawable.menu_background3);
			break;
		case "4":			
	        resideMenu.setBackground(R.drawable.menu_background4);
			break;
		default:
			resideMenu.setBackground(R.drawable.menu_background3);
			break;
		}

        resideMenu.attachToActivity(this);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "主界面");
        itemMypackage  = new ResideMenuItem(this, R.drawable.icon_package,  "我的包裹");
        itemProfile  = new ResideMenuItem(this, R.drawable.icon_profile,  "个人资料");
        itemShare = new ResideMenuItem(this,R.drawable.icon_share,"好友分享");
        itemAnalysis = new ResideMenuItem(this, R.drawable.icon_analysis, "购物分析");
        itemSkin = new ResideMenuItem(this, R.drawable.icon_settings, "多彩皮肤");
        itemSignOut  = new ResideMenuItem(this, R.drawable.icon_back,  "注销账号");

        

        itemHome.setOnClickListener(this);
        itemMypackage.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemShare.setOnClickListener(this);
        itemSignOut.setOnClickListener(this);
        itemAnalysis.setOnClickListener(this);
        itemSkin.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemMypackage, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSkin, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemShare,ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemAnalysis, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSignOut, ResideMenu.DIRECTION_RIGHT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }
    @Override
    public void onClick(View view) {

        if (view == itemHome){
            TurnControl.curFragment = 0;
        	changeFragment(new HomeFragment());
        }else if(view==itemMypackage){
        	TurnControl.curFragment = 1;
        	changeFragment(new PackageFragment());
        }else if (view == itemProfile){
            TurnControl.curFragment = 2;
            Intent intent = new Intent();
        	intent.setClass(MenuActivity.this, ProfileActivity.class);
        	startActivity(intent);
        }else if(view == itemShare){
        	TurnControl.curFragment = 3;
        }else if (view == itemSkin){
            TurnControl.curFragment = 4;
        	changeFragment(new SkinFragment());
        }else if (view == itemAnalysis){
            Packages.calc();
	        TurnControl.curFragment = 5;
	    	changeFragment(new AnalysisFragment());
        }else if (view == itemSignOut){
        	Intent intent = new Intent();
        	intent.setClass(MenuActivity.this, LoginActivity.class);
        	startActivity(intent);
        	finish();
        }

        resideMenu.closeMenu();
    }

    protected void dialog(){
    	AlertDialog.Builder dialog_out = new Builder(mContext);
    	dialog_out.setMessage("确认退出吗？");
    	dialog_out.setTitle("提示");
    	dialog_out.setPositiveButton("确认", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				mContext.finish();
			}
    	});
    	dialog_out.setNegativeButton("取消", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
    	});
    	dialog_out.create().show();
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
   	 
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	switch (TurnControl.curFragment)
        	{
        	case 1: case 2: case 3: case 4: case 5:
        		TurnControl.curFragment = 0;
        		changeFragment(new HomeFragment());
        		break;
        	case 10:		//QR code fragment
        		TurnControl.curFragment = 1;
        		changeFragment(new PackageFragment());
        		break;
        	case 0:
        		if (event.getRepeatCount() == 0){
        			dialog();
        		}
        		
        	default:
        		TurnControl.curFragment = 0;
        		changeFragment(new HomeFragment());

        	}
        	
            return true;
         }
         return false;
     }
    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu
    public ResideMenu getResideMenu(){
        return resideMenu;
    }
}
