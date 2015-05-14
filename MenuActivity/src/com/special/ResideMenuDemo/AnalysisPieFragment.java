package com.special.ResideMenuDemo;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

public class AnalysisPieFragment extends Fragment {
	private PieChart mChart;
	View pieView;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
		pieView = inflater.inflate(R.layout.analysis_pie, container, false);
        initView();
    	Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "SourceCodePro-ExtraLight.ttf");     
        mChart.setDescription("");
        mChart.setCenterTextTypeface(tf);
        mChart.setCenterText(String.valueOf(Packages.Num2));
        mChart.setCenterTextSize(22f);
        mChart.setCenterTextTypeface(tf);
       // mChart.setOnChartValueSelectedListener(this);
        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f); 
        mChart.setTransparentCircleRadius(50f);
        mChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        mChart.setData(generatePieData());
        
        return pieView;
	}
	public void initView(){
    	mChart = (PieChart)pieView.findViewById(R.id.chart1);
	}
	protected PieData generatePieData() {
        
        int count = 3;
        
        ArrayList<Entry> entries1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        
        xVals.add("3C");
        xVals.add("Education");
        xVals.add("Life-Style");
        //xVals.add("Other");
            xVals.add("entry" + 1);
            entries1.add(new Entry((float)Packages.Category3C.doubleValue()/100, 1));
            xVals.add("entry" + 2);
            entries1.add(new Entry((float)Packages.CategoryEdu.doubleValue()/100, 2));
            xVals.add("entry" + 3);
            entries1.add(new Entry((float)Packages.CategoryLife.doubleValue()/100, 3));
        
        PieDataSet ds1 = new PieDataSet(entries1, "1KM ¹ºÎï·ÖÎö");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.BLACK);
        ds1.setValueTextSize(16f);
        
        PieData d = new PieData(xVals, ds1);
    	Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "SourceCodePro-Black.ttf");     
        d.setValueTypeface(tf);

        return d;
    }
}
