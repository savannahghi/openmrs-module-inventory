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

package org.openmrs.module.ehrinventory.web.controller.global;

import java.util.HashMap;

/**
 * <p> Class: SubStoreSingleton </p>
 * <p> Package: org.openmrs.module.inventory.web.controller.substore </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Dec 28, 2010 10:11:47 PM </p>
 * <p> Update date: Dec 28, 2010 10:11:47 PM </p>
 **/
public class StoreSingleton {
	private static StoreSingleton instance = null;
	   public static final StoreSingleton getInstance(){
		   if (instance == null) {
		         instance = new StoreSingleton();
		      }
		      return instance;
	   }
	   private static HashMap<String, Object> hash;
	   public static HashMap<String, Object> getHash() {
			if( hash == null )
				hash = new HashMap<String, Object>();
			return hash;
		}
}
