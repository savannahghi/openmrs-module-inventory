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

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugPatient;
import org.springframework.validation.Errors;

public class IssueDrugPatientValidator {
	/**
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
    	return InventoryStoreDrugPatient.class.equals(clazz);
    }

	/**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object command, Errors error) {
    	InventoryStoreDrugPatient cmd = (InventoryStoreDrugPatient) command;
    	String identifier = cmd.getIdentifier();
    	String prefix = Context.getAdministrationService().getGlobalProperty("registration.identifier_prefix");
    	if( identifier.contains("-") && !identifier.contains(prefix)){
			identifier = prefix+identifier;
    	}
    	if( StringUtils.isBlank(cmd.getIdentifier())){
    		error.reject("ehrinventory.issueDrug.identifier.required");
    	}
    	if(Context.getPatientService().getPatients(identifier) == null){
    		error.reject("ehrinventory.issueDrug.identifier.required");
    	}
    	
    	
    }
}
