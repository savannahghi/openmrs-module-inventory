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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugIndent;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.hospitalcore.util.Action;
import org.openmrs.module.hospitalcore.util.ActionValue;
import org.openmrs.module.ehrinventory.util.PagingUtil;
import org.openmrs.module.ehrinventory.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("SubStoreIndentDrugListController")
@RequestMapping("/module/ehrinventory/subStoreIndentDrugList.form")
public class SubStoreIndentDrugListController {
	@RequestMapping(method = RequestMethod.GET)
	public String showList( 
			@RequestParam(value="statusId",required=false)  Integer statusId,
			@RequestParam(value="indentName",required=false)  String indentName,
			 @RequestParam(value="fromDate",required=false)  String fromDate,
             @RequestParam(value="toDate",required=false)  String toDate,
			@RequestParam(value="pageSize",required=false)  Integer pageSize, 
            @RequestParam(value="currentPage",required=false)  Integer currentPage,
			Model model,
			HttpServletRequest request
			) {
		InventoryService inventoryService = Context.getService(InventoryService.class);
		//InventoryStore subStore =  inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
		//System.out.println("id main store controller: "+subStore.getId());
		//System.out.println("name main store controller: "+subStore.getName());
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
		int total = inventoryService.countSubStoreIndent(subStore.getId(), indentName, statusId, fromDate, toDate);
		
		String temp = "";
		if(!StringUtils.isBlank(indentName)){	
				temp = "?indentName="+indentName;
		}
		
		if(statusId != null){	
			if(StringUtils.isBlank(temp)){
				temp = "?statusId="+statusId;
			}else{
				temp +="&statusId="+statusId;
			}
		}
		if(!StringUtils.isBlank(fromDate)){	
			if(StringUtils.isBlank(temp)){
				temp = "?fromDate="+fromDate;
			}else{
				temp +="&fromDate="+fromDate;
			}
	}
		if(!StringUtils.isBlank(toDate)){	
			if(StringUtils.isBlank(temp)){
				temp = "?toDate="+toDate;
			}else{
				temp +="&toDate="+toDate;
			}
	}
		PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request)+temp , pageSize, currentPage, total );
		List<InventoryStoreDrugIndent> listIndent = inventoryService.listSubStoreIndent(subStore.getId(), indentName, statusId, fromDate, toDate, pagingUtil.getStartPos(), pagingUtil.getPageSize());
		List<Action> listSubStoreStatus = ActionValue.getListIndentSubStore();
		model.addAttribute("listSubStoreStatus", listSubStoreStatus);
		model.addAttribute("listIndent", listIndent);
		model.addAttribute("indentName", indentName);
		model.addAttribute("statusId", statusId);
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		model.addAttribute("pagingUtil", pagingUtil);
		model.addAttribute("store", subStore );
		return "/module/ehrinventory/substore/subStoreIndentDrugList";
	}
}
