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
package org.openmrs.module.ehrinventory.web.controller.mainstoreItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryItemSubCategory;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemTransactionDetail;
import org.openmrs.module.ehrinventory.util.PagingUtil;
import org.openmrs.module.ehrinventory.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("itemViewStockBalanceController")
@RequestMapping("/module/ehrinventory/itemViewStockBalance.form")
public class ViewStockBalanceController {
	@RequestMapping(method = RequestMethod.GET)
	public String list( @RequestParam(value="pageSize",required=false)  Integer pageSize, 
            @RequestParam(value="currentPage",required=false)  Integer currentPage,
            @RequestParam(value="categoryId",required=false)  Integer categoryId,
            @RequestParam(value="itemName",required=false)  String itemName,
            
            //new
            @RequestParam(value="attribute",required=false)  String attribute,
            @RequestParam(value="fromDate",required=false)  String fromDate,
            @RequestParam(value="toDate",required=false)  String toDate,
            Map<String, Object> model, HttpServletRequest request
	) {
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
	 int total = inventoryService.countStoreItemViewStockBalance(store.getId(), categoryId, itemName,attribute,  fromDate, toDate);
	 String temp = "";
		if(categoryId != null){	
				temp = "?categoryId="+categoryId;
		}
		
		if(itemName != null){	
			if(StringUtils.isBlank(temp)){
				temp = "?itemName="+itemName;
			}else{
				temp +="&itemName="+itemName;
			}
	}
		//edited
		if(attribute != null){	
			if(StringUtils.isBlank(temp)){
				temp = "?attribute="+attribute;
			}else{
				temp +="&attribute="+attribute;
			}
	}
		
		if(fromDate != null){	
			if(StringUtils.isBlank(temp)){
				temp = "?fromDate="+fromDate;
			}else{
				temp +="&fromDate="+fromDate;
			}
	}
		if(toDate != null){	
			if(StringUtils.isBlank(temp)){
				temp = "?toDate="+toDate;
			}else{
				temp +="&toDate="+toDate;
			}
	}
		
		PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request)+temp , pageSize, currentPage, total );
		List<InventoryStoreItemTransactionDetail> stockBalances = inventoryService.listStoreItemViewStockBalance(store.getId(), categoryId, itemName,attribute,  fromDate, toDate, pagingUtil.getStartPos(), pagingUtil.getPageSize());
		List<InventoryItemSubCategory> listCategory = inventoryService.listItemSubCategory("", 0, 0);
		//03/07/2012: Kesavulu:sort Item Names  #300 
		// Harsh checked for null before sorting #300
		if (stockBalances!=null)
		Collections.sort(stockBalances);
		
		model.put("categoryId", categoryId );
		model.put("itemName", itemName );
		model.put("fromDate", fromDate );
		model.put("toDate", toDate );
		model.put("pagingUtil", pagingUtil );
		model.put("stockBalances", stockBalances );
		model.put("listCategory", listCategory );
	 return "/module/ehrinventory/mainstoreItem/itemViewStockBalance";
	 
	}
}
