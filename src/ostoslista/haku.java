/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostoslista;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Jesse
 */
public class haku {
    public void suorita(Stage ikkuna, HBox boksi, tuotteita_korissa kori) {
        BorderPane paneeli = new BorderPane();
        paneeli.setTop(boksi);
        
        
        
        BorderPane nakyma = new BorderPane();
        paneeli.setCenter(nakyma);
        ListView nakyma2 = new ListView();
        paneeli.setRight(nakyma2);
        
        
        TextField kentta = new TextField();
        HBox hakua = new HBox();
        hakua.setPadding(new Insets(15, 12, 15, 12));
        hakua.setSpacing(10);
        hakua.getChildren().add(kentta);
        Button nappii = new Button("Etsi");
        
        hakua.getChildren().add(nappii);
        nakyma.setTop(hakua);
        
        ListView list = new ListView();
        nakyma.setCenter(list);
        
        nappii.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                hae(kori, list, kentta.getText(), nakyma2);
            }
        });
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
    public void hae(tuotteita_korissa kori, ListView list, String text, ListView nakyma2) {
                ArrayList<Tuote> listaa = new XmlApu().getTuotteet();
                ArrayList<String> lista1 = new ArrayList<>();
                for(int kierros=0;kierros<listaa.size();kierros++){
                    if(listaa.get(kierros).getNimi().toLowerCase().contains(text.toLowerCase())){
                        lista1.add(listaa.get(kierros).toString());
                    }
                }
                ObservableList<String> items =FXCollections.observableArrayList (lista1);
                list.setItems(items);
                list.setOnMouseClicked(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {
                        ObservableList<String> selectedItems =  list.getSelectionModel().getSelectedItems();

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
