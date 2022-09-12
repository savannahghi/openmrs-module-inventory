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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.Concept;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.Role;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.InventoryCommonService;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.InventoryDrugCategory;
import org.openmrs.module.hospitalcore.model.InventoryDrugFormulation;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugPatient;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugPatientDetail;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugTransactionDetail;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.hospitalcore.util.PatientUtils;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("IssueDrugFormController")
@RequestMapping("/module/ehrinventory/subStoreIssueDrugForm.form")
public class IssueDrugFormController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@RequestParam(value = "categoryId", required = false) Integer categoryId, Model model) {
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		//InventoryStore store =  inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
		/*if(store != null && store.getParent() != null && store.getIsDrug() != 1){
			return "redirect:/module/inventory/subStoreIssueDrugAccountForm.form";
		}*/
		InventoryCommonService inventoryCommonService = Context
				.getService(InventoryCommonService.class);
		List<Concept> drugFrequencyConcept = inventoryCommonService
				.getDrugFrequency();
		model.addAttribute("drugFrequencyList", drugFrequencyConcept);
		List<InventoryDrugCategory> listCategory = inventoryService.findDrugCategory("");
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("categoryId", categoryId);

		if (categoryId != null && categoryId > 0) {
			List<InventoryDrug> drugs = inventoryService.findDrug(categoryId, null);
			model.addAttribute("drugs", drugs);
			
		} else {
			List<InventoryDrug> drugs = inventoryService.getAllDrug();
			model.addAttribute("drugs", drugs);
		}
		
