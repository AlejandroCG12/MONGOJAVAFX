 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import modelo.Chef;
import modelo.Ingrediente;
import modelo.Menu;
import modelo.Plato;
import org.bson.types.ObjectId;
import org.omg.CORBA.portable.UnknownException;

/**
 *
 * @author andrns.arcila
 */
public class ConsultasController implements Initializable {

    private DB db;
    private DBCollection colPlato;
    private DBCollection colMenu;
    
    private String htmlConsultas;
    @FXML
    private WebView webViewConsultas;
    private WebEngine webEngineConsultas;
    @FXML
    private Button buttonRealizarConsulta;
    @FXML
    private ComboBox comboBoxConsultas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        webEngineConsultas = webViewConsultas.getEngine();
        webEngineConsultas = webViewConsultas.getEngine();
        htmlConsultas = "";
        
        db = Util.conectarBaseDatos();
        colPlato = Util.conectarCollection(db, Plato.class);
        colMenu = Util.conectarCollection(db, Menu.class);

        ObservableList<String> consultas = FXCollections.observableArrayList(
                "Mostrar todos los platos con más de 600 calorias",
                "Mostrar los platos con menos de 4000 en valor real",
                "Mostrar el plato \"Albondigas\" y mostrar solo su nombre y los ingredientes asociados",
                "Mostrar todos los menus que empiecen por la letra \"I\"",
                "Mostrar todos los chefs");
        comboBoxConsultas.setItems(consultas);
    }

    @FXML
    private void handleButtonActionRealizarConsulta(ActionEvent event) {

        String consulta = comboBoxConsultas.getValue() + "";

        if (consulta.equals("Mostrar todos los platos con más de 600 calorias")) {
            consultarPlatosMasDe600Calorias();
        } else if (consulta.equals("Mostrar los platos con menos de 4000 en valor real")) {
            consultarPlatosMenosDe4000ValorReal();
        } else if (consulta.equals("Mostrar el plato \"Albondigas\" y mostrar solo su nombre y los ingredientes asociados")) {
            consultarAlbondigas();
        } else if (consulta.equals("Mostrar todos los menus que empiecen por la letra \"I\"")) {
            consultarMenusConI();
        } else if (consulta.equals("Mostrar todos los chefs")) {
            consultarChefs();
        }
    }

    @FXML
    private void handleButtonActionInsertarMenu(ActionEvent event) throws IOException {
        RestauranteMongo.changeScene("Menu.fxml", event);
    }
    
    @FXML
    private void handleButtonActionInsertarPlato(ActionEvent event) throws IOException {
        RestauranteMongo.changeScene("Plato.fxml", event);
    }

    

    private void consultarPlatosMasDe600Calorias() {

        BasicDBObject getQuery = new BasicDBObject();
        getQuery.put("calorias", new BasicDBObject("$gt", 600));
        LinkedList<Plato> platos = (LinkedList<Plato>) Util.buscar(colPlato, Plato.class, getQuery);
        
        htmlConsultas = "<table border=1 width=100%><tr><th>Nombre</th>"
                + "<th>Calorias</th>"
                + "<th>Valor real</th>"
                + "<th>Valor comercial</th>"
                + "<th>Receta</th>"
                + "<th>Ingredientes</th></tr>";
        for (Plato plato : platos) {
            htmlConsultas += "<tr>";
            
            htmlConsultas += "<th>" + plato.getNombre() + "</th>";
            htmlConsultas += "<th>" + plato.getCalorias()+ "</th>";
            htmlConsultas += "<th>" + plato.getValorReal()+ "</th>";
            htmlConsultas += "<th>" + plato.getValorComercial()+ "</th>";
            htmlConsultas += "<th>" + plato.getReceta()+ "</th>";
            
//            List<Ingrediente> ingredientes = plato.getIngredientes();
            BasicDBObject getQueryIngredientes = new BasicDBObject();
            getQuery.put("calorias", new BasicDBObject("$gt", 600));
            List<Ingrediente> ingredientes = (List<Ingrediente>)plato.getIngredientes();
            String htmlIngredientes = "";
            for (Ingrediente ingrediente : ingredientes) {
                htmlIngredientes += ingrediente.getNombre() + ": " + ingrediente.getCantidad() + " " + ingrediente.getMedida() + "\n";
            }          
            
            htmlConsultas += "<th title=\"" + htmlIngredientes + "\">" + plato.getIngredientes().size()+ "</th>";
            
            htmlConsultas += "</tr>";
        }

        htmlConsultas += "</table>";
        webEngineConsultas.loadContent(htmlConsultas);
    }

    private void consultarPlatosMenosDe4000ValorReal() {
        BasicDBObject getQuery = new BasicDBObject();
        getQuery.put("valor_real", new BasicDBObject("$lt", 4000));
        LinkedList<Plato> platos = (LinkedList<Plato>) Util.buscar(colPlato, Plato.class, getQuery);
        
        htmlConsultas = "<table border=1 width=100%><tr><th>Nombre</th>"
                + "<th>Calorias</th>"
                + "<th>Valor real</th>"
                + "<th>Valor comercial</th>"
                + "<th>Receta</th>"
                + "<th>Ingredientes</th></tr>";
        for (Plato plato : platos) {
            htmlConsultas += "<tr>";
            
            htmlConsultas += "<th>" + plato.getNombre() + "</th>";
            htmlConsultas += "<th>" + plato.getCalorias()+ "</th>";
            htmlConsultas += "<th>" + plato.getValorReal()+ "</th>";
            htmlConsultas += "<th>" + plato.getValorComercial()+ "</th>";
            htmlConsultas += "<th>" + plato.getReceta()+ "</th>";
            
//            List<Ingrediente> ingredientes = plato.getIngredientes();
            BasicDBObject getQueryIngredientes = new BasicDBObject();
            getQuery.put("calorias", new BasicDBObject("$gt", 600));
            List<Ingrediente> ingredientes = (List<Ingrediente>)plato.getIngredientes();
            String htmlIngredientes = "";
            for (Ingrediente ingrediente : ingredientes) {
                htmlIngredientes += ingrediente.getNombre() + ": " + ingrediente.getCantidad() + " " + ingrediente.getMedida() + "\n";
            }          
            
            htmlConsultas += "<th title=\"" + htmlIngredientes + "\">" + plato.getIngredientes().size()+ "</th>";
            
            htmlConsultas += "</tr>";
        }

        htmlConsultas += "</table>";
        webEngineConsultas.loadContent(htmlConsultas);

    }

    private void consultarAlbondigas() {
        BasicDBObject getQuery = new BasicDBObject();
        BasicDBObject fields = new BasicDBObject("nombre", 1).append("ingredientes", 1);
        getQuery.put("nombre", "Albondigas");
        LinkedList<Plato> platos = (LinkedList<Plato>) Util.buscar(colPlato, Plato.class, getQuery,fields);
        htmlConsultas = "<table border=1 width=100%><tr><th>Nombre plato</th>"
                + "<th>Ingredientes</th>"
                + "</tr>";
        for (Plato plato : platos) {
            
            htmlConsultas += "<tr>";
            
            htmlConsultas += "<th>" + plato.getNombre() + "</th>";

            List<Ingrediente> ingredientes = (List<Ingrediente>)plato.getIngredientes();
            String htmlIngredientes = "";
            for (Ingrediente ingrediente : ingredientes) {
                htmlIngredientes += ingrediente.getNombre() + ": " + ingrediente.getCantidad() + " " + ingrediente.getMedida() + "\n";
            }          
            htmlConsultas += "<th title=\"" + htmlIngredientes + "\">" + plato.getIngredientes().size()+ "</th>";
            htmlConsultas += "</tr>";
        }
        htmlConsultas += "</table>";
        webEngineConsultas.loadContent(htmlConsultas);
    }

    private void consultarMenusConI() {
        Pattern I = Pattern.compile("I", Pattern.LITERAL);
        BasicDBObject getQuery = new BasicDBObject("nombre", I);
        LinkedList<Menu> menus = (LinkedList<Menu>) Util.buscar(colMenu, Menu.class, getQuery);

        htmlConsultas = "<table border=1 width=100%><tr><th>Nombre</th>"
                + "<th>Vigente</th>"
                + "<th>Fecha de inicio</th>"
                + "<th>Fecha de fin</th>"
                + "<th>Chef</th>"
                + "<th>Platos</th></tr>";
        for (Menu menu : menus) {
            htmlConsultas += "<tr>";
            
            htmlConsultas += "<th>" + menu.getNombre() + "</th>";
            htmlConsultas += "<th>" + menu.getVigente()+ "</th>";
            htmlConsultas += "<th>" + menu.getFechaInicio()+ "</th>";
            htmlConsultas += "<th>" + menu.getFechaFin()+ "</th>";
            
            String htmlChef = menu.getChef().getNombre() + "\n" + menu.getChef().getExperiencia() + " años de experiencia\n"
                    + menu.getChef().getEmail();
            
            htmlConsultas += "<th title=\"" + htmlChef + "\">" + menu.getChef().getNombre()+ "</th>";
            
            String htmlPlatos = "";
            List<String> platosIDs = menu.getPlatosIDs();
            
            for (String platosID : platosIDs) {
                
            }
            
            LinkedList<Plato> platos = null; 
            String htmlPlato = "";
            for (String platoID : platosIDs) {
                ObjectId pID = new ObjectId(platoID);
                
                BasicDBObject getQueryP = new BasicDBObject();
                getQueryP.put("_id", pID);   
                platos = (LinkedList<Plato>) Util.buscar(colPlato, Plato.class, getQueryP);
                
                htmlPlato += platos.getFirst().getNombre() + ": $" + platos.getFirst().getValorComercial() + "\n";
                
            }
            
            if(platos != null && platos.size() >= 1)
            {
                htmlConsultas += "<th title=\"" + htmlPlato + "\">" + platosIDs.size() + "</th>";
            }
            else if(platos == null)
            {
                htmlConsultas += "<th>no hay platos</th>";
            }
            
            
            htmlConsultas += "</tr>";
        }

        htmlConsultas += "</table>";
        webEngineConsultas.loadContent(htmlConsultas);
        
    }

    private void consultarChefs() {
        LinkedList<Menu> menus = (LinkedList<Menu>)Util.buscar(colMenu, Menu.class, null);
        htmlConsultas = "<table border=1 width=100%><tr><th>Nombre</th>"
                + "<th>Años de experiencia</th>"
                + "<th>Email</th></tr>";
        
        for (Menu menu : menus) {
            Chef chef = menu.getChef();
            htmlConsultas += "<tr><th>" + chef.getNombre() + "</th>"
                + "<th>" + chef.getExperiencia() + "</th>"
                + "<th>" + chef.getEmail()+ "</th></tr>";
        }
        
        htmlConsultas += "</table>";
        webEngineConsultas.loadContent(htmlConsultas);
    }

}
