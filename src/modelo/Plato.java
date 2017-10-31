package modelo;

import com.mongodb.BasicDBObject;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author User
 */
public class Plato extends BasicDBObject {
    
    private static final String NOMBRE = "nombre";
    private static final String CALORIAS = "calorias";
    private static final String VALOR_REAL = "valor_real";
    private static final String VALOR_COMERCIAL = "valor_comercial";
    private static final String RECETA = "receta";
    private static final String INGREDIENTES = "ingredientes";
    
    private boolean partial;
    
    public Plato()
    {
        this.partial = true;
    }

    public Plato (String nombre, double valorReal, double valorComercial, double calorias, String receta, List<Ingrediente> ingredientes)
    {
        put(NOMBRE, nombre);
        put(CALORIAS, calorias);
        put(VALOR_REAL, valorReal);
        put(VALOR_COMERCIAL, valorComercial);
        put(RECETA, receta);
        put(INGREDIENTES, ingredientes);

        this.markAsPartialObject();
    }
    
    @Override
    public void markAsPartialObject() {
        Set<String> set = keySet();
        set.remove("_id");
        
        Set<String> setThis = new HashSet<>();
        setThis.add(NOMBRE);
        setThis.add(CALORIAS);
        setThis.add(VALOR_REAL);
        setThis.add(VALOR_COMERCIAL);
        setThis.add(RECETA);
        setThis.add(INGREDIENTES);
        
        partial =  !set.equals(setThis);
    }

    public String getID() {
        return getString("_id");
    }
    
    public String getNombre ()
    {
        return getString(NOMBRE);
    }
    
    public void setNombre (String nombre)
    {
        put(NOMBRE, nombre);
    }
    
    public double getCalorias ()
    {
        return getDouble(CALORIAS);
    }
    
    public void setCalorias (double calorias)
    {
        put(CALORIAS, calorias);
    }
    
    public double getValorReal ()
    {
        return getDouble(VALOR_REAL);
    }
    
    public void setValorReal (double valorReal)
    {
        put(VALOR_REAL, valorReal);
    }
    
    public double getValorComercial ()
    {
        return getDouble(VALOR_COMERCIAL);
    }
    
    public void setValorComercial (double valorComercial)
    {
        put(VALOR_COMERCIAL, valorComercial);
    }
    
    public String getReceta ()
    {
        return getString(RECETA);
    }
    
    public void setReceta (String receta)
    {
        put(RECETA, receta);
    }
    
    public List<? extends BasicDBObject> getIngredientes ()
    {   
        List<BasicDBObject> l = (List<BasicDBObject>) get(INGREDIENTES);
        LinkedList<Ingrediente> ingr = new LinkedList<>();
        for (BasicDBObject obj : l) {
            ingr.add(Ingrediente.create(obj));
        }
        return ingr;
    }
    
    public void setIngredientes (List<? extends BasicDBObject> ingredientes)
    {
        put(INGREDIENTES, ingredientes);
    }
    
}
