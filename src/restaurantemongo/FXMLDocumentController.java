/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantemongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.omg.CORBA.portable.UnknownException;

/**
 *
 * @author andrns.arcila
 */
public class FXMLDocumentController implements Initializable {
    
    DB db;
    DBCollection colPlato;
    DBCollection colMenu;
    
    @FXML
    private Button buttonRealizarConsulta;
    
    
    @FXML
    private ComboBox comboBoxConsultas;
    
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
        
        }
        else if(consulta.equals("Mostrar todos los chefs"))
        {
        
        }
        
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MongoClient mongo = crearConexion();
        db = mongo.getDB("Restaurante");
        colPlato = db.getCollection("Plato");
        colMenu = db.getCollection("Menu");
        
        ObservableList<String> consultas = FXCollections.observableArrayList(
                "Mostrar todos los platos con más de 600 calorias",
                "Mostrar los platos con menos de 4000 en valor real",
                "Mostrar el plato \"Albondigas\" y mostrar solo su nombre y los ingredientes asociados",
                "Mostrar todos los menus que empiecen por la letra \"I\"",
                "Mostrar todos los chefs");
        comboBoxConsultas.setItems(consultas);
    }    

    
    
    private static MongoClient crearConexion() {
        MongoClient mongo = null;
        try{
            mongo = new MongoClient("localhost", 27017);
        }catch(UnknownException ex){
            ex.printStackTrace();
        }
        
        return mongo;
    }

    private void consultarPlatosMasDe600Calorias() {
        BasicDBObject query = new BasicDBObject();
        query.put("vigente", true);
        DBCursor cursor = colMenu.find(query);
        while(cursor.hasNext())
        {
            BasicDBObject objeto = (BasicDBObject) cursor.next();
            
            System.out.println(objeto.get("nombre"));
            System.out.println(objeto.get("fecha_inicio"));
            System.out.println(objeto.get("fecha_fin"));
            System.out.println(((BasicDBObject) objeto.get("chef")).get("nombre"));
            System.out.println(((BasicDBObject) objeto.get("chef")).get("experiencia"));
            System.out.println(((BasicDBObject) objeto.get("chef")).get("email"));
            
        }
    }

    private void consultarPlatosMenosDe4000ValorReal() {
        BasicDBObject consulta = new BasicDBObject();
        consulta.put("valor_real", new BasicDBObject("$lt", 4000));
        DBCursor cursor = colPlato.find(consulta);
        while(cursor.hasNext())
        {
            BasicDBObject objeto = (BasicDBObject) cursor.next();
            
            System.out.println(objeto.get("nombre"));
            System.out.println(objeto.get("calorias"));
            System.out.println(objeto.get("valor_real"));
        }
    }

    private void consultarAlbondigas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
