package com.special.ResideMenuDemo;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVObjectReferenceCount;
import com.avos.avoscloud.LogUtil.log;

import android.app.Application;
import android.util.Log;

public class Avos extends Application{
	public void onCreate(){
		super.onCreate();
		AVOSCloud.initialize(this, "byln9ncnu6buv28w2e2veo6moh0ei4ej4hv8xdtcnpsgat4f", "e7fv4s2wxt6cjtpkzqfxl34b5ixbnmfjgr1f6mf40zqwrscy");

	}
}