		model.addAttribute("date", new Date());
		
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "issueDrugDetail_" + userId;
		List<InventoryStoreDrugPatientDetail> list = (List<InventoryStoreDrugPatientDetail>) StoreSingleton.getInstance()
		        .getHash().get(fowardParam);
		InventoryStoreDrugPatient issueDrugPatient = (InventoryStoreDrugPatient) StoreSingleton.getInstance().getHash()
		        .get("issueDrug_" + userId);
		if (issueDrugPatient != null) {
			PatientService ps = (PatientService) Context.getService(PatientService.class);

			model.addAttribute("patientCategory",ps.getPatient(issueDrugPatient.getPatient().getId()).getAttribute(PatientUtils.PATIENT_ATTRIBUTE_CATEGORY).getValue());

			HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
			List<PersonAttribute> pas = hcs.getPersonAttributes(issueDrugPatient.getPatient().getId());
			
			String patientType = hcs.getPatientType(issueDrugPatient.getPatient());
			model.addAttribute("patientType", patientType);
			
	        for (PersonAttribute pa : pas) {
	            PersonAttributeType attributeType = pa.getAttributeType(); 
	            PersonAttributeType personAttributePCT=hcs.getPersonAttributeTypeByName("Paying Category Type");
	            PersonAttributeType personAttributeNPCT=hcs.getPersonAttributeTypeByName("Non-Paying Category Type");
	            PersonAttributeType personAttributeSSCT=hcs.getPersonAttributeTypeByName("Special Scheme Category Type");
	            if(attributeType.getPersonAttributeTypeId()==personAttributePCT.getPersonAttributeTypeId()){
	            	model.addAttribute("paymentSubCategory",pa.getValue()); 
	            }
	            else if(attributeType.getPersonAttributeTypeId()==personAttributeNPCT.getPersonAttributeTypeId()){
	            	 model.addAttribute("paymentSubCategory",pa.getValue()); 
	            }
	            else if(attributeType.getPersonAttributeTypeId()==personAttributeSSCT.getPersonAttributeTypeId()){
	            	model.addAttribute("paymentSubCategory",pa.getValue()); 
	            }
	        }
	        
/*			if(ps.getPatient(issueDrugPatient.getPatient().getId()).getAttribute(PatientUtils.PATIENT_ATTRIBUTE_CATEGORY).getValue().equals("Waiver")){
				model.addAttribute("exemption", ps.getPatient(issueDrugPatient.getPatient().getId()).getAttribute(PatientUtils.PATIENT_ATTRIBUTE_WAIVER_CATEGORY)
			            .getValue());
			}
			else if(!ps.getPatient(issueDrugPatient.getPatient().getId()).getAttribute(PatientUtils.PATIENT_ATTRIBUTE_CATEGORY).getValue().equals("General") 
					&& !ps.getPatient(issueDrugPatient.getPatient().getId()).getAttribute(PatientUtils.PATIENT_ATTRIBUTE_CATEGORY).getValue().equals("Waiver")){
				model.addAttribute("exemption", ps.getPatient(issueDrugPatient.getPatient().getId()).getAttribute(PatientUtils.PATIENT_ATTRIBUTE_EXEMPTION_CATEGORY)
			            .getValue());
			}
			else {
				model.addAttribute("exemption", " ");
			}
*/			
		}
		model.addAttribute("listPatientDetail", list);
		model.addAttribute("issueDrugPatient", issueDrugPatient);
		return "/module/ehrinventory/substore/subStoreIssueDrugForm";
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model model) {
		List<String> errors = new ArrayList<String>();
		ConceptService conceptService = Context.getConceptService();
		int drugId = 0;
		String drugN = "", drugIdStr = "";
		InventoryDrug drug = null;
		
		int userId = Context.getAuthenticatedUser().getId();
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		
		
		List<InventoryDrugCategory> listCategory = inventoryService.findDrugCategory("");
		model.addAttribute("listCategory", listCategory);
		InventoryCommonService inventoryCommonService = Context
				.getService(InventoryCommonService.class);
		
		Integer frequency=NumberUtils.toInt(request.getParameter("frequency"),0);
		Concept freCon = conceptService.getConcept(frequency);
		Integer noOfDays;
		noOfDays = NumberUtils.toInt(request.getParameter("noOfDays"),0);
		String comments;
		comments = request.getParameter("comments");
		int category = NumberUtils.toInt(request.getParameter("category"), 0);
		Integer formulation = NumberUtils.toInt(request.getParameter("formulation"), 0);
		
		
		if (request.getParameter("drugName") != null)
			drugN = request.getParameter("drugName");
		
		if (request.getParameter("drugId") != null)
			drugIdStr = request.getParameter("drugId");
		
		if (!drugN.equalsIgnoreCase("")) {
			
			drug = inventoryService.getDrugByName(drugN);
			
		} else if (!drugIdStr.equalsIgnoreCase("")) {
			drugId = Integer.parseInt(drugIdStr);
			drug = inventoryService.getDrugById(drugId);
			
			
			
			
		}
		
		if (drug == null) {
			errors.add("ehrinventory.receiptDrug.drug.required");
		} else {
			drugId = drug.getId();
			
		}
		
		InventoryDrugFormulation formulationO = inventoryService.getDrugFormulationById(formulation);
		if (formulationO == null) {
			errors.add("ehrinventory.receiptDrug.formulation.required");
		}
		if (formulationO != null && drug != null && !drug.getFormulations().contains(formulationO)) {
			errors.add("ehrinventory.receiptDrug.formulation.notCorrect");
		}
		
		if (CollectionUtils.isNotEmpty(errors)) {
			
			model.addAttribute("category", category);
			model.addAttribute("errors", errors);
			String fowardParam = "issueDrugDetail_" + userId;
			List<InventoryStoreDrugPatientDetail> list = (List<InventoryStoreDrugPatientDetail>) StoreSingleton
			        .getInstance().getHash().get(fowardParam);
			StoreSingleton.getInstance().getHash().put(fowardParam, list);
			InventoryStoreDrugPatient issueDrugPatient = (InventoryStoreDrugPatient) StoreSingleton.getInstance().getHash()
			        .get("issueDrug_" + userId);
			model.addAttribute("issueDrugPatient", issueDrugPatient);
			model.addAttribute("listPatientDetail", list);
			return "/module/ehrinventory/substore/subStoreIssueDrugForm";
		}
		
		//InventoryStore store = inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser() .getAllRoles()));
		List <Role>role=new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles());
		
		InventoryStoreRoleRelation srl=null;
		Role rl = null;
		for(Role r: role){
			if(inventoryService.getStoreRoleByName(r.toString())!=null){
				srl = inventoryService.getStoreRoleByName(r.toString());	
				rl=r;
			}
		}
		InventoryStore store =null;
		if(srl!=null){
			store = inventoryService.getStoreById(srl.getStoreid());
			
		}
		List<Integer> listIssueQty = new ArrayList<Integer>();
		List<InventoryStoreDrugTransactionDetail> listReceiptDrug = inventoryService.listStoreDrugTransactionDetail(
		    store.getId(), drug.getId(), formulation, true);
		
		boolean checkCorrect = true;
		if (listReceiptDrug != null) {
			model.addAttribute("listReceiptDrug", listReceiptDrug);
			for (InventoryStoreDrugTransactionDetail t : listReceiptDrug) {
				
				Integer temp = NumberUtils.toInt(request.getParameter(t.getId() + ""), 0);
				
				if (temp > 0) {
					checkCorrect = false;
				} else {
					temp = 0;
				}
				listIssueQty.add(temp);
				if (temp > t.getCurrentQuantity()) {
					errors.add("ehrinventory.issueDrug.quantity.lessthanQuantity.required");
				}
			}
		} else {
			errors.add("ehrinventory.issueDrug.drug.required");
		}
		if (checkCorrect) {
			errors.add("ehrinventory.issueDrug.quantity.required");
		}
		if (errors != null && errors.size() > 0) {
			
			model.addAttribute("category", category);
			model.addAttribute("formulation", formulation);
			model.addAttribute("frequency", frequency);
			model.addAttribute("noOfDays", noOfDays);
			model.addAttribute("comments", comments);
			model.addAttribute("listIssueQty", listIssueQty);
			model.addAttribute("drugId", drugId);
			model.addAttribute("errors", errors);
			String fowardParam = "issueDrugDetail_" + userId;
			List<InventoryStoreDrugPatientDetail> list = (List<InventoryStoreDrugPatientDetail>) StoreSingleton
			        .getInstance().getHash().get(fowardParam);
			
			StoreSingleton.getInstance().getHash().put(fowardParam, list);
			InventoryStoreDrugPatient issueDrugPatient = (InventoryStoreDrugPatient) StoreSingleton.getInstance().getHash()
			        .get("issueDrug_" + userId);
			
			model.addAttribute("issueDrugPatient", issueDrugPatient);
			model.addAttribute("listPatientDetail", list);
			return "/module/ehrinventory/substore/subStoreIssueDrugForm";
		}
		
		String fowardParam = "issueDrugDetail_" + userId;
		List<InventoryStoreDrugPatientDetail> list = (List<InventoryStoreDrugPatientDetail>) StoreSingleton.getInstance()
		        .getHash().get(fowardParam);
		List<InventoryStoreDrugPatientDetail> listExt = null;
		if (list == null) {
			listExt = list = new ArrayList<InventoryStoreDrugPatientDetail>();
		} else {
			listExt = new ArrayList<InventoryStoreDrugPatientDetail>(list);
		}
		for (InventoryStoreDrugTransactionDetail t : listReceiptDrug) {
			Integer temp = NumberUtils.toInt(request.getParameter(t.getId() + ""), 0);
			
			
			if (temp > 0) {
				if (CollectionUtils.isNotEmpty(list)) {
					for (int i = 0; i < list.size(); i++) {
						InventoryStoreDrugPatientDetail dtail = list.get(i);
						
						if (t.getId().equals(dtail.getTransactionDetail().getId())) {
							
							listExt.remove(i);
							temp += dtail.getQuantity();
							
							break;
						}
					}
				}
				//frequency ,no of days,comments are added in issue drug to patient
				t.setFrequency(freCon.getName().getConcept());
				t.setNoOfDays(noOfDays.intValue());
				t.setComments(comments);
				
				InventoryStoreDrugPatientDetail issueDrugDetail = new InventoryStoreDrugPatientDetail();
				issueDrugDetail.setTransactionDetail(t);
				issueDrugDetail.setQuantity(temp);
				listExt.add(issueDrugDetail);
				
				
				 
			}
		}
		
		StoreSingleton.getInstance().getHash().put(fowardParam, listExt);
		InventoryStoreDrugPatient issueDrugPatient = (InventoryStoreDrugPatient) StoreSingleton.getInstance().getHash().get("issueDrug_" + userId);
		
		//model.addAttribute("issueDrugPatient", issueDrugPatient);
		//model.addAttribute("listPatientDetail", list);
		return "redirect:/module/ehrinventory/subStoreIssueDrugForm.form";
	}
}
