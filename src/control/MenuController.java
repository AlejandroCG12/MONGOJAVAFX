/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
import modelo.Chef;
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
        LocalDate localDate1 = datePickerFechaInicio.getValue();
        Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
        Date inicio = Date.from(instant1);
        LocalDate localDate2 = datePickerFechaFin.getValue();
        Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
        Date fin = Date.from(instant2);
        String nombrechef = textFieldNombreChef.getText();
        Double exp = Double.parseDouble(textFieldExperiencia.getText());
        String email = textFieldEmail.getText();
        Chef chef = new Chef(nombrechef,exp,email);
        
        List<String> platos = null;
        //platos.add((String)comboBoxPlatos.getValue());
        
        Menu menu = new Menu(nombre, vigente, inicio, fin, chef, platos);
        BasicDBObject objeto= new BasicDBObject();
        objeto.put("nombre", menu.getNombre());
        objeto.put("vigente", menu.getVigente());
        objeto.put("fecha_inicio", menu.getFechaInicio());
        objeto.put("fecha_fin", menu.getFechaFin());
        objeto.put("chef", new BasicDBObject("nombre",menu.getChef().getNombre()).append("experiencia", menu.getChef().getExperiencia()).append("email", menu.getChef().getEmail()));
        //objeto.put("platos", menu.getNombre());
        colMenu.insert(objeto);
        
    }
    
    @FXML
    private void handleButtonActionInsertarPlato(ActionEvent event){
        
        System.out.println(comboBoxPlatos.getValue());
//        comboBoxPlatos.
        
    }
    
    
}
