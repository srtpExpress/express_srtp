package com.special.ResideMenuDemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
        AvosDatabase avosDatabase=new AvosDatabase();
        avosDatabase.getDatabase(1);
        avosDatabase.getDatabase(2);
        home_sum_text = (TextView) parentView.findViewById(R.id.main_notation_number);
        //while(Packages.Num2<0);
    	

        parentView.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        home_sum_text.setText(String.valueOf(Packages.Num2 - 2));
        // add gesture operation's ignored views
       // FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
       // resideMenu.addIgnoredView(ignored_view);
    }

}
