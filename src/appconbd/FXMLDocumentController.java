package appconbd;

import basedatos.ConexionMySQL;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Miguel
 */
public class FXMLDocumentController implements Initializable {
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    
    @FXML
    private TextField txtFieldUsuario;
    
    @FXML
    private Text txtMensajeLogin;
    

        
    /*
    Al ejecutar la aplicación se creará la conexión con la base de datos
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = ConexionMySQL.ConnecrDb();
    }    
    
    /*
    Cuando el usuario pulse el botón se consultará en base de datos
    */
    @FXML
    public void mostrarUsuario(ActionEvent event) throws SQLException {
        //Consulta SQL para seleccionar el valor de la columna usuario en la tabla credenciales, donde
        // el valor de la columna id_credencial sea 1
        String sql = "SELECT usuario FROM credenciales where id_credencial=1";
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery(sql); //En rs estará el valor de la tabla

        if (rs.next()) {                            //Si ha encontrado un valor
            String usuBd = rs.getString("usuario");   //En lbl estará el valor de la columna usuario
            String mensaje = "";
            if(usuBd != null && txtFieldUsuario!=null && usuBd.equals(txtFieldUsuario.getText())){
                mensaje = "Usuario encontrado";   
                
                Parent root;
                try {
                    FXMLLoader.load(getClass().getResource("/appconbd/FXMLDocument.fxml"));
                    root = FXMLLoader.load(getClass().getResource("/appconbd/FXMLAlta.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Alta");
                    stage.setScene(new Scene(root, 450, 450));
                    stage.show();
                    // Hide this current window (if this is what you want)
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                mensaje = "Usuario no encontrado";   
            }
            
            txtMensajeLogin.setText(mensaje);                     //Establecer el texto de la etiqueta que se muestra por pantalla
        }else{
            txtMensajeLogin.setText("Usuario no encontrado en base de datos"); 
        }
    }
}
