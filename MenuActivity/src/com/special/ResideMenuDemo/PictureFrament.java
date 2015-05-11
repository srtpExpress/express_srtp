package com.special.ResideMenuDemo;

import java.security.PublicKey;

import tree.love.animtabsview.R.string;

import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.LogUtil.log;
import com.special.ResideMenu.ResideMenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PictureFrament extends Fragment {
		View parentView;
		ImageView imageView;
		TextView textView;
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		   parentView = inflater.inflate(R.layout.picture, container, false);
		   initview();
	       return parentView;
	         
	    }
	    public void initview(){
	    	imageView= (ImageView) parentView.findViewById(R.id.picture);
	    	imageView.setImageResource(R.drawable.qr);
	    	textView = (TextView) parentView.findViewById(R.id.twoDCode);
	    	textView.setText("让快递箱扫一扫，打开箱子完成取货吧^.^");
   	
	    	Button btn = (Button)parentView.findViewById(R.id.get_button);
		    btn.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					AvosDatabase packages = new AvosDatabase();
					if (packages.update(getActivity(), TurnControl.selectPackage)){
						Toast.makeText(getActivity(), "取货成功O(∩_∩)O", Toast.LENGTH_SHORT).show();
				        TurnControl.curFragment = 1;
			        	changeFragment(new PackageFragment());

					}else{
						Toast.makeText(getActivity(), "臣妾做不到啊T.T", Toast.LENGTH_SHORT).show();
					}
					
				}
		    	
		    });
	    }
		 private void changeFragment(Fragment targetFragment){
		        getActivity().getSupportFragmentManager()
		                .beginTransaction()
		                .replace(R.id.main_fragment, targetFragment, "fragment")
		                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
		                .commit();
		    }

}
