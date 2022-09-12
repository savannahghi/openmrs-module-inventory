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
package org.openmrs.module.ehrinventory.web.controller.global;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.Role;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.BillingService;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.model.IndoorPatientServiceBill;
import org.openmrs.module.hospitalcore.model.IndoorPatientServiceBillItem;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.InventoryDrugCategory;
import org.openmrs.module.hospitalcore.model.InventoryDrugFormulation;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugIndent;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugPatient;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugPatientDetail;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugTransaction;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugTransactionDetail;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.hospitalcore.model.OpdDrugOrder;
import org.openmrs.module.hospitalcore.util.ActionValue;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryItem;
import org.openmrs.module.ehrinventory.model.InventoryItemSpecification;
import org.openmrs.module.ehrinventory.model.InventoryItemSubCategory;
import org.openmrs.module.ehrinventory.model.InventoryStoreDrugAccount;
import org.openmrs.module.ehrinventory.model.InventoryStoreDrugAccountDetail;
import org.openmrs.module.ehrinventory.model.InventoryStoreDrugIndentDetail;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemAccount;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemAccountDetail;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemIndent;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemIndentDetail;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemPatient;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemPatientDetail;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemTransaction;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemTransactionDetail;
import org.openmrs.module.ehrinventory.util.DateUtils;
import org.openmrs.module.ehrinventory.util.PagingUtil;
import org.openmrs.module.ehrinventory.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("AjaxGlobalController")
public class AjaxController {

	@RequestMapping("/module/ehrinventory/drugByCategory.form")
	public String drugByCategory(
			@RequestParam(value = "categoryId", required = false) Integer categoryId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		List<InventoryDrug> drugs = inventoryService.listDrug(categoryId, null,
				0, 0);
		model.addAttribute("drugs", drugs);
		return "/module/ehrinventory/autocomplete/drugByCategory";
	}

	@RequestMapping("/module/ehrinventory/drugByCategoryForIssue.form")
	public String drugByCategoryForIssue(
			@RequestParam(value = "categoryId", required = false) Integer categoryId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		List<InventoryDrug> drugs = inventoryService.listDrug(categoryId, null,
				0, 0);
		model.addAttribute("drugs", drugs);
		return "/module/ehrinventory/autocomplete/drugByCategoryForIssue";
	}

	@RequestMapping("/module/ehrinventory/itemBySubCategory.form")
	public String itemByCategory(
			@RequestParam(value = "categoryId", required = false) Integer categoryId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		List<InventoryItem> items = inventoryService.listItem(categoryId, null,
				0, 0);
		model.addAttribute("items", items);
		return "/module/ehrinventory/autocomplete/itemBySubCategory";
	}

	@RequestMapping("/module/ehrinventory/itemBySubCategoryForIssue.form")
	public String itemBySubCategoryForIssue(
			@RequestParam(value = "categoryId", required = false) Integer categoryId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		List<InventoryItem> items = inventoryService.listItem(categoryId, null,
				0, 0);
		model.addAttribute("items", items);
		return "/module/ehrinventory/autocomplete/itemBySubCategoryForIssue";
	}

	@RequestMapping("/module/ehrinventory/itemBySubCategoryForIssuePatient.form")
	public String itemBySubCategoryForIssuePatient(
			@RequestParam(value = "categoryId", required = false) Integer categoryId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		List<InventoryItem> items = inventoryService.listItem(categoryId, null,
				0, 0);
		model.addAttribute("items", items);
		return "/module/ehrinventory/autocomplete/itemBySubCategoryForIssuePatient";
	}

	@RequestMapping("/module/ehrinventory/formulationByDrug.form")
	public String formulationByDrug(
			@RequestParam(value = "drugId", required = false) Integer drugId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		InventoryDrug drug = inventoryService.getDrugById(drugId);
		if (drug != null) {
			List<InventoryDrugFormulation> formulations = new ArrayList<InventoryDrugFormulation>(
					drug.getFormulations());
			model.addAttribute("formulations", formulations);
		}
		return "/module/ehrinventory/autocomplete/formulationByDrug";
	}

	@RequestMapping("/module/ehrinventory/formulationByDrugName.form")
	public String formulationByDrugName(
			@RequestParam(value = "drugName", required = false) String drugName,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		InventoryDrug drug = inventoryService.getDrugByName(drugName);
		if (drug != null) {
			List<InventoryDrugFormulation> formulations = new ArrayList<InventoryDrugFormulation>(
					drug.getFormulations());
			model.addAttribute("formulations", formulations);
		}
		return "/module/ehrinventory/autocomplete/formulationByDrug";
	}

	@RequestMapping("/module/ehrinventory/specificationByItem.form")
	public String specificationByItem(
			@RequestParam(value = "itemId", required = false) Integer itemId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		InventoryItem item = inventoryService.getItemById(itemId);
		if (item != null) {
			List<InventoryItemSpecification> specifications = new ArrayList<InventoryItemSpecification>(
					item.getSpecifications());
			model.addAttribute("specifications", specifications);
		}
		return "/module/ehrinventory/autocomplete/specificationByItem";
	}

	@SuppressWarnings("static-access")
	@RequestMapping("/module/ehrinventory/clearSlip.form")
	public String clearSlip(
			@RequestParam(value = "action", required = false) String name,
			Model model) {
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "reipt_" + userId;
		if ("1".equals(name)) {
			// Clear slip
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			return "redirect:/module/ehrinventory/receiptsToGeneralStore.form";
		}
		return "/module/ehrinventory/mainstore/addDescriptionReceiptSlip";
	}

	@RequestMapping("/module/ehrinventory/itemClearSlip.form")
	public String clearSlipItem(
			@RequestParam(value = "action", required = false) String name,
			Model model) {
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "itemReceipt_" + userId;
		if ("1".equals(name)) {
			// Clear slip
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			return "redirect:/module/ehrinventory/itemReceiptsToGeneralStore.form";
		}
		return "/module/ehrinventory/mainstoreItem/itemAddDescriptionReceiptSlip";
	}

	@RequestMapping("/module/ehrinventory/clearPurchaseOrder.form")
	public String clearPurchase(
			@RequestParam(value = "action", required = false) String name,
			Model model) {
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "purchase_" + userId;
		if ("1".equals(name)) {
			// Clear slip
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			return "redirect:/module/ehrinventory/purchaseOrderForGeneralStore.form";
		}
		return "/module/ehrinventory/mainstore/purchaseOrderForGeneralStore";
	}

	@RequestMapping("/module/ehrinventory/itemClearPurchaseOrder.form")
	public String itemClearPurchase(
			@RequestParam(value = "action", required = false) String name,
			Model model) {
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "itemPurchase_" + userId;
		if ("1".equals(name)) {
			// Clear slip
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			return "redirect:/module/ehrinventory/itemPurchaseOrderForGeneralStore.form";
		}
		return "/module/ehrinventory/mainstoreItem/itemPurchaseOrderForGeneralStore";
	}

	@RequestMapping("/module/ehrinventory/clearSubStoreIndent.form")
	public String clearIndent(
			@RequestParam(value = "action", required = false) String name,
			Model model) {
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "subStoreIndentDrug_" + userId;
		if ("1".equals(name)) {
			// Clear slip
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			return "redirect:/module/ehrinventory/subStoreIndentDrug.form";
		}
		return "/module/ehrinventory/substore/subStoreIndentDrug";
	}

	@RequestMapping("/module/ehrinventory/itemClearSubStoreIndent.form")
	public String itemClearIndent(
			@RequestParam(value = "action", required = false) String name,
			Model model) {
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "subStoreIndentItem_" + userId;
		if ("1".equals(name)) {
			// Clear slip
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			return "redirect:/module/inventory/subStoreIndentItem.form";
		}
		return "/module/ehrinventory/substoreItem/subStoreIndentItem";
	}

	@RequestMapping("/module/ehrinventory/indentDrugDetail.form")
	public String detailSubStoreDrugIndent(
			@RequestParam(value = "indentId", required = false) Integer indentId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		InventoryStoreDrugIndent indent = inventoryService
				.getStoreDrugIndentById(indentId);
		List<InventoryStoreDrugIndentDetail> listIndentDetail = inventoryService
				.listStoreDrugIndentDetail(indentId);
		model.addAttribute("listIndentDetail", listIndentDetail);
		if (indent != null && indent.getTransaction() != null) {
			List<InventoryStoreDrugTransactionDetail> listTransactionDetail = inventoryService
					.listTransactionDetail(indent.getTransaction().getId());
			model.addAttribute("listTransactionDetail", listTransactionDetail);
		}
		model.addAttribute("store",
				!CollectionUtils.isEmpty(listIndentDetail) ? listIndentDetail
						.get(0).getIndent().getStore() : null);
		model.addAttribute("date",
				!CollectionUtils.isEmpty(listIndentDetail) ? listIndentDetail
						.get(0).getIndent().getCreatedOn() : null);
		return "/module/ehrinventory/autocomplete/indentDrugDetail";
	}

	@RequestMapping("/module/ehrinventory/indentItemDetail.form")
	public String detailSubStoreItemIndent(
			@RequestParam(value = "indentId", required = false) Integer indentId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		List<InventoryStoreItemIndentDetail> listIndentDetail = inventoryService
				.listStoreItemIndentDetail(indentId);
		// InventoryStoreItemIndent indent =
		// inventoryService.getStoreItemIndentById(indentId);
		model.addAttribute("listIndentDetail", listIndentDetail);
		/*
		 * if(indent != null && indent.getTransaction() != null){
		 * List<InventoryStoreItemTransactionDetail> listTransactionDetail =
		 * inventoryService
		 * .listStoreItemTransactionDetail(indent.getTransaction().getId());
		 * model.addAttribute("listTransactionDetail", listTransactionDetail); }
		 */
		model.addAttribute("store",
				!CollectionUtils.isEmpty(listIndentDetail) ? listIndentDetail
						.get(0).getIndent().getStore() : null);
		model.addAttribute("date",
				!CollectionUtils.isEmpty(listIndentDetail) ? listIndentDetail
						.get(0).getIndent().getCreatedOn() : null);
		return "/module/ehrinventory/autocomplete/indentItemDetail";
	}

	@RequestMapping("/module/ehrinventory/sentDrugIndentToMainStore.form")
	public String sendDrugIndentToMainStore(
			@RequestParam(value = "indentId", required = false) Integer indentId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		InventoryStoreDrugIndent indent = inventoryService
				.getStoreDrugIndentById(indentId);
		if (indent != null) {
			indent.setSubStoreStatus(ActionValue.INDENT_SUBSTORE[1]);
			indent.setMainStoreStatus(ActionValue.INDENT_MAINSTORE[0]);
			inventoryService.saveStoreDrugIndent(indent);
		}
		return "redirect:/module/ehrinventory/subStoreIndentDrugList.form";
	}

	@RequestMapping("/module/ehrinventory/sentItemIndentToMainStore.form")
	public String sendItemIndentToMainStore(
			@RequestParam(value = "indentId", required = false) Integer indentId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		InventoryStoreItemIndent indent = inventoryService
				.getStoreItemIndentById(indentId);
		if (indent != null) {
			indent.setSubStoreStatus(ActionValue.INDENT_SUBSTORE[1]);
			indent.setMainStoreStatus(ActionValue.INDENT_MAINSTORE[0]);
			inventoryService.saveStoreItemIndent(indent);
		}
		return "redirect:/module/ehrinventory/subStoreIndentItemList.form";
	}

	@RequestMapping("/module/ehrinventory/listReceiptDrug.form")
	public String listReceiptDrugAvailable(
			@RequestParam(value = "drugId", required = false) Integer drugId,
			@RequestParam(value = "formulationId", required = false) Integer formulationId,
			
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		ConceptService conceptService = Context.getConceptService();
		InventoryDrug drug = inventoryService.getDrugById(drugId);
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
		if (store != null && drug != null && formulationId != null ) {
			
			List<InventoryStoreDrugTransactionDetail> listReceiptDrug = inventoryService
					.listStoreDrugTransactionDetail(store.getId(),
							drug.getId(), formulationId, true);
			// check that drug is issued before
			int userId = Context.getAuthenticatedUser().getId();

			String fowardParam = "issueDrugAccountDetail_" + userId;
			String fowardParamDrug = "issueDrugDetail_" + userId;
			List<InventoryStoreDrugPatientDetail> listDrug = (List<InventoryStoreDrugPatientDetail>) StoreSingleton
					.getInstance().getHash().get(fowardParamDrug);
			List<InventoryStoreDrugAccountDetail> listDrugAccount = (List<InventoryStoreDrugAccountDetail>) StoreSingleton
					.getInstance().getHash().get(fowardParam);
			List<InventoryStoreDrugTransactionDetail> listReceiptDrugReturn = new ArrayList<InventoryStoreDrugTransactionDetail>();
			boolean check = false;
			if (CollectionUtils.isNotEmpty(listDrug)) {
				if (CollectionUtils.isNotEmpty(listReceiptDrug)) {
					for (InventoryStoreDrugTransactionDetail drugDetail : listReceiptDrug) {
						for (InventoryStoreDrugPatientDetail drugPatient : listDrug) {
							
							if (drugDetail.getId().equals(
									drugPatient.getTransactionDetail().getId())) {
								drugDetail.setCurrentQuantity(drugDetail
										.getCurrentQuantity()
										);
								
							}

						}
						if (drugDetail.getCurrentQuantity() > 0) {
						
							listReceiptDrugReturn.add(drugDetail);
							check = true;
						}
					}
				}
			}

			if (CollectionUtils.isNotEmpty(listDrugAccount)) {
				if (CollectionUtils.isNotEmpty(listReceiptDrug)) {
					for (InventoryStoreDrugTransactionDetail drugDetail : listReceiptDrug) {
						for (InventoryStoreDrugAccountDetail drugAccount : listDrugAccount) {
							if (drugDetail.getId().equals(
									drugAccount.getTransactionDetail().getId())) {
								drugDetail.setCurrentQuantity(drugDetail
										.getCurrentQuantity()
										);
								
							}
						}
						if (drugDetail.getCurrentQuantity() > 0 && !check) {
							listReceiptDrugReturn.add(drugDetail);
						}
					}
				}
			}
			if (CollectionUtils.isEmpty(listReceiptDrugReturn)
					&& CollectionUtils.isNotEmpty(listReceiptDrug)) {
				listReceiptDrugReturn.addAll(listReceiptDrug);
			}

			model.addAttribute("listReceiptDrug", listReceiptDrugReturn);
			//model.addAttribute("frequencyName", frequencyName);
			//model.addAttribute("noOfDays", days);
			//model.addAttribute("comments", comments);
		}

		return "/module/ehrinventory/autocomplete/listReceiptDrug";
	}

