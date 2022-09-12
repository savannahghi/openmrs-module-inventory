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

import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.model.OpdDrugOrder;
import org.openmrs.module.hospitalcore.model.PatientSearch;
import org.openmrs.module.ehrinventory.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("ListOfDrugOrderController")
@RequestMapping("/module/ehrinventory/listoforder.form")
public class ListOfDrugOrderController {
	@RequestMapping(method = RequestMethod.GET)
	public String main(Model model, 
                        @RequestParam("patientId") Integer patientId,
			@RequestParam(value = "date", required = false) String dateStr) {
		InventoryService inventoryService = Context
				.getService(InventoryService.class);
		PatientService patientService = Context.getPatientService();
		Patient patient = patientService.getPatient(patientId);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<OpdDrugOrder> listOfOrders = inventoryService.listOfOrder(patientId,date);
		HospitalCoreService hospitalCoreService = Context.getService(HospitalCoreService.class);
		PatientSearch patientSearch = hospitalCoreService.getPatientByPatientId(patientId);
		Patient p = new Patient(patientId);
	
		String patientType = hospitalCoreService.getPatientType(p);
		
		model.addAttribute("patientType", patientType);
		model.addAttribute("patientSearch", patientSearch);
		model.addAttribute("listOfOrders", listOfOrders);
		//model.addAttribute("serviceOrderSize", serviceOrderList.size());
		model.addAttribute("patientId", patientId);
		model.addAttribute("date", dateStr);
                
                
                
		return "/module/ehrinventory/queue/listOfOrder";
	}
}
