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
import org.openmrs.module.hospitalcore.model.InventoryDrugUnit;
import org.openmrs.module.ehrinventory.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller("DrugUnitFormController")
@RequestMapping("/module/ehrinventory/drugUnit.form")
public class DrugUnitFormController {
Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("drugUnit") InventoryDrugUnit drugUnit, @RequestParam(value="drugUnitId",required=false) Integer id, Model model) {
		if( id != null ){
			drugUnit = Context.getService(InventoryService.class).getDrugUnitById(id);
			model.addAttribute("drugUnit",drugUnit);
		}
		return "/module/ehrinventory/drug/drugUnit";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("drugUnit") InventoryDrugUnit drugUnit, BindingResult bindingResult, HttpServletRequest request, SessionStatus status) {
		new DrugUnitValidator().validate(drugUnit, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/ehrinventory/drug/drugUnit";
			
		}else{
			InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
			drugUnit.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			drugUnit.setCreatedOn(new Date());
			inventoryService.saveDrugUnit(drugUnit);
			status.setComplete();
			return "redirect:/module/ehrinventory/drugUnitList.form";
		}
	}
	
}
