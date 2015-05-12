package com.special.ResideMenuDemo;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.special.ResideMenu.ResideMenu;



public class HomeFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;
	TextView home_sum_text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
        setUpViews();
        return parentView;
    }

    private void setUpViews() {
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();
        home_sum_text = (TextView) parentView.findViewById(R.id.main_notation_number);
        //while(Packages.Num2<0);
    	

        parentView.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        home_sum_text.setText(String.valueOf(TurnControl.number));
        // add gesture operation's ignored views
       // FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
       // resideMenu.addIgnoredView(ignored_view);
    }

}
