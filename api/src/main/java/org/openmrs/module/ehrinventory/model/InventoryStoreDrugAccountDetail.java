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

import org.openmrs.module.hospitalcore.model.InventoryStoreDrugTransactionDetail;

public class InventoryStoreDrugAccountDetail implements  Serializable {
		 private static final long serialVersionUID = 1L;
		 private Integer id;
		 private InventoryStoreDrugAccount drugAccount;
		 private Integer quantity;
		 private InventoryStoreDrugTransactionDetail transactionDetail;
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
		
		public InventoryStoreDrugAccount getDrugAccount() {
			return drugAccount;
		}
		public void setDrugAccount(InventoryStoreDrugAccount drugAccount) {
			this.drugAccount = drugAccount;
		}
		public InventoryStoreDrugTransactionDetail getTransactionDetail() {
			return transactionDetail;
		}
		public void setTransactionDetail(
				InventoryStoreDrugTransactionDetail transactionDetail) {
			this.transactionDetail = transactionDetail;
		}
		
		
		 
		 
}
