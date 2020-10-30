package appconbd;

import basedatos.ConexionMySQL;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
    private PasswordField txtFieldPassword;

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
    public void mostrarUsuario(ActionEvent event) throws SQLException, IOException {
        //Consulta SQL para seleccionar el valor de la columna usuario en la tabla credenciales, donde
        // el valor de la columna id_credencial sea 1
        String sql = "SELECT usuario FROM credenciales where usuario='" + txtFieldUsuario.getText() + "' and password='"
                + txtFieldPassword.getText() + "'";
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery(sql); //En rs estará el valor de la tabla

        if (rs.next()) {                            //Si ha encontrado un valor
            String usuBd = rs.getString("usuario");   //En lbl estará el valor de la columna usuario
            String mensaje = "";
            if (usuBd != null && txtFieldUsuario != null && usuBd.equals(txtFieldUsuario.getText())) {
                mensaje = "Usuario encontrado";
                //CREAR LA VENTANA DE ALTA
                Parent root;
                root = FXMLLoader.load(getClass().getResource("/appconbd/FXMLAlta.fxml"));
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/appconbd/FXMLAlta.fxml"));
                Parent root2 = loader2.load();
                Scene miEscena = new Scene(root2);
                //AnchorPane root3 = (AnchorPane)loader2.load();
                //Text foo2;
                //foo2 = (Text)loader2.getNamespace().get("txtBienvenida");
                //foo2.setText(foo2.getText()+txtFieldUsuario.getText());

                Stage stage = new Stage();
                stage.setTitle("Alta");
                stage.setScene(new Scene(root, 450, 450));
                stage.show();
                // Hide this current window (if this is what you want)
                ((Node) (event.getSource())).getScene().getWindow().hide();

            }
        }
        conn.close();
    }

}
