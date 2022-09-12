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
package org.openmrs.module.ehrinventory.web.controller.property.editor;

import java.beans.PropertyEditorSupport;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;

public class PatientPropertyEditor extends PropertyEditorSupport {
	private Log log = LogFactory.getLog(this.getClass());
	public PatientPropertyEditor() {
	}
	public void setAsText(String text) throws IllegalArgumentException {
		
		if (text != null && text.trim().length() > 0 ) {
			try {
				//System.out.println("text: "+text);
				
				List<Patient> patientsList = Context.getPatientService().getPatients( text );
				if(patientsList != null && patientsList.size() > 0){
					//System.out.println("get day ne: "+patientsList.get(0));
					setValue(patientsList.get(0));
				}else{
					setValue(null);
				}
			}
			catch (Exception ex) {
				log.error("Error setting text: " + text, ex);
				throw new IllegalArgumentException("Patient not found: " + ex.getMessage());
			}
		} else {
			setValue(null);
		}
	}
	
	public String getAsText() {
		Patient s = (Patient) getValue();
		if (s == null ) {
			return null; 
		} else {
			return s.getPatientIdentifier().getIdentifier();
		}
	}
}
