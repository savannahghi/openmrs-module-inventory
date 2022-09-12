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
package org.openmrs.module.ehrinventory.web.controller.store;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugIndent;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugTransaction;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemIndent;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemTransaction;
import org.openmrs.module.ehrinventory.util.PagingUtil;
import org.openmrs.module.ehrinventory.util.RequestUtil;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("storeListController")
@RequestMapping("/module/ehrinventory/storeList.form")
public class StoreListController {
	 Log log = LogFactory.getLog(this.getClass());
	@RequestMapping(method=RequestMethod.POST)
    public String deleteStores(@RequestParam("ids") String[] ids,HttpServletRequest request){
		String temp = "";
    	HttpSession httpSession = request.getSession();
		Integer storeId  = null;
		try{
			InventoryService inventoryService = (InventoryService)Context.getService(InventoryService.class);
			if( ids != null && ids.length > 0 ){
				for(String sId : ids )
				{
					storeId = Integer.parseInt(sId);
					InventoryStore store = inventoryService.getStoreById(storeId);
					List<InventoryStoreItemTransaction> listItemTransaction = inventoryService.listStoreItemTransaction(null, storeId, "", "", "", 0, 1);
					List<InventoryStoreDrugTransaction> listdrugTransaction = inventoryService.listStoreDrugTransaction(null, storeId, "", "", "", 0, 1);
					List<InventoryStoreItemIndent> listItemIndent= inventoryService.listStoreItemIndent(storeId, "", "", "", 0, 1);
					List<InventoryStoreDrugIndent> listDrugIndent= inventoryService.listStoreDrugIndent(storeId, "", "", "", 0, 1);
					if( store!= null && CollectionUtils.isEmpty(listItemTransaction) && CollectionUtils.isEmpty(listdrugTransaction) && CollectionUtils.isEmpty(listItemIndent) && CollectionUtils.isEmpty(listDrugIndent))
					{
						inventoryService.deleteStore(store);
					}else{
						//temp += "We can't delete store="+store.getName()+" because that store is using please check <br/>";
						temp = "This store/stores cannot be deleted as it is in use";
					}
				}
			}
		}catch (Exception e) {
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
			"Can not delete store ");
			log.error(e);
		}
		httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, StringUtils.isBlank(temp) ?  "store.deleted" : temp);
    	
    	return "redirect:/module/ehrinventory/storeList.form";
    }
	
	@RequestMapping(method=RequestMethod.GET)
	public String listStore(@RequestParam(value="pageSize",required=false)  Integer pageSize, 
	                         @RequestParam(value="currentPage",required=false)  Integer currentPage,
	                         Map<String, Object> model, HttpServletRequest request){
		
		InventoryService inventoryService = Context.getService(InventoryService.class);
		
		int total = inventoryService.countListStore();
		
		PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request) , pageSize, currentPage, total );
		
		List<InventoryStore> stores = inventoryService.listInventoryStore(pagingUtil.getStartPos(), pagingUtil.getPageSize());
		
		List<InventoryStoreRoleRelation> roles = inventoryService.listInventoryStoreRole();
		
		model.put("roless", roles );
		
		model.put("stores", stores );
		
		model.put("pagingUtil", pagingUtil);
		
		return "/module/ehrinventory/store/list";
	}
}
