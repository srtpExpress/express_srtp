package com.special.ResideMenuDemo;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
import com.special.ResideMenu.ResideMenu;



public class HomeFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;
    private IWeiboShareAPI mWeiboShareAPI;
    private int mShareType = 1;
	TextView home_sum_text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
        setUpViews();
        return parentView;
    }
    private void sendSingleMessage() {
        
        WeiboMessage weiboMessage = new WeiboMessage();
        weiboMessage.mediaObject = getTextObj();
        //weiboMessage.mediaObject = getImageObj();
        //weiboMessage.mediaObject = getVideoObj();
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;        
        mWeiboShareAPI.sendRequest(getActivity(), request);
    }
    private void sendMultiMessage() {
        
	        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
	        weiboMessage.textObject = getTextObj();
	        //weiboMessage.mediaObject = getImageObj();
	        //weiboMessage.mediaObject = getVideoObj();
	        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
	        request.transaction = String.valueOf(System.currentTimeMillis());
	        request.multiMessage = weiboMessage;
	        if (mShareType == 1) {
	            boolean i=mWeiboShareAPI.sendRequest(getActivity(), request);
	            if(i)
	            	Toast.makeText(getActivity(), "hahah", Toast.LENGTH_SHORT).show();
	        }
    }

    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = getString(R.string.weibo);
        return textObject;
    }

    /*private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(R.drawable.button_on);
        return imageObject;
    }*/
    
   /* private VideoObject getVideoObj() {
       
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = mShareVideoView.getTitle();
        videoObject.description = mShareVideoView.getShareDesc();
        
      
        videoObject.setThumbImage(mShareVideoView.getThumbBitmap());
        videoObject.actionUrl = mShareVideoView.getShareUrl();
        videoObject.dataUrl = "www.weibo.com";
        videoObject.dataHdUrl = "www.weibo.com";
        videoObject.duration = 10;
        videoObject.defaultText = "Vedio";
        return videoObject;
    }*/
    
    private void setUpViews() {
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();
        home_sum_text = (TextView) parentView.findViewById(R.id.main_notation_number);
        //while(Packages.Num2<0);
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(getActivity(), Constants.APP_KEY);
        boolean isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
        int supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI(); 
        
        mWeiboShareAPI.registerApp();
        parentView.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        parentView.findViewById(R.id.main_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            	if (mShareType == 1) {
                    if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
                        int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
                        if (supportApi >= 10351 /*ApiUtils.BUILD_INT_VER_2_2*/) {
                            sendMultiMessage();
                        } else {
                            sendSingleMessage();
                        }
                    } else {
                        Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        home_sum_text.setText(String.valueOf(TurnControl.number));
        // add gesture operation's ignored views
       // FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
       // resideMenu.addIgnoredView(ignored_view);
    }

}
