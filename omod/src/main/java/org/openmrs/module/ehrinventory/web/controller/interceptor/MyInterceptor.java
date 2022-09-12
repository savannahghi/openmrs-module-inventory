/**
 * <p> File: org.openmrs.module.inventory.web.controller.interceptor.MyInterceptor.java </p>
 * <p> Project: inventory-omod </p>
 * <p> Copyright (c) 2011 CHT Technologies. </p>
 * <p> All rights reserved. </p>
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Jan 5, 2011 12:57:29 PM </p>
 * <p> Update date: Jan 5, 2011 12:57:29 PM </p>
 **/

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
package org.openmrs.module.ehrinventory.web.controller.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.ehrinventory.InventoryService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p> Class: MyInterceptor </p>
 * <p> Package: org.openmrs.module.inventory.web.controller.interceptor </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Jan 5, 2011 12:57:29 PM </p>
 * <p> Update date: Jan 5, 2011 12:57:29 PM </p>
 **/
//@Controller("myInterceptor")

public class MyInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object object) throws Exception {
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		// InventoryStore store = inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
		 List <Role>role=new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles());
			
			InventoryStoreRoleRelation srl=null;
			Role rl = null;
			for(Role r: role){
				if(inventoryService.getStoreRoleByName(r.toString())!=null){
					srl = inventoryService.getStoreRoleByName(r.toString());	
					rl=r;
				}
			}
			InventoryStore store =null;
			if(srl!=null){
				store = inventoryService.getStoreById(srl.getStoreid());
				
			}
		if(store != null && store.getParentStores() == null){
			 response.sendRedirect("/module/ehrinventory/mainstore/mainPage");
		 }else{
			 response.sendRedirect( "/module/ehrinventory/substore/mainPage");
		 }
		return false;
	}

}