	@RequestMapping("/module/ehrinventory/listReceiptItem.form")
	public String listReceiptItemAvailable(
			@RequestParam(value = "itemId", required = false) Integer itemId,
			@RequestParam(value = "specificationId", required = false) Integer specificationId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		InventoryItem item = inventoryService.getItemById(itemId);
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
		if (store != null && item != null) {
			Integer sumReceiptItem = inventoryService
					.sumStoreItemCurrentQuantity(store.getId(), item.getId(),
							specificationId);

			int userId = Context.getAuthenticatedUser().getId();
			String fowardParam = "issueItemDetail_" + userId;
			@SuppressWarnings("unchecked")
			List<InventoryStoreItemAccountDetail> list = (List<InventoryStoreItemAccountDetail>) StoreSingleton
					.getInstance().getHash().get(fowardParam);
			if (CollectionUtils.isNotEmpty(list)) {
				for (InventoryStoreItemAccountDetail itemAccount : list) {
					if (itemAccount.getTransactionDetail().getItem().getId()
							.equals(itemId)) {
						if (specificationId != null) {
							if (itemAccount.getTransactionDetail()
									.getSpecification() != null
									&& itemAccount.getTransactionDetail()
											.getSpecification().getId()
											.equals(specificationId)) {
								sumReceiptItem -= itemAccount.getQuantity();
							}
						} else {
							if (itemAccount.getTransactionDetail()
									.getSpecification() == null) {
								sumReceiptItem -= itemAccount.getQuantity();
							}
						}
					}
				}
			}

			model.addAttribute("sumReceiptItem", sumReceiptItem);
		}

		return "/module/ehrinventory/autocomplete/listReceiptItem";
	}

	@RequestMapping("/module/ehrinventory/formulationByDrugForIssue.form")
	public String formulationByDrugForIssueDrug(
			@RequestParam(value = "drugId", required = false) Integer drugId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		InventoryDrug drug = inventoryService.getDrugById(drugId);
		if (drug != null) {
			List<InventoryDrugFormulation> formulations = new ArrayList<InventoryDrugFormulation>(
					drug.getFormulations());
			model.addAttribute("formulations", formulations);

		}
		return "/module/ehrinventory/autocomplete/formulationByDrugForIssue";
	}

	@RequestMapping("/module/ehrinventory/formulationByDrugNameForIssue.form")
	public String formulationByDrugNameForIssueDrug(
			@RequestParam(value = "drugName", required = false) String drugName,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		InventoryDrug drug = inventoryService.getDrugByName(drugName);
		if (drug != null) {
			List<InventoryDrugFormulation> formulations = new ArrayList<InventoryDrugFormulation>(
					drug.getFormulations());
			model.addAttribute("formulations", formulations);
			model.addAttribute("drugId", drug.getId());
		}
		return "/module/ehrinventory/autocomplete/formulationByDrugForIssue";
	}

	@RequestMapping("/module/ehrinventory/specificationByItemForIssue.form")
	public String specificationByItemForIssueItem(
			@RequestParam(value = "itemId", required = false) Integer itemId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		InventoryItem item = inventoryService.getItemById(itemId);
		if (item != null) {
			if (item.getSpecifications() != null
					&& item.getSpecifications().size() > 0) {
				List<InventoryItemSpecification> specifications = new ArrayList<InventoryItemSpecification>(
						item.getSpecifications());
				model.addAttribute("specifications", specifications);
				return "/module/ehrinventory/autocomplete/specificationByItemForIssue";
			} else {
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
				if (store != null) {
					Integer sumReceiptItem = inventoryService
							.sumStoreItemCurrentQuantity(store.getId(),
									item.getId(), null);

					int userId = Context.getAuthenticatedUser().getId();
					String fowardParam = "issueItemDetail_" + userId;
					List<InventoryStoreItemAccountDetail> list = (List<InventoryStoreItemAccountDetail>) StoreSingleton
							.getInstance().getHash().get(fowardParam);
					if (CollectionUtils.isNotEmpty(list)) {
						for (InventoryStoreItemAccountDetail itemAccount : list) {
							if (itemAccount.getTransactionDetail().getItem()
									.getId().equals(itemId)) {
								if (itemAccount.getTransactionDetail()
										.getSpecification() == null) {
									sumReceiptItem -= itemAccount.getQuantity();
								}

							}
						}
					}

					model.addAttribute("sumReceiptItem", sumReceiptItem);
				}
				return "/module/ehrinventory/autocomplete/listReceiptItem";
			}
		}

		return "/module/ehrinventory/autocomplete/specificationByItemForIssue";
	}

	@RequestMapping("/module/ehrinventory/specificationByItemPatientForIssue.form")
	public String specificationByItemForIssueItemPatient(
			@RequestParam(value = "itemId", required = false) Integer itemId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		InventoryItem item = inventoryService.getItemById(itemId);
		if (item != null) {
			if (item.getSpecifications() != null
					&& item.getSpecifications().size() > 0) {
				List<InventoryItemSpecification> specifications = new ArrayList<InventoryItemSpecification>(
						item.getSpecifications());
				model.addAttribute("specifications", specifications);
				return "/module/ehrinventory/autocomplete/specificationByItemForIssue";
			} else {
			//	InventoryStore store = inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
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
				if (store != null) {
					Integer sumReceiptItem = inventoryService
							.sumStoreItemCurrentQuantity(store.getId(),
									item.getId(), null);

					int userId = Context.getAuthenticatedUser().getId();
					String fowardParam = "issueItemDetailPatient_" + userId;
					List<InventoryStoreItemPatientDetail> list = (List<InventoryStoreItemPatientDetail>) StoreSingleton
							.getInstance().getHash().get(fowardParam);
					if (CollectionUtils.isNotEmpty(list)) {
						for (InventoryStoreItemPatientDetail itemPatient : list) {
							if (itemPatient.getTransactionDetail().getItem()
									.getId().equals(itemId)) {
								if (itemPatient.getTransactionDetail()
										.getSpecification() == null) {
									sumReceiptItem -= itemPatient.getQuantity();
								}

							}
						}
					}

					model.addAttribute("sumReceiptItem", sumReceiptItem);
				}
				return "/module/ehrinventory/autocomplete/listReceiptItem";
			}
		}

		return "/module/ehrinventory/autocomplete/specificationByItemForIssue";
	}

