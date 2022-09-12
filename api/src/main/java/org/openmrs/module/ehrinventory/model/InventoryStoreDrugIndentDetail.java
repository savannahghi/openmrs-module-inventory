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

import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.InventoryDrugFormulation;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugIndent;

public class InventoryStoreDrugIndentDetail implements  Serializable {
	 private static final long serialVersionUID = 1L;
	 private Integer id;
	 private InventoryStoreDrugIndent indent;
	 private InventoryDrug drug;
	 private InventoryDrugFormulation formulation;
	 private Integer quantity;
	 private Integer mainStoreTransfer;
	 private Date createdOn;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public InventoryStoreDrugIndent getIndent() {
		return indent;
	}
	public void setIndent(InventoryStoreDrugIndent indent) {
		this.indent = indent;
	}
	public InventoryDrug getDrug() {
		return drug;
	}
	public void setDrug(InventoryDrug drug) {
		this.drug = drug;
	}
	public InventoryDrugFormulation getFormulation() {
		return formulation;
	}
	public void setFormulation(InventoryDrugFormulation formulation) {
		this.formulation = formulation;
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
	
	 
	 
	 
}
