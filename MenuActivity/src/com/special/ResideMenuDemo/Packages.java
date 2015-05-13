package com.special.ResideMenuDemo;

import java.util.ArrayList;

import com.avos.avoscloud.LogUtil.log;

public class Packages {
	public static ArrayList<PackageInfo> List1 = new ArrayList<PackageInfo>();
	public static int Num1 = 0;
	public static ArrayList<PackageInfo> List2 = new ArrayList<PackageInfo>();
	public static int Num2 = 0;
	public static int SumPrice = 0;
	public static ArrayList<String> CategoryPerent = new ArrayList<String>();
	public static Double CategoryLife = 0.0;
	public static Double CategoryEdu = 0.0;
	public static Double Category3C = 0.0;
	
	
	public static void calc(){
		for (int i = 0; i < Num2; i++){
			switch (List2.get(i).category) {
			case "life-style":
				CategoryLife += Double.parseDouble(List2.get(i).price.substring(1));
				
				break;
			case "education":
				CategoryEdu += Double.parseDouble(List2.get(i).price.substring(1));
			case "3C":
				Category3C += Double.parseDouble(List2.get(i).price.substring(1));
				break;
			}
			SumPrice += Double.parseDouble(List2.get(i).price.substring(1));
		}
		updatePercent();
		
	}
	
	static void updatePercent() {
		int per_life = (int) (100 * Packages.CategoryLife / Packages.SumPrice);
		int per_edu = (int) (100 * Packages.CategoryEdu / Packages.SumPrice);
        int per_3c = 100 - per_life - per_edu;
        Packages.CategoryPerent.set(1, String.valueOf(per_life) + "%" );
        Packages.CategoryPerent.set(2, String.valueOf(per_edu) + "%");
        Packages.CategoryPerent.set(3, String.valueOf(per_3c)+ "%");
		
	}
}
