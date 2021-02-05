package Controller;

import TM.DCTM;
import TM.MCTM;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import db.db;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeadFormController implements Initializable{


    public JFXTextField txtSearch;
    public JFXButton btnNew;
    public TableView<DCTM> tblDeathSearch;
    public Label lblType;
    public JFXTextField txtType;
    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtFName;

    @FXML
    private JFXTextField txtMName;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXTextField txtBCNo;

    @FXML
    private JFXTextField txtBNo;

    @FXML
    private JFXDatePicker jdc_bod;

    @FXML
    private JFXButton btnUpdate;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtName.clear();
        txtFName.clear();
        txtMName.clear();
        txtBNo.clear();
        txtBCNo.clear();
        txtSearch.clear();
        btnSave.setDisable(false);
        txtBCNo.setEditable(true);
        txtBCNo.requestFocus();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        tblDeathSearch.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("docNo"));
        tblDeathSearch.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name1"));
        tblDeathSearch.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("name2"));
        tblDeathSearch.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("name3"));


        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                tblDeathSearch.getItems().clear();
                try {

                    Connection connection = db.getInstance().getConnection();
                    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM document WHERE docNo LIKE ? OR name1 LIKE ? OR name2 LIKE ? OR name3 LIKE ? OR place LIKE ? OR bNo LIKE ? OR dDate LIKE ?");
                    pstm.setString(1, "%" + txtSearch.getText() + "%");
                    pstm.setString(2, "%" + txtSearch.getText() + "%");
                    pstm.setString(3, "%" + txtSearch.getText() + "%");
                    pstm.setString(4, "%" + txtSearch.getText() + "%");
                    pstm.setString(5, "%" + txtSearch.getText() + "%");
                    pstm.setString(6, "%" + txtSearch.getText() + "%");
                    pstm.setString(7, "%" + txtSearch.getText() + "%");


                    ResultSet rst = pstm.executeQuery();

                    ObservableList<DCTM> items = tblDeathSearch.getItems();


                    while (rst.next()) {
                        items.add(new DCTM(rst.getString(1),
                                rst.getString(3),
                                rst.getString(4),
                                rst.getString(5)));
                    }

                    if (txtSearch.getText().length()==0){
                        tblDeathSearch.getItems().clear();
                    }





                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this record?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {


            Connection connection = db.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM document WHERE docNo=?");
            pstm.setString(1, txtBCNo.getText());
            if (pstm.executeUpdate() == 0) {
                throw new RuntimeException("Something went wrong");
            }
            txtName.clear();
            txtFName.clear();
            txtMName.clear();
            txtBNo.clear();
            txtBCNo.clear();
            txtBCNo.requestFocus();
        }
    }

    @FXML
    void btnSave_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
      doSave();
    }

    @FXML
    void btnUpdate_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Connection connection = db.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE document SET dDate=?, name1=?, name2=?,name3=?,place=? WHERE docNo=?");
        pstm.setString(1,jdc_bod.getValue().toString());
        pstm.setString(2, txtName.getText());
        pstm.setString(3, txtFName.getText());
        pstm.setString(4, txtMName.getText());
        pstm.setString(5, txtBNo.getText());
        pstm.setString(6,txtBCNo.getText());

        if (pstm.executeUpdate() == 0){
            throw new RuntimeException("Something went wrong");
        }
        txtName.clear();
        txtFName.clear();
        txtMName.clear();
        txtBNo.clear();
        txtBCNo.clear();
        txtBCNo.requestFocus();
    }

    @FXML
    void navigateToHome(MouseEvent event) throws IOException {
        //URL resource = this.getClass().getResource("/view/NavForm.fxml");
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("/view/NavForm.fxml"));
        Parent root = fxmlLoader.load();


        NavFormController ctrl2 = fxmlLoader.getController();
        String type2 = lblType.getText().replace("Logged as : ","");
        ctrl2.lblType.setText(type2);

        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    @FXML
    void txtBCNo_onAction(ActionEvent event) {

    }

    @FXML
    void txtBCNo_onKeyPressed(KeyEvent event) {
        if (event.getCode()==KeyCode.ENTER){
            txtName.requestFocus();
        }
    }

    @FXML
    void txtBNo_onAction(ActionEvent event) {

    }

    @FXML
    void txtBNo_onKeyPressed(KeyEvent event) throws SQLException, ClassNotFoundException {
      if (event.getCode()==KeyCode.ENTER){
          doSave();
      }


    }

    private void doSave() throws SQLException, ClassNotFoundException {
        if (txtBCNo.getText().equals("")||txtBCNo.getText().length()==0){
            btnSave.setDisable(true);
        }else {
            btnSave.setDisable(false);
            Connection connection = db.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO document VALUES (?,?,?,?,?,?,?)");
            pstm.setString(1, txtBCNo.getText());
            pstm.setString(2, jdc_bod.getValue().toString());
            pstm.setString(3, txtName.getText());
            pstm.setString(4, txtFName.getText());
            pstm.setString(5, txtMName.getText());
            pstm.setString(6, txtBNo.getText());
            pstm.setString(7, "");

            System.out.println("good");
            if (pstm.executeUpdate() == 0) {
                throw new RuntimeException("Something went wrong");
            }
            txtName.clear();
            txtFName.clear();
            txtMName.clear();
            txtBNo.clear();
            txtBCNo.clear();
            txtBCNo.requestFocus();
        }
    }

    @FXML
    void txtFName_onAction(ActionEvent event) {

    }

    @FXML
    void txtFName_onKeyPressed(KeyEvent event) {
        if (event.getCode()==KeyCode.ENTER){
            txtMName.requestFocus();
        }
    }

    @FXML
    void txtMName_onAction(ActionEvent event) {

    }

    @FXML
    void txtMName_onKeyPressed(KeyEvent event) {
        if (event.getCode()==KeyCode.ENTER){
            txtBNo.requestFocus();
        }
    }

    @FXML
    void txtName_onAction(ActionEvent event) {

    }

    @FXML
    void txtName_onKeyPressed(KeyEvent event) {

    }

    public void txtSearch_onAction(ActionEvent actionEvent) {

    }

    public void txtSearch_keyPressed(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (keyEvent.getCode()== KeyCode.ENTER){
            Connection connection = db.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM document WHERE docNo=?");
            pstm.setString(1,txtSearch.getText());
            ResultSet rst = pstm.executeQuery();

            if (rst.next()){
                txtBCNo.setText(rst.getString("docNo"));
                txtName.setText(rst.getString("name1"));
                txtFName.setText(rst.getString("name2"));
                txtMName.setText(rst.getString("name3"));
                jdc_bod.setValue(LocalDate.parse(rst.getString("dDate")));
                txtBNo.setText(rst.getString("place"));
                txtSearch.clear();
                txtBCNo.setEditable(false);
                btnSave.setDisable(true);
            }else{
                new Alert(Alert.AlertType.ERROR, "No results").show();

            }
        }
    }

    public void btnNew_OnAction(ActionEvent actionEvent) {
        if (lblType.getText().equals("Logged as : admin")){

            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }else{
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }


        txtName.clear();
        txtFName.clear();
        txtMName.clear();
        txtBNo.clear();
        txtBCNo.clear();
        txtSearch.clear();
        btnSave.setDisable(false);
        txtBCNo.setEditable(true);
        txtBCNo.requestFocus();
    }



}
