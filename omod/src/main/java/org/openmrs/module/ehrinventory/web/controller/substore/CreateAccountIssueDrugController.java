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
package org.openmrs.module.ehrinventory.web.controller.substore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryStoreDrugAccount;
import org.openmrs.module.ehrinventory.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("CreateAccountIssueDrugController")
@RequestMapping("/module/ehrinventory/createAccountIssueDrug.form")
public class CreateAccountIssueDrugController {

	@RequestMapping(method = RequestMethod.GET)
	public String firstView(Model model) {
		return "/module/ehrinventory/substore/createAccountIssueDrug";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request,Model model) {
		String account = request.getParameter("accountName");
		if(!StringUtils.isBlank(account)){
			int userId = Context.getAuthenticatedUser().getId();
			InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
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
			InventoryStoreDrugAccount issueAccount = new InventoryStoreDrugAccount();
			issueAccount.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			issueAccount.setCreatedOn(new Date());
			issueAccount.setName(account);
			issueAccount.setStore(store);
			StoreSingleton.getInstance().getHash().put("issueDrugAccount_"+userId , issueAccount);
			model.addAttribute("message", "Succesfully");
			model.addAttribute("urlS", "subStoreIssueDrugAccountForm.form");
			return "/module/ehrinventory/thickbox/success";
		}
		return "/module/ehrinventory/substore/createAccountIssueDrug";
	}
	
}
