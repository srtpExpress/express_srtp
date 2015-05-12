package com.special.ResideMenuDemo;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogUtil.log;
import com.avos.avoscloud.*;

public class AvosDatabase {
	
	void countDatabase(final int flag){
		AVQuery<AVObject> query = AVQuery.getQuery("PackageList"+flag);
		query.whereEqualTo("UserID", TurnControl.user_ID);
		query.countInBackground(new CountCallback() {
			  public void done(int count, AVException e) {
			    if (e == null) {
			      if(flag==1)
			    	  Packages.Num1=count;
			      else {
			    	  Packages.Num2=count;
				}
			    } else {
			      // The request failed
			    }
			  }
			});
	}
	void getDatabase(final int flag){
		
		Packages.CategoryPerent.add("0%");
		Packages.CategoryPerent.add("0%");
		Packages.CategoryPerent.add("0%");
        if (flag == 1){
        	Packages.List1.clear();
        } else{
        	Packages.List2.clear();
        }
		AVQuery<AVObject> query = new AVQuery<AVObject>("PackageList" + flag);
		Date rtn = null;  
	    GregorianCalendar cal = new GregorianCalendar();  
	    Date date = new Date();  
	    cal.setTime(date);
	    cal.add(2,-1); 
	    Date date1=cal.getTime();
	    AVQuery<AVObject> query1 = new AVQuery<AVObject>("PackageList2");
	    query1.whereEqualTo("UserID",TurnControl.user_ID);
	    query1.whereGreaterThan("createdAt", date1);
        query1.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> packages, AVException e) {
					TurnControl.number=packages.size();
				}
			});
		query.whereEqualTo("UserID",TurnControl.user_ID);
        query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> packages, AVException e) {
				
				log.e("ERROR", "flag = " + flag);
				log.e("ERROR", String.valueOf(packages.size()));
				log.e("ERROR", TurnControl.user_ID);

				
				if (packages.size() > 0){
			        if (flag == 1){
			        	Packages.Num1 = packages.size();
			        	
			        }else{
			        	Packages.Num2 = packages.size();
			        }
					for (int i = 0; i < packages.size(); i++)
					{
			        	 PackageInfo good = new PackageInfo();
			             good.company = packages.get(i).getString("company");
			        	 good.category = packages.get(i).getString("category");
			             good.name = packages.get(i).getString("name");
			        	 good.price = packages.get(i).getString("price");
			        	 good.imageLoc = packages.get(i).getString("image");
			        	 good.image = BitmapFactory.decodeFile(good.imageLoc);
			             if (flag == 1){
			            	 Packages.List1.add(good);	 
			             }else{
			            	 Packages.List2.add(good);

			             } 
					}
				}else{
					//Toast.makeText(context, "No package", Toast.LENGTH_SHORT).show();
				}
			}
		});
	             
    }
	
	boolean update(final Context context, final int num){
		PackageInfo tmpList1Package = Packages.List1.get(num);
		
		AVObject tmpPackage = new AVObject("PackageList2");
		tmpPackage.put("name", tmpList1Package.name);
		tmpPackage.put("category", tmpList1Package.category);
		tmpPackage.put("price", tmpList1Package.price);
		tmpPackage.put("company", tmpList1Package.company);
		tmpPackage.put("image", tmpList1Package.imageLoc);
		tmpPackage.put("UserID", TurnControl.user_ID);
		tmpPackage.saveInBackground();
		
		AVQuery<AVObject> query = new AVQuery<AVObject>("PackageList1");
 		query.whereEqualTo("name", tmpList1Package.name);
		
 		query.deleteAllInBackground(new DeleteCallback() {
			@Override
			public void done(AVException e) {
				if (e == null){
					Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
 		log.e("ERROR",String.valueOf(Packages.List1.size()));
 		Packages.SumPrice += Double.parseDouble(tmpList1Package.price.substring(1));
 		switch (tmpList1Package.category) {
		case "life-sytle":
//			Packages.CategorySum.set(1, Packages.CategorySum.get(1) + Integer.parseInt(tmpList1Package.price));
			Packages.CategoryLife += Double.parseDouble(tmpList1Package.price.substring(1));
			break;
		case "education":
//			Packages.CategorySum.set(2, Packages.CategorySum.get(2) + Integer.parseInt(tmpList1Package.price));
			Packages.CategoryEdu += Double.parseDouble(tmpList1Package.price.substring(1));
			break;
		case "3C":
//			Packages.CategorySum.set(3, Packages.CategorySum.get(3) + Integer.parseInt(tmpList1Package.price));
			Packages.Category3C += Double.parseDouble(tmpList1Package.price.substring(1));
			break;
		}

 		Packages.updatePercent();
 		
 		Packages.List2.add(tmpList1Package);
 		Packages.Num2++;
 		Packages.List1.remove(num);
 		Packages.Num1--;
		return true;
	}


	
}
