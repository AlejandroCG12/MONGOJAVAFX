/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.omg.CORBA.portable.UnknownException;

/**
 *
 * @author User
 */
public class Util {

    public static DB conectarBaseDatos() {
        MongoClient mongo = crearConexion();
        DB db = mongo.getDB("Restaurante");

        return db;

    }

    public static MongoClient crearConexion() {
        MongoClient mongo = null;
        try {
            mongo = new MongoClient("localhost", 27017);
        } catch (UnknownException ex) {
            ex.printStackTrace();
        }
        //fwsfesf

        return mongo;
    }

//    public static DBCollection conectarCollection(DB db, String nombre) {
//        DBCollection collection = db.getCollection(nombre);
//        return collection;
//    }

    public static DBCollection conectarCollection(DB db, Class<? extends BasicDBObject> c) {
        DBCollection collection = db.getCollection(c.getSimpleName());
        collection.setObjectClass(c);
        
        return collection;
    }
}
