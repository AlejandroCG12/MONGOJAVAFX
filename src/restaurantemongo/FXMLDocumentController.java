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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private void handleButtonActionRealizarConsulta(ActionEvent event) {
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MongoClient mongo = crearConexion();
        db = mongo.getDB("Restaurante");
        colPlato = db.getCollection("Plato");
        colMenu = db.getCollection("Menu");
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
    
}
