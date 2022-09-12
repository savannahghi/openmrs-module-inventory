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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryItem;
import org.openmrs.module.ehrinventory.model.InventoryItemSubCategory;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemAccount;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemAccountDetail;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemTransactionDetail;
import org.openmrs.module.ehrinventory.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller("IssueItemFormController")
@RequestMapping("/module/ehrinventory/subStoreIssueItemForm.form")
public class IssueItemFormController {
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(
			@RequestParam(value="categoryId",required=false)  Integer categoryId,
			Model model) {
	 InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
	 List<InventoryItemSubCategory> listCategory = inventoryService.listItemSubCategory("", 0, 0);
	 model.addAttribute("listCategory", listCategory);
	 model.addAttribute("categoryId", categoryId);
	 if(categoryId != null && categoryId > 0){
		 List<InventoryItem> items = inventoryService.findItem(categoryId, null);
		 model.addAttribute("items",items);
			
	 }
	 model.addAttribute("date",new Date());
 	 int userId = Context.getAuthenticatedUser().getId();
	 String fowardParam = "issueItemDetail_"+userId;
	 List<InventoryStoreItemAccountDetail> list = (List<InventoryStoreItemAccountDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
	 InventoryStoreItemAccount issueItemAccount = (InventoryStoreItemAccount )StoreSingleton.getInstance().getHash().get("issueItem_"+userId);
	 model.addAttribute("listAccountDetail", list);
	 model.addAttribute("issueItemAccount", issueItemAccount);
	 return "/module/ehrinventory/substoreItem/subStoreIssueItemForm";
	 
	}
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model model) {
		List<String> errors = new ArrayList<String>();
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		 List<InventoryItemSubCategory> listCategory = inventoryService.listItemSubCategory("", 0, 0);
		 model.addAttribute("listCategory", listCategory);
		int category = NumberUtils.toInt(request.getParameter("category"),0);
		Integer specification = NumberUtils.toInt(request.getParameter("specification"),0);
		Integer itemId = NumberUtils.toInt(request.getParameter("itemId"), 0);
		int userId = Context.getAuthenticatedUser().getId();
		Integer issueItemQuantity = NumberUtils.toInt(request.getParameter("issueItemQuantity"),0);
		InventoryItem item = inventoryService.getItemById(itemId);
		
		if(item == null || (item.getSpecifications() != null && item.getSpecifications().size() > 0 && specification <=0)){
			errors.add("inventory.issueItem.quantity.required");
			model.addAttribute("errors", errors);
			model.addAttribute("category", category);
			String fowardParam = "issueItemDetail_"+userId;
			List<InventoryStoreItemAccountDetail> list = (List<InventoryStoreItemAccountDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
			 InventoryStoreItemAccount issueItemAccount = (InventoryStoreItemAccount )StoreSingleton.getInstance().getHash().get("issueItem_"+userId);
			 model.addAttribute("issueItemAccount", issueItemAccount);
			model.addAttribute("listAccountDetail", list);
			return "/module/ehrinventory/substoreItem/subStoreIssueItemForm";
		}
		
		
		InventoryStore store =  inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
		
		Integer sumCurrentOfItem = inventoryService.sumStoreItemCurrentQuantity(store.getId(), item.getId(), specification);
		if(sumCurrentOfItem == 0 || issueItemQuantity <= 0 ){
			errors.add("ehrinventory.issueItem.quantity.required");
		}
		if(sumCurrentOfItem < issueItemQuantity){
			errors.add("ehrinventory.issueItem.quantity.lessthanQuantity.required");
		}
		
		if(errors != null && errors.size() > 0){
			String fowardParam = "issueItemDetail_"+userId;
			List<InventoryStoreItemAccountDetail> list = (List<InventoryStoreItemAccountDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
			 InventoryStoreItemAccount issueItemAccount = (InventoryStoreItemAccount )StoreSingleton.getInstance().getHash().get("issueItem_"+userId);
			 model.addAttribute("issueItemAccount", issueItemAccount);
			model.addAttribute("listAccountDetail", list);
			model.addAttribute("category", category);
			model.addAttribute("specification", specification);
			model.addAttribute("issueItemQuantity", issueItemQuantity);
			model.addAttribute("itemId", itemId);
			model.addAttribute("errors", errors);
			return "/module/ehrinventory/substoreItem/subStoreIssueItemForm";
		}
		
		
		String fowardParam = "issueItemDetail_"+userId;
		List<InventoryStoreItemAccountDetail> list = (List<InventoryStoreItemAccountDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
		
		List<InventoryStoreItemTransactionDetail> listReceiptItem = inventoryService.listStoreItemTransactionDetail(store.getId(), item.getId(), specification, true);
		if(list == null){
			list = new ArrayList<InventoryStoreItemAccountDetail>();
		}
		List<InventoryStoreItemAccountDetail> listExt = new ArrayList<InventoryStoreItemAccountDetail>(list); 
		if(CollectionUtils.isNotEmpty(list)){
			for(InventoryStoreItemAccountDetail tDetail : list){
				if(tDetail.getTransactionDetail().getItem().getId().equals(item.getId()) ){
					if(tDetail.getTransactionDetail().getSpecification() != null && specification != null  ){
						if(tDetail.getTransactionDetail().getSpecification().getId().equals(specification)){
							issueItemQuantity += tDetail.getQuantity();
							listExt.remove(tDetail);
						}
					}else{
						if(tDetail.getTransactionDetail().getSpecification() == null ){
							issueItemQuantity += tDetail.getQuantity();
							listExt.remove(tDetail);
						}
					}
				}
			}
		}
		
		
		
		for(InventoryStoreItemTransactionDetail t: listReceiptItem){
			InventoryStoreItemAccountDetail issueItemDetail = new InventoryStoreItemAccountDetail();
			//ghanshyam 7-august-2013 code review bug
			if(t.getItem().getId().equals(item.getId())){
				if(t.getCurrentQuantity() >= issueItemQuantity){
					issueItemDetail.setTransactionDetail(t);
					issueItemDetail.setQuantity(issueItemQuantity);
					listExt.add(issueItemDetail);
					break;
				}else{
					issueItemDetail.setTransactionDetail(t);
					issueItemDetail.setQuantity(t.getCurrentQuantity());
					issueItemQuantity -= t.getCurrentQuantity();
					listExt.add(issueItemDetail);
					
				}
				
			}
		}
		StoreSingleton.getInstance().getHash().put(fowardParam, listExt);
		 InventoryStoreItemAccount issueItemAccount = (InventoryStoreItemAccount )StoreSingleton.getInstance().getHash().get("issueItem_"+userId);
		// model.addAttribute("issueItemAccount", issueItemAccount);
		//model.addAttribute("listAccountDetail", list);
	 return "redirect:/module/ehrinventory/subStoreIssueItemForm.form";
	}
}
