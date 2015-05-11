package com.special.ResideMenuDemo;

import tree.love.animtabsview.AnimTabsView;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


	/**
	 * A placeholder fragment containing a simple view.
	 */
	@SuppressLint("NewApi")
	public class PackageFragment extends Fragment {
		private ListView listview;
		private AnimTabsView mTabsView;
		Bundle saveBundle;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			saveBundle=savedInstanceState;
			View rootView = inflater.inflate(R.layout.pakage, container, false);
			 listview   = (ListView) rootView.findViewById(R.id.listView);	 
			setupViews(rootView);
			return rootView;
		}
		
		@SuppressLint("NewApi")
		private void setupViews(View rootView) {
			mTabsView = (AnimTabsView) rootView.findViewById(R.id.publiclisten_tab);
			mTabsView.addItem("未取包裹");
			mTabsView.addItem("已取包裹");
	        
	        final AnimalListAdapter arrayAdapter = new AnimalListAdapter (getActivity());

			TurnControl.flagGet = 1;
			listview.setAdapter(arrayAdapter);
	        
            mTabsView.setOnAnimTabsItemViewChangeListener(new AnimTabsView.IAnimTabsItemViewChangeListener() {
                @Override
                public void onChange(AnimTabsView tabsView, int oldPosition, int currentPosition) {
                	TurnControl.curPosition = currentPosition;
                	if(oldPosition-currentPosition>0){
                		TurnControl.flagGet = 2;
                		listview.setAdapter(arrayAdapter);
                	}else {
                        TurnControl.flagGet = 1;
                		listview.setAdapter(arrayAdapter);
					}
                }
            });
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                	if (TurnControl.flagGet == 1)
                	{
                		TurnControl.curFragment = 10;
                		TurnControl.selectPackage = i;
                		changeFragment(new PictureFrament());
                	} else{
                		Toast.makeText(getActivity(), "(⊙o⊙)你已经取过这个包裹啦！", Toast.LENGTH_SHORT).show();
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
		

