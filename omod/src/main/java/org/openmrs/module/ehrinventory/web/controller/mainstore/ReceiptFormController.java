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
package org.openmrs.module.ehrinventory.web.controller.mainstore;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.InventoryDrugCategory;
import org.openmrs.module.hospitalcore.model.InventoryDrugFormulation;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugTransactionDetail;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.util.DateUtils;
import org.openmrs.module.ehrinventory.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("ReceiptFormController")
@RequestMapping("/module/ehrinventory/receiptsToGeneralStore.form")
public class ReceiptFormController {
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(
			@RequestParam(value="categoryId",required=false)  Integer categoryId,
			Model model) {
	 InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
	 List<InventoryDrugCategory> listCategory = inventoryService.findDrugCategory("");
	 model.addAttribute("listCategory", listCategory);
	 model.addAttribute("categoryId", categoryId);
	 if(categoryId != null && categoryId > 0){
		 List<InventoryDrug> drugs = inventoryService.findDrug(categoryId, null);
		 model.addAttribute("drugs",drugs);
		 
	 }
	 model.addAttribute("date",new Date());
	// InventoryStore store = inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
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
	 model.addAttribute("store",store);
 	 int userId = Context.getAuthenticatedUser().getId();
	 String fowardParam = "reipt_"+userId;
	 List<InventoryStoreDrugTransactionDetail> list = (List<InventoryStoreDrugTransactionDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
	 model.addAttribute("listReceipt", list);
	 
	 return "/module/ehrinventory/mainstore/receiptsToGeneralStore";
	 
	}
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model model) {
		List<String> errors = new ArrayList<String>();
		int drugId=0;
		String drugN="",drugIdStr="";
		InventoryDrug drug=null;
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		 List<InventoryDrugCategory> listCategory = inventoryService.findDrugCategory("");
		 model.addAttribute("listCategory", listCategory);
		//int category = NumberUtils.toInt(request.getParameter("category"),0);
		int formulation = NumberUtils.toInt(request.getParameter("formulation"),0);
		
		if (request.getParameter("drugName")!=null)
		drugN=request.getParameter("drugName");
		if (request.getParameter("drugId")!=null)
	 drugIdStr=request.getParameter("drugId");
		
		if (!drugN.equalsIgnoreCase("")){
			
			 drug=inventoryService.getDrugByName(drugN);
		}else if (!drugIdStr.equalsIgnoreCase("")){
			drugId=Integer.parseInt(drugIdStr);
			 drug=inventoryService.getDrugById(drugId);
		}
		
		if(drug == null){
			errors.add("ehrinventory.receiptDrug.drug.required");
		}else{
		 drugId = drug.getId();
		}
		int quantity = NumberUtils.toInt(request.getParameter("quantity"),0);
		BigDecimal VAT  = new BigDecimal(0);
		BigDecimal unitPrice  = new BigDecimal(0);
		if(null!=request.getParameter("VAT") && ""!=request.getParameter("VAT"))
		{
			VAT = NumberUtils.createBigDecimal(request.getParameter("VAT"));
		}
		BigDecimal costToPatient = NumberUtils.createBigDecimal(request.getParameter("costToPatient"));
		if(null!=request.getParameter("unitPrice") && ""!=request.getParameter("unitPrice"))
		{
			unitPrice =  NumberUtils.createBigDecimal(request.getParameter("unitPrice"));
		}
		String batchNo = request.getParameter("batchNo");
		String companyName = request.getParameter("companyName");
		// Sagar Bele : Date - 22-01-2013 Issue Number 660 : [Inventory] Add receipt from field in Table and front end	
		String receiptFrom = request.getParameter("receiptFrom");
		String dateManufacture = request.getParameter("dateManufacture");
		String dateExpiry = request.getParameter("dateExpiry");
		String receiptDate = request.getParameter("receiptDate");
		if(!StringUtils.isBlank(dateManufacture)){
			Date dateManufac = DateUtils.getDateFromStr(dateManufacture);
			Date dateExpi = DateUtils.getDateFromStr(dateExpiry);
			if(dateManufac.after(dateExpi)  ){
				errors.add("ehrinventory.receiptDrug.manufacNeedLessThanExpiry");
			}
		}
		
		InventoryDrugFormulation formulationO = inventoryService.getDrugFormulationById(formulation);
		if(formulationO == null)
		{
			errors.add("ehrinventory.receiptDrug.formulation.required");
		}
		//InventoryDrug drug = inventoryService.getDrugById(drugId);
	
		if(formulationO != null && drug != null && !drug.getFormulations().contains(formulationO))
		{
			errors.add("ehrinventory.receiptDrug.formulation.notCorrect");
		}
		if(!CollectionUtils.isEmpty(errors)){
			model.addAttribute("errors", errors);
		//	model.addAttribute("category", category);
			model.addAttribute("formulation", formulation);
			model.addAttribute("drugId", drugId);
			model.addAttribute("quantity", quantity);
			model.addAttribute("VAT", VAT);
			model.addAttribute("costToPatient", costToPatient);
			model.addAttribute("batchNo", batchNo);
			model.addAttribute("unitPrice", unitPrice);
			model.addAttribute("companyName", companyName);
			model.addAttribute("dateManufacture", dateManufacture);
			model.addAttribute("dateExpiry", dateExpiry);
			// Sagar Bele : Date - 22-01-2013 Issue Number 660 : [Inventory] Add receipt from field in Table and front end	
			model.addAttribute("receiptFrom", receiptFrom);
			
			return "/module/ehrinventory/mainstore/receiptsToGeneralStore";
		}
		
		InventoryStoreDrugTransactionDetail transactionDetail = new InventoryStoreDrugTransactionDetail();
		transactionDetail.setDrug(drug);
		transactionDetail.setAttribute(drug.getAttributeName());
		transactionDetail.setReorderPoint(drug.getReorderQty());
		transactionDetail.setFormulation(inventoryService.getDrugFormulationById(formulation));
		transactionDetail.setBatchNo(batchNo);
		transactionDetail.setCompanyName(companyName);
		transactionDetail.setCurrentQuantity(quantity);
		transactionDetail.setQuantity(quantity);
		transactionDetail.setUnitPrice(unitPrice);
		transactionDetail.setVAT(VAT);
		transactionDetail.setCostToPatient(costToPatient);
		transactionDetail.setIssueQuantity(0);
		transactionDetail.setCreatedOn(new Date());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			transactionDetail.setDateExpiry(formatter.parse(dateExpiry+" 23:59:59"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		transactionDetail.setDateManufacture(DateUtils.getDateFromStr(dateManufacture));
		transactionDetail.setReceiptDate(DateUtils.getDateFromStr(receiptDate));
		
		//Sagar Bele : Date - 22-01-2013 Issue Number 660 : [Inventory] Add receipt from field in Table and front end	
		transactionDetail.setReceiptFrom(receiptFrom);
		
		/*Money moneyUnitPrice = new Money(unitPrice);
		Money totl = moneyUnitPrice.times(quantity);
		totl = totl.plus(totl.times((double)VAT/100));
		transactionDetail.setTotalPrice(totl.getAmount());*/
		
		BigDecimal moneyUnitPrice = costToPatient.multiply(new BigDecimal(quantity));
		//moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice.multiply(VAT.divide(new BigDecimal(100))));
		transactionDetail.setTotalPrice(moneyUnitPrice);
		
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "reipt_"+userId;
		List<InventoryStoreDrugTransactionDetail> list = (List<InventoryStoreDrugTransactionDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
		if(list == null){
			list = new ArrayList<InventoryStoreDrugTransactionDetail>();
		}
		list.add(transactionDetail);
		StoreSingleton.getInstance().getHash().put(fowardParam, list);
		//model.addAttribute("listReceipt", list);
	 return "redirect:/module/ehrinventory/receiptsToGeneralStore.form";
	}
}
