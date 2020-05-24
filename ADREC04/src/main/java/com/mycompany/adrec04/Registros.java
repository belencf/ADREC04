/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.adrec04;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Belén
 */
public class Registros {
   
    private String dateRep;
    private int cases;
    private String countriesAndTerritories;
    private int deaths;
    private String continentExp;
    private String geoId;
    
    /**
     * @return the dateRep
     */


    /**
     * @param dateRep the dateRep to set
     */
    public void setDateRep(String dateRep) {
        this.dateRep = dateRep;
    }

    /**
     * @return the cases
     */
    public int getCases() {
        return cases;
    }

    /**
     * @param cases the cases to set
     */
    public void setCases(int cases) {
        this.cases = cases;
    }

    /**
     * @return the countriesAndTerritories
     */
    public String getCountriesAndTerritories() {
        return countriesAndTerritories;
    }

    /**
     * @param countriesAndTerritories the countriesAndTerritories to set
     */
    public void setCountriesAndTerritories(String countriesAndTerritories) {
        this.countriesAndTerritories = countriesAndTerritories;
    }

    /**
     * @return the deaths
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     * @param deaths the deaths to set
     */
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    /**
     * @return the continentExp
     */
    public String getContinentExp() {
        return continentExp;
    }

    /**
     * @param continentExp the continentExp to set
     */
    public void setContinentExp(String continentExp) {
        this.continentExp = continentExp;
    }

    /**
     * @return the year
     * @throws java.text.ParseException
     */
   

    public Date getDateRep() throws ParseException {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
       
            return formateador.parse(dateRep);
        
        
    }
    public String getSDateRep() {
        
        return dateRep;
    }

    /**
     * @return the geoId
     */
    public String getGeoId() {
        return geoId;
    }

    /**
     * @param geoId the geoId to set
     */
    public void setGeoId(String geoId) {
        this.geoId = geoId;
    }
    
}
