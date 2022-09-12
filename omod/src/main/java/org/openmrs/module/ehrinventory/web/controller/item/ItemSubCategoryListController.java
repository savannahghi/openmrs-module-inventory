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
package org.openmrs.module.ehrinventory.web.controller.item;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.model.InventoryItemSubCategory;
import org.openmrs.module.ehrinventory.util.PagingUtil;
import org.openmrs.module.ehrinventory.util.RequestUtil;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller("ItemSubCategoryListController")
@RequestMapping("/module/ehrinventory/itemSubCategoryList.form")
public class ItemSubCategoryListController {
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
						InventoryItemSubCategory itemSubCategory = inventoryService.getItemSubCategoryById( NumberUtils.toInt(sId));
						//ghanshyam 7-august-2013 code review bug
						if( itemSubCategory!= null ){
						int countItem = inventoryService.countItem(null,  null, itemSubCategory.getId(), null);
						if( countItem == 0 )
						{
							inventoryService.deleteItemSubCategory(itemSubCategory);
						}else{
							//temp += "We can't delete subCategory="+itemSubCategory.getName()+" because that subCategory is using please check <br/>";
							temp = "This sub-category/sub-categories cannot be deleted as it is in use";
						}
					  }	
					}
				}
			}catch (Exception e) {
				httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
				"Can not delete itemSubCategory ");
				log.error(e);
			}
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR,StringUtils.isBlank(temp) ?  "itemSubCategory.deleted" : temp
			);
	    	
	    	return "redirect:/module/ehrinventory/itemSubCategoryList.form";
	    }
		
		@RequestMapping(method=RequestMethod.GET)
		public String list( @RequestParam(value="searchName",required=false)  String searchName, 
								 @RequestParam(value="pageSize",required=false)  Integer pageSize, 
		                         @RequestParam(value="currentPage",required=false)  Integer currentPage,
		                         Map<String, Object> model, HttpServletRequest request){
			
			InventoryService inventoryService = Context.getService(InventoryService.class);
			
			int total = inventoryService.countListItemSubCategory(searchName);
			String temp = "";
			if(!StringUtils.isBlank(searchName)){	
					temp = "?searchName="+searchName;
			}
			PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request)+temp , pageSize, currentPage, total );
			
			List<InventoryItemSubCategory> itemSubCategorys = inventoryService.listItemSubCategory(searchName, pagingUtil.getStartPos(), pagingUtil.getPageSize());
			
			model.put("itemSubCategorys", itemSubCategorys );
			model.put("searchName", searchName);
			model.put("pagingUtil", pagingUtil);
			
			return "/module/ehrinventory/item/itemSubCategoryList";
		}
}
