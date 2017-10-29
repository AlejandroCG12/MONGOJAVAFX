/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import org.omg.CORBA.portable.UnknownException;

/**
 *
 * @author andrns.arcila
 */
public class ConsultasController implements Initializable {
    
    private DB db;
    private DBCollection colPlato;
    private DBCollection colMenu;
    
    @FXML
    private Button buttonRealizarConsulta;
    @FXML
    private ComboBox comboBoxConsultas;
    @FXML
    private TextArea TextArea;
    
    @FXML
    private void handleButtonActionRealizarConsulta(ActionEvent event) {
 
        String consulta = comboBoxConsultas.getValue() + "";
                
        if(consulta.equals("Mostrar todos los platos con más de 600 calorias"))
        {
            consultarPlatosMasDe600Calorias();
        }
        else if(consulta.equals("Mostrar los platos con menos de 4000 en valor real"))
        {
            consultarPlatosMenosDe4000ValorReal();
        }
        else if(consulta.equals("Mostrar el plato \"Albondigas\" y mostrar solo su nombre y los ingredientes asociados"))
        {
            consultarAlbondigas();
        }
        else if(consulta.equals("Mostrar todos los menus que empiecen por la letra \"I\""))
        {
            consultarMenusConI();
        }
        else if(consulta.equals("Mostrar todos los chefs"))
        {
            consultarChefs();
        }    
    }
    
    @FXML
    private void handleButtonActionInsertarMenu(ActionEvent event) throws IOException {
        RestauranteMongo.changeScene("Menu.fxml", event);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        db = Util.conectarBaseDatos();
        colPlato = Util.conectarCollection(db, "Plato");
        colMenu = Util.conectarCollection(db, "Menu");
        
        ObservableList<String> consultas = FXCollections.observableArrayList(
                "Mostrar todos los platos con más de 600 calorias",
                "Mostrar los platos con menos de 4000 en valor real",
                "Mostrar el plato \"Albondigas\" y mostrar solo su nombre y los ingredientes asociados",
                "Mostrar todos los menus que empiecen por la letra \"I\"",
                "Mostrar todos los chefs");
        comboBoxConsultas.setItems(consultas);
    }    
 
    

    private void consultarPlatosMasDe600Calorias() {
        TextArea.setText("");
        BasicDBObject getQuery = new BasicDBObject();
        getQuery.put("calorias", new BasicDBObject("$gt", 600));
        DBCursor cursor = colPlato.find(getQuery);
        System.out.println(cursor.hasNext());
        while(cursor.hasNext())
        {
            BasicDBObject objeto = (BasicDBObject) cursor.next();
            String cadena = "Nombre: "+(String)objeto.get("nombre")+", Calorias: "+String.valueOf(objeto.get("calorias"))+", Valor real: "+
            String.valueOf(objeto.get("valor_real"))+", Valor comercial: "+String.valueOf(objeto.get("valor_comercial"))+", Receta: "+
            String.valueOf(objeto.get("receta"));   
            TextArea.setText(cadena+"\n"+TextArea.getText());            
        }
    }

    private void consultarPlatosMenosDe4000ValorReal() {
        TextArea.setText("");
        BasicDBObject getQuery = new BasicDBObject();
        getQuery.put("valor_real", new BasicDBObject("$lt", 4000));
        DBCursor cursor = colPlato.find(getQuery);
        while(cursor.hasNext()){
            BasicDBObject objeto = (BasicDBObject) cursor.next();
            String cadena = "Nombre: "+(String)objeto.get("nombre")+", Calorias: "+String.valueOf(objeto.get("calorias"))+", Valor real: "+
            String.valueOf(objeto.get("valor_real"))+", Valor comercial: "+String.valueOf(objeto.get("valor_comercial"))+", Receta: "+
            String.valueOf(objeto.get("receta"));
            
            TextArea.setText(cadena+"\n"+TextArea.getText());
        }
    }

    private void consultarAlbondigas() {
        TextArea.setText("");
        BasicDBObject getQuery = new BasicDBObject();
        BasicDBObject fields = new BasicDBObject("nombre", 1).append("ingredientes", 1);
        getQuery.put("nombre", "Albondigas");
        DBCursor cursor = colPlato.find(getQuery,fields);
        while(cursor.hasNext()){
            BasicDBObject objeto = (BasicDBObject) cursor.next();
            String cadena = "Nombre: "+(String)objeto.get("nombre");
            
            TextArea.setText(cadena+"\n"+TextArea.getText());
        }
    }
    
    private void consultarMenusConI() {
        TextArea.setText("");
        Pattern I = Pattern.compile("I", Pattern.LITERAL);
        BasicDBObject getQuery = new BasicDBObject("nombre",I);
        DBCursor cursor = colMenu.find(getQuery);
        while(cursor.hasNext()){
            BasicDBObject objeto = (BasicDBObject) cursor.next();
            String cadena = "Nombre: "+(String)objeto.get("nombre")+", Vigente: "+String.valueOf(objeto.get("vigente"))+", Fecha inicio: "+
            String.valueOf(objeto.get("fecha_inicio"))+", Fecha fin: "+(String.valueOf(objeto.get("fecha_fin")));
            TextArea.setText(cadena+"\n"+TextArea.getText());  
        }
    }
    
    private void consultarChefs(){
        TextArea.setText("");
        BasicDBObject getQuery = new BasicDBObject();
        BasicDBObject fields = new BasicDBObject("chef", 1);
        DBCursor cursor = colMenu.find(getQuery,fields);
        while(cursor.hasNext()){
            BasicDBObject objeto = (BasicDBObject) cursor.next();
            String cadena = "Nombre: "+((BasicDBObject)objeto.get("chef")).get("nombre")
            +", Experiencia: "+((BasicDBObject)objeto.get("chef")).get("experiencia")+", Email: "+((BasicDBObject)objeto.get("chef")).get("email");
            TextArea.setText(cadena+"\n"+TextArea.getText());  
        }
    }

    
    
    
    
}