	@RequestMapping("/module/ehrinventory/autoCompletePatientList.form")
	public String showPatientList(
			@RequestParam(value = "searchPatient", required = false) String identifier,
			Model model) {

		if (StringUtils.isNotBlank(identifier)) {
			String prefix = Context.getAdministrationService()
					.getGlobalProperty("registration.identifier_prefix");
			if (identifier.contains("-") && !identifier.contains(prefix)) {
				identifier = prefix + identifier;
			}
			List<Patient> patientsList = null;
			try {
				patientsList = Context.getPatientService().getPatients(
						identifier.trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("patients", patientsList);
		}

		return "/module/ehrinventory/autocomplete/autoCompletePatientList";
	}

	@RequestMapping("/module/ehrinventory/processIssueDrug.form")
	public String processIssueDrug(
			@RequestParam(value = "action", required = false) Integer action,
			@RequestParam(value = "patientType", required = false) String patientType,
			Model model) {
		
	
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "issueDrugDetail_" + userId;
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
		if (action == 1) {
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash()
					.remove("issueDrug_" + userId);
			return "redirect:/module/ehrinventory/subStoreIssueDrugForm.form";
		}
		List<InventoryStoreDrugPatientDetail> list = (List<InventoryStoreDrugPatientDetail>) StoreSingleton
				.getInstance().getHash().get(fowardParam);
		InventoryStoreDrugPatient issueDrugPatient = (InventoryStoreDrugPatient) StoreSingleton
				.getInstance().getHash().get("issueDrug_" + userId);
		if (issueDrugPatient != null && list != null && list.size() > 0) {
			
			Date date = new Date();
			// create transaction issue from substore
			InventoryStoreDrugTransaction transaction = new InventoryStoreDrugTransaction();
			transaction.setDescription("ISSUE DRUG TO PATIENT "
					+ DateUtils.getDDMMYYYY());
			transaction.setStore(store);
			transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
			transaction.setCreatedOn(date);
			//transaction.setPaymentMode(paymentMode);
			transaction.setPaymentCategory(issueDrugPatient.getPatient().getAttribute(14).getValue());
			transaction.setCreatedBy(Context.getAuthenticatedUser()
					.getGivenName());
			transaction = inventoryService
					.saveStoreDrugTransaction(transaction);

			issueDrugPatient = inventoryService
					.saveStoreDrugPatient(issueDrugPatient);
			for (InventoryStoreDrugPatientDetail pDetail : list) {
				
				Date date1 = new Date();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Integer totalQuantity = inventoryService
						.sumCurrentQuantityDrugOfStore(store.getId(), pDetail
								.getTransactionDetail().getDrug().getId(),
								pDetail.getTransactionDetail().getFormulation()
										.getId());
				   int t = totalQuantity;
				InventoryStoreDrugTransactionDetail drugTransactionDetail = inventoryService
						.getStoreDrugTransactionDetailById(pDetail
								.getTransactionDetail().getId());
				pDetail.getTransactionDetail().setCurrentQuantity(
						drugTransactionDetail.getCurrentQuantity());
				inventoryService.saveStoreDrugTransactionDetail(pDetail
						.getTransactionDetail());
				
		    
				// save transactiondetail first
				InventoryStoreDrugTransactionDetail transDetail = new InventoryStoreDrugTransactionDetail();
				transDetail.setTransaction(transaction);
				transDetail.setCurrentQuantity(0);
				transDetail.setIssueQuantity(pDetail.getQuantity());
				transDetail.setOpeningBalance(totalQuantity);
				transDetail.setClosingBalance(t);
				transDetail.setQuantity(0);
				transDetail.setVAT(pDetail.getTransactionDetail().getVAT());
				transDetail.setCostToPatient(pDetail.getTransactionDetail().getCostToPatient());
				transDetail.setUnitPrice(pDetail.getTransactionDetail()
						.getUnitPrice());
				transDetail.setDrug(pDetail.getTransactionDetail().getDrug());
				transDetail.setFormulation(pDetail.getTransactionDetail()
						.getFormulation());
				transDetail.setBatchNo(pDetail.getTransactionDetail()
						.getBatchNo());
				transDetail.setCompanyName(pDetail.getTransactionDetail()
						.getCompanyName());
				transDetail.setDateManufacture(pDetail.getTransactionDetail()
						.getDateManufacture());
				transDetail.setDateExpiry(pDetail.getTransactionDetail()
						.getDateExpiry());
				transDetail.setReceiptDate(pDetail.getTransactionDetail()
						.getReceiptDate());
				transDetail.setCreatedOn(date1);
				transDetail.setReorderPoint(pDetail.getTransactionDetail().getDrug().getReorderQty());
				transDetail.setAttribute(pDetail.getTransactionDetail().getDrug().getAttributeName());
				transDetail.setPatientType(patientType);
				
				 transDetail.setFrequency(pDetail.getTransactionDetail().getFrequency());
				 transDetail.setNoOfDays(pDetail.getTransactionDetail().getNoOfDays());
				 transDetail.setComments(pDetail.getTransactionDetail().getComments());
				/*
				 * Money moneyUnitPrice = new
				 * Money(pDetail.getTransactionDetail().getUnitPrice()); Money
				 * vATUnitPrice = new
				 * Money(pDetail.getTransactionDetail().getVAT()); Money m =
				 * moneyUnitPrice.plus(vATUnitPrice); Money totl = m.times(
				 * pDetail.getQuantity());
				 * transDetail.setTotalPrice(totl.getAmount());
				 */

				/*
				 * Money moneyUnitPrice = new
				 * Money(pDetail.getTransactionDetail().getUnitPrice()); Money
				 * totl = moneyUnitPrice.times(pDetail.getQuantity());
				 * 
				 * totl =
				 * totl.plus(totl.times((double)pDetail.getTransactionDetail
				 * ().getVAT()/100));
				 * transDetail.setTotalPrice(totl.getAmount());
				 */

				BigDecimal moneyUnitPrice = pDetail.getTransactionDetail()
						.getCostToPatient()
						.multiply(new BigDecimal(pDetail.getQuantity()));
				/*moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice
						.multiply(pDetail.getTransactionDetail().getVAT()
								.divide(new BigDecimal(100))));*/
				transDetail.setTotalPrice(moneyUnitPrice);

				transDetail.setParent(pDetail.getTransactionDetail());
				transDetail = inventoryService
						.saveStoreDrugTransactionDetail(transDetail);

				pDetail.setStoreDrugPatient(issueDrugPatient);
				pDetail.setTransactionDetail(transDetail);
				// save issue to patient detail
				inventoryService.saveStoreDrugPatientDetail(pDetail);
				// save issues transaction detail

			}

			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash()
					.remove("issueDrug_" + userId);
		}

		return "redirect:/module/ehrinventory/patientQueueDrugOrder.form";
	}

	@RequestMapping("/module/ehrinventory/processIssueDrugAccount.form")
	public String processIssueDrugAccount(
			@RequestParam(value = "action", required = false) Integer action,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "issueDrugAccountDetail_" + userId;
		//InventoryStore store = inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context	.getAuthenticatedUser().getAllRoles()));
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
		if (action == 1) {
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash()
					.remove("issueDrugAccount_" + userId);
			return "redirect:/module/ehrinventory/subStoreIssueDrugAccountForm.form";
		}
		List<InventoryStoreDrugAccountDetail> list = (List<InventoryStoreDrugAccountDetail>) StoreSingleton
				.getInstance().getHash().get(fowardParam);
		InventoryStoreDrugAccount issueDrugAccount = (InventoryStoreDrugAccount) StoreSingleton
				.getInstance().getHash().get("issueDrugAccount_" + userId);
		if (issueDrugAccount != null && list != null && list.size() > 0) {

			Date date = new Date();
			// create transaction issue from substore
			InventoryStoreDrugTransaction transaction = new InventoryStoreDrugTransaction();
			transaction.setDescription("ISSUE DRUG TO ACCOUNT "
					+ DateUtils.getDDMMYYYY());
			transaction.setStore(store);
			transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
			transaction.setCreatedOn(date);
			transaction.setCreatedBy(Context.getAuthenticatedUser()
					.getGivenName());
			transaction = inventoryService
					.saveStoreDrugTransaction(transaction);

			issueDrugAccount = inventoryService
					.saveStoreDrugAccount(issueDrugAccount);
			for (InventoryStoreDrugAccountDetail pDetail : list) {
				Date date1 = new Date();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Integer totalQuantity = inventoryService
						.sumCurrentQuantityDrugOfStore(store.getId(), pDetail
								.getTransactionDetail().getDrug().getId(),
								pDetail.getTransactionDetail().getFormulation()
										.getId());
				int t = totalQuantity - pDetail.getQuantity();

				InventoryStoreDrugTransactionDetail drugTransactionDetail = inventoryService
						.getStoreDrugTransactionDetailById(pDetail
								.getTransactionDetail().getId());
				pDetail.getTransactionDetail().setCurrentQuantity(
						drugTransactionDetail.getCurrentQuantity()
								- pDetail.getQuantity());
				inventoryService.saveStoreDrugTransactionDetail(pDetail
						.getTransactionDetail());

				// save transactiondetail first
				InventoryStoreDrugTransactionDetail transDetail = new InventoryStoreDrugTransactionDetail();
				transDetail.setTransaction(transaction);
				transDetail.setCurrentQuantity(0);
				transDetail.setIssueQuantity(pDetail.getQuantity());
				transDetail.setOpeningBalance(totalQuantity);
				transDetail.setClosingBalance(t);
				transDetail.setQuantity(0);
				transDetail.setVAT(pDetail.getTransactionDetail().getVAT());
				transDetail.setCostToPatient(pDetail.getTransactionDetail().getCostToPatient());
				transDetail.setUnitPrice(pDetail.getTransactionDetail()
						.getUnitPrice());
				transDetail.setDrug(pDetail.getTransactionDetail().getDrug());
				transDetail.setFormulation(pDetail.getTransactionDetail()
						.getFormulation());
				transDetail.setBatchNo(pDetail.getTransactionDetail()
						.getBatchNo());
				transDetail.setCompanyName(pDetail.getTransactionDetail()
						.getCompanyName());
				transDetail.setDateManufacture(pDetail.getTransactionDetail()
						.getDateManufacture());
				transDetail.setDateExpiry(pDetail.getTransactionDetail()
						.getDateExpiry());
				transDetail.setReceiptDate(pDetail.getTransactionDetail()
						.getReceiptDate());
				transDetail.setCreatedOn(date1);
				transDetail.setReorderPoint(pDetail.getTransactionDetail().getDrug().getReorderQty());
				transDetail.setAttribute(pDetail.getTransactionDetail().getDrug().getAttributeName());
				transDetail.setPatientType(pDetail.getTransactionDetail().getPatientType());
				/*
				 * Money moneyUnitPrice = new
				 * Money(pDetail.getTransactionDetail().getUnitPrice()); Money
				 * vATUnitPrice = new
				 * Money(pDetail.getTransactionDetail().getVAT()); Money m =
				 * moneyUnitPrice.plus(vATUnitPrice); Money totl = m.times(
				 * pDetail.getQuantity());
				 * transDetail.setTotalPrice(totl.getAmount());
				 */

				/*
				 * Money moneyUnitPrice = new
				 * Money(pDetail.getTransactionDetail().getUnitPrice()); Money
				 * totl = moneyUnitPrice.times(pDetail.getQuantity());
				 * 
				 * totl =
				 * totl.plus(totl.times((double)pDetail.getTransactionDetail
				 * ().getVAT()/100));
				 * transDetail.setTotalPrice(totl.getAmount());
				 */
				BigDecimal moneyUnitPrice = pDetail.getTransactionDetail()
						.getCostToPatient()
						.multiply(new BigDecimal(pDetail.getQuantity()));
				/*moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice
						.multiply(pDetail.getTransactionDetail().getVAT()
								.divide(new BigDecimal(100))));*/
				transDetail.setTotalPrice(moneyUnitPrice);

				transDetail.setParent(pDetail.getTransactionDetail());
				transDetail = inventoryService
						.saveStoreDrugTransactionDetail(transDetail);

				pDetail.setDrugAccount(issueDrugAccount);
				pDetail.setTransactionDetail(transDetail);
				// save issue to patient detail
				inventoryService.saveStoreDrugAccountDetail(pDetail);
				// save issues transaction detail

			}

			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash()
					.remove("issueDrugAccount_" + userId);
		}

		return "redirect:/module/ehrinventory/subStoreIssueDrugAccountList.form";
	}

	@RequestMapping("/module/ehrinventory/processIssueItem.form")
	public String processIssueItem(
			@RequestParam(value = "action", required = false) Integer action,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "issueItemDetail_" + userId;
		//InventoryStore store = inventoryServic	.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
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
		if (action == 1) {
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash()
					.remove("issueItem_" + userId);
			return "redirect:/module/ehrinventory/subStoreIssueItemForm.form";
		}
		List<InventoryStoreItemAccountDetail> list = (List<InventoryStoreItemAccountDetail>) StoreSingleton
				.getInstance().getHash().get(fowardParam);
		InventoryStoreItemAccount issueItemAccount = (InventoryStoreItemAccount) StoreSingleton
				.getInstance().getHash().get("issueItem_" + userId);
		if (issueItemAccount != null && list != null && list.size() > 0) {

			Date date = new Date();
			// create transaction issue from substore
			InventoryStoreItemTransaction transaction = new InventoryStoreItemTransaction();
			transaction.setDescription("ISSUE ITEM " + DateUtils.getDDMMYYYY());
			transaction.setStore(store);
			transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
			transaction.setCreatedOn(date);
			transaction.setCreatedBy(Context.getAuthenticatedUser()
					.getGivenName());
			transaction = inventoryService
					.saveStoreItemTransaction(transaction);

			issueItemAccount = inventoryService
					.saveStoreItemAccount(issueItemAccount);
			for (InventoryStoreItemAccountDetail pDetail : list) {
				Date date1 = new Date();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Integer specificationId = pDetail.getTransactionDetail()
						.getSpecification() != null ? pDetail
						.getTransactionDetail().getSpecification().getId()
						: null;
				Integer totalQuantity = inventoryService
						.sumStoreItemCurrentQuantity(store.getId(), pDetail
								.getTransactionDetail().getItem().getId(),
								specificationId);
			
				int t = totalQuantity - pDetail.getQuantity();
				InventoryStoreItemTransactionDetail itemTransactionDetail = inventoryService
						.getStoreItemTransactionDetailById(pDetail
								.getTransactionDetail().getId());
				pDetail.getTransactionDetail().setCurrentQuantity(
						itemTransactionDetail.getCurrentQuantity()
								- pDetail.getQuantity());
				
				inventoryService.saveStoreItemTransactionDetail(pDetail
						.getTransactionDetail());

				// save transactiondetail first
				InventoryStoreItemTransactionDetail transDetail = new InventoryStoreItemTransactionDetail();
				transDetail.setTransaction(transaction);
				transDetail.setCurrentQuantity(0);
				transDetail.setIssueQuantity(pDetail.getQuantity());
				transDetail.setOpeningBalance(totalQuantity);
				transDetail.setClosingBalance(t);
				transDetail.setQuantity(0);
				transDetail.setVAT(pDetail.getTransactionDetail().getVAT());
				transDetail.setCostToPatient(pDetail.getTransactionDetail().getCostToPatient());
				transDetail.setUnitPrice(pDetail.getTransactionDetail()
						.getUnitPrice());
				transDetail.setItem(pDetail.getTransactionDetail().getItem());
				transDetail.setSpecification(pDetail.getTransactionDetail()
						.getSpecification());
				transDetail.setCompanyName(pDetail.getTransactionDetail()
						.getCompanyName());
				transDetail.setDateManufacture(pDetail.getTransactionDetail()
						.getDateManufacture());
				transDetail.setReceiptDate(pDetail.getTransactionDetail()
						.getReceiptDate());
				transDetail.setCreatedOn(date1);

				// -------------
				// Money moneyUnitPrice = new
				// Money(pDetail.getTransactionDetail().getUnitPrice());
				// Money vATUnitPrice = new
				// Money(pDetail.getTransactionDetail().getVAT());
				// Money m = moneyUnitPrice.plus(vATUnitPrice);
				// Money totl = m.times( pDetail.getQuantity());
				// transDetail.setTotalPrice(totl.getAmount());
				// -----------------

				/*
				 * Money moneyUnitPrice = new
				 * Money(pDetail.getTransactionDetail().getUnitPrice()); Money
				 * totl = moneyUnitPrice.times(pDetail.getQuantity());
				 * 
				 * totl =
				 * totl.plus(totl.times(pDetail.getTransactionDetail().getVAT
				 * ().divide(new BigDecimal(100),2)));
				 * transDetail.setTotalPrice(totl.getAmount());
				 */

				BigDecimal moneyUnitPrice = pDetail.getTransactionDetail()
						.getCostToPatient()
						.multiply(new BigDecimal(pDetail.getQuantity()));
				/*moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice
						.multiply(pDetail.getTransactionDetail().getVAT()
								.divide(new BigDecimal(100))));*/
				transDetail.setTotalPrice(moneyUnitPrice);

				transDetail.setParent(pDetail.getTransactionDetail());
				transDetail = inventoryService
						.saveStoreItemTransactionDetail(transDetail);
                 
				pDetail.setItemAccount(issueItemAccount);
				pDetail.setTransactionDetail(transDetail);
				// save issue to patient detail
				inventoryService.saveStoreItemAccountDetail(pDetail);
				// save issues transaction detail

			}

			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash()
					.remove("issueItem_" + userId);
		}

		return "redirect:/module/ehrinventory/subStoreIssueItemList.form";
	}

	@RequestMapping("/module/ehrinventory/processIssueItemPatient.form")
	public String processIssueItemPatient(
			@RequestParam(value = "action", required = false) Integer action,
			@RequestParam(value = "patientType", required = false) String patientType,
			Model model) {
		
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "issueItemDetailPatient_" + userId;
		//InventoryStore store = inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context	.getAuthenticatedUser().getAllRoles()));
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
		if (action == 1) {
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash()
					.remove("issueItemPatient_" + userId);
			return "redirect:/module/ehrinventory/subStoreIssueItemPatientForm.form";
		}
		List<InventoryStoreItemPatientDetail> list = (List<InventoryStoreItemPatientDetail>) StoreSingleton
				.getInstance().getHash().get(fowardParam);
		InventoryStoreItemPatient issueItemPatient = (InventoryStoreItemPatient) StoreSingleton
				.getInstance().getHash().get("issueItemPatient_" + userId);
		
		if (issueItemPatient != null && list != null && list.size() > 0) {

			Date date = new Date();
			// create transaction issue from substore
			InventoryStoreItemTransaction transaction = new InventoryStoreItemTransaction();
			transaction.setDescription("ISSUE ITEM " + DateUtils.getDDMMYYYY());
			transaction.setStore(store);
			transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
			transaction.setCreatedOn(date);
			transaction.setPaymentCategory(issueItemPatient.getPatient().getAttribute(14).getValue());
			transaction.setCreatedBy(Context.getAuthenticatedUser()
					.getGivenName());
			transaction = inventoryService
					.saveStoreItemTransaction(transaction);

			issueItemPatient = inventoryService
					.saveStoreItemPatient(issueItemPatient);
			for (InventoryStoreItemPatientDetail pDetail : list) {
				Date date1 = new Date();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Integer specificationId = pDetail.getTransactionDetail()
						.getSpecification() != null ? pDetail
						.getTransactionDetail().getSpecification().getId()
						: null;
				Integer totalQuantity = inventoryService
						.sumStoreItemCurrentQuantity(store.getId(), pDetail
								.getTransactionDetail().getItem().getId(),
								specificationId);
				int t = totalQuantity;
				InventoryStoreItemTransactionDetail itemTransactionDetail = inventoryService
						.getStoreItemTransactionDetailById(pDetail
								.getTransactionDetail().getId());
				pDetail.getTransactionDetail().setCurrentQuantity(
						itemTransactionDetail.getCurrentQuantity());
				
				inventoryService.saveStoreItemTransactionDetail(pDetail
						.getTransactionDetail());

				// save transactiondetail first
				InventoryStoreItemTransactionDetail transDetail = new InventoryStoreItemTransactionDetail();
				transDetail.setTransaction(transaction);
				transDetail.setCurrentQuantity(0);
				transDetail.setIssueQuantity(pDetail.getQuantity());
				transDetail.setOpeningBalance(totalQuantity);
				transDetail.setClosingBalance(t);
				transDetail.setQuantity(0);
				transDetail.setVAT(pDetail.getTransactionDetail().getVAT());
				transDetail.setCostToPatient(pDetail.getTransactionDetail().getCostToPatient());
				transDetail.setUnitPrice(pDetail.getTransactionDetail()
						.getUnitPrice());
				transDetail.setItem(pDetail.getTransactionDetail().getItem());
				transDetail.setSpecification(pDetail.getTransactionDetail()
						.getSpecification());
				transDetail.setCompanyName(pDetail.getTransactionDetail()
						.getCompanyName());
				transDetail.setDateManufacture(pDetail.getTransactionDetail()
						.getDateManufacture());
				transDetail.setReceiptDate(pDetail.getTransactionDetail()
						.getReceiptDate());
				transDetail.setCreatedOn(date1);
                
				// -------------
				// Money moneyUnitPrice = new
				// Money(pDetail.getTransactionDetail().getUnitPrice());
				// Money vATUnitPrice = new
				// Money(pDetail.getTransactionDetail().getVAT());
				// Money m = moneyUnitPrice.plus(vATUnitPrice);
				// Money totl = m.times( pDetail.getQuantity());
				// transDetail.setTotalPrice(totl.getAmount());
				// -----------------

				/*
				 * Money moneyUnitPrice = new
				 * Money(pDetail.getTransactionDetail().getUnitPrice()); Money
				 * totl = moneyUnitPrice.times(pDetail.getQuantity());
				 * 
				 * totl =
				 * totl.plus(totl.times(pDetail.getTransactionDetail().getVAT
				 * ().divide(new BigDecimal(100),2)));
				 * transDetail.setTotalPrice(totl.getAmount());
				 */

				BigDecimal moneyUnitPrice = pDetail.getTransactionDetail()
						.getCostToPatient()
						.multiply(new BigDecimal(pDetail.getQuantity()));
				/*moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice
						.multiply(pDetail.getTransactionDetail().getVAT()
								.divide(new BigDecimal(100))));*/
				transDetail.setTotalPrice(moneyUnitPrice);

				transDetail.setParent(pDetail.getTransactionDetail());
				transDetail.setAttribute(pDetail.getTransactionDetail().getAttribute());
				transDetail.setPatientType(patientType);
				transDetail = inventoryService
						.saveStoreItemTransactionDetail(transDetail);

				pDetail.setStoreItemPatient(issueItemPatient);
				pDetail.setTransactionDetail(transDetail);
				// save issue to patient detail
				inventoryService.saveStoreItemPatientDetail(pDetail);
				// save issues transaction detail

			}

			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash()
					.remove("issueItemPatient_" + userId);
		}

		return "redirect:/module/ehrinventory/subStoreIssueItem.form";
	}

	@RequestMapping("/module/ehrinventory/viewStockBalanceDetail.form")
	public String viewStockBalanceDetail(
			@RequestParam(value = "drugId", required = false) Integer drugId,
			@RequestParam(value = "formulationId", required = false) Integer formulationId,
			@RequestParam(value = "expiry", required = false) Integer expiry,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
	//	InventoryStore store = inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context	.getAuthenticatedUser().getAllRoles()));
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
		List<InventoryStoreDrugTransactionDetail> listViewStockBalance = inventoryService
				.listStoreDrugTransactionDetail(store.getId(), drugId,
						formulationId, expiry);
		model.addAttribute("listViewStockBalance", listViewStockBalance);
		return "/module/ehrinventory/mainstore/viewStockBalanceDetail";
	}

	@RequestMapping("/module/ehrinventory/itemViewStockBalanceDetail.form")
	public String itemViewStockBalanceDetail(
			@RequestParam(value = "itemId", required = false) Integer itemId,
			@RequestParam(value = "specificationId", required = false) Integer specificationId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
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
		List<InventoryStoreItemTransactionDetail> listViewStockBalance = inventoryService
				.listStoreItemTransactionDetail(store.getId(), itemId,
						specificationId, 0, 0);
		model.addAttribute("listViewStockBalance", listViewStockBalance);
		return "/module/ehrinventory/mainstoreItem/itemViewStockBalanceDetail";
	}

	@RequestMapping("/module/ehrinventory/viewStockBalanceSubStoreDetail.form")
	public String viewStockBalanceSubStoreDetail(
			@RequestParam(value = "drugId", required = false) Integer drugId,
			@RequestParam(value = "formulationId", required = false) Integer formulationId,
			@RequestParam(value = "expiry", required = false) Integer expiry,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		//InventoryStore store = inventoryServic.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
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
		List<InventoryStoreDrugTransactionDetail> listViewStockBalance = inventoryService
				.listStoreDrugTransactionDetail(store.getId(), drugId,
						formulationId, expiry);
		model.addAttribute("listViewStockBalance", listViewStockBalance);
		return "/module/ehrinventory/substore/viewStockBalanceDetail";
	}

	@RequestMapping("/module/ehrinventory/itemViewStockBalanceSubStoreDetail.form")
	public String itemViewStockBalanceSubStoreDetail(
			@RequestParam(value = "itemId", required = false) Integer itemId,
			@RequestParam(value = "specificationId", required = false) Integer specificationId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
	//	InventoryStore store = inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context	.getAuthenticatedUser().getAllRoles()));
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
		List<InventoryStoreItemTransactionDetail> listViewStockBalance = inventoryService
				.listStoreItemTransactionDetail(store.getId(), itemId,
						specificationId, 0, 0);
		model.addAttribute("listViewStockBalance", listViewStockBalance);
		return "/module/ehrinventory/substoreItem/itemViewStockBalanceDetail";
	}

	@RequestMapping("/module/ehrinventory/subStoreIssueDrugDettail.form")
	public String viewDetailIssueDrug(
			@RequestParam(value = "issueId", required = false) Integer issueId,
			Model model) {
		
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		//InventoryStore store =  inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
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
		List<InventoryStoreDrugPatientDetail> listDrugIssue = inventoryService
				.listStoreDrugPatientDetail(issueId);
		InventoryStoreDrugPatient inventoryStoreDrugPatient = new InventoryStoreDrugPatient();
		
		if ( inventoryStoreDrugPatient != null && listDrugIssue != null && listDrugIssue.size() > 0) {
		
		
		InventoryStoreDrugTransaction transaction = new InventoryStoreDrugTransaction();
		transaction.setDescription("ISSUE DRUG TO PATIENT "+DateUtils.getDDMMYYYY());
		transaction.setStore(store);
		transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
		
		transaction.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
		
		transaction = inventoryService.saveStoreDrugTransaction(transaction);
		for (InventoryStoreDrugPatientDetail pDetail : listDrugIssue) {
			
			Date date1 = new Date();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Integer totalQuantity = inventoryService
					.sumCurrentQuantityDrugOfStore(store.getId(), pDetail
							.getTransactionDetail().getDrug().getId(),
							pDetail.getTransactionDetail().getFormulation()
									.getId());
			int t = totalQuantity;
			
			Integer receipt=pDetail.getStoreDrugPatient().getId();
			model.addAttribute("receiptid", receipt);
			
			InventoryStoreDrugTransactionDetail inventoryStoreDrugTransactionDetail  = inventoryService
					.getStoreDrugTransactionDetailById(pDetail.getTransactionDetail().getParent().getId());
			
			InventoryStoreDrugTransactionDetail drugTransactionDetail = inventoryService.getStoreDrugTransactionDetailById(inventoryStoreDrugTransactionDetail.getId());
			
			inventoryStoreDrugTransactionDetail.setCurrentQuantity(drugTransactionDetail.getCurrentQuantity());
			
			Integer flags =pDetail.getTransactionDetail().getFlag();
			model.addAttribute("flag", flags);
			inventoryService.saveStoreDrugTransactionDetail( inventoryStoreDrugTransactionDetail);
			// save transactiondetail first
			InventoryStoreDrugTransactionDetail transDetail = new InventoryStoreDrugTransactionDetail();
			transDetail.setTransaction(transaction);
			transDetail.setCurrentQuantity(0);
			transDetail.setIssueQuantity(pDetail.getQuantity());
			transDetail.setOpeningBalance( totalQuantity);
			transDetail.setClosingBalance(t);
			transDetail.setQuantity(0);
			transDetail.setVAT(pDetail.getTransactionDetail().getVAT());
			transDetail.setCostToPatient(pDetail.getTransactionDetail().getCostToPatient());
			transDetail.setUnitPrice(pDetail.getTransactionDetail()
					.getUnitPrice());
			transDetail.setDrug(pDetail.getTransactionDetail().getDrug());
			transDetail.setFormulation(pDetail.getTransactionDetail()
					.getFormulation());
			transDetail.setBatchNo(pDetail.getTransactionDetail()
					.getBatchNo());
			transDetail.setCompanyName(pDetail.getTransactionDetail()
					.getCompanyName());
			transDetail.setDateManufacture(pDetail.getTransactionDetail()
					.getDateManufacture());
			transDetail.setDateExpiry(pDetail.getTransactionDetail()
					.getDateExpiry());
			transDetail.setReceiptDate(pDetail.getTransactionDetail()
					.getReceiptDate());
			transDetail.setCreatedOn(date1);
			transDetail.setReorderPoint(pDetail.getTransactionDetail().getDrug().getReorderQty());
			transDetail.setAttribute(pDetail.getTransactionDetail().getDrug().getAttributeName());
			transDetail.setFrequency(pDetail.getTransactionDetail().getFrequency());transDetail.setNoOfDays(pDetail.getTransactionDetail().getNoOfDays());
			transDetail.setComments(pDetail.getTransactionDetail().getComments());
			transDetail.setFlag(1);
			 
           
			BigDecimal moneyUnitPrice = pDetail.getTransactionDetail().getCostToPatient().multiply(new BigDecimal(pDetail.getQuantity()));
			
			transDetail.setTotalPrice(moneyUnitPrice);
            
           
			transDetail.setParent(pDetail.getTransactionDetail());
			transDetail = inventoryService
					.saveStoreDrugTransactionDetail(transDetail);
			 
			
   
		}
         
		}
          
		model.addAttribute("listDrugIssue", listDrugIssue);
		if (CollectionUtils.isNotEmpty(listDrugIssue)) {
			model.addAttribute("issueDrugPatient", listDrugIssue.get(0)
					.getStoreDrugPatient());                     
                        
			model.addAttribute("date", listDrugIssue.get(0)
					.getStoreDrugPatient().getCreatedOn());
			model.addAttribute("age", listDrugIssue.get(0)
					.getStoreDrugPatient().getPatient().getAge());
             //TODO starts here 
			
            PatientIdentifier pi = listDrugIssue.get(0).getStoreDrugPatient().getPatient().getPatientIdentifier();    
           
            int patientId = pi.getPatient().getPatientId();
            Date issueDate = listDrugIssue.get(0).getStoreDrugPatient().getCreatedOn();
            Encounter encounterId = listDrugIssue.get(0).getTransactionDetail().getEncounter();
            
            List<OpdDrugOrder> listOfNotDispensedOrder = null;
             if(encounterId!= null )
            {
            	listOfNotDispensedOrder = inventoryService.listOfNotDispensedOrder(patientId,issueDate,encounterId);
            	
            	model.addAttribute("listOfNotDispensedOrder", listOfNotDispensedOrder);
            	
            }
    		/*Iterator<OpdDrugOrder>  itr = listOfNotDispensedOrder.iterator();
             System.out.println("listOfNotDispensedOrder:"+ listOfNotDispensedOrder.size());
             while(itr.hasNext())
             {
            	 System.out.println("drugs: "+itr.next().getInventoryDrug().getId());
             }
             for(int i = 0, n = listOfNotDispensedOrder.size(); i < n; i++) {
                 System.out.println(listOfNotDispensedOrder.get(i).getInventoryDrug().getId());
                 System.out.println(listOfNotDispensedOrder.get(i).getInventoryDrug().getFormulations().iterator().next().getName());
                 System.out.println(listOfNotDispensedOrder.get(i).getEncounter());
                 System.out.println(listOfNotDispensedOrder.get(i).getComments());
                 System.out.println(listOfNotDispensedOrder.get(i).getNoOfDays());
                 System.out.println(listOfNotDispensedOrder.get(i).getFrequency());
             }*/
          
           
          //TODO ends here 
            
             
             
            
                        model.addAttribute("identifier", listDrugIssue.get(0)
					.getStoreDrugPatient().getPatient().getPatientIdentifier());
                        model.addAttribute("givenName", listDrugIssue.get(0)
					.getStoreDrugPatient().getPatient().getGivenName());
                        model.addAttribute("familyName", listDrugIssue.get(0)
					.getStoreDrugPatient().getPatient().getFamilyName());
                        if(listDrugIssue.get(0).getStoreDrugPatient().getPatient().getMiddleName()!=null){
                            model.addAttribute("middleName", listDrugIssue.get(0)
                					.getStoreDrugPatient().getPatient().getMiddleName());                        	
                        }

                        
			if(listDrugIssue.get(0)
					.getStoreDrugPatient().getPatient().getGender().equals("M")){
				model.addAttribute("gender", "Male");
			}
			if(listDrugIssue.get(0)
					.getStoreDrugPatient().getPatient().getGender().equals("F")){
				model.addAttribute("gender", "Female");
			}

			model.addAttribute("cashier", listDrugIssue.get(0)
					.getStoreDrugPatient().getCreatedBy());
			
			HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
			List<PersonAttribute> pas = hcs.getPersonAttributes(listDrugIssue.get(0)
					.getStoreDrugPatient().getPatient().getId());
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
			

		}
		return "/module/ehrinventory/substore/subStoreIssueDrugDettail";
	}
	@RequestMapping("/module/ehrinventory/subStoreIssueDrugDeduct.form")
	public String viewDrugDeduct(
			
			@RequestParam(value = "receiptid", required = false) Integer receiptid,
			@RequestParam(value = "flag", required = false) Integer flag,
			Model model) {
		
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		//InventoryStore store =  inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
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
		List<InventoryStoreDrugPatientDetail> listDrugIssue = inventoryService
				.listStoreDrugPatientDetail(receiptid);
		InventoryStoreDrugPatient inventoryStoreDrugPatient = new InventoryStoreDrugPatient();
	  
		if ( inventoryStoreDrugPatient != null && listDrugIssue != null && listDrugIssue.size() > 0) {
		
		
		InventoryStoreDrugTransaction transaction = new InventoryStoreDrugTransaction();
		transaction.setDescription("ISSUE DRUG TO PATIENT "+DateUtils.getDDMMYYYY());
		transaction.setStore(store);
		transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
		
		transaction.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
		
		transaction = inventoryService.saveStoreDrugTransaction(transaction);
		for (InventoryStoreDrugPatientDetail pDetail : listDrugIssue) {
			
			Date date1 = new Date();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Integer totalQuantity = inventoryService
					.sumCurrentQuantityDrugOfStore(store.getId(), pDetail
							.getTransactionDetail().getDrug().getId(),
							pDetail.getTransactionDetail().getFormulation()
									.getId());
			int t = totalQuantity - pDetail.getQuantity();
			
			InventoryStoreDrugTransactionDetail inventoryStoreDrugTransactionDetail  = inventoryService
					.getStoreDrugTransactionDetailById(pDetail.getTransactionDetail().getParent().getId());
			
			InventoryStoreDrugTransactionDetail drugTransactionDetail = inventoryService.getStoreDrugTransactionDetailById(inventoryStoreDrugTransactionDetail.getId());
			
			inventoryStoreDrugTransactionDetail.setCurrentQuantity(drugTransactionDetail.getCurrentQuantity()-pDetail.getQuantity());
			
			inventoryService.saveStoreDrugTransactionDetail( inventoryStoreDrugTransactionDetail);

			// save transactiondetail first
			InventoryStoreDrugTransactionDetail transDetail = new InventoryStoreDrugTransactionDetail();
			transDetail.setTransaction(transaction);
			transDetail.setCurrentQuantity(0);
			
			
			transDetail.setIssueQuantity(pDetail.getQuantity());
			transDetail.setOpeningBalance( totalQuantity);
			transDetail.setClosingBalance(t);
			transDetail.setQuantity(0);
			transDetail.setVAT(pDetail.getTransactionDetail().getVAT());
			transDetail.setCostToPatient(pDetail.getTransactionDetail().getCostToPatient());
			transDetail.setUnitPrice(pDetail.getTransactionDetail()
					.getUnitPrice());
			transDetail.setDrug(pDetail.getTransactionDetail().getDrug());
			transDetail.setFormulation(pDetail.getTransactionDetail()
					.getFormulation());
			transDetail.setBatchNo(pDetail.getTransactionDetail()
					.getBatchNo());
			transDetail.setCompanyName(pDetail.getTransactionDetail()
					.getCompanyName());
			transDetail.setDateManufacture(pDetail.getTransactionDetail()
					.getDateManufacture());
			transDetail.setDateExpiry(pDetail.getTransactionDetail()
					.getDateExpiry());
			transDetail.setReceiptDate(pDetail.getTransactionDetail()
					.getReceiptDate());
			transDetail.setCreatedOn(date1);
			transDetail.setReorderPoint(pDetail.getTransactionDetail().getDrug().getReorderQty());
			transDetail.setAttribute(pDetail.getTransactionDetail().getDrug().getAttributeName());
			transDetail.setFrequency(pDetail.getTransactionDetail().getFrequency());transDetail.setNoOfDays(pDetail.getTransactionDetail().getNoOfDays());
			transDetail.setComments(pDetail.getTransactionDetail().getComments());
			transDetail.setFlag(1);
			
	           
				BigDecimal moneyUnitPrice = pDetail.getTransactionDetail().getCostToPatient().multiply(new BigDecimal(pDetail.getQuantity()));
				
				transDetail.setTotalPrice(moneyUnitPrice);
	          

			transDetail.setParent(pDetail.getTransactionDetail());
			transDetail = inventoryService
					.saveStoreDrugTransactionDetail(transDetail);
			pDetail.setQuantity( pDetail.getQuantity());
			
			 pDetail.setTransactionDetail(transDetail);
			
			
			// save issue to patient detail
			inventoryService.saveStoreDrugPatientDetail(pDetail);
			
             if(transDetail.getFlag()==1)
             {   
            	 inventoryStoreDrugPatient=inventoryService.getStoreDrugPatientById(pDetail.getStoreDrugPatient().getId());
            	 inventoryStoreDrugPatient.setStatuss(1);
            	
             }
		
         
		}
          
		model.addAttribute("listDrugIssue", listDrugIssue);
		if (CollectionUtils.isNotEmpty(listDrugIssue)) {
			model.addAttribute("issueDrugPatient", listDrugIssue.get(0)
					.getStoreDrugPatient());                     
                        
			model.addAttribute("date", listDrugIssue.get(0)
					.getStoreDrugPatient().getCreatedOn());
			model.addAttribute("age", listDrugIssue.get(0)
					.getStoreDrugPatient().getPatient().getAge());
             //TODO starts here 
			
            PatientIdentifier pi = listDrugIssue.get(0).getStoreDrugPatient().getPatient().getPatientIdentifier();    
           
            int patientId = pi.getPatient().getPatientId();
            Date issueDate = listDrugIssue.get(0).getStoreDrugPatient().getCreatedOn();
            Encounter encounterId = listDrugIssue.get(0).getTransactionDetail().getEncounter();
            
            List<OpdDrugOrder> listOfNotDispensedOrder = null;
             if(encounterId!= null )
            {
            	listOfNotDispensedOrder = inventoryService.listOfNotDispensedOrder(patientId,issueDate,encounterId);
            	
            	model.addAttribute("listOfNotDispensedOrder", listOfNotDispensedOrder);
            }
    		/*Iterator<OpdDrugOrder>  itr = listOfNotDispensedOrder.iterator();
             System.out.println("listOfNotDispensedOrder:"+ listOfNotDispensedOrder.size());
             while(itr.hasNext())
             {
            	 System.out.println("drugs: "+itr.next().getInventoryDrug().getId());
             }
             for(int i = 0, n = listOfNotDispensedOrder.size(); i < n; i++) {
                 System.out.println(listOfNotDispensedOrder.get(i).getInventoryDrug().getId());
                 System.out.println(listOfNotDispensedOrder.get(i).getInventoryDrug().getFormulations().iterator().next().getName());
                 System.out.println(listOfNotDispensedOrder.get(i).getEncounter());
                 System.out.println(listOfNotDispensedOrder.get(i).getComments());
                 System.out.println(listOfNotDispensedOrder.get(i).getNoOfDays());
                 System.out.println(listOfNotDispensedOrder.get(i).getFrequency());
             }*/
          
           
          //TODO ends here 
            
             
             
            
                        model.addAttribute("identifier", listDrugIssue.get(0)
					.getStoreDrugPatient().getPatient().getPatientIdentifier());
                        model.addAttribute("givenName", listDrugIssue.get(0)
					.getStoreDrugPatient().getPatient().getGivenName());
                        model.addAttribute("familyName", listDrugIssue.get(0)
					.getStoreDrugPatient().getPatient().getFamilyName());
                        if(listDrugIssue.get(0).getStoreDrugPatient().getPatient().getMiddleName()!=null){
                            model.addAttribute("middleName", listDrugIssue.get(0)
                					.getStoreDrugPatient().getPatient().getMiddleName());                        	
                        }

                        
			if(listDrugIssue.get(0)
					.getStoreDrugPatient().getPatient().getGender().equals("M")){
				model.addAttribute("gender", "Male");
			}
			if(listDrugIssue.get(0)
					.getStoreDrugPatient().getPatient().getGender().equals("F")){
				model.addAttribute("gender", "Female");
			}

			model.addAttribute("cashier", listDrugIssue.get(0)
					.getStoreDrugPatient().getCreatedBy());
			
			HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
			List<PersonAttribute> pas = hcs.getPersonAttributes(listDrugIssue.get(0)
					.getStoreDrugPatient().getPatient().getId());
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
		}

		}  
		    
		return "/module/ehrinventory/substore/subStoreIssueDrugDeduct";
	}
	@RequestMapping("/module/ehrinventory/subStoreIssueDrugAccountDettail.form")
	public String viewDetailIssueDrugAccount(
			@RequestParam(value = "issueId", required = false) Integer issueId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		List<InventoryStoreDrugAccountDetail> listDrugIssue = inventoryService
				.listStoreDrugAccountDetail(issueId);
		model.addAttribute("listDrugIssue", listDrugIssue);
		if (CollectionUtils.isNotEmpty(listDrugIssue)) {
			model.addAttribute("issueDrugAccount", listDrugIssue.get(0)
					.getDrugAccount());
			model.addAttribute("date", listDrugIssue.get(0).getDrugAccount()
					.getCreatedOn());
		}
		return "/module/ehrinventory/substore/subStoreIssueDrugAccountDettail";
	}

	@RequestMapping("/module/ehrinventory/subStoreIssueItemDettail.form")
	public String viewDetailIssueItem(
			@RequestParam(value = "issueId", required = false) Integer issueId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		List<InventoryStoreItemAccountDetail> listItemIssue = inventoryService
				.listStoreItemAccountDetail(issueId);
		model.addAttribute("listItemIssue", listItemIssue);
		if (CollectionUtils.isNotEmpty(listItemIssue)) {
			model.addAttribute("issueItemAccount", listItemIssue.get(0)
					.getItemAccount());
			model.addAttribute("date", listItemIssue.get(0).getItemAccount()
					.getCreatedOn());
		}
		return "/module/ehrinventory/substoreItem/subStoreIssueItemDettail";
	}

	@RequestMapping("/module/ehrinventory/subStoreIssueItemPatientDettail.form")
	public String viewDetailIssueItemPatient(
			@RequestParam(value = "issueId", required = false) Integer issueId,
			Model model) {
		
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		//InventoryStore store =  inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
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
		List<InventoryStoreItemPatientDetail> listItemIssue = inventoryService
				.listStoreItemPatientDetail(issueId);
		
		
		InventoryStoreItemPatient inventoryStoreItemPatient = new InventoryStoreItemPatient();
		if ( inventoryStoreItemPatient != null && listItemIssue != null && listItemIssue.size() > 0) {
		
			
			InventoryStoreItemTransaction transaction = new InventoryStoreItemTransaction();
			transaction.setDescription("ISSUE DRUG TO PATIENT "+DateUtils.getDDMMYYYY());
			transaction.setStore(store);
			transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
			
			transaction.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			
			transaction = inventoryService.saveStoreItemTransaction(transaction);
			for (InventoryStoreItemPatientDetail pDetail : listItemIssue)
			{  
				
				Date date1 = new Date();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Integer totalQuantity = inventoryService.sumStoreItemCurrentQuantity(store.getId(), pDetail
								.getTransactionDetail().getItem().getId(),
								pDetail.getTransactionDetail().getSpecification()
										.getId());
				int t = totalQuantity;
				Integer receipt=pDetail.getStoreItemPatient().getId();
				model.addAttribute("receiptid", receipt);
				InventoryStoreItemTransactionDetail inventoryStoreItemTransactionDetail  = inventoryService
						.getStoreItemTransactionDetailById(pDetail.getTransactionDetail().getParent().getId());
				
				InventoryStoreItemTransactionDetail itemTransactionDetail = inventoryService.getStoreItemTransactionDetailById(inventoryStoreItemTransactionDetail.getId());
				
				inventoryStoreItemTransactionDetail.setCurrentQuantity(itemTransactionDetail.getCurrentQuantity());
				
				Integer flags =pDetail.getTransactionDetail().getFlag();
				model.addAttribute("flag", flags);
				inventoryService.saveStoreItemTransactionDetail( inventoryStoreItemTransactionDetail);
				
				
			}
		}
		model.addAttribute("listItemPatientIssue", listItemIssue);
		if (CollectionUtils.isNotEmpty(listItemIssue)) {

			model.addAttribute("issueItemPatient", listItemIssue.get(0)
					.getStoreItemPatient());
			model.addAttribute("date", listItemIssue.get(0)
					.getStoreItemPatient().getCreatedOn());
			model.addAttribute("age", listItemIssue.get(0)
					.getStoreItemPatient().getPatient().getAge());
			if(listItemIssue.get(0)
					.getStoreItemPatient().getPatient().getGender().equals("M")){
				model.addAttribute("gender", "Male");
			}
			if(listItemIssue.get(0)
					.getStoreItemPatient().getPatient().getGender().equals("F")){
				model.addAttribute("gender", "Female");
			}
			model.addAttribute("cashier", listItemIssue.get(0)
					.getStoreItemPatient().getCreatedBy());
			/*model.addAttribute("paymentMode", listItemIssue.get(0)
					.getTransactionDetail().getTransaction().getPaymentMode());*/
			HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
			List<PersonAttribute> pas = hcs.getPersonAttributes(listItemIssue.get(0)
					.getStoreItemPatient().getPatient().getId());
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
			/*model.addAttribute("category", listItemIssue.get(0)
					.getTransactionDetail().getTransaction().getPaymentCategory());*/

		/*	if (listItemIssue.get(0)
					.getTransactionDetail().getTransaction().getPaymentCategory().equals("Waiver")) {
				model.addAttribute("exemption", listItemIssue.get(0)
						.getStoreItemPatient().getPatient().getAttribute(32));
			} else if (!listItemIssue.get(0)
					.getTransactionDetail().getTransaction().getPaymentCategory().equals("General")
					&& !listItemIssue.get(0)
					.getTransactionDetail().getTransaction().getPaymentCategory().equals("Waiver")) {
				model.addAttribute("exemption", listItemIssue.get(0)
						.getStoreItemPatient().getPatient().getAttribute(36));
			} else {
				model.addAttribute("exemption", " ");
			}*/
	        
	     
		}
		return "/module/ehrinventory/substoreItem/subStoreIssueItemPatientDettail";
	}
	@RequestMapping("/module/ehrinventory/subStoreIssueItemDeduct.form")
	public String viewItemDeduct(
			@RequestParam(value = "receiptid", required = false) Integer receiptid,
			@RequestParam(value = "flag", required = false) Integer flag,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		
		//InventoryStore store =  inventoryService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
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
		List<InventoryStoreItemPatientDetail> listItemIssue = inventoryService
				.listStoreItemPatientDetail(receiptid);
		
		InventoryStoreItemPatient inventoryStoreItemPatient = new InventoryStoreItemPatient();
		if ( inventoryStoreItemPatient != null && listItemIssue != null && listItemIssue.size() > 0) {
			
			
			InventoryStoreItemTransaction transaction = new InventoryStoreItemTransaction();
			transaction.setDescription("ISSUE DRUG TO PATIENT "+DateUtils.getDDMMYYYY());
			transaction.setStore(store);
			transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
			
			transaction.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			
			transaction = inventoryService.saveStoreItemTransaction(transaction);
			for (InventoryStoreItemPatientDetail pDetail : listItemIssue)
			{
				
				Date date1 = new Date();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Integer totalQuantity = inventoryService.sumStoreItemCurrentQuantity(store.getId(), pDetail
								.getTransactionDetail().getItem().getId(),
								pDetail.getTransactionDetail().getSpecification()
										.getId());
				int t = totalQuantity -pDetail.getQuantity();
               
				InventoryStoreItemTransactionDetail inventoryStoreItemTransactionDetail  = inventoryService
						.getStoreItemTransactionDetailById(pDetail.getTransactionDetail().getParent().getId());
				
				InventoryStoreItemTransactionDetail itemTransactionDetail = inventoryService.getStoreItemTransactionDetailById(inventoryStoreItemTransactionDetail.getId());
				
				inventoryStoreItemTransactionDetail.setCurrentQuantity(itemTransactionDetail.getCurrentQuantity()-pDetail.getQuantity());
				
				inventoryService.saveStoreItemTransactionDetail( inventoryStoreItemTransactionDetail);

				
				
				
				
				//inventoryService.saveStoreItemTransactionDetail( inventoryStoreItemTransactionDetail);
				InventoryStoreItemTransactionDetail transDetail = new InventoryStoreItemTransactionDetail();
				transDetail.setTransaction(transaction);
				transDetail.setCurrentQuantity(0);
				transDetail.setIssueQuantity(pDetail.getQuantity());
				transDetail.setOpeningBalance(totalQuantity);
				transDetail.setClosingBalance(t);
				transDetail.setQuantity(0);
				transDetail.setVAT(pDetail.getTransactionDetail().getVAT());
				transDetail.setCostToPatient(pDetail.getTransactionDetail().getCostToPatient());
				transDetail.setUnitPrice(pDetail.getTransactionDetail()
						.getUnitPrice());
				transDetail.setItem(pDetail.getTransactionDetail().getItem());
				transDetail.setSpecification(pDetail.getTransactionDetail()
						.getSpecification());
				transDetail.setCompanyName(pDetail.getTransactionDetail()
						.getCompanyName());
				transDetail.setDateManufacture(pDetail.getTransactionDetail()
						.getDateManufacture());
				transDetail.setReceiptDate(pDetail.getTransactionDetail()
						.getReceiptDate());
				transDetail.setCreatedOn(date1);
				transDetail.setFlag(1);
				transDetail.setParent(pDetail.getTransactionDetail());
				transDetail.setAttribute(pDetail.getTransactionDetail().getAttribute());
				
				BigDecimal moneyUnitPrice = pDetail.getTransactionDetail().getCostToPatient().multiply(new BigDecimal(pDetail.getQuantity()));
				
				transDetail.setTotalPrice(moneyUnitPrice);
	            
	           
				transDetail.setParent(pDetail.getTransactionDetail());
				transDetail = inventoryService.saveStoreItemTransactionDetail(transDetail);
				pDetail.setQuantity( pDetail.getQuantity());
				//pDetail.setItemAccount(issueItemAccount);
				pDetail.setTransactionDetail(transDetail);
				inventoryService.saveStoreItemPatientDetail(pDetail);
				if(transDetail.getFlag()==1)
				{ 
					inventoryStoreItemPatient=inventoryService.getStoreItemPatientById(pDetail.getStoreItemPatient().getId());
					
					inventoryStoreItemPatient.setStatuss(1);
					
				
					
				}
				
				
			}
		}
		model.addAttribute("listItemPatientIssue", listItemIssue);
		if (CollectionUtils.isNotEmpty(listItemIssue)) {

			model.addAttribute("issueItemPatient", listItemIssue.get(0)
					.getStoreItemPatient());
			model.addAttribute("date", listItemIssue.get(0)
					.getStoreItemPatient().getCreatedOn());
			model.addAttribute("age", listItemIssue.get(0)
					.getStoreItemPatient().getPatient().getAge());
			if(listItemIssue.get(0)
					.getStoreItemPatient().getPatient().getGender().equals("M")){
				model.addAttribute("gender", "Male");
			}
			if(listItemIssue.get(0)
					.getStoreItemPatient().getPatient().getGender().equals("F")){
				model.addAttribute("gender", "Female");
			}
			model.addAttribute("cashier", listItemIssue.get(0)
					.getStoreItemPatient().getCreatedBy());
			/*model.addAttribute("paymentMode", listItemIssue.get(0)
					.getTransactionDetail().getTransaction().getPaymentMode());*/
			HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
			List<PersonAttribute> pas = hcs.getPersonAttributes(listItemIssue.get(0)
					.getStoreItemPatient().getPatient().getId());
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
		}
		return "/module/ehrinventory/substoreItem/subStoreIssueItemDeduct";
	}

	@RequestMapping("/module/ehrinventory/subCatByCat.form")
	public String getSubCatByCat(
			@RequestParam(value = "categoryId", required = false) Integer categoryId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		List<InventoryItemSubCategory> subCategories = inventoryService
				.listSubCatByCat(categoryId);
		model.addAttribute("subCategories", subCategories);
		return "/module/ehrinventory/item/subCatByCat";
	}

	@RequestMapping("/module/ehrinventory/drugReceiptDetail.form")
	public String drugReceiptDetail(
			@RequestParam(value = "receiptId", required = false) Integer receiptId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		List<InventoryStoreDrugTransactionDetail> transactionDetails = inventoryService
				.listTransactionDetail(receiptId);
		if (!CollectionUtils.isEmpty(transactionDetails)) {
			model.addAttribute("store", transactionDetails.get(0)
					.getTransaction().getStore());
			model.addAttribute("date", transactionDetails.get(0)
					.getTransaction().getCreatedOn());
		}
		model.addAttribute("transactionDetails", transactionDetails);
		return "/module/ehrinventory/mainstore/receiptsToGeneralStoreDetail";
	}

	@RequestMapping("/module/ehrinventory/itemReceiptDetail.form")
	public String itemReceiptDetail(
			@RequestParam(value = "receiptId", required = false) Integer receiptId,
			Model model) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		List<InventoryStoreItemTransactionDetail> transactionDetails = inventoryService
				.listStoreItemTransactionDetail(receiptId);
		if (!CollectionUtils.isEmpty(transactionDetails)) {
			model.addAttribute("store", transactionDetails.get(0)
					.getTransaction().getStore());
			model.addAttribute("date", transactionDetails.get(0)
					.getTransaction().getCreatedOn());
		}
		model.addAttribute("transactionDetails", transactionDetails);
		return "/module/ehrinventory/mainstoreItem/itemReceiptsToGeneralStoreDetail";
	}

	@RequestMapping(value = "/module/ehrinventory/viewStockBalanceExpiry.form", method = RequestMethod.GET)
	public String viewStockBalanceExpiry(
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "currentPage", required = false) Integer currentPage,
			@RequestParam(value = "categoryId", required = false) Integer categoryId,
			@RequestParam(value = "drugName", required = false) String drugName,
			@RequestParam(value = "attribute", required = false) String attribute,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			Map<String, Object> model, HttpServletRequest request) {
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
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
		// ghanshyam 7-august-2013 code review bug
		if (store != null) {
			int total = inventoryService.countViewStockBalance(store.getId(),
					categoryId, drugName, attribute, fromDate, toDate, true);
			String temp = "";
			if (categoryId != null) {
				temp = "?categoryId=" + categoryId;
			}

			if (drugName != null) {
				if (StringUtils.isBlank(temp)) {
					temp = "?drugName=" + drugName;
				} else {
					temp += "&drugName=" + drugName;
				}
			}
			//NEW 
			if(attribute != null){	
				if(StringUtils.isBlank(temp)){
					temp = "?attribute="+attribute;
				}else{
					temp +="&attribute="+attribute;
				}
		}
			
			if (fromDate != null) {
				if (StringUtils.isBlank(temp)) {
					temp = "?fromDate=" + fromDate;
				} else {
					temp += "&fromDate=" + fromDate;
				}
			}
			if (toDate != null) {
				if (StringUtils.isBlank(temp)) {
					temp = "?toDate=" + toDate;
				} else {
					temp += "&toDate=" + toDate;
				}
			}

			PagingUtil pagingUtil = new PagingUtil(
					RequestUtil.getCurrentLink(request) + temp, pageSize,
					currentPage, total);
			List<InventoryStoreDrugTransactionDetail> stockBalances = inventoryService
					.listViewStockBalance(store.getId(), categoryId, drugName,attribute,
							fromDate, toDate, true, pagingUtil.getStartPos(),
							pagingUtil.getPageSize());
			List<InventoryDrugCategory> listCategory = inventoryService
					.listDrugCategory("", 0, 0);
			// 03/07/2012: Kesavulu:sort Item Names #300
			if (stockBalances != null) {
				Collections.sort(stockBalances);
			}
			model.put("categoryId", categoryId);
			model.put("drugName", drugName);
			//NEW
			model.put("attribute", attribute );
			model.put("fromDate", fromDate);
			model.put("toDate", toDate);
			model.put("pagingUtil", pagingUtil);
			model.put("stockBalances", stockBalances);
			model.put("listCategory", listCategory);
			model.put("store", store);
		}
		if (store != null && store.getParentStores().isEmpty()) {
			return "/module/ehrinventory/mainstore/viewStockBalanceExpiry";
		} else {
			return "/module/ehrinventory/substore/viewStockBalanceExpiry";
		}

	}

