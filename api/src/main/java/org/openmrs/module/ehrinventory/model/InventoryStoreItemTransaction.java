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
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.util.ActionValue;

/**
 * <p> Class: InventoryStoreItemTransaction </p>
 * <p> Package: org.openmrs.module.inventory.model </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Jan 5, 2011 1:28:02 PM </p>
 * <p> Update date: Jan 5, 2011 1:28:02 PM </p>
 **/
public class InventoryStoreItemTransaction implements  Serializable {
	 public static final int STATUS_DONE = 1;
	 public static final int STATUS_NOT_YET = 0;
	 private static final long serialVersionUID = 1L;
	 private Integer id;
	 private InventoryStore store;
	 private int status;// =1 done =0 not yet
	 private String description;
	 private int typeTransaction;
	 private Date createdOn;
	 private String createdBy;
	 private String paymentMode;
	 private String paymentCategory;
	 
	
	public String getPaymentCategory() {
		return paymentCategory;
	}
	public void setPaymentCategory(String paymentCategory) {
		this.paymentCategory = paymentCategory;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	private Set<InventoryStoreItemIndent> indents;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return StringUtils.isBlank(description)?"Unknown" : description ;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTypeTransaction() {
		return typeTransaction;
	}
	public String getTypeTransactionName() {
		return typeTransaction == 1? ActionValue.TRANSACTION_NAMES.get(0) : ActionValue.TRANSACTION_NAMES.get(1);
	}
	public void setTypeTransaction(int typeTransaction) {
		this.typeTransaction = typeTransaction;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Set<InventoryStoreItemIndent> getIndents() {
		return indents;
	}
	public void setIndents(Set<InventoryStoreItemIndent> indents) {
		this.indents = indents;
	}
	 
}