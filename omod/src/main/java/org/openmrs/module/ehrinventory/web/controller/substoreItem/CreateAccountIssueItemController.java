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
package org.openmrs.module.ehrinventory.web.controller.substoreItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemAccount;
import org.openmrs.module.ehrinventory.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller("CreateAccountIssueItemController")
@RequestMapping("/module/ehrinventory/createAccountIssueItem.form")
public class CreateAccountIssueItemController {

	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("issue") InventoryStoreItemAccount issue ,Model model) {
		return "/module/ehrinventory/substoreItem/createAccountIssueItem";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("issue") InventoryStoreItemAccount issue, BindingResult bindingResult, HttpServletRequest request, SessionStatus status, Model model) {
		new IssueItemAccountValidator().validate(issue, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/ehrinventory/substoreItem/createAccountIssueItem";
			
		}else{
			
			InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
			int userId = Context.getAuthenticatedUser().getId();
			//InventoryStore subStore =  inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
			 List <Role>role=new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles());
				
				InventoryStoreRoleRelation srl=null;
				Role rl = null;
				for(Role r: role){
					if(inventoryService.getStoreRoleByName(r.toString())!=null){
						srl = inventoryService.getStoreRoleByName(r.toString());	
						rl=r;
					}
				}
				InventoryStore subStore =null;
				if(srl!=null){
					subStore = inventoryService.getStoreById(srl.getStoreid());
					
				}
			issue.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			issue.setCreatedOn(new Date());
			issue.setStore(subStore);
			status.setComplete();
			
			String fowardParam = "issueItem_"+userId;
			StoreSingleton.getInstance().getHash().put(fowardParam,issue);
			
			model.addAttribute("message", "Succesfully");
			model.addAttribute("urlS", "subStoreIssueItemForm.form");
			
			return "/module/ehrinventory/thickbox/success";
		}
	}
}
