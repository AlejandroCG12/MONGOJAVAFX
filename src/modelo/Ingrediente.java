/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.mongodb.BasicDBObject;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author User
 */
public class Ingrediente extends BasicDBObject {

    private static final String NOMBRE = "nombre";
    private static final String CANTIDAD = "cantidad";
    private static final String MEDIDA = "medida";
    
    private boolean partial;
    
    public Ingrediente()
    {
        this.partial = true;
    }
    
    public Ingrediente(String nombre, double cantidad, String medida) {
        put(NOMBRE, nombre);
        put(CANTIDAD, cantidad);
        put(MEDIDA, medida);
        
        this.markAsPartialObject();
    }
    
    @Override
    public void markAsPartialObject() {
        Set<String> set = keySet();
        set.remove("_id");
        
        Set<String> setThis = new HashSet<>();
        setThis.add(NOMBRE);
        setThis.add(CANTIDAD);
        setThis.add(MEDIDA);
        
        partial =  !set.equals(setThis);
    }

    public String getNombre() {
        return getString("nombre");
    }

    public void setNombre(String nombre) {
        put("nombre", nombre);
    }

    public double getCantidad() {
        return getDouble("cantidad");
    }

    public void setNombre(double cantidad) {
        put("cantidad", cantidad);
    }

    public String getMedida() {
        return getString("medida");
    }

    public void setMedida(double medida) {
        put("medida", medida);
    }
    
    public static Ingrediente create (BasicDBObject object)
    {
        String nombre = object.getString(NOMBRE);
        double cantidad = object.getDouble(CANTIDAD);
        String medida = object.getString(MEDIDA);
        
        return new Ingrediente(nombre, cantidad, medida);
    }

}
