/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import modelo.Menu;
import modelo.Plato;

/**
 * FXML Controller class
 *
 * @author Momo
 */
public class MenuController implements Initializable {

    DB db;
    DBCollection colPlato;
    DBCollection colMenu;
    
    @FXML
    private ComboBox comboBoxPlatos;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private CheckBox checkBoxVigente;
    @FXML
    private DatePicker datePickerFechaInicio;
    @FXML
    private DatePicker datePickerFechaFin;
    @FXML
    private TextField textFieldNombreChef;
    @FXML
    private TextField textFieldExperiencia;
    @FXML
    private TextField textFieldEmail;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = Util.conectarBaseDatos();
        colPlato = Util.conectarCollection(db, Plato.class);
        colMenu = Util.conectarCollection(db, Menu.class);
        
        LinkedList<Plato> platos = (LinkedList<Plato>) Util.buscar(colPlato, Menu.class, null);
        ObservableList<String> PlatosNombres = FXCollections.observableArrayList();        
        for (Plato plato : platos) {
            PlatosNombres.add(plato.getNombre());
        }
        
        
        comboBoxPlatos.setItems(PlatosNombres);
        
    }    
    
    @FXML
    private void handleButtonActionInsertarMenu(ActionEvent event) {
        String nombre = textFieldNombre.getText();
        boolean vigente = checkBoxVigente.isSelected();
        Date fechaInicio = Date.from(datePickerFechaInicio.valueProperty().getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println(fechaInicio);
//        Menu menu = new Menu(nombre, vigente, fechaInicio, fechaFin, chef, platos);
    }
    
    @FXML
    private void handleButtonActionInsertarPlato(ActionEvent event){
        
        System.out.println(comboBoxPlatos.getValue());
//        comboBoxPlatos.
        
    }
    
    
}
