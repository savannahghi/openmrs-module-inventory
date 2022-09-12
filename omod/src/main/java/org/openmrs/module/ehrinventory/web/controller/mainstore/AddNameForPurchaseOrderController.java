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
package org.openmrs.module.ehrinventory.web.controller.mainstore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugIndent;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryStoreDrugIndentDetail;
import org.openmrs.module.ehrinventory.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller("AddNameForPurchaseOrderController")
@RequestMapping("/module/ehrinventory/addNameForPurchaseOrderSlip.form")
public class AddNameForPurchaseOrderController {
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(Model model) {
		return "/module/ehrinventory/mainstore/addNameForPurchaseOrderSlip";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model model) {
		String indentName = request.getParameter("indentName");
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		Date date = new Date();
		int userId = Context.getAuthenticatedUser().getId();
		//InventoryStore store = inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
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
		
		
		String fowardParam = "purchase_"+userId;
		List<InventoryStoreDrugIndentDetail> list = (List<InventoryStoreDrugIndentDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
		
		InventoryStoreDrugIndent indent = new InventoryStoreDrugIndent();
		indent.setName(indentName);
		indent.setCreatedOn(date);
		indent.setStore(store);
		indent.setMainStoreStatus(0);
		indent.setSubStoreStatus(0);
		indent = inventoryService.saveStoreDrugIndent(indent);
		
		if(list != null && list.size() > 0){
			for(int i=0;i< list.size();i++){
				InventoryStoreDrugIndentDetail indentDetail = list.get(i);
				indentDetail.setCreatedOn(date);
				indentDetail.setIndent(indent);
				inventoryService.saveStoreDrugIndentDetail(indentDetail);
			}
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			model.addAttribute("message", "Succesfully");
			model.addAttribute("urlS", "purchaseOrderForGeneralStoreList.form");
		}else{
			model.addAttribute("message", "Sorry don't have any purchase to save");
			model.addAttribute("urlS", "purchaseOrderForGeneralStore.form");
		}
	 return "/module/ehrinventory/thickbox/success";
	}
}
