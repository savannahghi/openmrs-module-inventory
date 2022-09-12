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
package org.openmrs.module.ehrinventory.web.controller.substoreItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.Patient;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemPatient;
import org.openmrs.module.ehrinventory.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("CreatePatientIssueItemController")
@RequestMapping("/module/ehrinventory/createPatientIssueItem.form")
public class CreatePatientIssueItemController {

	@RequestMapping(method = RequestMethod.GET)
	public String firstView(Model model, @RequestParam(value="patientId",required=false)  Integer patientId) {
		
		if(patientId != null && patientId > 0){
			Patient patient = Context.getPatientService().getPatient(patientId);
			if(patient != null){
				InventoryStoreItemPatient issue = new InventoryStoreItemPatient();
				InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
				int userId = Context.getAuthenticatedUser().getId();
				//InventoryStore subStore =  inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
				 List <Role>role=new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles());
					
					InventoryStoreRoleRelation srl=null;
					Role rl = null;
					for(Role r: role){
						if(inventoryService.getStoreRoleByName(r.toString())!=null){
							srl = inventoryService.getStoreRoleByName(r.toString());	
							rl=r;
						}
					}
					InventoryStore subStore =null;
					if(srl!=null){
						subStore = inventoryService.getStoreById(srl.getStoreid());
						
					}
				issue.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
				issue.setCreatedOn(new Date());
				issue.setStore(subStore);
				issue.setIdentifier(patient.getPatientIdentifier().getIdentifier());
				if(patient.getMiddleName()!=null){
					issue.setName(patient.getGivenName()+" "+patient.getFamilyName()+" "+patient.getMiddleName().replace(","," "));
				}
				else {
					issue.setName(patient.getGivenName()+" "+patient.getFamilyName());
				}
				issue.setPatient(patient);
				String fowardParam = "issueItemPatient_"+userId;
				StoreSingleton.getInstance().getHash().put(fowardParam,issue);
				return "redirect:/module/ehrinventory/subStoreIssueItemPatientForm.form";
			}
			
		}
		return "/module/ehrinventory/substoreItem/createPatientIssueItem";
	}
	
}
