/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.adrec04;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author Belén
 */
public class LeerXML{
    
public List<Registros> LeerXML(){
registroshandler handler =new registroshandler();
List<Registros> lista=null;
try{
XMLReader myReader = XMLReaderFactory.createXMLReader();
myReader.setContentHandler(handler);
myReader.parse(new InputSource(new URL("https://opendata.ecdc.europa.eu/covid19/casedistribution/xml").openStream()));
lista = handler.getLista();
}catch (MalformedURLException ex) {
        Logger.getLogger(LeerXML.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException | SAXException ex) {
        Logger.getLogger(LeerXML.class.getName()).log(Level.SEVERE, null, ex);
    }
return lista;
}
}