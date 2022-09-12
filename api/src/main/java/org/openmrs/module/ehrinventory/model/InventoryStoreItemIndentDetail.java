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

public class InventoryStoreItemIndentDetail implements  Serializable {
	 private static final long serialVersionUID = 1L;
	 private Integer id;
	 private InventoryStoreItemIndent indent;
	 private InventoryItem item;
	 private InventoryItemSpecification specification;
	 private Integer quantity;
	 private Integer mainStoreTransfer;
	 private Date createdOn;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Integer getMainStoreTransfer() {
		return mainStoreTransfer;
	}
	public void setMainStoreTransfer(Integer mainStoreTransfer) {
		this.mainStoreTransfer = mainStoreTransfer;
	}
	public InventoryStoreItemIndent getIndent() {
		return indent;
	}
	public void setIndent(InventoryStoreItemIndent indent) {
		this.indent = indent;
	}
	public InventoryItem getItem() {
		return item;
	}
	public void setItem(InventoryItem item) {
		this.item = item;
	}
	public InventoryItemSpecification getSpecification() {
		return specification;
	}
	public void setSpecification(InventoryItemSpecification specification) {
		this.specification = specification;
	}
	
	 
	 
	 
}
