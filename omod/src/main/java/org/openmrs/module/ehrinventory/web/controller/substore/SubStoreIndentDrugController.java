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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.InventoryDrugCategory;
import org.openmrs.module.hospitalcore.model.InventoryDrugFormulation;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryStoreDrugIndentDetail;
import org.openmrs.module.ehrinventory.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("SubStoreIndentDrugController")
@RequestMapping("/module/ehrinventory/subStoreIndentDrug.form")
public class SubStoreIndentDrugController {
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(
			@RequestParam(value="categoryId",required=false)  Integer categoryId,
			Model model) {
	 InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
	 List<InventoryDrugCategory> listCategory = inventoryService.findDrugCategory("");
	 model.addAttribute("listCategory", listCategory);
	 model.addAttribute("categoryId", categoryId);
	 if(categoryId != null && categoryId > 0){
		 List<InventoryDrug> drugs = inventoryService.findDrug(categoryId, null);
		 model.addAttribute("drugs",drugs);
		 
	 }
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
	 model.addAttribute("store",store);
	 model.addAttribute("date",new Date());
 	 int userId = Context.getAuthenticatedUser().getId();
	 String fowardParam = "subStoreIndentDrug_"+userId;
	 List<InventoryStoreDrugIndentDetail> list = (List<InventoryStoreDrugIndentDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
	 model.addAttribute("listIndent", list);
	 
	 return "/module/ehrinventory/substore/subStoreIndentDrug";
	 
	}
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model model) {
		List<String> errors = new ArrayList<String>();
		InventoryDrug drug=null;
		String drugN="",drugIdStr="";
		int drugId=-1;
		
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		 List<InventoryDrugCategory> listCategory = inventoryService.findDrugCategory("");
		 model.addAttribute("listCategory", listCategory);
	//	int category = NumberUtils.toInt(request.getParameter("category"),0);
		int formulation = NumberUtils.toInt(request.getParameter("formulation"),0);
		
		
		if (request.getParameter("drugName")!=null)
		drugN=request.getParameter("drugName");
		if (request.getParameter("drugId")!=null)
	 drugIdStr=request.getParameter("drugId");
		
		if (!drugN.equalsIgnoreCase("")){
			
			 drug=inventoryService.getDrugByName(drugN);
		}else if (!drugIdStr.equalsIgnoreCase("")){
			drugId=Integer.parseInt(drugIdStr);
			 drug=inventoryService.getDrugById(drugId);
		}
		
		
		if(drug == null){
			errors.add("ehrinventory.indent.drug.required");
			
		} else 
			{
			drugId= drug.getId();
			}
		
		
		int quantity = NumberUtils.toInt(request.getParameter("quantity"),0);
		
		
		InventoryDrugFormulation formulationO = inventoryService.getDrugFormulationById(formulation);
		if(formulationO == null)
		{
			errors.add("ehrinventory.receiptDrug.formulation.required");
		}
		
	
		if(formulationO != null && drug != null && !drug.getFormulations().contains(formulationO))
		{
			errors.add("ehrinventory.receiptDrug.formulation.notCorrect");
		}
		
		if(CollectionUtils.isNotEmpty(errors)){
		//	model.addAttribute("category", category);
			model.addAttribute("formulation", formulation);
			model.addAttribute("drugId", drugId);
			model.addAttribute("quantity", quantity);
			model.addAttribute("errors", errors);
			return "/module/ehrinventory/substore/subStoreIndentDrug";
		}
		InventoryStoreDrugIndentDetail indentDetail = new InventoryStoreDrugIndentDetail();
		indentDetail.setDrug(drug);
		indentDetail.setFormulation(inventoryService.getDrugFormulationById(formulation));
		indentDetail.setQuantity(quantity);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "subStoreIndentDrug_"+userId;
		List<InventoryStoreDrugIndentDetail> list = (List<InventoryStoreDrugIndentDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
		
		List<InventoryStoreDrugIndentDetail> listExt = null;
		if(list == null){
			listExt = list = new ArrayList<InventoryStoreDrugIndentDetail>();
		}else{
			listExt = new ArrayList<InventoryStoreDrugIndentDetail>(list);
		}
		for(int i=0;i< list.size();i ++ ){
			InventoryStoreDrugIndentDetail d = list.get(i);
			
			if(d.getDrug().getId().equals(indentDetail.getDrug().getId()) && 
					d.getFormulation().getId().equals(indentDetail.getFormulation().getId()))
			{
				indentDetail.setQuantity(indentDetail.getQuantity() + d.getQuantity());
				listExt.remove(i);
				break;
			}
		}
		
		listExt.add(indentDetail);
		StoreSingleton.getInstance().getHash().put(fowardParam, listExt);
		//model.addAttribute("listIndent", list);
	 return "redirect:/module/ehrinventory/subStoreIndentDrug.form";
}
}