	@RequestMapping("/module/ehrinventory/removeObjectFromList.form")
	public String removeObjectFromList(
			@RequestParam(value = "position") Integer position,
			@RequestParam(value = "check") Integer check, Model model) {
		int userId = Context.getAuthenticatedUser().getId();

		String fowardParam1 = "issueItemDetail_" + userId;
		String fowardParam2 = "subStoreIndentItem_" + userId;
		String fowardParam3 = "subStoreIndentDrug_" + userId;
		String fowardParam4 = "issueDrugAccountDetail_" + userId;
		String fowardParam5 = "issueDrugDetail_" + userId;
		String fowardParam6 = "itemReceipt_" + userId;
		String fowardParam7 = "reipt_" + userId;
		List list = null;
		switch (check) {
		case 1:
			// process fowardParam1
			list = (List<InventoryStoreItemAccountDetail>) StoreSingleton
					.getInstance().getHash().get(fowardParam1);
			if (CollectionUtils.isNotEmpty(list)) {
				InventoryStoreItemAccountDetail a = (InventoryStoreItemAccountDetail) list
						.get(position);
				// System.out.println("a fowardParam1: "+a.getTransactionDetail().getItem().getName());
				list.remove(a);
			}
			StoreSingleton.getInstance().getHash().put(fowardParam1, list);
			return "redirect:/module/ehrinventory/subStoreIssueItemForm.form";
		case 2:
			// process fowardParam2
			list = (List<InventoryStoreItemIndentDetail>) StoreSingleton
					.getInstance().getHash().get(fowardParam2);
			if (CollectionUtils.isNotEmpty(list)) {
				InventoryStoreItemIndentDetail a = (InventoryStoreItemIndentDetail) list
						.get(position);
				// System.out.println("a fowardParam2: "+a.getItem().getName());
				list.remove(a);
			}
			StoreSingleton.getInstance().getHash().put(fowardParam2, list);
			return "redirect:/module/ehrinventory/subStoreIndentItem.form";
		case 3:
			// process fowardParam3
			list = (List<InventoryStoreDrugIndentDetail>) StoreSingleton
					.getInstance().getHash().get(fowardParam3);
			if (CollectionUtils.isNotEmpty(list)) {
				InventoryStoreDrugIndentDetail a = (InventoryStoreDrugIndentDetail) list
						.get(position);
				// System.out.println("fowardParam3 a drug : "+a.getDrug().getName());
				list.remove(a);
			}
			StoreSingleton.getInstance().getHash().put(fowardParam3, list);
			return "redirect:/module/inventory/subStoreIndentDrug.form";
		case 4:
			// process fowardParam4
			list = (List<InventoryStoreDrugAccountDetail>) StoreSingleton
					.getInstance().getHash().get(fowardParam4);
			if (CollectionUtils.isNotEmpty(list)) {
				InventoryStoreDrugAccountDetail a = (InventoryStoreDrugAccountDetail) list
						.get(position);
				// System.out.println("fowardParam4 a drug : "+a.getTransactionDetail().getDrug().getName());
				list.remove(a);
			}
			StoreSingleton.getInstance().getHash().put(fowardParam4, list);
			return "redirect:/module/ehrinventory/subStoreIssueDrugAccountForm.form";
		case 5:
			// process fowardParam5
			list = (List<InventoryStoreDrugPatientDetail>) StoreSingleton
					.getInstance().getHash().get(fowardParam5);
			if (CollectionUtils.isNotEmpty(list)) {
				InventoryStoreDrugPatientDetail a = (InventoryStoreDrugPatientDetail) list
						.get(position);
				// .out.println("fowardParam5 a drug : "+a.getTransactionDetail().getDrug().getName());
				list.remove(a);
			}
			StoreSingleton.getInstance().getHash().put(fowardParam5, list);
			return "redirect:/module/ehrinventory/subStoreIssueDrugForm.form";
		case 6:
			// process fowardParam6
			list = (List<InventoryStoreItemTransactionDetail>) StoreSingleton
					.getInstance().getHash().get(fowardParam6);
			if (CollectionUtils.isNotEmpty(list)) {
				InventoryStoreItemTransactionDetail a = (InventoryStoreItemTransactionDetail) list
						.get(position);
				// System.out.println("fowardParam6 a item : "+a.getItem().getName());
				list.remove(a);
			}
			StoreSingleton.getInstance().getHash().put(fowardParam6, list);
			return "redirect:/module/ehrinventory/itemReceiptsToGeneralStore.form";
		case 7:
			// process fowardParam7
			list = (List<InventoryStoreDrugTransactionDetail>) StoreSingleton
					.getInstance().getHash().get(fowardParam7);
			if (CollectionUtils.isNotEmpty(list)) {
				InventoryStoreDrugTransactionDetail a = (InventoryStoreDrugTransactionDetail) list
						.get(position);
				// System.out.println("fowardParam7 a drug : "+a.getDrug().getName());
				list.remove(a);
			}
			StoreSingleton.getInstance().getHash().put(fowardParam7, list);
			return "redirect:/module/ehrinventory/receiptsToGeneralStore.form";
		default:
		}

		return "redirect:/module/ehrinventory/main.form";
	}

