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

package org.openmrs.module.ehrinventory.web.controller.patientqueuedrugorder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.openmrs.module.hospitalcore.model.PatientSearch;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.util.PagingUtil;

@Controller("PatientSearchForDrugQueueController")
@RequestMapping("/module/ehrinventory/patientsearchdruggqueue.form")
public class PatientSearchForDrugQueueController {
	@RequestMapping(method = RequestMethod.GET)
	public String main(
			@RequestParam(value = "date", required = false) String dateStr,
			@RequestParam(value = "searchKey", required = false) String searchKey,
			@RequestParam(value = "currentPage", required = false) Integer currentPage,
                        // 26/11/2014 to work with size selector
                        @RequestParam(value = "pgSize", required = false) Integer pgSize,
			Model model) {
		InventoryService inventoryService = Context
				.getService(InventoryService.class);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<PatientSearch> patientSearchResult = inventoryService.searchListOfPatient(date, searchKey, currentPage,pgSize);
		if (currentPage == null) currentPage = 1;
		int total = inventoryService.countSearchListOfPatient(date, searchKey, currentPage);
		PagingUtil pagingUtil = new PagingUtil(pgSize,	currentPage, total);
		model.addAttribute("pagingUtil", pagingUtil);
		model.addAttribute("patientList", patientSearchResult);
		model.addAttribute("date", dateStr);
		return "/module/ehrinventory/queue/searchResult";
	}
}
