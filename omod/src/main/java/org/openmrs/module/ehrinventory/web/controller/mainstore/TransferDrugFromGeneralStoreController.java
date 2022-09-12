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

@Controller("TransferDrugFromGeneralStoreController")
@RequestMapping("/module/ehrinventory/transferDrugFromGeneralStore.form")
public class TransferDrugFromGeneralStoreController {
	@RequestMapping(method = RequestMethod.GET)
	public String showAttributeList( 
			@RequestParam(value="indentId",required=false)  Integer id,
			@RequestParam(value="storeId",required=false)  Integer storeId,
			@RequestParam(value="statusId",required=false)  Integer statusId,
			@RequestParam(value="indentName",required=false)  String indentName,
			 @RequestParam(value="fromDate",required=false)  String fromDate,
             @RequestParam(value="toDate",required=false)  String toDate,
			@RequestParam(value="pageSize",required=false)  Integer pageSize, 
            @RequestParam(value="currentPage",required=false)  Integer currentPage,
            @RequestParam(value="viewIndent",required=false)  Integer viewIndent,
			Model model,
			HttpServletRequest request
			) {
		InventoryService inventoryService = Context.getService(InventoryService.class);
		//InventoryStore mainStore =  inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
		 List <Role>role=new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles());
			
			InventoryStoreRoleRelation srl=null;
			Role rl = null;
			for(Role r: role){
				if(inventoryService.getStoreRoleByName(r.toString())!=null){
					srl = inventoryService.getStoreRoleByName(r.toString());	
					rl=r;
				}
			}
			InventoryStore mainStore =null;
			if(srl!=null){
				mainStore = inventoryService.getStoreById(srl.getStoreid());
				
			}
		int total = inventoryService.countMainStoreIndent(id ,mainStore.getId(), storeId, indentName, statusId, fromDate, toDate);
		
		String temp = "";
		if(!StringUtils.isBlank(indentName)){	
				temp = "?indentName="+indentName;
		}
		
		if(storeId != null){	
			if(StringUtils.isBlank(temp)){
				temp = "?storeId="+storeId;
			}else{
				temp +="&storeId="+storeId;
			}
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
		List<InventoryStoreDrugIndent> listIndent = inventoryService.listMainStoreIndent(id ,mainStore.getId(), storeId, indentName, statusId, fromDate, toDate, pagingUtil.getStartPos(), pagingUtil.getPageSize());
		List<InventoryStore> listStore = inventoryService.listStoreByMainStore(mainStore.getId(),false);
		List<Action> listMainStoreStatus = ActionValue.getListIndentMainStore();
		model.addAttribute("listMainStoreStatus", listMainStoreStatus);
		model.addAttribute("listIndent", listIndent);
		model.addAttribute("listStore", listStore);
		model.addAttribute("indentName", indentName);
		model.addAttribute("statusId", statusId);
		model.addAttribute("storeId", storeId);
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		model.addAttribute("pagingUtil", pagingUtil);
		model.addAttribute("viewIndent", viewIndent);
		
		return "/module/ehrinventory/mainstore/transferDrugFromGeneralStore";
	}

}
