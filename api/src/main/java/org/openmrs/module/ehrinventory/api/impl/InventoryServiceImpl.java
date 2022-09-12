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
package org.openmrs.module.ehrinventory.api.impl;

import org.openmrs.Encounter;
import org.openmrs.Role;
import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.InventoryDrugCategory;
import org.openmrs.module.hospitalcore.model.InventoryDrugFormulation;
import org.openmrs.module.hospitalcore.model.InventoryDrugUnit;
import org.openmrs.module.hospitalcore.model.InventoryStore;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugIndent;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugPatient;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugPatientDetail;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugTransaction;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugTransactionDetail;
import org.openmrs.module.hospitalcore.model.InventoryStoreRoleRelation;
import org.openmrs.module.hospitalcore.model.OpdDrugOrder;
import org.openmrs.module.hospitalcore.model.PatientSearch;
import org.openmrs.module.ehrinventory.InventoryService;
import org.openmrs.module.ehrinventory.api.db.InventoryDAO;
import org.openmrs.module.ehrinventory.model.InventoryItem;
import org.openmrs.module.ehrinventory.model.InventoryItemCategory;
import org.openmrs.module.ehrinventory.model.InventoryItemSpecification;
import org.openmrs.module.ehrinventory.model.InventoryItemSubCategory;
import org.openmrs.module.ehrinventory.model.InventoryItemUnit;
import org.openmrs.module.ehrinventory.model.InventoryStoreDrug;
import org.openmrs.module.ehrinventory.model.InventoryStoreDrugAccount;
import org.openmrs.module.ehrinventory.model.InventoryStoreDrugAccountDetail;
import org.openmrs.module.ehrinventory.model.InventoryStoreDrugIndentDetail;
import org.openmrs.module.ehrinventory.model.InventoryStoreItem;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemAccount;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemAccountDetail;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemIndent;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemIndentDetail;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemPatient;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemPatientDetail;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemTransaction;
import org.openmrs.module.ehrinventory.model.InventoryStoreItemTransactionDetail;
import org.openmrs.module.ehrinventory.model.ToxoidModel;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class InventoryServiceImpl extends BaseOpenmrsService implements InventoryService {
	
	public InventoryServiceImpl() {
	}
	
	protected InventoryDAO inventoryDAO;
	
	public void setInventoryDAO(InventoryDAO inventoryDAO) {
		this.inventoryDAO = inventoryDAO;
	}
	
	/**
	 * STORE
	 */
	public List<InventoryStore> listInventoryStore(int min, int max) throws APIException {
		return inventoryDAO.listInventoryStore(min, max);
	}
	
	public InventoryStore saveStore(InventoryStore store) throws APIException {
		return inventoryDAO.saveStore(store);
	}
	
	public int countListStore() throws APIException {
		return inventoryDAO.countListStore();
	}
	
	public InventoryStore getStoreById(Integer id) throws APIException {
		return inventoryDAO.getStoreById(id);
	}
	
	public InventoryStore getStoreByRole(String role) throws APIException {
		return inventoryDAO.getStoreByRole(role);
	}
	
	public InventoryStore getStoreByCollectionRole(List<Role> roles) throws APIException {
		return inventoryDAO.getStoreByCollectionRole(roles);
	}
	
	public List<InventoryStore> listMainStore() throws APIException {
		return inventoryDAO.listMainStore();
	}
	
	public void deleteStore(InventoryStore store) throws APIException {
		inventoryDAO.deleteStore(store);
	}
	
	public List<InventoryStore> listAllInventoryStore() throws APIException {
		return inventoryDAO.listAllInventoryStore();
	}
	
	public InventoryStore getStoreByName(String name) throws APIException {
		return inventoryDAO.getStoreByName(name);
	}
	
	public List<InventoryStore> listStoreByMainStore(Integer mainStoreid, boolean bothMainStore) throws APIException {
		return inventoryDAO.listStoreByMainStore(mainStoreid, bothMainStore);
	}
	
	/**
	 * ItemCategory
	 */
	
	public List<InventoryItemCategory> listItemCategory(String name, int min, int max) throws APIException {
		return inventoryDAO.listItemCategory(name, min, max);
	}
	
	public List<InventoryItemCategory> findItemCategory(String name) throws APIException {
		return inventoryDAO.findItemCategory(name);
	}
	
	public InventoryItemCategory saveItemCategory(InventoryItemCategory category) throws APIException {
		return inventoryDAO.saveItemCategory(category);
	}
	
	public int countListItemCategory(String name) throws APIException {
		return inventoryDAO.countListItemCategory(name);
	}
	
	public InventoryItemCategory getItemCategoryById(Integer id) throws APIException {
		return inventoryDAO.getItemCategoryById(id);
	}
	
	public InventoryItemCategory getItemCategoryByName(String name) throws APIException {
		return inventoryDAO.getItemCategoryByName(name);
	}
	
	public void deleteItemCategory(InventoryItemCategory category) throws APIException {
		inventoryDAO.deleteItemCategory(category);
	}
	
	/**
	 * ItemSubCategory
	 */
	
	public List<InventoryItemSubCategory> listItemSubCategory(String name, int min, int max) throws APIException {
		return inventoryDAO.listItemSubCategory(name, min, max);
	}
	
	public List<InventoryItemSubCategory> findItemSubCategory(String name) throws APIException {
		return inventoryDAO.findItemSubCategory(name);
	}
	
	public InventoryItemSubCategory saveItemSubCategory(InventoryItemSubCategory subCategory) throws APIException {
		return inventoryDAO.saveItemSubCategory(subCategory);
	}
	
	public int countListItemSubCategory(String name) throws APIException {
		return inventoryDAO.countListItemSubCategory(name);
	}
	
	public InventoryItemSubCategory getItemSubCategoryById(Integer id) throws APIException {
		return inventoryDAO.getItemSubCategoryById(id);
	}
	
	public InventoryItemSubCategory getItemSubCategoryByName(Integer categoryId, String name) throws APIException {
		return inventoryDAO.getItemSubCategoryByName(categoryId, name);
	}
	
	public void deleteItemSubCategory(InventoryItemSubCategory subCategory) throws APIException {
		inventoryDAO.deleteItemSubCategory(subCategory);
	}
	
	public List<InventoryItemSubCategory> listSubCatByCat(Integer categoryId) throws APIException {
		return inventoryDAO.listSubCatByCat(categoryId);
	}
	
	/**
	 * ItemSpecification
	 */
	
	public List<InventoryItemSpecification> listItemSpecification(String name, int min, int max) throws APIException {
		return inventoryDAO.listItemSpecification(name, min, max);
	}
	
	public List<InventoryItemSpecification> findItemSpecification(String name) throws APIException {
		return inventoryDAO.findItemSpecification(name);
	}
	
	public InventoryItemSpecification saveItemSpecification(InventoryItemSpecification specification) throws APIException {
		return inventoryDAO.saveItemSpecification(specification);
	}
	
	public int countListItemSpecification(String name) throws APIException {
		return inventoryDAO.countListItemSpecification(name);
	}
	
	public InventoryItemSpecification getItemSpecificationById(Integer id) throws APIException {
		return inventoryDAO.getItemSpecificationById(id);
	}
	
	public InventoryItemSpecification getItemSpecificationByName(String name) throws APIException {
		return inventoryDAO.getItemSpecificationByName(name);
	}
	
	public void deleteItemSpecification(InventoryItemSpecification specification) throws APIException {
		inventoryDAO.deleteItemSpecification(specification);
	}
	
	/**
	 * ItemUnit
	 */
	
	public List<InventoryItemUnit> listItemUnit(String name, int min, int max) throws APIException {
		return inventoryDAO.listItemUnit(name, min, max);
	}
	
	public List<InventoryItemUnit> findItemUnit(String name) throws APIException {
		return inventoryDAO.findItemUnit(name);
	}
	
	public InventoryItemUnit saveItemUnit(InventoryItemUnit unit) throws APIException {
		return inventoryDAO.saveItemUnit(unit);
	}
	
	public int countListItemUnit(String name) throws APIException {
		return inventoryDAO.countListItemUnit(name);
	}
	
	public InventoryItemUnit getItemUnitById(Integer id) throws APIException {
		return inventoryDAO.getItemUnitById(id);
	}
	
	public InventoryItemUnit getItemUnitByName(String name) throws APIException {
		return inventoryDAO.getItemUnitByName(name);
	}
	
	public void deleteItemUnit(InventoryItemUnit unit) throws APIException {
		inventoryDAO.deleteItemUnit(unit);
	}
	
	/**
	 * Item
	 */
	
	public List<InventoryItem> listItem(Integer categoryId, String name, int min, int max) throws APIException {
		return inventoryDAO.listItem(categoryId, name, min, max);
	}
	
	public List<InventoryItem> findItem(String name) throws APIException {
		return inventoryDAO.findItem(name);
	}
	
	public InventoryItem saveItem(InventoryItem item) throws APIException {
		return inventoryDAO.saveItem(item);
	}
	
	public int countListItem(Integer categoryId, String name) throws APIException {
		return inventoryDAO.countListItem(categoryId, name);
	}
	
	public InventoryItem getItemById(Integer id) throws APIException {
		return inventoryDAO.getItemById(id);
	}
	
	public InventoryItem getItemByName(String name) throws APIException {
		return inventoryDAO.getItemByName(name);
	}
	
	public void deleteItem(InventoryItem item) throws APIException {
		inventoryDAO.deleteItem(item);
	}
	
	public List<InventoryItem> findItem(Integer categoryId, String name) throws APIException {
		return inventoryDAO.findItem(categoryId, name);
	}
	
	public int countItem(Integer categoryId, Integer unitId, Integer subCategoryId, Integer specificationId)
	                                                                                                        throws DAOException {
		return inventoryDAO.countItem(categoryId, unitId, subCategoryId, specificationId);
	}
	
	/**
	 * Drug
	 */
	
	public List<InventoryDrug> listDrug(Integer categoryId, String name, int min, int max) throws APIException {
		return inventoryDAO.listDrug(categoryId, name, min, max);
	}
	
	public List<InventoryDrug> findDrug(Integer categoryId, String name) throws APIException {
		return inventoryDAO.findDrug(categoryId, name);
	}
	
	public InventoryDrug saveDrug(InventoryDrug drug) throws APIException {
		return inventoryDAO.saveDrug(drug);
	}
	
	public int countListDrug(Integer categoryId, String name) throws APIException {
		return inventoryDAO.countListDrug(categoryId, name);
	}
	
	public InventoryDrug getDrugById(Integer id) throws APIException {
		return inventoryDAO.getDrugById(id);
	}
	
	public InventoryDrug getDrugByName(String name) throws APIException {
		return inventoryDAO.getDrugByName(name);
	}
	
	public void deleteDrug(InventoryDrug drug) throws APIException {
		inventoryDAO.deleteDrug(drug);
	}
	
	public List<InventoryDrug> getAllDrug() throws APIException {
		return inventoryDAO.getAllDrug();
	}
	
	@Override
	public int countListDrug(Integer categoryId, Integer unitId, Integer formulationId) throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.countListDrug(categoryId, unitId, formulationId);
	}
	
	/**
	 * DrugCategory
	 */
	
	public List<InventoryDrugCategory> listDrugCategory(String name, int min, int max) throws APIException {
		return inventoryDAO.listDrugCategory(name, min, max);
	}
	
	public List<InventoryDrugCategory> findDrugCategory(String name) throws APIException {
		return inventoryDAO.findDrugCategory(name);
	}
	
	public InventoryDrugCategory saveDrugCategory(InventoryDrugCategory drugCategory) throws APIException {
		return inventoryDAO.saveDrugCategory(drugCategory);
	}
	
	public int countListDrugCategory(String name) throws APIException {
		return inventoryDAO.countListDrugCategory(name);
	}
	
	public InventoryDrugCategory getDrugCategoryById(Integer id) throws APIException {
		return inventoryDAO.getDrugCategoryById(id);
	}
	
	public InventoryDrugCategory getDrugCategoryByName(String name) throws APIException {
		return inventoryDAO.getDrugCategoryByName(name);
	}
	
	public void deleteDrugCategory(InventoryDrugCategory drugCategory) throws APIException {
		inventoryDAO.deleteDrugCategory(drugCategory);
	}
	
	/**
	 * DrugFormulation
	 */
	
	public List<InventoryDrugFormulation> listDrugFormulation(String name, int min, int max) throws APIException {
		return inventoryDAO.listDrugFormulation(name, min, max);
	}
	
	public List<InventoryDrugFormulation> findDrugFormulation(String name) throws APIException {
		return inventoryDAO.findDrugFormulation(name);
	}
	
	public InventoryDrugFormulation saveDrugFormulation(InventoryDrugFormulation drugFormulation) throws APIException {
		return inventoryDAO.saveDrugFormulation(drugFormulation);
	}
	
	public int countListDrugFormulation(String name) throws APIException {
		return inventoryDAO.countListDrugFormulation(name);
	}
	
	public InventoryDrugFormulation getDrugFormulationById(Integer id) throws APIException {
		return inventoryDAO.getDrugFormulationById(id);
	}
	
	public InventoryDrugFormulation getDrugFormulationByName(String name) throws APIException {
		return inventoryDAO.getDrugFormulationByName(name);
	}
	
	public InventoryDrugFormulation getDrugFormulation(String name, String dozage) throws APIException {
		return inventoryDAO.getDrugFormulation(name, dozage);
	}
	
	public void deleteDrugFormulation(InventoryDrugFormulation drugFormulation) throws APIException {
		inventoryDAO.deleteDrugFormulation(drugFormulation);
	}
	
	/**
	 * DrugUnit
	 */
	
	public List<InventoryDrugUnit> listDrugUnit(String name, int min, int max) throws APIException {
		return inventoryDAO.listDrugUnit(name, min, max);
	}
	
	public List<InventoryDrugUnit> findDrugUnit(String name) throws APIException {
		return inventoryDAO.findDrugUnit(name);
	}
	
	public InventoryDrugUnit saveDrugUnit(InventoryDrugUnit drugUnit) throws APIException {
		return inventoryDAO.saveDrugUnit(drugUnit);
	}
	
	public int countListDrugUnit(String name) throws APIException {
		return inventoryDAO.countListDrugUnit(name);
	}
	
	public InventoryDrugUnit getDrugUnitById(Integer id) throws APIException {
		return inventoryDAO.getDrugUnitById(id);
	}
	
	public InventoryDrugUnit getDrugUnitByName(String name) throws APIException {
		return inventoryDAO.getDrugUnitByName(name);
	}
	
	public void deleteDrugUnit(InventoryDrugUnit drugUnit) throws APIException {
		inventoryDAO.deleteDrugUnit(drugUnit);
	}
	
	/**
	 * StoreDrug
	 */
	
	public List<InventoryStoreDrug> listStoreDrug(Integer storeId, Integer categoryId, String drugName, Integer reOrderQty,
	                                              int min, int max) throws APIException {
		return inventoryDAO.listStoreDrug(storeId, categoryId, drugName, reOrderQty, min, max);
	}
	
	public int countStoreDrug(Integer storeId, Integer categoryId, String drugName, Integer reOrderQty) throws APIException {
		return inventoryDAO.countStoreDrug(storeId, categoryId, drugName, reOrderQty);
	}
	
	public InventoryStoreDrug getStoreDrugById(Integer id) throws APIException {
		return inventoryDAO.getStoreDrugById(id);
	}
	
	public InventoryStoreDrug getStoreDrug(Integer storeId, Integer drugId, Integer formulationId) throws APIException {
		return inventoryDAO.getStoreDrug(storeId, drugId, formulationId);
	}
	
	public InventoryStoreDrug saveStoreDrug(InventoryStoreDrug storeDrug) throws APIException {
		return inventoryDAO.saveStoreDrug(storeDrug);
	}
	
	/**
	 * StoreDrugTransaction
	 */
	
	public List<InventoryStoreDrugTransaction> listStoreDrugTransaction(Integer transactionType, Integer storeId,
	                                                                    String description, String fromDate, String toDate,
	                                                                    int min, int max) throws APIException {
		return inventoryDAO.listStoreDrugTransaction(transactionType, storeId, description, fromDate, toDate, min, max);
	}
	
	public InventoryStoreDrugTransaction saveStoreDrugTransaction(InventoryStoreDrugTransaction storeTransaction)
	                                                                                                             throws APIException {
		return inventoryDAO.saveStoreDrugTransaction(storeTransaction);
	}
	
	public int countStoreDrugTransaction(Integer transactionType, Integer storeId, String description, String fromDate,
	                                     String toDate) throws APIException {
		return inventoryDAO.countStoreDrugTransaction(transactionType, storeId, description, fromDate, toDate);
	}
	
	public InventoryStoreDrugTransaction getStoreDrugTransactionById(Integer id) throws APIException {
		return inventoryDAO.getStoreDrugTransactionById(id);
	}
	
	@Override
	public InventoryStoreDrugTransaction getStoreDrugTransactionByParentId(Integer parentId) throws APIException {
		return inventoryDAO.getStoreDrugTransactionByParentId(parentId);
	}
	
	/**
	 * StoreDrugTransactionDetail
	 */
	
	public List<InventoryStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, Integer categoryId,
	                                                                                String drugName, String formulationName,
	                                                                                String fromDate, String toDate, int min,
	                                                                                int max) throws APIException {
		return inventoryDAO
		        .listStoreDrugTransactionDetail(storeId, categoryId, drugName, formulationName, fromDate, toDate, min, max);
	}
	
	public InventoryStoreDrugTransactionDetail saveStoreDrugTransactionDetail(InventoryStoreDrugTransactionDetail storeTransactionDetail)
	                                                                                                                                     throws APIException {
		return inventoryDAO.saveStoreDrugTransactionDetail(storeTransactionDetail);
	}
	
	public int countStoreDrugTransactionDetail(Integer storeId, Integer categoryId, String drugName, String formulationName,
	                                           String fromDate, String toDate) throws APIException {
		return inventoryDAO.countStoreDrugTransactionDetail(storeId, categoryId, drugName, formulationName, fromDate, toDate);
	}
	
	public InventoryStoreDrugTransactionDetail getStoreDrugTransactionDetailById(Integer id) throws APIException {
		return inventoryDAO.getStoreDrugTransactionDetailById(id);
	}
	
	public List<InventoryStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, Integer drugId,
	                                                                                Integer formulationId,
	                                                                                boolean haveQuantity)
	                                                                                                     throws APIException {
		return inventoryDAO.listStoreDrugTransactionDetail(storeId, drugId, formulationId, haveQuantity);
	}
	
	@Override
	public Integer sumCurrentQuantityDrugOfStore(Integer storeId, Integer drugId, Integer formulationId) throws APIException {
		return inventoryDAO.sumCurrentQuantityDrugOfStore(storeId, drugId, formulationId);
	}
	
	@Override
	public List<InventoryStoreDrugTransactionDetail> listStoreDrugAvaiable(Integer storeId, Collection<Integer> drugs,
	                                                                       Collection<Integer> formulations)
	                                                                                                        throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.listStoreDrugAvaiable(storeId, drugs, formulations);
	}
	
	@Override
	public List<InventoryStoreDrugTransactionDetail> listTransactionDetail(Integer transactionId) throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.listTransactionDetail(transactionId);
	}
	
	@Override
	public Integer countViewStockBalance(Integer storeId, Integer categoryId, String drugName,String attribute, String fromDate,
	                                     String toDate, boolean isExpiry) throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.countViewStockBalance(storeId, categoryId, drugName, attribute, fromDate, toDate, isExpiry);
	}
	
	@Override
	public List<InventoryStoreDrugTransactionDetail> listViewStockBalance(Integer storeId, Integer categoryId,
	                                                                      String drugName,String attribute, String fromDate, String toDate,
	                                                                      boolean isExpiry, int min, int max)
	                                                                                                         throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.listViewStockBalance(storeId, categoryId, drugName, attribute, fromDate, toDate, isExpiry, min, max);
	}
	
	@Override
	public List<InventoryStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, Integer drugId,
	                                                                                Integer formulationId, Integer isExpriry)
	                                                                                                                         throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.listStoreDrugTransactionDetail(storeId, drugId, formulationId, isExpriry);
	}
	
	@Override
	public int checkExistDrugTransactionDetail(Integer drugId) throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.checkExistDrugTransactionDetail(drugId);
	}
	
	/**
	 * InventoryStoreDrugIndent
	 */
	
	public List<InventoryStoreDrugIndent> listSubStoreIndent(Integer storeId, String name, Integer status, String fromDate,
	                                                         String toDate, int min, int max) throws APIException {
		return inventoryDAO.listSubStoreIndent(storeId, name, status, fromDate, toDate, min, max);
	}
	
	@Override
	public List<InventoryStoreDrugIndent> listStoreDrugIndent(Integer StoreId, String name, String fromDate, String toDate,
	                                                          int min, int max) throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.listStoreDrugIndent(StoreId, name, fromDate, toDate, min, max);
	}
	
	@Override
	public int countStoreDrugIndent(Integer StoreId, String name, String fromDate, String toDate) throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.countStoreDrugIndent(StoreId, name, fromDate, toDate);
	}
	
	public int countSubStoreIndent(Integer storeId, String name, Integer status, String fromDate, String toDate)
	                                                                                                            throws APIException {
		return inventoryDAO.countSubStoreIndent(storeId, name, status, fromDate, toDate);
	}
	
	public List<InventoryStoreDrugIndent> listMainStoreIndent(Integer id, Integer mainStoreId, Integer subStoreId,
	                                                          String name, Integer status, String fromDate, String toDate,
	                                                          int min, int max) throws APIException {
		return inventoryDAO.listMainStoreIndent(id, mainStoreId, subStoreId, name, status, fromDate, toDate, min, max);
	}
	
	public int countMainStoreIndent(Integer id, Integer mainStoreId, Integer subStoreId, String name, Integer status,
	                                String fromDate, String toDate) throws APIException {
		return inventoryDAO.countMainStoreIndent(id, mainStoreId, subStoreId, name, status, fromDate, toDate);
	}
	
	public InventoryStoreDrugIndent saveStoreDrugIndent(InventoryStoreDrugIndent storeDrugIndent) throws APIException {
		return inventoryDAO.saveStoreDrugIndent(storeDrugIndent);
	}
	
	public InventoryStoreDrugIndent getStoreDrugIndentById(Integer id) throws APIException {
		return inventoryDAO.getStoreDrugIndentById(id);
	}
	
	/**
	 * InventoryStoreDrugIndentDetail
	 */
	
	public List<InventoryStoreDrugIndentDetail> listStoreDrugIndentDetail(Integer storeId, Integer categoryId,
	                                                                      String indentName, String drugName,
	                                                                      String fromDate, String toDate, int min, int max)
	                                                                                                                       throws APIException {
		return inventoryDAO.listStoreDrugIndentDetail(storeId, categoryId, indentName, drugName, fromDate, toDate, min, max);
	}
	
	public int countStoreDrugIndentDetail(Integer storeId, Integer categoryId, String indentName, String drugName,
	                                      String fromDate, String toDate) throws APIException {
		return inventoryDAO.countStoreDrugIndentDetail(storeId, categoryId, indentName, drugName, fromDate, toDate);
	}
	
	public InventoryStoreDrugIndentDetail saveStoreDrugIndentDetail(InventoryStoreDrugIndentDetail storeDrugIndentDetail)
	                                                                                                                     throws APIException {
		return inventoryDAO.saveStoreDrugIndentDetail(storeDrugIndentDetail);
	}
	
	public InventoryStoreDrugIndentDetail getStoreDrugIndentDetailById(Integer id) throws APIException {
		return inventoryDAO.getStoreDrugIndentDetailById(id);
	}
	
	@Override
	public List<InventoryStoreDrugIndentDetail> listStoreDrugIndentDetail(Integer indentId) throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.listStoreDrugIndentDetail(indentId);
	}
	
	@Override
	public int checkExistDrugIndentDetail(Integer drugId) throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.checkExistDrugIndentDetail(drugId);
	}
	
	/**
	 * InventoryStoreDrugPatient
	 */
	public List<InventoryStoreDrugPatient> listStoreDrugPatient(Integer storeId, Integer receiptId, String name, String fromDate,
	                                                            String toDate, int min, int max) throws APIException {
		return inventoryDAO.listStoreDrugPatient(storeId,receiptId, name, fromDate, toDate, min, max);
	}
	
	public int countStoreDrugPatient(Integer storeId, String name, String fromDate, String toDate) throws APIException {
		return inventoryDAO.countStoreDrugPatient(storeId, name, fromDate, toDate);
	}
	
	public InventoryStoreDrugPatient saveStoreDrugPatient(InventoryStoreDrugPatient bill) throws APIException {
		return inventoryDAO.saveStoreDrugPatient(bill);
	}
	
	public InventoryStoreDrugPatient getStoreDrugPatientById(Integer id) throws APIException {
		return inventoryDAO.getStoreDrugPatientById(id);
	}
	
	/**
	 * InventoryStoreDrugPatientDetail
	 */
	public List<InventoryStoreDrugPatientDetail> listStoreDrugPatientDetail(Integer storeDrugPatientDetailId)
	                                                                                                         throws APIException {
		return inventoryDAO.listStoreDrugPatientDetail(storeDrugPatientDetailId);
	}
	
	public InventoryStoreDrugPatientDetail saveStoreDrugPatientDetail(InventoryStoreDrugPatientDetail storeDrugPatientDetail)
	                                                                                                                         throws APIException {
		return inventoryDAO.saveStoreDrugPatientDetail(storeDrugPatientDetail);
	}
	
	public InventoryStoreDrugPatientDetail getStoreDrugPatientDetailById(Integer id) throws APIException {
		return inventoryDAO.getStoreDrugPatientDetailById(id);
	}
	
	//change code
	/**
	 * StoreItem
	 */
	
	public List<InventoryStoreItem> listStoreItem(Integer storeId, Integer categoryId, String itemName, Integer reorderQty,
	                                              int min, int max) throws APIException {
		return inventoryDAO.listStoreItem(storeId, categoryId, itemName, reorderQty, min, max);
	}
	
	public int countStoreItem(Integer storeId, Integer categoryId, String itemName, Integer reorderQty) throws APIException {
		return inventoryDAO.countStoreItem(storeId, categoryId, itemName, reorderQty);
	}
	
	public InventoryStoreItem getStoreItemById(Integer id) throws APIException {
		return inventoryDAO.getStoreItemById(id);
	}
	
	public InventoryStoreItem getStoreItem(Integer storeId, Integer itemId, Integer specificationId) throws APIException {
		return inventoryDAO.getStoreItem(storeId, itemId, specificationId);
	}
	
	public InventoryStoreItem saveStoreItem(InventoryStoreItem StoreItem) throws APIException {
		return inventoryDAO.saveStoreItem(StoreItem);
	}
	
	/**
	 * StoreItemTransaction
	 */
	
	public List<InventoryStoreItemTransaction> listStoreItemTransaction(Integer transactionType, Integer storeId,
	                                                                    String description, String fromDate, String toDate,
	                                                                    int min, int max) throws APIException {
		return inventoryDAO.listStoreItemTransaction(transactionType, storeId, description, fromDate, toDate, min, max);
	}
	
	public InventoryStoreItemTransaction saveStoreItemTransaction(InventoryStoreItemTransaction storeTransaction)
	                                                                                                             throws APIException {
		return inventoryDAO.saveStoreItemTransaction(storeTransaction);
	}
	
	public int countStoreItemTransaction(Integer transactionType, Integer storeId, String description, String fromDate,
	                                     String toDate) throws APIException {
		return inventoryDAO.countStoreItemTransaction(transactionType, storeId, description, fromDate, toDate);
	}
	
	public InventoryStoreItemTransaction getStoreItemTransactionById(Integer id) throws APIException {
		return inventoryDAO.getStoreItemTransactionById(id);
	}
	
	public InventoryStoreItemTransaction getStoreItemTransactionByParentId(Integer parentId) throws APIException {
		return inventoryDAO.getStoreItemTransactionByParentId(parentId);
	}
	
	/**
	 * StoreItemTransactionDetail
	 */
	
	public List<InventoryStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer storeId, Integer categoryId,
	                                                                                String itemName,
	                                                                                String specificationName,
	                                                                                String fromDate, String toDate, int min,
	                                                                                int max) throws APIException {
		return inventoryDAO.listStoreItemTransactionDetail(storeId, categoryId, itemName, specificationName, fromDate, toDate, min,
		    max);
	}
	
	public InventoryStoreItemTransactionDetail saveStoreItemTransactionDetail(InventoryStoreItemTransactionDetail storeTransactionDetail)
	                                                                                                                                     throws APIException {
		return inventoryDAO.saveStoreItemTransactionDetail(storeTransactionDetail);
	}
	
	public int countStoreItemTransactionDetail(Integer storeId, Integer categoryId, String itemName,
	                                           String specificationName, String fromDate, String toDate) throws APIException {
		return inventoryDAO.countStoreItemTransactionDetail(storeId, categoryId, itemName, specificationName, fromDate, toDate);
	}
	
	public InventoryStoreItemTransactionDetail getStoreItemTransactionDetailById(Integer id) throws APIException {
		return inventoryDAO.getStoreItemTransactionDetailById(id);
	}
	
	public List<InventoryStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer storeId, Integer itemId,
	                                                                                Integer specificationId,
	                                                                                boolean haveQuantity)
	                                                                                                     throws APIException {
		return inventoryDAO.listStoreItemTransactionDetail(storeId, itemId, specificationId, haveQuantity);
	}
	
	public List<InventoryStoreItemTransactionDetail> listStoreItemAvaiable(Integer storeId, Collection<Integer> items,
	                                                                       Collection<Integer> specifications)
	                                                                                                          throws APIException {
		return inventoryDAO.listStoreItemAvaiable(storeId, items, specifications);
	}
	
	public Integer sumStoreItemCurrentQuantity(Integer storeId, Integer itemId, Integer specificationId) throws APIException {
		return inventoryDAO.sumStoreItemCurrentQuantity(storeId, itemId, specificationId);
	}
	
	public List<InventoryStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer transactionId)
	                                                                                                      throws APIException {
		return inventoryDAO.listStoreItemTransactionDetail(transactionId);
	}
	//edited
	public Integer countStoreItemViewStockBalance(Integer storeId, Integer categoryId, String itemName,String attribute, String fromDate,
	                                              String toDate) throws APIException {
		return inventoryDAO.countStoreItemViewStockBalance(storeId, categoryId, itemName,attribute, fromDate, toDate);
	}
	//edited
	public List<InventoryStoreItemTransactionDetail> listStoreItemViewStockBalance(Integer storeId, Integer categoryId,
	                                                                               String itemName,String attribute, String fromDate,
	                                                                               String toDate, int min, int max)
	                                                                                                             throws APIException {
		//edited 
		return inventoryDAO.listStoreItemViewStockBalance(storeId, categoryId, itemName,attribute, fromDate, toDate, min, max);
	}
	
	public List<InventoryStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer storeId, Integer itemId,
	                                                                                Integer specificationId, int min, int max)
	                                                                                                                          throws APIException {
		return inventoryDAO.listStoreItemTransactionDetail(storeId, itemId, specificationId, min, max);
	}
	
	@Override
	public int checkExistItemTransactionDetail(Integer itemId) throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.checkExistItemTransactionDetail(itemId);
	}
	
	/**
	 * InventoryStoreItemIndent
	 */
	
	public List<InventoryStoreItemIndent> listStoreItemIndent(Integer StoreId, String name, String fromDate, String toDate,
	                                                          int min, int max) throws APIException {
		return inventoryDAO.listStoreItemIndent(StoreId, name, fromDate, toDate, min, max);
	}
	
	public int countStoreItemIndent(Integer StoreId, String name, String fromDate, String toDate) throws APIException {
		return inventoryDAO.countStoreItemIndent(StoreId, name, fromDate, toDate);
	}
	
	public List<InventoryStoreItemIndent> listSubStoreItemIndent(Integer storeId, String name, Integer status,
	                                                             String fromDate, String toDate, int min, int max)
	                                                                                                              throws APIException {
		return inventoryDAO.listSubStoreItemIndent(storeId, name, status, fromDate, toDate, min, max);
	}
	
	public int countSubStoreItemIndent(Integer storeId, String name, Integer status, String fromDate, String toDate)
	                                                                                                                throws APIException {
		return inventoryDAO.countSubStoreItemIndent(storeId, name, status, fromDate, toDate);
	}
	
	public List<InventoryStoreItemIndent> listMainStoreItemIndent(Integer id, Integer mainStoreId, Integer subStoreId,
	                                                              String name, Integer status, String fromDate,
	                                                              String toDate, int min, int max) throws APIException {
		return inventoryDAO.listMainStoreItemIndent(id, mainStoreId, subStoreId, name, status, fromDate, toDate, min, max);
	}
	
	public int countMainStoreItemIndent(Integer id, Integer mainStoreId, Integer subStoreId, String name, Integer status,
	                                    String fromDate, String toDate) throws APIException {
		return inventoryDAO.countMainStoreItemIndent(id, mainStoreId, subStoreId, name, status, fromDate, toDate);
	}
	
	public InventoryStoreItemIndent saveStoreItemIndent(InventoryStoreItemIndent storeItemIndent) throws APIException {
		return inventoryDAO.saveStoreItemIndent(storeItemIndent);
	}
	
	public InventoryStoreItemIndent getStoreItemIndentById(Integer id) throws APIException {
		return inventoryDAO.getStoreItemIndentById(id);
	}
	
	/**
	 * InventoryStoreItemIndentDetail
	 */
	
	public List<InventoryStoreItemIndentDetail> listStoreItemIndentDetail(Integer storeId, Integer categoryId,
	                                                                      String indentName, String itemName,
	                                                                      String fromDate, String toDate, int min, int max)
	                                                                                                                       throws APIException {
		return inventoryDAO.listStoreItemIndentDetail(storeId, categoryId, indentName, itemName, fromDate, toDate, min, max);
	}
	
	public int countStoreItemIndentDetail(Integer storeId, Integer categoryId, String indentName, String itemName,
	                                      String fromDate, String toDate) throws APIException {
		return inventoryDAO.countStoreItemIndentDetail(storeId, categoryId, indentName, itemName, fromDate, toDate);
	}
	
	public List<InventoryStoreItemIndentDetail> listStoreItemIndentDetail(Integer indentId) throws APIException {
		return inventoryDAO.listStoreItemIndentDetail(indentId);
	}
	
	public InventoryStoreItemIndentDetail saveStoreItemIndentDetail(InventoryStoreItemIndentDetail storeItemIndentDetail)
	                                                                                                                     throws APIException {
		return inventoryDAO.saveStoreItemIndentDetail(storeItemIndentDetail);
	}
	
	public InventoryStoreItemIndentDetail getStoreItemIndentDetailById(Integer id) throws APIException {
		return inventoryDAO.getStoreItemIndentDetailById(id);
	}
	
	@Override
	public int checkExistItemIndentDetail(Integer itemId) throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.checkExistItemIndentDetail(itemId);
	}
	
	/**
	 * InventoryStoreItemAccount
	 */
	public List<InventoryStoreItemAccount> listStoreItemAccount(Integer storeId, String name, String fromDate,
	                                                            String toDate, int min, int max) throws APIException {
		return inventoryDAO.listStoreItemAccount(storeId, name, fromDate, toDate, min, max);
	}
	
	public int countStoreItemAccount(Integer storeId, String name, String fromDate, String toDate) throws APIException {
		return inventoryDAO.countStoreItemAccount(storeId, name, fromDate, toDate);
	}
	
	public InventoryStoreItemAccount saveStoreItemAccount(InventoryStoreItemAccount issue) throws APIException {
		return inventoryDAO.saveStoreItemAccount(issue);
	}
	
	public InventoryStoreItemAccount getStoreItemAccountById(Integer id) throws APIException {
		return inventoryDAO.getStoreItemAccountById(id);
	}
	
	/**
	 * InventoryStoreItemAccountDetail
	 */
	public List<InventoryStoreItemAccountDetail> listStoreItemAccountDetail(Integer storeItemAccountDetailId)
	                                                                                                         throws APIException {
		return inventoryDAO.listStoreItemAccountDetail(storeItemAccountDetailId);
	}
	
	public InventoryStoreItemAccountDetail saveStoreItemAccountDetail(InventoryStoreItemAccountDetail storeItemAccountDetail)
	                                                                                                                         throws APIException {
		return inventoryDAO.saveStoreItemAccountDetail(storeItemAccountDetail);
	}
	
	public InventoryStoreItemAccountDetail getStoreItemAccountDetailById(Integer id) throws APIException {
		return inventoryDAO.getStoreItemAccountDetailById(id);
	}
	
	/**
	 * InventoryStoreDrugAccount
	 */
	public List<InventoryStoreDrugAccount> listStoreDrugAccount(Integer storeId, String name, String fromDate,
	                                                            String toDate, int min, int max) throws APIException {
		return inventoryDAO.listStoreDrugAccount(storeId, name, fromDate, toDate, min, max);
	}
	
	public int countStoreDrugAccount(Integer storeId, String name, String fromDate, String toDate) throws APIException {
		return inventoryDAO.countStoreDrugAccount(storeId, name, fromDate, toDate);
	}
	
	public InventoryStoreDrugAccount saveStoreDrugAccount(InventoryStoreDrugAccount issue) throws APIException {
		return inventoryDAO.saveStoreDrugAccount(issue);
	}
	
	public InventoryStoreDrugAccount getStoreDrugAccountById(Integer id) throws APIException {
		return inventoryDAO.getStoreDrugAccountById(id);
	}
	
	/**
	 * InventoryStoreDrugAccountDetail
	 */
	public List<InventoryStoreDrugAccountDetail> listStoreDrugAccountDetail(Integer storeDrugAccountDetailId)
	                                                                                                         throws APIException {
		return inventoryDAO.listStoreDrugAccountDetail(storeDrugAccountDetailId);
	}
	
	public InventoryStoreDrugAccountDetail saveStoreDrugAccountDetail(InventoryStoreDrugAccountDetail storeDrugAccountDetail)
	                                                                                                                         throws APIException {
		return inventoryDAO.saveStoreDrugAccountDetail(storeDrugAccountDetail);
	}
	
	public InventoryStoreDrugAccountDetail getStoreDrugAccountDetailById(Integer id) throws APIException {
		return inventoryDAO.getStoreDrugAccountDetailById(id);
	}
	
	// ghanshyam 15-june-2013 New Requirement #1636 User is able to see and dispense drugs in patient queue for issuing drugs, as ordered from dashboard
	public List<PatientSearch> searchListOfPatient(Date date, String searchKey,int page) throws APIException {
		return inventoryDAO.searchListOfPatient(date,searchKey,page);
	}
        
        // to work with size Selector
        public List<PatientSearch> searchListOfPatient(Date date, String searchKey,int page,int pgSize) throws APIException {
		return inventoryDAO.searchListOfPatient(date,searchKey,page,pgSize);
	}
	
        //to work with size Selector
	public int countSearchListOfPatient(Date date, String searchKey,int page) throws APIException {
		return inventoryDAO.countSearchListOfPatient(date,searchKey,page);
	}
                
	public List<OpdDrugOrder> listOfOrder(Integer patientId,Date date) throws APIException {
		return inventoryDAO.listOfOrder(patientId,date);
	}
	
	public List<OpdDrugOrder> listOfDrugOrder(Integer patientId, Integer encounterId) throws APIException {
		return inventoryDAO.listOfDrugOrder(patientId,encounterId);
	}
	
	public OpdDrugOrder getOpdDrugOrder(Integer patientId,Integer encounterId,Integer inventoryDrugId,Integer formulationId) throws APIException {
		return inventoryDAO.getOpdDrugOrder(patientId,encounterId,inventoryDrugId,formulationId);
	}

	/**
	 * InventoryStoreItemPatient
	 */
	public List<InventoryStoreItemPatient> listStoreItemPatient(Integer storeId,Integer receiptId,  String name, String fromDate,
	                                                            String toDate, int min, int max) throws APIException {
		return inventoryDAO.listStoreItemPatient(storeId,receiptId, name, fromDate, toDate, min, max);
	}
	
	public int countStoreItemPatient(Integer storeId, String name, String fromDate, String toDate) throws APIException {
		return inventoryDAO.countStoreItemPatient(storeId, name, fromDate, toDate);
	}
	
	public InventoryStoreItemPatient saveStoreItemPatient(InventoryStoreItemPatient bill) throws APIException {
		return inventoryDAO.saveStoreItemPatient(bill);
	}
	
	public InventoryStoreItemPatient getStoreItemPatientById(Integer id) throws APIException {
		return inventoryDAO.getStoreItemPatientById(id);
	}
	
	/**
	 * InventoryStoreItemPatientDetail
	 */
	public List<InventoryStoreItemPatientDetail> listStoreItemPatientDetail(Integer storeItemPatientDetailId)
	                                                                                                         throws APIException {
		return inventoryDAO.listStoreItemPatientDetail(storeItemPatientDetailId);
	}
	
	public InventoryStoreItemPatientDetail saveStoreItemPatientDetail(InventoryStoreItemPatientDetail storeItemPatientDetail)
	                                                                                                                         throws APIException {
		return inventoryDAO.saveStoreItemPatientDetail(storeItemPatientDetail);
	}
	
	public InventoryStoreItemPatientDetail getStoreItemPatientDetailById(Integer id) throws APIException {
		return inventoryDAO.getStoreItemPatientDetailById(id);
	}

	
	public List<OpdDrugOrder> listOfNotDispensedOrder(Integer patientId,Date date, Encounter encounterId) throws APIException {
		return inventoryDAO.listOfNotDispensedOrder(patientId,date,encounterId);
	}

	@Override
	public InventoryStoreRoleRelation saveStores(InventoryStoreRoleRelation role)
			throws APIException {
		return inventoryDAO.saveStores(role);
	}

	@Override
	public List<InventoryStoreRoleRelation> listOfRoleRelation(Integer id,String role) throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.listOfRoleRelation(id,role);
	}

	@Override
	public InventoryStoreRoleRelation getStoreRoleById(Integer id) {
		// TODO Auto-generated method stub
		return inventoryDAO.getStoreRoleById(id);
	}

	@Override
	public InventoryStoreRoleRelation getStoreRoleByName(String name) {
		// TODO Auto-generated method stub
		return inventoryDAO.getStoreRoleByName(name);
	}

	@Override
	public List<InventoryStoreRoleRelation> listInventoryStoreRole()
			throws APIException {
		// TODO Auto-generated method stub
		return inventoryDAO.listInventoryStoreRole();
	}

	@Override
	public void deleteStoreRole(InventoryStoreRoleRelation rl)
			throws APIException {
		inventoryDAO.deleteStoreRole(rl);
		
	}

	

	@Override
	public List<InventoryStoreRoleRelation> listOfRoleRelationStore(Integer id)
			throws APIException {
		return inventoryDAO.listOfRoleRelationStore(id);
		
	}

	@Override
	public List<ToxoidModel> getTetanusToxoidTransactions(int patientId) {
		return inventoryDAO.getTetanusToxoidTransactionsByPatient(patientId);
	}

	@Override
	public List<InventoryDrug> getInventoryDrugListByName(String name) throws APIException {
		return inventoryDAO.getInventoryDrugListByName(name);
	}


}
