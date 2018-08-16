/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostoslista;

import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Jesse
 */
public class luokittelu {
    public void suorita(Stage ikkuna, HBox boksi, tuotteita_korissa kori) {
        BorderPane paneeli = new BorderPane();
        paneeli.setTop(boksi);
        BorderPane listaus = new BorderPane();
        ChoiceBox<String> cb = new ChoiceBox(FXCollections.observableArrayList(valinnat()));
        listaus.setTop(cb);
        listaus.setAlignment(cb,Pos.CENTER);
        ListView nakyma = new ListView();
        listaus.setCenter(nakyma);
        paneeli.setCenter(listaus);
        
        ListView nakyma2 = new ListView();
        paneeli.setRight(nakyma2);
        
        cb.getSelectionModel().selectedItemProperty().addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> listalle(kori, nakyma, newValue, nakyma2));
        
        if(kori.getKoko() >0){
                    ObservableList<String> items2 =FXCollections.observableArrayList (kori.getLista());
                        nakyma2.setItems(items2);
                        
        }
        if(nakyma2.getItems().size() > 0){
            System.out.println("lausekkeessa");
        nakyma2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


                nakyma2.setOnMouseClicked(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {
                        ObservableList<String> selectedItems =  nakyma2.getSelectionModel().getSelectedItems();

                        for(String s : selectedItems){
                            System.out.println("pois");
                             System.out.println("alku lista: " + kori.getLista());
                            kori.poista(s);
                            System.out.println("lista:" + kori.getLista());
                            ObservableList<String> items2 =FXCollections.observableArrayList (kori.getLista());
                        nakyma2.setItems(items2);
                        }

                    }

                });
        }
        ikkuna.setScene(new Scene(paneeli));
    }
    public ArrayList<String> valinnat(){
        ArrayList<Tuote> lista = new XmlApu().getTuotteet();
        ArrayList<String> apu = new ArrayList<>();
        for(int k=0;k<lista.size();k++){
            if(!apu.contains(lista.get(k).getLuokka())){
                apu.add(lista.get(k).getLuokka());
            }
        }
        return apu;
    }

    private void listalle(tuotteita_korissa kori, ListView nakyma, String arvo, ListView nakyma2) {
        ArrayList<String> tulokset = new ArrayList<>();
        ArrayList<Tuote> lista = new XmlApu().getTuotteet();
        for(int k=0;k<lista.size();k++){
            if(lista.get(k).getLuokka().contains(arvo)){
                tulokset.add(lista.get(k).toString());
            }
        }
        ObservableList<String> items =FXCollections.observableArrayList (tulokset);
                nakyma.setItems(items);
                nakyma.setOnMouseClicked(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {
                        ObservableList<String> selectedItems =  nakyma.getSelectionModel().getSelectedItems();

                        for(String s : selectedItems){
                            kori.lisaa(s);
                            ObservableList<String> items2 =FXCollections.observableArrayList (kori.getLista());
                        nakyma2.setItems(items2);
                        nakyma2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

//test
                        
                nakyma2.setOnMouseClicked(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {
                        ObservableList<String> selectedItems =  nakyma2.getSelectionModel().getSelectedItems();

                        for(String s : selectedItems){
                            System.out.println("pois");
                            System.out.println("alku lista: " + kori.getLista());
                            kori.poista(s);
                            System.out.println("lista:" + kori.getLista());
                           ObservableList<String> items2 =FXCollections.observableArrayList (kori.getLista());
                        nakyma2.setItems(items2);
                        }

                    }

                });
                        }

                    }

                });
    }
}
