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
package org.openmrs.module.ehrinventory.web.controller.drug;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryDrugCategory;
import org.openmrs.module.ehrinventory.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller("DrugCategoryFormController")
@RequestMapping("/module/ehrinventory/drugCategory.form")
public class DrugCategoryFormController {
Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("drugCategory") InventoryDrugCategory drugCategory, @RequestParam(value="drugCategoryId",required=false) Integer id, Model model) {
		if( id != null ){
			drugCategory = Context.getService(InventoryService.class).getDrugCategoryById(id);
			model.addAttribute("drugCategory",drugCategory);
		}
		return "/module/ehrinventory/drug/drugCategory";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("drugCategory") InventoryDrugCategory drugCategory, BindingResult bindingResult, HttpServletRequest request, SessionStatus status) {
		new DrugCategoryValidator().validate(drugCategory, bindingResult);
		//storeValidator.validate(store, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "/module/ehrinventory/drug/drugCategory";
			
		}else{
			InventoryService inventoryService = (InventoryService) Context
			.getService(InventoryService.class);
			//save store
			drugCategory.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			drugCategory.setCreatedOn(new Date());
			inventoryService.saveDrugCategory(drugCategory);
			status.setComplete();
			return "redirect:/module/ehrinventory/drugCategoryList.form";
		}
	}
	
}
