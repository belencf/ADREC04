package com.mycompany.adrec04;

/**
 *
 * @author Belén
 */
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.ParseException;
import java.util.*;
import javax.persistence.Temporal;

@Entity
@Table(name="Paises")
public class Paises implements Serializable{
    
  
    @Id
    @Column(name="id",nullable = false, length = 20)
    private String id;
    @Column(name="cases")
    private int cases;
    @Column(name="countriesAndTerritories")
    private String countriesAndTerritories;
    @Column(name="deaths")
    private int deaths;
    @Column(name="continentExp")
    private String continentExp;
    @Column(name="dateRep")
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dateRep;
    
    
    
    
    public Paises(){
    }
    
    public Paises(String id,int cases,int deaths,String countriesAndTerritories,String continentExp,Date dateRep) throws ParseException{
        
        this.id = id;
        this.countriesAndTerritories = countriesAndTerritories;
        this.continentExp=continentExp;
        this.cases = cases;
        this.deaths = deaths;
        this.dateRep=dateRep;
}
}