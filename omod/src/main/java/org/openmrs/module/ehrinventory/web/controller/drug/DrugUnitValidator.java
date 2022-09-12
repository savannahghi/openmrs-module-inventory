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

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryDrugUnit;
import org.openmrs.module.ehrinventory.InventoryService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DrugUnitValidator implements Validator {

	/**
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
    	return InventoryDrugUnit.class.equals(clazz);
    }

	/**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object command, Errors error) {
    	InventoryDrugUnit unit = (InventoryDrugUnit) command;
    	
    	if( StringUtils.isBlank(unit.getName())){
    		error.reject("ehrinventory.drugUnit.name.required");
    	}
    	InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
    	InventoryDrugUnit unitE = inventoryService.getDrugUnitByName(unit.getName());
    	if(unit.getId() != null){
    		if(unitE != null && unitE.getId().intValue() != unit.getId().intValue()){
    			error.reject("ehrinventory.drugUnit.name.existed");
    		}
    	}else{
    		if(unitE != null){
    	    		error.reject("ehrinventory.drugUnit.name.existed");
    		}
    	}
    	
    	
    }

}