	// ghanshyam 15-june-2013 New Requirement #1636 User is able to see and
	// dispense drugs in patient queue for issuing drugs, as ordered from
	// dashboard
	@RequestMapping("/module/ehrinventory/processDrugOrder.form")
	public String listReceiptDrugAvailablee(
			@RequestParam(value = "drugId", required = false) Integer drugId,
			@RequestParam(value = "formulationId", required = false) Integer formulationId,
			@RequestParam(value = "frequencyName", required = false) String frequencyName,
			@RequestParam(value = "days", required = false) Integer days,
			@RequestParam(value = "comments", required = false) String comments,
			Model model) {

		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		InventoryDrug drug = inventoryService.getDrugById(drugId);
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
		if (store != null && drug != null && formulationId != null) {
			List<InventoryStoreDrugTransactionDetail> listReceiptDrug = inventoryService
					.listStoreDrugTransactionDetail(store.getId(),
							drug.getId(), formulationId, true);
			// check that drug is issued before
			int userId = Context.getAuthenticatedUser().getId();

			String fowardParam = "issueDrugAccountDetail_" + userId;
			String fowardParamDrug = "issueDrugDetail_" + userId;
			List<InventoryStoreDrugPatientDetail> listDrug = (List<InventoryStoreDrugPatientDetail>) StoreSingleton
					.getInstance().getHash().get(fowardParamDrug);
			List<InventoryStoreDrugAccountDetail> listDrugAccount = (List<InventoryStoreDrugAccountDetail>) StoreSingleton
					.getInstance().getHash().get(fowardParam);
			List<InventoryStoreDrugTransactionDetail> listReceiptDrugReturn = new ArrayList<InventoryStoreDrugTransactionDetail>();
			boolean check = false;
			if (CollectionUtils.isNotEmpty(listDrug)) {
				if (CollectionUtils.isNotEmpty(listReceiptDrug)) {
					for (InventoryStoreDrugTransactionDetail drugDetail : listReceiptDrug) {
						for (InventoryStoreDrugPatientDetail drugPatient : listDrug) {
							if (drugDetail.getId().equals(
									drugPatient.getTransactionDetail().getId())) {
								drugDetail.setCurrentQuantity(drugDetail
										.getCurrentQuantity()
										- drugPatient.getQuantity());
							}

						}
						if (drugDetail.getCurrentQuantity() > 0) {
							listReceiptDrugReturn.add(drugDetail);
							check = true;
						}
					}
				}
			}

			if (CollectionUtils.isNotEmpty(listDrugAccount)) {
				if (CollectionUtils.isNotEmpty(listReceiptDrug)) {
					for (InventoryStoreDrugTransactionDetail drugDetail : listReceiptDrug) {
						for (InventoryStoreDrugAccountDetail drugAccount : listDrugAccount) {
							if (drugDetail.getId().equals(
									drugAccount.getTransactionDetail().getId())) {
								drugDetail.setCurrentQuantity(drugDetail
										.getCurrentQuantity()
										- drugAccount.getQuantity());
							}
						}
						if (drugDetail.getCurrentQuantity() > 0 && !check) {
							listReceiptDrugReturn.add(drugDetail);
						}
					}
				}
			}
			if (CollectionUtils.isEmpty(listReceiptDrugReturn)
					&& CollectionUtils.isNotEmpty(listReceiptDrug)) {
				listReceiptDrugReturn.addAll(listReceiptDrug);
			}

			model.addAttribute("listReceiptDrug", listReceiptDrugReturn);

			// ghanshyam,4-july-2013, issue no # 1984, User can issue drugs only
			// from the first indent
			String listOfDrugQuantity = "";
			for (InventoryStoreDrugTransactionDetail lrdr : listReceiptDrugReturn) {
				listOfDrugQuantity = listOfDrugQuantity
						+ lrdr.getId().toString() + ".";
			}

			model.addAttribute("listOfDrugQuantity", listOfDrugQuantity);
			model.addAttribute("frequencyName", frequencyName);
			model.addAttribute("noOfDays", days);
			model.addAttribute("comments", comments);
		}

		return "/module/ehrinventory/queue/processDrugOrder";
	}
	
	
	@RequestMapping("/module/ehrinventory/processIssueDrugForIpdPatient.form")
	public String processIssueDrugForIpdPatient(
			@RequestParam(value = "action", required = false) Integer action,
			@RequestParam(value = "patientType", required = false) String patientType,
			Model model) {
		BigDecimal moneyUnitPrice = null;
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "issueDrugDetail_" + userId;
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
		if (action == 1) {
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash()
					.remove("issueDrug_" + userId);
			return "redirect:/module/ehrinventory/subStoreIssueDrugForm.form";
		}
		List<InventoryStoreDrugPatientDetail> list = (List<InventoryStoreDrugPatientDetail>) StoreSingleton
				.getInstance().getHash().get(fowardParam);
		InventoryStoreDrugPatient issueDrugPatient = (InventoryStoreDrugPatient) StoreSingleton
				.getInstance().getHash().get("issueDrug_" + userId);
		if (issueDrugPatient != null && list != null && list.size() > 0) {
			
			List<EncounterType> types = new ArrayList<EncounterType>();
			EncounterType eType = new EncounterType(10);
			types.add(eType);
			Encounter lastVisitEncounter = hcs.getLastVisitEncounter(issueDrugPatient.getPatient(), types);
			Date date = new Date();
			// create transaction issue from substore
			InventoryStoreDrugTransaction transaction = new InventoryStoreDrugTransaction();
			transaction.setDescription("ISSUE DRUG TO PATIENT "
					+ DateUtils.getDDMMYYYY());
			transaction.setStore(store);
			transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
			transaction.setCreatedOn(date);
			//transaction.setPaymentMode(paymentMode);
			transaction.setPaymentCategory(issueDrugPatient.getPatient().getAttribute(14).getValue());
			transaction.setCreatedBy(Context.getAuthenticatedUser()
					.getGivenName());
			transaction = inventoryService
					.saveStoreDrugTransaction(transaction);

			issueDrugPatient = inventoryService
					.saveStoreDrugPatient(issueDrugPatient);
			for (InventoryStoreDrugPatientDetail pDetail : list) {
				
				Date date1 = new Date();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Integer totalQuantity = inventoryService
						.sumCurrentQuantityDrugOfStore(store.getId(), pDetail
								.getTransactionDetail().getDrug().getId(),
								pDetail.getTransactionDetail().getFormulation()
										.getId());
				int t = totalQuantity - pDetail.getQuantity();
				InventoryStoreDrugTransactionDetail drugTransactionDetail = inventoryService
						.getStoreDrugTransactionDetailById(pDetail
								.getTransactionDetail().getId());
				pDetail.getTransactionDetail().setCurrentQuantity(
						drugTransactionDetail.getCurrentQuantity()
								- pDetail.getQuantity());
				inventoryService.saveStoreDrugTransactionDetail(pDetail
						.getTransactionDetail());

				// save transactiondetail first
				InventoryStoreDrugTransactionDetail transDetail = new InventoryStoreDrugTransactionDetail();
				transDetail.setTransaction(transaction);
				transDetail.setCurrentQuantity(0);
				transDetail.setIssueQuantity(pDetail.getQuantity());
				transDetail.setOpeningBalance(totalQuantity);
				transDetail.setClosingBalance(t);
				transDetail.setQuantity(0);
				transDetail.setVAT(pDetail.getTransactionDetail().getVAT());
				transDetail.setCostToPatient(pDetail.getTransactionDetail().getCostToPatient());
				transDetail.setUnitPrice(pDetail.getTransactionDetail()
						.getUnitPrice());
				transDetail.setDrug(pDetail.getTransactionDetail().getDrug());
				transDetail.setFormulation(pDetail.getTransactionDetail()
						.getFormulation());
				transDetail.setBatchNo(pDetail.getTransactionDetail()
						.getBatchNo());
				transDetail.setCompanyName(pDetail.getTransactionDetail()
						.getCompanyName());
				transDetail.setDateManufacture(pDetail.getTransactionDetail()
						.getDateManufacture());
				transDetail.setDateExpiry(pDetail.getTransactionDetail()
						.getDateExpiry());
				transDetail.setReceiptDate(pDetail.getTransactionDetail()
						.getReceiptDate());
				transDetail.setCreatedOn(date1);
				transDetail.setReorderPoint(pDetail.getTransactionDetail().getDrug().getReorderQty());
				transDetail.setAttribute(pDetail.getTransactionDetail().getDrug().getAttributeName());
				transDetail.setPatientType(patientType);
				

				moneyUnitPrice = pDetail.getTransactionDetail()
						.getCostToPatient()
						.multiply(new BigDecimal(pDetail.getQuantity()));
				/*moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice
						.multiply(pDetail.getTransactionDetail().getVAT()
								.divide(new BigDecimal(100))));*/
				transDetail.setTotalPrice(moneyUnitPrice);

				transDetail.setParent(pDetail.getTransactionDetail());
				transDetail = inventoryService
						.saveStoreDrugTransactionDetail(transDetail);

				pDetail.setStoreDrugPatient(issueDrugPatient);
				pDetail.setTransactionDetail(transDetail);
				// save issue to patient detail
				inventoryService.saveStoreDrugPatientDetail(pDetail);
				// save issues transaction detail
		
				List<PersonAttribute> pas = hcs.getPersonAttributes(issueDrugPatient.getPatient().getId());
						String patientSubCatergory = null;
		        for (PersonAttribute pa : pas) {
		            PersonAttributeType attributeType = pa.getAttributeType(); 
		            PersonAttributeType personAttributePCT=hcs.getPersonAttributeTypeByName("Paying Category Type");
		            PersonAttributeType personAttributeNPCT=hcs.getPersonAttributeTypeByName("Non-Paying Category Type");
		            PersonAttributeType personAttributeSSCT=hcs.getPersonAttributeTypeByName("Special Scheme Category Type");
		            if(attributeType.getPersonAttributeTypeId()==personAttributePCT.getPersonAttributeTypeId()){
		            	patientSubCatergory =  pa.getValue();
		            }
		            else if(attributeType.getPersonAttributeTypeId()==personAttributeNPCT.getPersonAttributeTypeId()){
		            	patientSubCatergory =  pa.getValue(); 
		            }
		            else if(attributeType.getPersonAttributeTypeId()==personAttributeSSCT.getPersonAttributeTypeId()){
		            	patientSubCatergory =  pa.getValue();
		            }
		        }
				
		        
				BillingService billingService = Context.getService(BillingService.class);
				IndoorPatientServiceBill bill = new IndoorPatientServiceBill();
				
				if(patientSubCatergory.equals("GENERAL")  || patientSubCatergory.equals("EXPECTANT MOTHER") 
						|| patientSubCatergory.equals("TB PATIENT") || patientSubCatergory.equals("CCC PATIENT"))
				{
					bill.setActualAmount(moneyUnitPrice);
					bill.setAmount(moneyUnitPrice);
				}
				else
				{
					bill.setActualAmount(new BigDecimal(0));
					bill.setAmount(new BigDecimal(0));
				}
				
				bill.setEncounter(lastVisitEncounter);
				bill.setCreatedDate(new Date());
				bill.setPatient(issueDrugPatient.getPatient());
				bill.setCreator(Context.getAuthenticatedUser());

				
				IndoorPatientServiceBillItem item = new IndoorPatientServiceBillItem();
				if(patientSubCatergory.equals("GENERAL")  || patientSubCatergory.equals("EXPECTANT MOTHER") 
					|| patientSubCatergory.equals("TB PATIENT") || patientSubCatergory.equals("CCC PATIENT"))
				{
					item.setUnitPrice(pDetail.getTransactionDetail().getCostToPatient());
					item.setAmount(moneyUnitPrice);
				
				}
				else
				{
					item.setUnitPrice(new BigDecimal(0));
					item.setAmount(new BigDecimal(0));
				}
				
				item.setQuantity(pDetail.getQuantity());
				item.setName(pDetail.getTransactionDetail().getDrug().getName());
				item.setCreatedDate(new Date());
				//item.setOrder();
				item.setIndoorPatientServiceBill(bill);
				item.setActualAmount(moneyUnitPrice);
				item.setOrderType("DRUG");
				bill.addBillItem(item);
				bill = billingService.saveIndoorPatientServiceBill(bill);
				
			}

			
			
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash()
					.remove("issueDrug_" + userId);
		}

		return "redirect:/module/ehrinventory/subStoreIssueDrugList.form";
	}
	
	
	@RequestMapping("/module/ehrinventory/processIssueItemPatientForIpdPatient.form")
	public String processIssueItemPatientForIpdPatient(
			@RequestParam(value = "action", required = false) Integer action,
			@RequestParam(value = "patientType", required = false) String patientType,
			Model model) {
		
		InventoryService inventoryService = (InventoryService) Context
				.getService(InventoryService.class);
		HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "issueItemDetailPatient_" + userId;
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
		if (action == 1) {
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash()
					.remove("issueItemPatient_" + userId);
			return "redirect:/module/ehrinventory/subStoreIssueItemPatientForm.form";
		}
		List<InventoryStoreItemPatientDetail> list = (List<InventoryStoreItemPatientDetail>) StoreSingleton
				.getInstance().getHash().get(fowardParam);
		InventoryStoreItemPatient issueItemPatient = (InventoryStoreItemPatient) StoreSingleton
				.getInstance().getHash().get("issueItemPatient_" + userId);
		if (issueItemPatient != null && list != null && list.size() > 0) {

			List<EncounterType> types = new ArrayList<EncounterType>();
			EncounterType eType = new EncounterType(10);
			types.add(eType);
			Encounter lastVisitEncounter = hcs.getLastVisitEncounter(issueItemPatient.getPatient(), types);
			Date date = new Date();
			// create transaction issue from substore
			InventoryStoreItemTransaction transaction = new InventoryStoreItemTransaction();
			transaction.setDescription("ISSUE ITEM " + DateUtils.getDDMMYYYY());
			transaction.setStore(store);
			transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
			transaction.setCreatedOn(date);
			transaction.setPaymentCategory(issueItemPatient.getPatient().getAttribute(14).getValue());
			transaction.setCreatedBy(Context.getAuthenticatedUser()
					.getGivenName());
			transaction = inventoryService
					.saveStoreItemTransaction(transaction);

			issueItemPatient = inventoryService
					.saveStoreItemPatient(issueItemPatient);
			for (InventoryStoreItemPatientDetail pDetail : list) {
				Date date1 = new Date();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Integer specificationId = pDetail.getTransactionDetail()
						.getSpecification() != null ? pDetail
						.getTransactionDetail().getSpecification().getId()
						: null;
				Integer totalQuantity = inventoryService
						.sumStoreItemCurrentQuantity(store.getId(), pDetail
								.getTransactionDetail().getItem().getId(),
								specificationId);
				int t = totalQuantity - pDetail.getQuantity();
				InventoryStoreItemTransactionDetail itemTransactionDetail = inventoryService
						.getStoreItemTransactionDetailById(pDetail
								.getTransactionDetail().getId());
				pDetail.getTransactionDetail().setCurrentQuantity(
						itemTransactionDetail.getCurrentQuantity()
								- pDetail.getQuantity());
					inventoryService.saveStoreItemTransactionDetail(pDetail
						.getTransactionDetail());

				// save transactiondetail first
				InventoryStoreItemTransactionDetail transDetail = new InventoryStoreItemTransactionDetail();
				transDetail.setTransaction(transaction);
				transDetail.setCurrentQuantity(0);
				transDetail.setIssueQuantity(pDetail.getQuantity());
				transDetail.setOpeningBalance(totalQuantity);
				transDetail.setClosingBalance(t);
				transDetail.setQuantity(0);
				transDetail.setVAT(pDetail.getTransactionDetail().getVAT());
				transDetail.setCostToPatient(pDetail.getTransactionDetail().getCostToPatient());
				transDetail.setUnitPrice(pDetail.getTransactionDetail()
						.getUnitPrice());
				transDetail.setItem(pDetail.getTransactionDetail().getItem());
				transDetail.setSpecification(pDetail.getTransactionDetail()
						.getSpecification());
				transDetail.setCompanyName(pDetail.getTransactionDetail()
						.getCompanyName());
				transDetail.setDateManufacture(pDetail.getTransactionDetail()
						.getDateManufacture());
				transDetail.setReceiptDate(pDetail.getTransactionDetail()
						.getReceiptDate());
				transDetail.setCreatedOn(date1);

				BigDecimal moneyUnitPrice = pDetail.getTransactionDetail()
						.getCostToPatient()
						.multiply(new BigDecimal(pDetail.getQuantity()));
				/*moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice
						.multiply(pDetail.getTransactionDetail().getVAT()
								.divide(new BigDecimal(100))));*/
				transDetail.setTotalPrice(moneyUnitPrice);

				transDetail.setParent(pDetail.getTransactionDetail());
				transDetail.setAttribute(pDetail.getTransactionDetail().getAttribute());
				transDetail.setPatientType(patientType);
				transDetail = inventoryService
						.saveStoreItemTransactionDetail(transDetail);

				pDetail.setStoreItemPatient(issueItemPatient);
				pDetail.setTransactionDetail(transDetail);
				// save issue to patient detail
				inventoryService.saveStoreItemPatientDetail(pDetail);
				// save issues transaction detail
				
				List<PersonAttribute> pas = hcs.getPersonAttributes(issueItemPatient.getPatient().getId());
				String patientSubCatergory = null;
        for (PersonAttribute pa : pas) {
            PersonAttributeType attributeType = pa.getAttributeType(); 
            PersonAttributeType personAttributePCT=hcs.getPersonAttributeTypeByName("Paying Category Type");
            PersonAttributeType personAttributeNPCT=hcs.getPersonAttributeTypeByName("Non-Paying Category Type");
            PersonAttributeType personAttributeSSCT=hcs.getPersonAttributeTypeByName("Special Scheme Category Type");
            if(attributeType.getPersonAttributeTypeId()==personAttributePCT.getPersonAttributeTypeId()){
            	patientSubCatergory =  pa.getValue();
            }
            else if(attributeType.getPersonAttributeTypeId()==personAttributeNPCT.getPersonAttributeTypeId()){
            	patientSubCatergory =  pa.getValue(); 
            }
            else if(attributeType.getPersonAttributeTypeId()==personAttributeSSCT.getPersonAttributeTypeId()){
            	patientSubCatergory =  pa.getValue();
            }
        }
		
        		BillingService billingService = Context.getService(BillingService.class);
				IndoorPatientServiceBill bill = new IndoorPatientServiceBill();
				

				if(patientSubCatergory.equals("GENERAL")  || patientSubCatergory.equals("EXPECTANT MOTHER") 
						|| patientSubCatergory.equals("TB PATIENT") || patientSubCatergory.equals("CCC PATIENT"))
				{
					bill.setActualAmount(moneyUnitPrice);
					bill.setAmount(moneyUnitPrice);
				}
				else
				{
					bill.setActualAmount(new BigDecimal(0));
					bill.setAmount(new BigDecimal(0));
				}
	
				bill.setEncounter(lastVisitEncounter);
				bill.setCreatedDate(new Date());
				bill.setPatient(issueItemPatient.getPatient());
				bill.setCreator(Context.getAuthenticatedUser());

				
				IndoorPatientServiceBillItem item = new IndoorPatientServiceBillItem();
				if(patientSubCatergory.equals("GENERAL")  || patientSubCatergory.equals("EXPECTANT MOTHER") 
						|| patientSubCatergory.equals("TB PATIENT") || patientSubCatergory.equals("CCC PATIENT"))
				{
					item.setUnitPrice(pDetail.getTransactionDetail().getCostToPatient());
					item.setAmount(moneyUnitPrice);
				}
				else
				{
					item.setUnitPrice(new BigDecimal(0));
					item.setAmount(new BigDecimal(0));
				}

				item.setQuantity(pDetail.getQuantity());
				item.setName(pDetail.getTransactionDetail().getItem().getName());
				item.setCreatedDate(new Date());
				//item.setOrder();
				item.setIndoorPatientServiceBill(bill);
				item.setActualAmount(moneyUnitPrice);
				item.setOrderType("ITEM");
				bill.addBillItem(item);
				bill = billingService.saveIndoorPatientServiceBill(bill);
			
			}

			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash()
					.remove("issueItemPatient_" + userId);
		}

		return "redirect:/module/ehrinventory/subStoreIssueItemPatientList.form";
	}
}
