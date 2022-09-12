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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.hospitalcore.util.ActionValue;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryStoreItem;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemIndent;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemIndentDetail;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemTransaction;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemTransactionDetail;
import org.openmrs.module.ehrinventory.util.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("SubStoreItemProcessReceiptIndentController")
@RequestMapping("/module/ehrinventory/subStoreItemProcessIndent.form")
public class SubStoreItemProcessReceiptIndentController {

	
	@RequestMapping(method = RequestMethod.GET)
	public String sendIndent( @RequestParam(value="indentId",required=false)  Integer id,Model model) {
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		InventoryStoreItemIndent indent = inventoryService.getStoreItemIndentById(id);
		if(indent != null && indent.getSubStoreStatus() != 3 && indent.getMainStoreStatus() != 3){
			return "redirect:/module/ehrinventory/subStoreIndentItemList.form";
		}
		List<InventoryStoreItemIndentDetail> listItemNeedProcess = inventoryService.listStoreItemIndentDetail(id);
		//Collection<Integer> formulationIds = new ArrayList<Integer>();
		//Collection<Integer> ItemIds = new ArrayList<Integer>();
		model.addAttribute("listItemNeedProcess", listItemNeedProcess);
		model.addAttribute("indent", indent);
		return "/module/ehrinventory/substoreItem/subStoreItemProcessIndent";
		
		
		
	}
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit( HttpServletRequest request, Model model) {
	 InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
 	 Integer indentId = NumberUtils.toInt(request.getParameter("indentId"));
	 InventoryStoreItemIndent indent =inventoryService.getStoreItemIndentById(indentId);
	 List<InventoryStoreItemIndentDetail> listIndentDetail = inventoryService.listStoreItemIndentDetail(indentId);
	 
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
	 List<InventoryStoreItemTransactionDetail> refundItemList = inventoryService.listStoreItemTransactionDetail(indent.getTransaction().getId());
	
	 if("1".equals(request.getParameter("refuse"))){
		if(indent != null){
			indent.setMainStoreStatus(ActionValue.INDENT_MAINSTORE[3]);
			indent.setSubStoreStatus(ActionValue.INDENT_SUBSTORE[3]);
			inventoryService.saveStoreItemIndent(indent);
			
			for(InventoryStoreItemIndentDetail t : listIndentDetail){
				 Integer specificationId = t.getSpecification() != null ? t.getSpecification().getId() : null;
				InventoryStoreItem storeItem = inventoryService.getStoreItem(indent.getStore().getId(), t.getItem().getId(),specificationId);
				if(storeItem != null ){
					storeItem.setStatusIndent(0);
					inventoryService.saveStoreItem(storeItem);
				}
				
			}
		}
		
		
		if(refundItemList != null && refundItemList.size() > 0){
			InventoryStoreItemTransaction transaction = new InventoryStoreItemTransaction();
			//transaction.setStore(subStore.getParent());
			/*
			 * luan- should update code here as new requirement for multiple main stores
			 */
			transaction.setDescription("REFUND ITEM BC SUBSTORE REFUSE "+DateUtils.getDDMMYYYY());
			transaction.setTypeTransaction(ActionValue.TRANSACTION[0]);
			transaction.setCreatedBy("System");
			transaction.setCreatedOn(new Date());
			transaction = inventoryService.saveStoreItemTransaction(transaction);
			 
			 
			for(InventoryStoreItemTransactionDetail refund : refundItemList){
				
				Date date = new Date();
				//Integer sumTotalQuantity = inventoryService.sumStoreItemCurrentQuantity(subStore.getParent().getId(), refund.getItem().getId(), refund.getSpecification()!= null?refund.getSpecification().getId() : null);
				/*
				 * luan- should update code here as new requirement for multiple main stores
				 */
				Integer sumTotalQuantity = 0; //just for testing. Should update later
				InventoryStoreItemTransactionDetail transDetail = new InventoryStoreItemTransactionDetail();
				transDetail.setTransaction(transaction);
				transDetail.setItem(refund.getItem());
				transDetail.setCompanyName(refund.getCompanyName());

				transDetail.setAttribute(refund.getItem().getAttributeName());

				transDetail.setCreatedOn(date);
				transDetail.setDateManufacture(refund.getDateManufacture());
				transDetail.setSpecification(refund.getSpecification());
				transDetail.setUnitPrice(refund.getUnitPrice());
				transDetail.setVAT(refund.getVAT());
				transDetail.setCostToPatient(refund.getCostToPatient());
				transDetail.setParent(refund);
				transDetail.setReceiptDate(date);
				transDetail.setQuantity(refund.getIssueQuantity());
				/* Money moneyUnitPrice = new Money(refund.getUnitPrice());
				 Money vATUnitPrice = new Money(refund.getVAT());
				 Money m = moneyUnitPrice.plus(vATUnitPrice);
				 Money totl = m.times(refund.getIssueQuantity());
				 transDetail.setTotalPrice(totl.getAmount());*/
				/*
				 Money moneyUnitPrice = new Money(refund.getUnitPrice());
				 Money totl = moneyUnitPrice.times(refund.getIssueQuantity());
				
				totl = totl.plus(totl.times(refund.getVAT().divide(new BigDecimal(100))));
				*/
				BigDecimal moneyUnitPrice = refund.getCostToPatient().multiply(new BigDecimal(refund.getIssueQuantity()));
			//	moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice.multiply(refund.getVAT().divide(new BigDecimal(100))));
				transDetail.setTotalPrice(moneyUnitPrice);
				
				transDetail.setCurrentQuantity(refund.getIssueQuantity());
				transDetail.setIssueQuantity(0);
				transDetail.setOpeningBalance(sumTotalQuantity);
				transDetail.setClosingBalance(sumTotalQuantity + refund.getIssueQuantity());
				inventoryService.saveStoreItemTransactionDetail(transDetail);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return "redirect:/module/ehrinventory/subStoreIndentItemList.form";
	 }
	 //save here 

	 InventoryStoreItemTransaction transaction = new InventoryStoreItemTransaction();
		transaction.setStore(subStore);
		transaction.setDescription("RECEIPT "+DateUtils.getDDMMYYYY());
		transaction.setTypeTransaction(ActionValue.TRANSACTION[0]);
		transaction.setCreatedBy("System");
		transaction.setCreatedOn(new Date());
		transaction = inventoryService.saveStoreItemTransaction(transaction);
		 
		 
		for(InventoryStoreItemTransactionDetail refund : refundItemList){
			
			Date date = new Date();
			Integer specificationId = refund.getSpecification() != null ? refund.getSpecification().getId() : null;
			Integer sumTotalQuantity = inventoryService.sumStoreItemCurrentQuantity(subStore.getId(), refund.getItem().getId(), specificationId);
			InventoryStoreItemTransactionDetail transDetail = new InventoryStoreItemTransactionDetail();
			transDetail.setTransaction(transaction);
			transDetail.setItem(refund.getItem());
			transDetail.setCompanyName(refund.getCompanyName());
			transDetail.setCreatedOn(date);
			transDetail.setDateManufacture(refund.getDateManufacture());
			transDetail.setSpecification(refund.getSpecification());
			transDetail.setUnitPrice(refund.getUnitPrice());
			transDetail.setVAT(refund.getVAT());
			transDetail.setCostToPatient(refund.getCostToPatient());
			transDetail.setParent(refund);
			transDetail.setQuantity(refund.getIssueQuantity());
			transDetail.setReceiptDate(date);
			//added
			transDetail.setAttribute(refund.getItem().getAttributeName());
			//
			
			
			 /*Money moneyUnitPrice = new Money(refund.getUnitPrice());
			 Money vATUnitPrice = new Money(refund.getVAT());
			 Money m = moneyUnitPrice.plus(vATUnitPrice);
			 Money totl = m.times(refund.getIssueQuantity());
			 transDetail.setTotalPrice(totl.getAmount());*/
			
			 /*Money moneyUnitPrice = new Money(refund.getUnitPrice());
			 Money totl = moneyUnitPrice.times(refund.getIssueQuantity());
			
			totl = totl.plus(totl.times(refund.getVAT().divide(new BigDecimal(100),2)));
			transDetail.setTotalPrice(totl.getAmount());*/
			
			BigDecimal moneyUnitPrice = refund.getCostToPatient().multiply(new BigDecimal(refund.getIssueQuantity()));
			//moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice.multiply(refund.getVAT().divide(new BigDecimal(100))));
			transDetail.setTotalPrice(moneyUnitPrice);
			
			
			 transDetail.setQuantity(refund.getIssueQuantity());
			transDetail.setCurrentQuantity(refund.getIssueQuantity());
			transDetail.setIssueQuantity(0);
			transDetail.setOpeningBalance(sumTotalQuantity);
			transDetail.setClosingBalance(sumTotalQuantity + refund.getIssueQuantity());
			inventoryService.saveStoreItemTransactionDetail(transDetail);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	 //indent.setMainStoreStatus(ActionValue.INDENT_MAINSTORE[2]);
	 indent.setSubStoreStatus(ActionValue.INDENT_SUBSTORE[4]);
	 inventoryService.saveStoreItemIndent(indent);
	 return "redirect:/module/ehrinventory/subStoreIndentItemList.form";
	 
 }
}
