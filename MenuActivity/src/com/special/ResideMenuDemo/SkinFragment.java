package com.special.ResideMenuDemo;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.special.ResideMenu.ResideMenu;

public class SkinFragment  extends Fragment {
	View parentView;
    private ResideMenu resideMenu;
	boolean isChangeSkin = false;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.skin, container, false);
        resideMenu = ((MenuActivity) getActivity()).getResideMenu();
        initView();
        return parentView;
    }
	public void initView() {


		
    	ImageButton btn1 = (ImageButton)parentView.findViewById(R.id.backgroundButton1);
	    btn1.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
	        	dialog(1);
	        	if (isChangeSkin) {
	        		resideMenu.setBackground(R.drawable.menu_background1);
	        		isChangeSkin = false;
	        	}
			}
	    	
	    });

	    ImageButton btn2 = (ImageButton)parentView.findViewById(R.id.backgroundButton2);
	    btn2.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog(2);
				if (isChangeSkin){
		        	resideMenu.setBackground(R.drawable.menu_background2);
		        	isChangeSkin = false;
				}
			}
	    	
	    });

	    ImageButton btn3 = (ImageButton)parentView.findViewById(R.id.backgroundButton3);
	    btn3.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog(3);
				if (isChangeSkin){
		        	resideMenu.setBackground(R.drawable.menu_background3);
		        	isChangeSkin = false;
				}
			}
	    	
	    });

	    ImageButton btn4 = (ImageButton)parentView.findViewById(R.id.backgroundButton4);
	    btn4.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog(4);
				if (isChangeSkin){
		        	resideMenu.setBackground(R.drawable.menu_background4);
		        	isChangeSkin = false;
				}

			}
	    	
	    });

	}
	
    protected void dialog(final int id){
    	AlertDialog.Builder dialog_out = new Builder(getActivity());
    	dialog_out.setMessage("你确定换肤么？");
    	dialog_out.setTitle("提示");
    	dialog_out.setPositiveButton("确认", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				TurnControl.userAvObject.put("backgroundID", String.valueOf(id));
				TurnControl.userAvObject.saveInBackground();
				isChangeSkin = true;
				dialog.dismiss();
				
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

}
