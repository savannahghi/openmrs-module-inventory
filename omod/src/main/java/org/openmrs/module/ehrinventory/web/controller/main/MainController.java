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
package org.openmrs.module.ehrinventory.web.controller.main;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.ehrinventory.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 */
@Controller("mainInventoryController")
@RequestMapping("/module/ehrinventory/main.form")
public class MainController {

	 @RequestMapping(method = RequestMethod.GET)
		public String firstView( Model model) {
		 InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		 
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
			
		 try { 
			 
			 
			// InventoryStore store = inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
			 
			 
			 if(store != null){
				 if( store.getParentStores().isEmpty() && !store.getRetired()){
					 return "redirect:/module/ehrinventory/viewStockBalance.form";
				 }else if( !store.getParentStores().isEmpty()&&rl.hasPrivilege("Drug order queue") && !store.getRetired() ){
					 return "redirect:/module/ehrinventory/patientQueueDrugOrder.form";
				 }else if( !store.getParentStores().isEmpty()&&rl.hasPrivilege("Drug/Item Dispense")  && !store.getRetired() ){
					// return "redirect:/module/inventory/itemViewStockBalanceSubStore.form";
                                     //17/11/2014  In order to get the Drug Windows first
                                     return "redirect:/module/ehrinventory/subStoreIssueDrugList.form";
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return "";
	}
}
