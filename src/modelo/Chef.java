/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.mongodb.BasicDBObject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author User
 */
public class Chef extends BasicDBObject {

    private static final String NOMBRE = "nombre";
    private static final String EXPERIENCIA = "experiencia";
    private static final String EMAIL = "email";

    private boolean partial;

    public Chef() {
        this.partial = true;
    }

    public Chef(String nombre, double experiencia, String email) {
        put(NOMBRE, nombre);
        put(EXPERIENCIA, experiencia);
        put(EMAIL, email);

        this.markAsPartialObject();
    }

    @Override
    public void markAsPartialObject() {
        Set<String> set = keySet();
        set.remove("_id");

        Set<String> setThis = new HashSet<>();
        setThis.add(NOMBRE);
        setThis.add(EXPERIENCIA);
        setThis.add(EMAIL);

        partial = !set.equals(setThis);
    }

    public String getNombre() {
        return getString(NOMBRE);
    }

    public void setNombre(String nombre) {
        put(NOMBRE, nombre);
    }

    public double getExperiencia() {
        return getDouble(EXPERIENCIA);
    }

    public void setExperiencia(String experiencia) {
        put(EXPERIENCIA, experiencia);
    }

    public String getEmail() {
        return getString(EMAIL);
    }

    public void setEmail(String email) {
        put(EMAIL, email);
    }

    public static Chef create(BasicDBObject object) {
        String nombre = object.getString(NOMBRE);
        double experiencia = object.getDouble(EXPERIENCIA);
        String email = object.getString(EMAIL);

        return new Chef(nombre, experiencia, email);
    }
}
