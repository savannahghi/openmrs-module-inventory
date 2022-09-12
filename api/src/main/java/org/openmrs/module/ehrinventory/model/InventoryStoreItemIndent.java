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
package org.openmrs.module.ehrinventory.model;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.util.ActionValue;

public class InventoryStoreItemIndent implements  Serializable {
	 private static final long serialVersionUID = 1L;
	 private Integer id;
	 private InventoryStore store;
	 private String name;
	 private Date createdOn;
	 private Integer subStoreStatus;
	 private Integer mainStoreStatus;
	 private InventoryStore mainStore;
	 private InventoryStoreItemTransaction transaction;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public InventoryStore getStore() {
		return store;
	}
	public void setStore(InventoryStore store) {
		this.store = store;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Integer getSubStoreStatus() {
		return subStoreStatus;
	}
	public void setSubStoreStatus(Integer subStoreStatus) {
		this.subStoreStatus = subStoreStatus;
	}
	public Integer getMainStoreStatus() {
		return mainStoreStatus;
	}
	public String getMainStoreStatusName() {
		return ActionValue.getIndentMainbStoreName(mainStoreStatus);
	}
	public String getSubStoreStatusName() {
		return ActionValue.getIndentSubStoreName(subStoreStatus);
	}
	public void setMainStoreStatus(Integer mainStoreStatus) {
		this.mainStoreStatus = mainStoreStatus;
	}
    public InventoryStore getMainStore() {
    	return mainStore;
    }
	
    public void setMainStore(InventoryStore mainStore) {
    	this.mainStore = mainStore;
    }
	public InventoryStoreItemTransaction getTransaction() {
		return transaction;
	}
	public void setTransaction(InventoryStoreItemTransaction transaction) {
		this.transaction = transaction;
	}
	 
}
