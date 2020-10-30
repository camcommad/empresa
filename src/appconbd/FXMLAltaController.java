/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appconbd;

import basedatos.ConexionMySQL;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Miguel
 */
public class FXMLAltaController implements Initializable {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    @FXML
    private TextField txtFieldNombreAlta;

    @FXML
    private TextField txtFieldAppellidosAlta;
        
    @FXML
    private TextField txtFieldEmailAlta;
            
    @FXML
    private TextField txtFieldDniAlta;
  
    @FXML
    private ScrollPane scrollPaneConsultaAlta;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = ConexionMySQL.ConnecrDb();               // Obtener la conexión de la base de datos
    }  
    
    /*
    * Método para dar de alta un empleado en base de datos.
    * Se ejecuta cuando se pulse el botón de dar de alta.
    @param event evento
    */
    @FXML
    public void altaEmpleado(ActionEvent event) {
        System.out.println("FXMLAltaController - altaEmpleado - inicio");
        
        //CREAR SENTENCIA SQL DE INSERCIÓN        
        String sql = "INSERT INTO empleados (dni,nombre,apellidos,email) VALUES('"
                +txtFieldDniAlta.getText()+"','"+txtFieldNombreAlta.getText()+"','"
                +txtFieldAppellidosAlta.getText()+"','"+txtFieldEmailAlta.getText()+"');";
        
        int resultadoAlta = 0;
        try {
            pst = conn.prepareStatement(sql);
            //EJECUTAR SENTENCIA DE INSERCIÓN SOBRE LA BASE DE DATOS
            resultadoAlta = pst.executeUpdate(sql); //En rs estará el valor de la tabla
            conn.close();                    //Cerrar la conexión
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAltaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("resultado="+resultadoAlta);                                  
        System.out.println("FXMLAltaController - altaEmpleado - fin");
    }
    
    
        /*
    * Método para mostrar los empleados de base de datos en pantalla.
    @param event evento
    */
    @FXML
    public void consultaEmpleados(ActionEvent event) {
        System.out.println("FXMLAltaController - consultaEmpleados - inicio");
        //CREAR SENTENCIA SQL DE INSERCIÓN        
        String sql = "SELECT * FROM empleados";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery(sql); //En rs estará el valor de la tabla
            
            VBox filas = new VBox();            // Caja vertical donde iremos metiendo las cajas horizontales
            HBox tabla;

            //AGREGAR LOS EMPLEADOS DE BASE DE DATOS AL PANEL SCROLL
            while (rs.next()) {                            //Mientras haya filas de la tabla
                System.out.println(rs);
                //Text dniText = new Text(rs.getString("dni"));
                tabla = new HBox();
                tabla.getChildren().addAll(new Text(rs.getString("dni")), new Text(rs.getString("nombre")),
                        new Text(rs.getString("apellidos")), new Text(rs.getString("email")));
                tabla.setSpacing(10);
                tabla.setPadding(new Insets(10));
                
                filas.getChildren().addAll(tabla);
                
            }
            scrollPaneConsultaAlta.setContent(filas);
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAltaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        System.out.println("FXMLAltaController - consultaEmpleados - fin");
    }
    
}
