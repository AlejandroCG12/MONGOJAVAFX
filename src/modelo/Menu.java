/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.mongodb.BasicDBObject;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author User
 */
public class Menu extends BasicDBObject
{
    private final String NOMBRE = "nombre";
    private final String VIGENTE = "vigente";
    private final String FECHA_INICIO = "fecha_inicio";
    private final String FECHA_FIN = "fecha_fin";
    private final String CHEF = "chef";
    private final String PLATOS = "platos";
    
    public String getNombre() {
        return getString(NOMBRE);
    }
    
    public void setNombre(String nombre) {
        put(NOMBRE, nombre);
    }
    
    public boolean getVigente() {
        return getBoolean(VIGENTE);
    }
    
    public void setVigente(boolean vigente) {
        put(VIGENTE, vigente);
    }
    
    public Date getFechaInicio() {
        return getDate(FECHA_INICIO);
    }
    
    public void setFechaInicio(Date fechaInicio) {
        put(FECHA_INICIO, fechaInicio);
    }
    
    public Date getFechaFin() {
        return getDate(FECHA_FIN);
    }
    
    public void setFechaFin(Date fechaFin) {
        put(FECHA_FIN, fechaFin);
    }
    
    public Chef getChef ()
    {   
        Chef chef = Chef.create((BasicDBObject)get(CHEF));
        return chef;
    }
    
    public void setChef (Chef chef)
    {
        put(CHEF, chef);
    }
    
    public List<String> getPlatosIDs ()
    {   
        return (List<String>) get(PLATOS);
    }
    
    public void setPlatosIDs (List<String> ingredientes)
    {
        put(PLATOS, ingredientes);
    }
}
