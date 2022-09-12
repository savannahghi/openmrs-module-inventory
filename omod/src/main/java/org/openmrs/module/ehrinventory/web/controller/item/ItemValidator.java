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
import org.openmrs.module.ehrinventory.model.InventoryItem;
import org.springframework.validation.Errors;

public class ItemValidator {
	/**
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
    	return InventoryItem.class.equals(clazz);
    }

	/**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object command, Errors error) {
    	InventoryItem item = (InventoryItem) command;
    	
    	if( StringUtils.isBlank(item.getName())){
    		error.reject("ehrinventory.item.name.required");
    	}
    	/*if( item.getCategory() == null){
    		error.reject("inventory.item.category.required");
    	}*/
    	if( item.getSubCategory() == null){
    		error.reject("ehrinventory.item.subCategory.required");
    	}
    	if( item.getUnit() == null ){
    		error.reject("ehrinventory.item.unit.required");
    	}
    	InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
    	InventoryItem itemE = inventoryService.getItemByName(item.getName());
    	if(item.getId() != null){
    	/*	int  countItemInTransactionDetail = inventoryService.checkExistItemTransactionDetail(item.getId());
			int  countItemInIndentDetail = inventoryService.checkExistItemIndentDetail(item.getId());
			if(countItemInIndentDetail > 0 ||  countItemInTransactionDetail > 0){
    			item.setSpecifications(inventoryService.getItemById(item.getId()).getSpecifications());
			}*/
    		if(itemE != null){
    			if(itemE.getId().intValue() != item.getId().intValue()){
    				error.reject("ehrinventory.item.name.existed");
    			}
    		}
    	}else{
    		if(itemE != null){
    	    		error.reject("ehrinventory.item.name.existed");
    		}
    	}
    	
    	
    }
}
