/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.ehrinventory.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.math.NumberUtils;

public class StringNumberUtils {
	private static final int[] MIN_MAX = {1, 10000}; 
	public static Integer[] convertStringArraytoIntArray(String[] sarray) throws Exception {
			if (sarray != null && sarray.length > 0)
			{
				Integer intarray[] = new Integer[sarray.length];
				for (int i = 0; i < sarray.length; i++) {
					intarray[i] = NumberUtils.toInt(sarray[i]);
				}
				return intarray;
			}
			return null;
			}
	
	public static <T> List<T> convertArrayToList(T[] array){
		if(array == null || array.length == 0 )
			return null;
		List<T> list = new ArrayList<T>();
		for(T element : array){
			list.add(element);
		}
		return list;
		}

	
	public static  Collection difference(Collection a, Collection b) {
		if(b == null){
			return a;
		}
		if(a == null){
			return null;
		}
		return ListUtils.removeAll(a, b);
		}
	public static int getRandom()
	{
		Random rand = new Random();
		return rand.nextInt((MIN_MAX[1]+1) - MIN_MAX[0]) + MIN_MAX[0];
	}
	public static void main(String[] args) {
		/*List a = new ArrayList();
		a.add("1");
		a.add("2");
		a.add("3");
		a.add("4");
		a.add("5");
		
		List b =null;
	
		System.out.println(difference(a,b));
		System.out.println("a: "+a);
		System.out.println(b);*/

		for(int i=0; i<1000;i++){
			System.out.println(getRandom());
		}
		//String[] ab =new String[]{"1","2","3"};
		//List<Integer> inte = convertArrayToList(ab);
		//System.out.println(inte);
		
	}
}
