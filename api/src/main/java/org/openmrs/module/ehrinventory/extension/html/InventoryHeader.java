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
package org.openmrs.module.ehrinventory.extension.html;

import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.ehrinventory.InventoryConstants;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.web.extension.LinkExt;

import java.util.ArrayList;
import java.util.List;

//import org.openmrs.module.Extension;
//import org.openmrs.module.web.extension.AdministrationSectionExt;

/**
 * This class defines the links that will appear on the administration page
 * 
 * This extension is enabled by defining (uncommenting) it in the 
 * /metadata/config.xml file. 
 */
public class InventoryHeader extends LinkExt {

	/**
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getMediaType()
	 */
	public MEDIA_TYPE getMediaType() {
		return MEDIA_TYPE.html;
	}
	
	/**
	 * @see
	 */
	@Override
	public String getRequiredPrivilege() {
		return InventoryConstants.STORE_VIEW;
	}

	/**
	 * @see org.openmrs.module.web.extension.LinkExt#getLabel()
	 */
	@Override
	public String getLabel() {
		InventoryService inventoryService = (InventoryService) Context.getService(InventoryService.class);
		
		List <Role>role=new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles());
		
		InventoryStoreRoleRelation srl=null;
		for(Role r: role){
			if(inventoryService.getStoreRoleByName(r.toString())!=null){
				srl = inventoryService.getStoreRoleByName(r.toString());	
			}
		}
		InventoryStore s1 =null;
		String nameToDisplay = "";
		if(srl!=null){
			s1 = inventoryService.getStoreById(srl.getStoreid());
		}
		
		
		try {
			if(s1!=null && !s1.getRetired()){
				nameToDisplay = s1.getName();
				return nameToDisplay;
			}else{
				return nameToDisplay;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}

		return nameToDisplay;
	}

	/** 
	 * @see org.openmrs.module.web.extension.LinkExt#getUrl()
	 */
	@Override
	public String getUrl() {
		return "module/ehrinventory/main.form";
	}
}
