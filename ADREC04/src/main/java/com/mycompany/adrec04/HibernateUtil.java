/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.adrec04;

/**
 *
 * @author Belén
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    
    //Este método devolve a sesión para poder facer operacións coa base de datos
    public static SessionFactory getSessionFactory(){
        FileReader fr = null;
        try {
            fr = new FileReader("config.json");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        JsonReader jsonReader = Json.createReader(fr);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        try {
            fr.close();
        } catch (IOException ex) {
            Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        JsonObject jo = jsonObject.getJsonObject("dbConnection");
        JsonObject j = jsonObject.getJsonObject("hibernate");
        String address=(jo.getString("address"));
        String name=(jo.getString("name"));
        String password=(jo.getString("password"));
        String user=(jo.getString("user"));
        String port=(jo.getString("port"));
        String dialect=(j.getString("dialect"));
        String driver=(j.getString("driver"));
        String HBM2DDL_AUTO=(j.getString("HBM2DDL_AUTO"));
        Boolean SHOW_SQL=(j.getBoolean("SHOW_SQL"));
        String SHOW=Boolean.toString(SHOW_SQL);
        
        
        
        
        //Se a sesion non se configurou, creamolo
        if(sessionFactory == null){
            try{
                Configuration conf = new Configuration();
                
                //Engadimos as propiedades
                Properties settings = new Properties();
                
                //Indicamos o conector da base de datos que vamos a usar
                settings.put(Environment.DRIVER,driver);
                
                //Indicamos a localización da base de datos que vamos a utilizar
                settings.put(Environment.URL,"jdbc:mysql://"+address+":"+port+"/"+name+"?useUnicode=true&useJDBCCompliantTimezoneShift=true"+"&useLegacyDatetimeCode=false&serverTimezone=Europe/Madrid&amp");
                
                //Indicamos o usuario da base de datos con cal nos vamos conectar e o seu contrasinal
                settings.put(Environment.USER,user);
                settings.put(Environment.PASS,password);
                
                //Indicamos o dialecto que ten que usar Hibernate 
                settings.put(Environment.DIALECT,dialect);
                
                //Indicamos que se as táboas todas se borren e se volvan crear
                settings.put(Environment.HBM2DDL_AUTO, HBM2DDL_AUTO);
                settings.put(Environment.STATEMENT_BATCH_SIZE, "100");
                //Indicamos que se mostre as operacións SQL que Hibernate leva a cabo
                settings.put(Environment.SHOW_SQL, SHOW);
                conf.setProperties(settings);
                
                //Engaidmos aquelas clases nas que queremos facer persistencia
                conf.addAnnotatedClass(Paises.class);
                
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
                sessionFactory = conf.buildSessionFactory(serviceRegistry);
            }catch(HibernateException e){
                System.out.println(e);
            }
        }
        return sessionFactory;
    }
    
}