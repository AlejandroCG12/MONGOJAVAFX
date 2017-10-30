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
import modelo.Ingrediente;
import modelo.Menu;
import modelo.Plato;
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
    @FXML
    private TextArea TextArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        webEngineConsultas = webViewConsultas.getEngine();
        webEngineConsultas = webViewConsultas.getEngine();
        htmlConsultas = "";
        
        db = Util.conectarBaseDatos();
        colPlato = Util.conectarCollection(db, Plato.class);
        colMenu = Util.conectarCollection(db, Menu.class);

        List<Ingrediente> i = new LinkedList<>();
        i.add(new Ingrediente("Agua", 1, "l"));
        i.add(new Ingrediente("Papa", 2, "unidades"));
        i.add(new Ingrediente("sal", 10, "g"));
        i.add(new Ingrediente("Pollo", 15, "Kg"));
        Plato p = new Plato("Sopa", 3000, 3500, 800, "la otra receta", i);
//        colPlato.save(p);

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

    

    private void consultarPlatosMasDe600Calorias() {
        TextArea.setText("");

        BasicDBObject getQuery = new BasicDBObject();
        getQuery.put("calorias", new BasicDBObject("$gt", 600));
        LinkedList<Plato> platos = (LinkedList<Plato>) Util.buscar(colPlato, Plato.class, getQuery);
        
        htmlConsultas = "<table border=1 width=100%><tr><th>nombre</th>"
                + "<th>calorias</th>"
                + "<th>valor real</th>"
                + "<th>valor comercial</th>"
                + "<th>receta</th>"
                + "<th>ingredientes</th></tr>";
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
        TextArea.setText(platos.toString());
    }

    private void consultarPlatosMenosDe4000ValorReal() {
        TextArea.setText("");
        BasicDBObject getQuery = new BasicDBObject();
        getQuery.put("valor_real", new BasicDBObject("$lt", 4000));
        LinkedList<Plato> platos = (LinkedList<Plato>) Util.buscar(colPlato, Plato.class, getQuery);
        
        htmlConsultas = "<table border=1 width=100%><tr><th>nombre</th>"
                + "<th>calorias</th>"
                + "<th>valor real</th>"
                + "<th>valor comercial</th>"
                + "<th>receta</th>"
                + "<th>ingredientes</th></tr>";
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

        TextArea.setText(platos.toString());
    }

    private void consultarAlbondigas() {
        TextArea.setText("");
        BasicDBObject getQuery = new BasicDBObject();
        BasicDBObject fields = new BasicDBObject("nombre", 1).append("ingredientes", 1);
        getQuery.put("nombre", "Albondigas");
        DBCursor cursor = colPlato.find(getQuery, fields);
        while (cursor.hasNext()) {
            BasicDBObject objeto = (BasicDBObject) cursor.next();
            String cadena = "Nombre: " + (String) objeto.get("nombre");

            TextArea.setText(cadena + "\n" + TextArea.getText());
        }
    }

    private void consultarMenusConI() {
        TextArea.setText("");
        Pattern I = Pattern.compile("I", Pattern.LITERAL);
        BasicDBObject getQuery = new BasicDBObject("nombre", I);
        LinkedList<Menu> menus = (LinkedList<Menu>) Util.buscar(colMenu, Menu.class, getQuery);

        htmlConsultas = "<table border=1 width=100%><tr><th>nombre</th>"
                + "<th>vigente</th>"
                + "<th>fecha de inicio</th>"
                + "<th>fecha de fin</th>"
                + "<th>chef</th>"
                + "<th>platos</th></tr>";
        for (Menu menu : menus) {
            htmlConsultas += "<tr>";
            
            htmlConsultas += "<th>" + menu.getNombre() + "</th>";
            htmlConsultas += "<th>" + menu.getVigente()+ "</th>";
            htmlConsultas += "<th>" + menu.getFechaInicio()+ "</th>";
            htmlConsultas += "<th>" + menu.getFechaFin()+ "</th>";
            
            String htmlChef = menu.getChef().getNombre() + "\n" + menu.getChef().getExperiencia() + " años de experiencia\n"
                    + menu.getChef().getEmail();
            
            htmlConsultas += "<th title=\"" + htmlChef + "\">" + menu.getChef().getNombre()+ "</th>";
            
            List<String> platosIDs = menu.getPlatosIDs();
            
            
            LinkedList<Plato> platos = null; 
            String htmlPlato = "";
            for (String platoID : platosIDs) {
                BasicDBObject getQueryP = new BasicDBObject();
                getQueryP.put("_id", platoID);   
                platos = (LinkedList<Plato>) Util.buscar(colPlato, Plato.class, getQueryP);
                if(platos.size() == 1)
                {
                    htmlPlato = platos.getFirst().getNombre() + ": $" + platos.getFirst().getValorComercial();
                }
                
            }           
            
            if(platos != null && platos.size() == 1)
            {
                htmlConsultas += "<th title=\"" + htmlPlato + "\">" + platos.getFirst().getNombre() + "</th>";
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
        TextArea.setText("");
        BasicDBObject getQuery = new BasicDBObject();
        BasicDBObject fields = new BasicDBObject("chef", 1);
        DBCursor cursor = colMenu.find(getQuery, fields);
        while (cursor.hasNext()) {
            BasicDBObject objeto = (BasicDBObject) cursor.next();
            String cadena = "Nombre: " + ((BasicDBObject) objeto.get("chef")).get("nombre")
                    + ", Experiencia: " + ((BasicDBObject) objeto.get("chef")).get("experiencia") + ", Email: " + ((BasicDBObject) objeto.get("chef")).get("email");
            TextArea.setText(cadena + "\n" + TextArea.getText());
        }
    }

}
