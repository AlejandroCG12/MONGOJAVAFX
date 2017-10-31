/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.Ingrediente;
import modelo.Menu;
import modelo.Plato;

/**
 * FXML Controller class
 *
 * @author Momo
 */
public class PlatoController implements Initializable {

    DB db;
    DBCollection colPlato;
    DBCollection colMenu;
    List<Ingrediente> Ingredientes;
    
    @FXML
    private TextField TFNombreP,TFNombreI,TFCalorias,TFVR,TFVC,TFReceta,TFCantidad,TFMedida;
    @FXML
    private TextArea TextArea;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = Util.conectarBaseDatos();
        colPlato = Util.conectarCollection(db, Plato.class);
        colMenu = Util.conectarCollection(db, Menu.class);
        
        Ingredientes = new LinkedList<>();
    }    
    
    @FXML
    private void handleButtonActionSalir(ActionEvent event) throws IOException{
        RestauranteMongo.changeScene("Consultas.fxml", event);
    }
    
    @FXML
    private void handleButtonActionInsertarPlato(ActionEvent event){
        String nombreP = TFNombreP.getText();
        Double Calorias = Double.parseDouble(TFCalorias.getText());
        Double valor_real = Double.parseDouble(TFVR.getText());
        Double valor_comercial = Double.parseDouble(TFVC.getText());
        String receta = TFReceta.getText();
        
        Plato plato = new Plato(nombreP, Calorias, valor_real, valor_comercial, receta, Ingredientes);
        colPlato.insert(plato);
        
        Ingredientes.clear();
        TFNombreP.setText("");
        TFCalorias.setText("");
        TFVR.setText("");
        TFVC.setText("");
        TFReceta.setText("");
        TextArea.setText("");
    }
    
    @FXML
    private void handleButtonActionInsertarIngrediente(ActionEvent event){
        String nombreI = TFNombreI.getText();
        Double cantidad = Double.parseDouble(TFCantidad.getText());
        String medida = TFMedida.getText();
        String ingredientesagregados = TextArea.getText();
        
        Ingrediente ing = new Ingrediente(nombreI, cantidad, medida);
        Ingredientes.add(ing);
        TFNombreI.setText("");
        TFCantidad.setText("");
        TFMedida.setText("");
        ingredientesagregados += nombreI+"\n";
        TextArea.setText(ingredientesagregados);
    }
    
    @FXML
    private void handleButtonActionCancelar(ActionEvent event){
        TFNombreI.setText("");
        TFCantidad.setText("");
        TFMedida.setText("");
    }
    
}
