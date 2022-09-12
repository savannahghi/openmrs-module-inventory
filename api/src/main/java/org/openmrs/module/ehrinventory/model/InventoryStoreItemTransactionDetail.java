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
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;

/**
 * <p> Class: InventoryStoreDrugTransaction </p>
 * <p> Package: org.openmrs.module.inventory.model </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Jan 5, 2011 1:28:02 PM </p>
 * <p> Update date: Jan 5, 2011 1:28:02 PM </p>
 **/
public class InventoryStoreItemTransactionDetail implements  Serializable , Comparable<InventoryStoreItemTransactionDetail>, Comparator<InventoryStoreItemTransactionDetail> {

	 private static final long serialVersionUID = 1L;
	 private Integer id;
	 private InventoryStoreItemTransaction transaction;
	 private InventoryItem item;
	 private InventoryItemSpecification specification;
	 private Integer quantity ;
	 private Integer currentQuantity ;
	 private Integer issueQuantity;
	 private BigDecimal unitPrice;
	 private BigDecimal totalPrice;
	 private BigDecimal VAT;
	 private BigDecimal costToPatient;

	 private String companyName ;
	 private Date dateManufacture;

	 private Date createdOn;
	 private long openingBalance;
	 private long closingBalance;
	 private Integer flag;
	 private InventoryStoreItemTransactionDetail parent;
	 private Set<InventoryStoreItemTransactionDetail> subDetails;
	 
	 private Date receiptDate;
	 //new
	 private String attribute;
	 
	 private String patientType;
	 
	 
    public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public InventoryStoreItemTransactionDetail() {
	 
    }
	 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public BigDecimal getVAT() {
		return VAT;
	}
	public void setVAT(BigDecimal vAT) {
		VAT = vAT;
	}
	public BigDecimal getCostToPatient() {
		return costToPatient;
	}

	public void setCostToPatient(BigDecimal costToPatient) {
		this.costToPatient = costToPatient;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Date getDateManufacture() {
		return dateManufacture;
	}
	public void setDateManufacture(Date dateManufacture) {
		this.dateManufacture = dateManufacture;
	}
	
	public Date getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getCurrentQuantity() {
		return currentQuantity;
	}
	public void setCurrentQuantity(Integer currentQuantity) {
		this.currentQuantity = currentQuantity;
	}
	
	
	public InventoryStoreItemTransactionDetail getParent() {
		return parent;
	}
	public void setParent(InventoryStoreItemTransactionDetail parent) {
		this.parent = parent;
	}
	public Set<InventoryStoreItemTransactionDetail> getSubDetails() {
		return subDetails;
	}
	public void setSubDetails(Set<InventoryStoreItemTransactionDetail> subDetails) {
		this.subDetails = subDetails;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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
	public Integer getIssueQuantity() {
		return issueQuantity;
	}
	public void setIssueQuantity(Integer issueQuantity) {
		this.issueQuantity = issueQuantity;
	}
	public InventoryStoreItemTransaction getTransaction() {
		return transaction;
	}
	public void setTransaction(InventoryStoreItemTransaction transaction) {
		this.transaction = transaction;
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
	
	//03/07/2012: Kesavulu:sort Item Names  #300
	 @Override
	public int compare(InventoryStoreItemTransactionDetail i1, InventoryStoreItemTransactionDetail i2) {
	 
	    return 0;
	}
	 @Override
	public int compareTo(InventoryStoreItemTransactionDetail i) {
	 
	    return (this.item).compareTo(i.item);
	}

	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	
	 
	 
	 
	 
}