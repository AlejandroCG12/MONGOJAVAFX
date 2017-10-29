/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Momo
 */
public class MenuController implements Initializable {

    DB db;
    DBCollection colPlato;
    DBCollection colMenu;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = Util.conectarBaseDatos();
        colPlato = Util.conectarCollection(db, "plato");
        colMenu = Util.conectarCollection(db, "menu");
    }    
    
}
