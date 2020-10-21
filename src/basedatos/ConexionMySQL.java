package basedatos;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Clase para configurar la conexión con la base de datos MySQL
 * @author Miguel
 */
public class ConexionMySQL {
    
    Connection conn = null;

    public static Connection ConnecrDb() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/empresa","miguel","miguel");

            System.out.println("Conectado con base de datos!!!");

            return conn;

        } catch(Exception e){

            System.out.println(e);

            return null;

        }

    }

    
    
}
