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
import java.text.ParseException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
public class Main {
    
    public static void main(String args[]) throws ParseException{
        XML fichero =new XML();
        List<Registros> lista;
        lista=fichero.parsear("coronavirus.xml");    
        Transaction tran = null;
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            tran = session.beginTransaction();
            for (int i=0;i<lista.size();i++) {
                if(i % 100 == 0 ) { 
                        session.flush();
                        session.clear();
                    }
                    Registros reg=lista.get(i);
                    String id=reg.getGeoId()+reg.getSDateRep();
                    Paises pais=new Paises(id,reg.getCases(),reg.getDeaths(),reg.getCountriesAndTerritories(),reg.getContinentExp(),reg.getDateRep());
                    session.save(pais);
            }
            tran.commit();
        }catch(HibernateException e){
            System.out.println(e);
        }
        Scanner reader = new Scanner(System.in);
        boolean sair = false;
        int opcion; 
        Session session = HibernateUtil.getSessionFactory().openSession();
        while (!sair) {
            
            System.out.println("1. Obter aqueles paises con un número determinado de casos totais maior ao número proporcionado.");
            System.out.println("2. Obter para cada pais o maior número de mortes nun día. Ademais deberase indicar cal foi ese día. Se non hay mortes, mostrase a última data con datos");
            System.out.println("3. O pais con maior número de casos para cada día do que se teña información");
            System.out.println("4. Sair");
 
            try { 
                System.out.println("Elixe unha opción");
                opcion = reader.nextInt();
 
                switch (opcion) {
                    case 1:
                        System.out .println("Escribe un numero");
                        int a = reader.nextInt();
                        Query q2 = session.createQuery("SELECT x.countriesAndTerritories,SUM(x.cases) FROM Paises x GROUP BY x.countriesAndTerritories HAVING SUM(x.cases)>"+a);
                        List<Object[]> paises2 = q2.list();
                        paises2.forEach((pais) -> {
                        System.out.println(pais[0]+" "+pais[1]);   
                        });
                        break;
                    case 2:
                        Query q1= session.createQuery("SELECT x.countriesAndTerritories,max(x.deaths),x.dateRep From Paises x GROUP BY x.countriesAndTerritories");
                        List<Object[]> paises1=q1.list();
                        paises1.forEach((pais) -> { 
                        Query q0= session.createQuery("SELECT max(x.dateRep) From Paises x WHERE (x.deaths=:muertos AND x.countriesAndTerritories=:pais)").setParameter("muertos", pais[1]).setParameter("pais", pais[0]);
                        Date fecha=(Date)q0.uniqueResult();
                        pais[2]=fecha;
                        });
                        paises1.forEach((pais) -> {
                        System.out.println(pais[0]+" "+pais[1]+" "+pais[2]);   
                        });
                        break;
                    case 3:
                        System.out.println("Estamos descargando o ficheiro, espera uns segundos,por favor");
                        LeerXML lector=new LeerXML();
                        lista=lector.LeerXML();
                        try{
                        tran = session.beginTransaction();
                        for (int i=0;i<lista.size();i++) {
                        if(i % 100 == 0 ) { 
                        session.flush();
                        session.clear();
                            }
                        Registros reg=lista.get(i);
                        String id=reg.getGeoId()+reg.getSDateRep();
                        Paises pais=new Paises(id,reg.getCases(),reg.getDeaths(),reg.getCountriesAndTerritories(),reg.getContinentExp(),reg.getDateRep());
                        session.saveOrUpdate(pais);
                        }
                        tran.commit();
                        Query q3 = session.createQuery("SELECT MAX(x.cases),x.dateRep,max(x.countriesAndTerritories) FROM Paises x GROUP BY x.dateRep");
                        List<Object[]> paises3 = q3.list();
                        paises3.forEach((pais) -> {
                        String nombre= (String)session.createQuery("SELECT max(x.countriesAndTerritories) FROM Paises x where x.dateRep=:fecha AND x.cases=:muertos").setParameter("fecha", pais[1]).setParameter("muertos",pais[0]).uniqueResult();
                        pais[2]=nombre;
                        });
                        paises3.forEach((pais) -> {
                        System.out.println(pais[0]+" "+pais[1]+" "+pais[2]);
                        });
                        }catch(HibernateException ex){
                            System.out.println(ex);
                        }
                        break;
                    case 4:
                        sair = true;
                        break;
                    default:
                        System.out.println("Non escolliches unha opción correcta");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                reader.next();
            }
        }
        session.close();
    }
}
