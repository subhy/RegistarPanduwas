package Controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavFormController implements Initializable {
    public ImageView imgViewOrders;
    public ImageView imgOrder;
    public Label lblMenu;
    public Label lblDescription;
    public ImageView imgOrder1;
    public AnchorPane root;
    public ImageView imgLogout;
    public Label lblType;


    public void initialize(URL url, ResourceBundle rb){
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }
    public void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView){
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT =new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }

    public void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView){
            ImageView icon = (ImageView) event.getSource();

            switch(icon.getId()){

                case "imgOrder":
                    lblMenu.setText("Birth Certificate");
                    lblDescription.setText("Click here to save,edit,view,delete Birth certificate");
                    break;
                case "imgViewOrders":
                    lblMenu.setText("Marriage Certificate");
                    lblDescription.setText("Click here to save,edit,view,delete Marriage certificate");

                    break;
                case "imgOrder1":
                    lblMenu.setText("Death Certificate");
                    lblDescription.setText("Click here to save,edit,view,delete Death certificate");

                    break;

                case "imgLogout":
                    lblMenu.setText("Logout");
                    lblDescription.setText("Click here to logout");

                    break;
            }

            ScaleTransition scaleT =new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }



    public void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            Parent root = null;

            FXMLLoader fxmlLoader = null;

            switch (icon.getId()) {

                case "imgOrder":
                    fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/BirthForm.fxml"));
                    root = fxmlLoader.load();

                    BirthFormController ctrl = fxmlLoader.getController();
                    String type = lblType.getText();
                    ctrl.lblType.setText("Logged as : "+type);
                    break;
                case "imgViewOrders":
                    fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/MarryForm.fxml"));
                    root = fxmlLoader.load();

                    MarryFormController ctrl1 = fxmlLoader.getController();
                    String type1 = lblType.getText();
                    ctrl1.lblType.setText("Logged as : "+type1);
                    break;

                case "imgOrder1":
                    fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/DeadForm.fxml"));
                    root = fxmlLoader.load();

                    DeadFormController ctrl2 = fxmlLoader.getController();
                    String type2 = lblType.getText();
                    ctrl2.lblType.setText("Logged as : "+type2);

                    break;

                case "imgLogout":
                    fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/MainForm.fxml"));
                    root = fxmlLoader.load();

                    break;
            }

            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();

                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();

            }
        }
    }
}
