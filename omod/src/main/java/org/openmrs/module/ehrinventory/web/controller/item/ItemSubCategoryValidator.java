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

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryItemSubCategory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ItemSubCategoryValidator implements Validator {

	/**
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
    	return InventoryItemSubCategory.class.equals(clazz);
    }

	/**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object command, Errors error) {
    	InventoryItemSubCategory subCategory = (InventoryItemSubCategory) command;
    	
    	if( StringUtils.isBlank(subCategory.getName())){
    		error.reject("ehrinventory.itemSubCategory.name.required");
    	}
    	if( subCategory.getCategory() == null){
    		error.reject("ehrinventory.itemSubCategory.category.required");
    	}
    	InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
    	InventoryItemSubCategory subCategoryE = inventoryService.getItemSubCategoryByName(subCategory.getCategory().getId() , subCategory.getName());
    	if(subCategory.getId() != null){
    		if(subCategoryE != null && subCategoryE.getId().intValue() != subCategory.getId().intValue()){
    			error.reject("ehrinventory.itemSubCategory.name.existed");
    		}
    	}else{
    		if(subCategoryE != null){
    	    		error.reject("ehrinventory.itemSubCategory.name.existed");
    		}
    	}
    	
    	
    }

}