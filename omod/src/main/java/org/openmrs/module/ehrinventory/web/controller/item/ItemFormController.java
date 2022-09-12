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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryItem;
import org.openmrs.module.ehrinventory.model.InventoryItemCategory;
import org.openmrs.module.ehrinventory.model.InventoryItemSpecification;
import org.openmrs.module.ehrinventory.model.InventoryItemSubCategory;
import org.openmrs.module.ehrinventory.model.InventoryItemUnit;
import org.openmrs.module.hospitalcore.util.Action;
import org.openmrs.module.hospitalcore.util.ActionValue;
import org.openmrs.module.ehrinventory.web.controller.property.editor.ItemCategoryPropertyEditor;
import org.openmrs.module.ehrinventory.web.controller.property.editor.ItemSubCategoryPropertyEditor;
import org.openmrs.module.ehrinventory.web.controller.property.editor.ItemUnitPropertyEditor;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
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

@Controller("ItemFormController")
@RequestMapping("/module/ehrinventory/item.form")
public class ItemFormController {
Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("item") InventoryItem item, @RequestParam(value="itemId",required=false) Integer id, Model model) {
		if( id != null ){
			InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
			item = inventoryService.getItemById(id);
			int  countItemInTransactionDetail = inventoryService.checkExistItemTransactionDetail(item.getId());
			int  countItemInIndentDetail = inventoryService.checkExistItemIndentDetail(item.getId());
			if(countItemInIndentDetail > 0 ||  countItemInTransactionDetail > 0){
				model.addAttribute("add", "You can edit this drug");
			}
			model.addAttribute("item",item);
		}
		return "/module/ehrinventory/item/item";
	}
	/*@ModelAttribute("categories")
	public List<InventoryItemCategory> populateCategories() {
 
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		List<InventoryItemCategory> categories = inventoryService.findItemCategory("");
		return categories;
	}*/
	@ModelAttribute("subCategories")
	public List<InventoryItemSubCategory> populateSubCategories() {
 
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		List<InventoryItemSubCategory> subCategories = inventoryService.findItemSubCategory("");
		return subCategories;
	}
	@ModelAttribute("specifications")
	public List<InventoryItemSpecification> populateFormulation() {
 
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		List<InventoryItemSpecification> specifications = inventoryService.findItemSpecification("");
		return specifications;
	}
	@ModelAttribute("units")
	public List<InventoryItemUnit> populateUnit() {
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		List<InventoryItemUnit> units = inventoryService.findItemUnit("");
		return units;
	}
	@ModelAttribute("attributes")
	public List<Action> populateAttribute() {
		List<Action> attributes = ActionValue.getListItemAttribute();
		return attributes;
	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(InventoryItemCategory.class, new ItemCategoryPropertyEditor());
		binder.registerCustomEditor(InventoryItemSubCategory.class, new ItemSubCategoryPropertyEditor());
		binder.registerCustomEditor(java.lang.Integer.class,new CustomNumberEditor(java.lang.Integer.class, true));
		binder.registerCustomEditor(InventoryItemUnit.class, new ItemUnitPropertyEditor());
		binder.registerCustomEditor(Set.class, "specifications",new CustomCollectionEditor(Set.class){
			InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
			protected Object convertElement(Object element)
			    {
				  Integer specificationId = null;
			      if (element instanceof Integer)
			    	  specificationId = (Integer) element;
			      else if (element instanceof String)
			    	  specificationId = NumberUtils.toInt((String) element , 0);
			      return specificationId != null ? inventoryService.getItemSpecificationById(specificationId) : null;
			    }
		});
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("item") InventoryItem item, BindingResult bindingResult, HttpServletRequest request, SessionStatus status) {
		new ItemValidator().validate(item, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/ehrinventory/item/item";
			
		}else{
			InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
			item.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			item.setCreatedOn(new Date());
			item.setCategory(item.getSubCategory().getCategory());
			inventoryService.saveItem(item);
			status.setComplete();
			return "redirect:/module/ehrinventory/itemList.form";
		}
	}
}
