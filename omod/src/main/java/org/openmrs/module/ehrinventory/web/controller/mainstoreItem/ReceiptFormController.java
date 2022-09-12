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
package org.openmrs.module.ehrinventory.web.controller.mainstoreItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryItem;
import org.openmrs.module.ehrinventory.model.InventoryItemSubCategory;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemTransactionDetail;
import org.openmrs.module.ehrinventory.util.DateUtils;
import org.openmrs.module.ehrinventory.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("itemReceiptFormController")
@RequestMapping("/module/ehrinventory/itemReceiptsToGeneralStore.form")
public class ReceiptFormController {
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(
			@RequestParam(value="categoryId",required=false)  Integer categoryId,
			Model model) {
	 InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
	 List<InventoryItemSubCategory> listCategory = inventoryService.listItemSubCategory("", 0, 0);
	 model.addAttribute("listCategory", listCategory);
	 model.addAttribute("categoryId", categoryId);
	 if(categoryId != null && categoryId > 0){
		 List<InventoryItem> items = inventoryService.findItem(categoryId, null);
		 model.addAttribute("items",items);
			
	 }
	 model.addAttribute("date",new Date());
	 //InventoryStore store = inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
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
	 String fowardParam = "itemReceipt_"+userId;
	 List<InventoryStoreItemTransactionDetail> list = (List<InventoryStoreItemTransactionDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
	 model.addAttribute("listReceipt", list);
	 
	 return "/module/ehrinventory/mainstoreItem/itemReceiptsToGeneralStore";
	 
	}
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model model) {
		
		List<String> errors = new ArrayList<String>();
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		 List<InventoryItemSubCategory> listCategory = inventoryService.listItemSubCategory("", 0, 0);
		 model.addAttribute("listCategory", listCategory);
		int category = NumberUtils.toInt(request.getParameter("category"),0);
		int specification = NumberUtils.toInt(request.getParameter("specification"),0);
		int itemId = NumberUtils.toInt(request.getParameter("itemId"), 0 );
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
		String dateManufacture = request.getParameter("dateManufacture");
		String receiptDate = request.getParameter("receiptDate");
		//System.out.println("itemName: "+itemName);
		InventoryItem item = inventoryService.getItemById(itemId);
		
		if(item == null){
			errors.add("ehrinventory.receiptItem.Item.required");
			model.addAttribute("category", category);
			model.addAttribute("specification", specification);
			model.addAttribute("ItemId", itemId);
			model.addAttribute("quantity", quantity);
			model.addAttribute("VAT", VAT);
			model.addAttribute("costToPatient", costToPatient);
			model.addAttribute("batchNo", batchNo);
			model.addAttribute("unitPrice", unitPrice);
			model.addAttribute("companyName", companyName);
			model.addAttribute("dateManufacture", dateManufacture);
			model.addAttribute("companyName", companyName);
			model.addAttribute("receiptDate", receiptDate);
			return "/module/ehrinventory/mainstoreItem/itemReceiptsToGeneralStore";
		}else if(CollectionUtils.isNotEmpty(item.getSpecifications()) && specification == 0 )
		{
			errors.add("ehrinventory.receiptItem.specification.required");
			return "/module/ehrinventory/mainstoreItem/itemReceiptsToGeneralStore";
		}
		
		InventoryStoreItemTransactionDetail transactionDetail = new InventoryStoreItemTransactionDetail();
		transactionDetail.setItem(item);
		transactionDetail.setSpecification(inventoryService.getItemSpecificationById(specification));
		transactionDetail.setCompanyName(companyName);
		transactionDetail.setCurrentQuantity(quantity);
		transactionDetail.setQuantity(quantity);
		transactionDetail.setUnitPrice(unitPrice);
		transactionDetail.setVAT(VAT);
		transactionDetail.setCostToPatient(costToPatient);
		transactionDetail.setIssueQuantity(0);
		transactionDetail.setCreatedOn(new Date());
		transactionDetail.setReceiptDate(DateUtils.getDateFromStr(receiptDate));

		transactionDetail.setDateManufacture(DateUtils.getDateFromStr(dateManufacture));
		/*Money moneyUnitPrice = new Money(unitPrice);
		Money vATUnitPrice = new Money(VAT);
		Money m = moneyUnitPrice.plus(vATUnitPrice);
		Money totl = m.times(quantity);
		transactionDetail.setTotalPrice(totl.getAmount());*/
		
		/*Money moneyUnitPrice = new Money(unitPrice);
		Money totl = moneyUnitPrice.times(quantity);
		totl = totl.plus(totl.times(VAT.divide(new BigDecimal(100) , 2)));*/
		
		;
		
		BigDecimal moneyUnitPrice = costToPatient.multiply(new BigDecimal(quantity));
	//	moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice.multiply(VAT.divide(new BigDecimal(100))));
		transactionDetail.setTotalPrice(moneyUnitPrice);
		
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "itemReceipt_"+userId;
		List<InventoryStoreItemTransactionDetail> list = (List<InventoryStoreItemTransactionDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
		if(list == null){
			list = new ArrayList<InventoryStoreItemTransactionDetail>();
		}
		list.add(transactionDetail);
		//System.out.println("list receipt: "+list);
		StoreSingleton.getInstance().getHash().put(fowardParam, list);
		//model.addAttribute("listReceipt", list);
	 return "redirect:/module/ehrinventory/itemReceiptsToGeneralStore.form";
	}
}
