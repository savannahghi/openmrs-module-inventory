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
package org.openmrs.module.ehrinventory.web.controller.autocomplete;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.openmrs.Drug;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller("AutoCompleteController")
public class AutoCompleteController {
		@RequestMapping("/module/ehrinventory/autoCompleteDrugCoreList.form")
		public String drugCore(@RequestParam(value="q",required=false) String name, Model model) {
			List<Drug> drugs = new ArrayList<Drug>();
			if(!StringUtils.isBlank(name)){
				drugs = Context.getConceptService().getDrugs(name);
			}else{
				drugs = Context.getConceptService().getAllDrugs();
			}
				model.addAttribute("drugs",drugs);
			return "/module/ehrinventory/autocomplete/autoCompleteDrugCoreList";
		}
		@RequestMapping("/module/ehrinventory/autoCompleteDrugList.form")
		public String drug(@RequestParam(value="q",required=false) String name,@RequestParam(value="categoryId",required=false) Integer categoryId, Model model) {
			InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
			List<InventoryDrug> drugs = inventoryService.findDrug(categoryId, name);
			model.addAttribute("drugs",drugs);
			return "/module/ehrinventory/autocomplete/autoCompleteDrugList";
		}
		@RequestMapping("/module/ehrinventory/autoCompleteItemList.form")
		public String item(@RequestParam(value="term",required=false) String name,@RequestParam(value="categoryId",required=false) Integer categoryId, Model model) {
			InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
			List<InventoryItem> items = inventoryService.findItem(categoryId, name);
			model.addAttribute("items",items);
			return "/module/ehrinventory/autocomplete/autoCompleteItemList";
		}
		
		@RequestMapping("/module/ehrinventory/checkSession.form")
		public String checkSession(HttpServletRequest request,Model model) {
			 if( Context.getAuthenticatedUser() != null &&  Context.getAuthenticatedUser().getId() != null){
				 model.addAttribute("session","1");
			 }else{
				 model.addAttribute("session","0");
			 }
			
			return "/module/ehrinventory/session/checkSession";
		}

	public static void main(String[] args) {
		Date date = DateUtils.addDays(new Date(), 93);
		System.out.println("date: "+date);
		Date currentDate = new Date();
		Date date3Month = DateUtils.addMonths(currentDate, 3);
		System.out.println(date3Month);
		if(date.before(date3Month)){
			System.out.println("Can alert");
		}else{
			
		}
	}	
	}
