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

import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.InventoryDrugCategory;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryStoreDrugIndentDetail;
import org.openmrs.module.ehrinventory.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("PurchaseOrderFormController")
@RequestMapping("/module/ehrinventory/purchaseOrderForGeneralStore.form")
public class PurchaseOrderFormController {
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(
			@RequestParam(value="categoryId",required=false)  Integer categoryId,
			Model model) {
	 InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
	 List<InventoryDrugCategory> listCategory = inventoryService.findDrugCategory("");
	 model.addAttribute("listCategory", listCategory);
	 model.addAttribute("categoryId", categoryId);
	 
 	 int userId = Context.getAuthenticatedUser().getId();
	 String fowardParam = "purchase_"+userId;
	 List<InventoryStoreDrugIndentDetail> list = (List<InventoryStoreDrugIndentDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
	 model.addAttribute("listPurchase", list);
	 
	 return "/module/ehrinventory/mainstore/purchaseOrderForGeneralStore";
	 
	}
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model model) {
		List<String> errors = new ArrayList<String>();
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		 List<InventoryDrugCategory> listCategory = inventoryService.findDrugCategory("");
		 model.addAttribute("listCategory", listCategory);
		int category = NumberUtils.toInt(request.getParameter("category"),0);
		int formulation = NumberUtils.toInt(request.getParameter("formulation"),0);
		String drugName = request.getParameter("drugName");
		int quantity = NumberUtils.toInt(request.getParameter("quantity"),0);
		
		InventoryDrug drug = inventoryService.getDrugByName(drugName);
		if(drug == null){
			errors.add("ehrinventory.purchase.drug.required");
			model.addAttribute("category", category);
			model.addAttribute("formulation", formulation);
			model.addAttribute("drugName", drugName);
			model.addAttribute("quantity", quantity);
		
			return "/module/ehrinventory/mainstore/purchaseOrderForGeneralStore";
		}
		
		InventoryStoreDrugIndentDetail indentDetail = new InventoryStoreDrugIndentDetail();
		indentDetail.setDrug(drug);
		indentDetail.setFormulation(inventoryService.getDrugFormulationById(formulation));
		indentDetail.setQuantity(quantity);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "purchase_"+userId;
		List<InventoryStoreDrugIndentDetail> list = (List<InventoryStoreDrugIndentDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
		if(list == null){
			list = new ArrayList<InventoryStoreDrugIndentDetail>();
		}
		list.add(indentDetail);
		StoreSingleton.getInstance().getHash().put(fowardParam, list);
		model.addAttribute("listPurchase", list);
	 return "/module/ehrinventory/mainstore/purchaseOrderForGeneralStore";
	}
}
