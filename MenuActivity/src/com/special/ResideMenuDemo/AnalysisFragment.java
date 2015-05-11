package com.special.ResideMenuDemo;

import com.avos.avoscloud.LogUtil.log;
import com.special.ResideMenuDemo.AnimalListAdapter.ViewHolder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AnalysisFragment extends Fragment {
	View parentView;
	TextView percent_life;
	TextView percent_educate;
	TextView percent_3c;
	TextView totalPackage;
	
///storage/emulated/0/1KM/Goods/goods8.png
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.analysis, container, false);
        initView();

        Button btn_life = (Button)parentView.findViewById(R.id.ana_button1);
        Button btn_edu = (Button)parentView.findViewById(R.id.ana_button2);
        Button btn_3c = (Button)parentView.findViewById(R.id.ana_button3);
	    btn_life.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Hello Life", Toast.LENGTH_SHORT).show();
			}
	    	
	    });
	    
	    btn_edu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "Good Good Study Day Day Up!", Toast.LENGTH_SHORT).show();
				
			}
		});
	    
	    btn_3c.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "ZJU Shao Qian Na Jia Qiang?(*^__^*)", Toast.LENGTH_LONG).show();
				
			}
		});
        
        return parentView;
    }
    
    public void initView()
    {
    	totalPackage = (TextView)parentView.findViewById(R.id.ana_textView_total);
    	totalPackage.setText(String.valueOf(Packages.Num2));
    	percent_life = (TextView)parentView.findViewById(R.id.ana_button1);
    	percent_life.setText(Packages.CategoryPerent.get(1));
    	percent_educate = (TextView)parentView.findViewById(R.id.ana_button2);
    	percent_educate.setText(Packages.CategoryPerent.get(2));
    	percent_3c = (TextView)parentView.findViewById(R.id.ana_button3);
    	percent_3c.setText(Packages.CategoryPerent.get(3));
    }
	    

}
