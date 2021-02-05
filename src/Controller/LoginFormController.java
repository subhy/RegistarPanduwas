package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.db;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    public Label lblUser;
    public String User;
    public ImageView card;
    public JFXTextField txtName;
    public JFXPasswordField pfPass;
    public JFXButton btnLogin;
    public AnchorPane root;
    Connection connection;

    {
        try {
            connection = db.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtName.requestFocus();
    }


    public void btnLogin_onKeyPressed(KeyEvent keyEvent) throws SQLException, IOException {


    }

    public void txtName_onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER){
            pfPass.requestFocus();
        }
    }

    public void pfPass_onKeyPressed(KeyEvent keyEvent) throws IOException, SQLException {
        if (keyEvent.getCode()==KeyCode.ENTER){
            doLogin();
        }
    }

    private void doLogin() throws SQLException, IOException {
        Statement stm = connection.createStatement();
        String sql = "SELECT * FROM user WHERE username='" + txtName.getText() + "' " +
                "AND password='" + pfPass.getText() + "'";
        ResultSet resultSet = stm.executeQuery(sql);
        if (resultSet.next()) {
            Stage primaryStage = ((Stage) (txtName.getScene().getWindow()));

            URL resource = this.getClass().getResource("/view/NavForm.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent root = fxmlLoader.load();


            // Parent root = FXMLLoader.load(this.getClass().getResource("/view/NavForm.fxml"));
            Scene mainScene = new Scene(root);
            primaryStage.setScene(mainScene);
            primaryStage.centerOnScreen();

            NavFormController ctrl = fxmlLoader.getController();
            String type = resultSet.getString("type");
            ctrl.lblType.setText(type);


            primaryStage.show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password, please try again later").show();
            txtName.selectAll();
            txtName.requestFocus();
        }


    }

    public void btnLogin_onAction(ActionEvent actionEvent) throws SQLException, IOException {
      doLogin();
    }

    public void btnCancel_onAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("/view/MainForm.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnCancel_onKeyPressed(KeyEvent keyEvent) {

    }
}
