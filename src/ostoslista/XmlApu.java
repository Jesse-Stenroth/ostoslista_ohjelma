/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostoslista;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jesse
 */
public class XmlApu {
    public ArrayList<Tuote> getTuotteet(){
        try{
        File inputFile=new File("Tuotteet.xml");
        ArrayList<Tuote> tuotteet = new ArrayList<>();
       // System.out.println("ok");
        //System.out.println(sc.hasNextLine());
     //  File inputFile = new File("input.txt");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         FileInputStream in = new FileInputStream(inputFile);
         Document doc = dBuilder.parse(in, "UTF-8");
         doc.getDocumentElement().normalize();
       //  System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("item");
        // System.out.println("----------------------------");
         
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
          //  System.out.println("\nCurrent Element :" + nNode.getNodeName());
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               
             String title = eElement
                  .getElementsByTagName("title")
                  .item(0)
                  .getTextContent();
             String luokkaa = eElement
                  .getElementsByTagName("luokka")
                  .item(0)
                  .getTextContent();
             String kokoaa = eElement
                  .getElementsByTagName("koko")
                  .item(0)
                  .getTextContent();
             String tyyppiia = eElement
                  .getElementsByTagName("tyyppi")
                  .item(0)
                  .getTextContent();
             tuotteet.add(new Tuote(title, luokkaa, Double.parseDouble(kokoaa), tyyppiia));
             
            }
         }
         return tuotteet;
        } catch(Exception e){
            ArrayList<Tuote> tyhja = new ArrayList<>();
            return tyhja;
        }
    }
    public ArrayList<String> getTuotteidenNimet(){
        ArrayList<Tuote> lista1 = getTuotteet();
        ArrayList<String> nimet = new ArrayList<>();
        for(int kierros=0; kierros < lista1.size(); kierros++){
            nimet.add(lista1.get(kierros).toString());
        }
        Collections.sort(nimet);
        return nimet;
    }
}
