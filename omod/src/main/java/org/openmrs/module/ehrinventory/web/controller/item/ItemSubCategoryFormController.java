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
package org.openmrs.module.ehrinventory.web.controller.item;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryItemCategory;
import org.openmrs.module.ehrinventory.model.InventoryItemSubCategory;
import org.openmrs.module.ehrinventory.web.controller.property.editor.ItemCategoryPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller("ItemSubCategoryFormController")
@RequestMapping("/module/ehrinventory/itemSubCategory.form")
public class ItemSubCategoryFormController {
Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("itemSubCategory") InventoryItemSubCategory itemSubCategory, @RequestParam(value="itemSubCategoryId",required=false) Integer id, Model model) {
		if( id != null ){
			itemSubCategory = Context.getService(InventoryService.class).getItemSubCategoryById(id);
			model.addAttribute("itemSubCategory",itemSubCategory);
		}
		return "/module/ehrinventory/item/itemSubCategory";
	}
	@ModelAttribute("categories")
	public List<InventoryItemCategory> populateCategories() {
 
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		List<InventoryItemCategory> categories = inventoryService.findItemCategory("");
		return categories;
	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(InventoryItemCategory.class, new ItemCategoryPropertyEditor());
		
	}
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("itemSubCategory") InventoryItemSubCategory itemSubCategory, BindingResult bindingResult, HttpServletRequest request, SessionStatus status) {
		new ItemSubCategoryValidator().validate(itemSubCategory, bindingResult);
		//storeValidator.validate(store, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "/module/ehrinventory/item/itemSubCategory";
			
		}else{
			InventoryService inventoryService = (InventoryService) Context
			.getService(InventoryService.class);
			//save store
			itemSubCategory.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			itemSubCategory.setCreatedOn(new Date());
			inventoryService.saveItemSubCategory(itemSubCategory);
			status.setComplete();
			return "redirect:/module/ehrinventory/itemSubCategoryList.form";
		}
	}
	
}
