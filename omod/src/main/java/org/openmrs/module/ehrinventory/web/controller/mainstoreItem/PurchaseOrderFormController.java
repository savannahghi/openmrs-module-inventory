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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryItem;
import org.openmrs.module.ehrinventory.model.InventoryItemSubCategory;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemIndentDetail;
import org.openmrs.module.ehrinventory.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("itemPurchaseOrderFormController")
@RequestMapping("/module/ehrinventory/itemPurchaseOrderForGeneralStore.form")
public class PurchaseOrderFormController {
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(
			@RequestParam(value="categoryId",required=false)  Integer categoryId,
			Model model) {
	 InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
	 List<InventoryItemSubCategory> listCategory = inventoryService.listItemSubCategory("", 0, 0);
	 model.addAttribute("listCategory", listCategory);
	 model.addAttribute("categoryId", categoryId);
	 
 	 int userId = Context.getAuthenticatedUser().getId();
	 String fowardParam = "itemPurchase_"+userId;
	 List<InventoryStoreItemIndentDetail> list = (List<InventoryStoreItemIndentDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
	 model.addAttribute("listPurchase", list);
	 
	 return "/module/ehrinventory/mainstoreItem/itemPurchaseOrderForGeneralStore";
	 
	}
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model model) {
		List<String> errors = new ArrayList<String>();
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		 List<InventoryItemSubCategory> listCategory = inventoryService.listItemSubCategory("", 0, 0);
		 model.addAttribute("listCategory", listCategory);
		int category = NumberUtils.toInt(request.getParameter("category"),0);
		int specification = NumberUtils.toInt(request.getParameter("specification"),0);
		String itemName = request.getParameter("itemName");
		int quantity = NumberUtils.toInt(request.getParameter("quantity"),0);
		
		InventoryItem item = inventoryService.getItemByName(itemName);
		if(item == null){
			errors.add("inventory.purchase.item.required");
			model.addAttribute("category", category);
			model.addAttribute("specification", specification);
			model.addAttribute("itemName", itemName);
			model.addAttribute("quantity", quantity);
		
			return "/module/ehrinventory/mainstoreItem/itemPurchaseOrderForGeneralStore";
		}
		
		InventoryStoreItemIndentDetail indentDetail = new InventoryStoreItemIndentDetail();
		indentDetail.setItem(item);
		indentDetail.setSpecification(inventoryService.getItemSpecificationById(specification));
		indentDetail.setQuantity(quantity);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "itemPurchase_"+userId;
		List<InventoryStoreItemIndentDetail> list = (List<InventoryStoreItemIndentDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
		if(list == null){
			list = new ArrayList<InventoryStoreItemIndentDetail>();
		}
		list.add(indentDetail);
		StoreSingleton.getInstance().getHash().put(fowardParam, list);
		model.addAttribute("listPurchase", list);
	 return "/module/ehrinventory/mainstoreItem/itemPurchaseOrderForGeneralStore";
	}
}
