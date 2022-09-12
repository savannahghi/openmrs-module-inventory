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

/**
 * @author HealthIT
 *         Created on 8/26/2016.
 */
public class ToxoidModel implements Serializable {
    private String vaccineName;
    private String dateGiven, dateRecorded, provider, formulation;

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getDateGiven() {
        return dateGiven;
    }

    public void setDateGiven(String dateGiven) {
        this.dateGiven = dateGiven;
    }

    public String getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(String dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {
        this.formulation = formulation;
    }

    @Override
    public String toString() {
        return "ToxoidModel{" +
                "vaccineName='" + vaccineName + '\'' +
                ", dateGiven='" + dateGiven + '\'' +
                ", dateRecorded='" + dateRecorded + '\'' +
                ", provider='" + provider + '\'' +
                ", formulation='" + formulation + '\'' +
                '}';
    }
}
