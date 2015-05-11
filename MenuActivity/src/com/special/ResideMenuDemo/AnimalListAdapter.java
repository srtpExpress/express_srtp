

package com.special.ResideMenuDemo;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import org.w3c.dom.ls.LSException;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;




public class AnimalListAdapter extends BaseAdapter 
{  
	class ViewHolder {  
		
		public int flag;
		public ImageView icon;
		public TextView cn_word0;
		public TextView cn_word1;
		public TextView cn_word2;
		public TextView cn_word3;
	}  
	private LayoutInflater mInflater = null;
	
	public AnimalListAdapter(Context context){
		super();
        mInflater = (LayoutInflater) context
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (TurnControl.flagGet == 1) return Packages.Num1;
		else return Packages.Num2;
	}
		
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder holder = null;  
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.pakage_item, null);
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_goods);  
			holder.cn_word0 = (TextView) convertView.findViewById(R.id.tv_goodsID);
			holder.cn_word1 = (TextView) convertView.findViewById(R.id.tv_logisticalCompany);  
			holder.cn_word2 = (TextView) convertView.findViewById(R.id.tv_goodsName);
			holder.cn_word3 = (TextView) convertView.findViewById(R.id.tv_goodsPrice);
			convertView.setTag(holder);  
		} else {  
			holder = (ViewHolder) convertView.getTag();  
		}
		PackageInfo good = null;
		if (TurnControl.flagGet == 1){
			good = Packages.List1.get(position);
		}else{
			good = Packages.List2.get(position);
		}
		
		holder.cn_word0.setText(String.valueOf(position + 1));
		holder.cn_word1.setText(good.company);
		holder.cn_word2.setText(good.name);
		holder.cn_word3.setText(good.price);
		holder.icon.setImageBitmap(good.image);
		return convertView;
	}  

}
