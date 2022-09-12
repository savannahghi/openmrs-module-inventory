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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.InventoryDrugCategory;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.util.PagingUtil;
import org.openmrs.module.ehrinventory.util.RequestUtil;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller("DrugListController")
@RequestMapping("/module/ehrinventory/drugList.form")
public class DrugListController {
	Log log = LogFactory.getLog(this.getClass());
	@RequestMapping(method=RequestMethod.POST)
    public String delete(@RequestParam("ids") String[] ids,HttpServletRequest request){
		String temp = "";
    	HttpSession httpSession = request.getSession();
		try{
			InventoryService inventoryService = (InventoryService)Context.getService(InventoryService.class);
			if( ids != null && ids.length > 0 ){
				for(String sId : ids )
				{
					InventoryDrug drug = inventoryService.getDrugById( NumberUtils.toInt(sId));
					//ghanshyam 7-august-2013 code review bug
					if( drug!= null ){
					int  countDrugInTransactionDetail = inventoryService.checkExistDrugTransactionDetail(drug.getId());
					int  countDrugInIndentDetail = inventoryService.checkExistDrugIndentDetail(drug.getId());
					if(countDrugInTransactionDetail == 0 && countDrugInIndentDetail == 0)
					{
						inventoryService.deleteDrug(drug);
					}else{
						//temp += "We can't delete drug="+drug.getName()+" because that drug is using please check <br/>";
						temp = "This drug/drugs cannot be deleted as it is in use";
					}
				  }	
				}
			}
		}catch (Exception e) {
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
			"Can not delete drug ");
			log.error(e);
		}
		httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR,StringUtils.isBlank(temp) ?  "drug.deleted" : temp);
    	
    	return "redirect:/module/ehrinventory/drugList.form";
    }
	
	@RequestMapping(method=RequestMethod.GET)
	public String list( @RequestParam(value="searchName",required=false)  String searchName, 
							 @RequestParam(value="categoryId",required=false)  Integer categoryId, 
							 @RequestParam(value="pageSize",required=false)  Integer pageSize, 
	                         @RequestParam(value="currentPage",required=false)  Integer currentPage,
	                         Map<String, Object> model, HttpServletRequest request){
		
		InventoryService inventoryService = Context.getService(InventoryService.class);
		
		int total = inventoryService.countListDrug(categoryId ,searchName);
		String temp = "";
		if(!StringUtils.isBlank(searchName)){	
				temp = "?searchName="+searchName;
		}
		if(categoryId != null){	
			if(StringUtils.isBlank(temp)){
				temp = "?categoryId="+categoryId;
			}else{
				temp +="&categoryId="+categoryId;
			}
	}
		PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request)+temp , pageSize, currentPage, total );
		
		List<InventoryDrug> drugs = inventoryService.listDrug(categoryId , searchName, pagingUtil.getStartPos(), pagingUtil.getPageSize());
		List<InventoryDrugCategory> categories = inventoryService.listDrugCategory("", 0, 0);
		model.put("drugs", drugs );
		model.put("categories", categories );
		model.put("categoryId", categoryId );
		model.put("searchName", searchName);
		model.put("pagingUtil", pagingUtil);
		
		return "/module/ehrinventory/drug/drugList";
	}
}
