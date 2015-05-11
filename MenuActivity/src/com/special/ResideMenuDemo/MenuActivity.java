package com.special.ResideMenuDemo;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.AlteredCharSequence;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
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

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new HomeFragment());
   
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
