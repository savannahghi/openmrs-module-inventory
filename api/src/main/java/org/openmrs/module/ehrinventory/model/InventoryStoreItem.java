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

public class InventoryStoreItem  implements  Serializable {

	 private static final long serialVersionUID = 1L;
	 private Integer id;
	 private InventoryStore store;
	 private InventoryItem item;
	 private InventoryItemSpecification specification;
	 private long currentQuantity;
	 private long receiptQuantity;
	 private long issueQuantity;
	 private Integer statusIndent;
	 private Integer reorderQty;
	 private Integer status;
	 private Date createdOn;
	 private long openingBalance;
	 private long closingBalance;
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
	public long getCurrentQuantity() {
		return currentQuantity;
	}
	public void setCurrentQuantity(long currentQuantity) {
		this.currentQuantity = currentQuantity;
	}
	public long getReceiptQuantity() {
		return receiptQuantity;
	}
	public void setReceiptQuantity(long receiptQuantity) {
		this.receiptQuantity = receiptQuantity;
	}
	public long getIssueQuantity() {
		return issueQuantity;
	}
	public void setIssueQuantity(long issueQuantity) {
		this.issueQuantity = issueQuantity;
	}
	public Integer getStatusIndent() {
		return statusIndent;
	}
	public String getStatusIndentName(){
		if(closingBalance < reorderQty){
			return "Reorder";
		}
		return " ";
	}
	public void setStatusIndent(Integer statusIndent) {
		this.statusIndent = statusIndent;
	}
	public Integer getReorderQty() {
		return reorderQty;
	}
	public void setReorderQty(Integer reorderQty) {
		this.reorderQty = reorderQty;
	}
	public long getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(long openingBalance) {
		this.openingBalance = openingBalance;
	}
	public long getClosingBalance() {
		return closingBalance;
	}
	public void setClosingBalance(long closingBalance) {
		this.closingBalance = closingBalance;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
}
