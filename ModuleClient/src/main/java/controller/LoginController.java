package controller;

import bugtracing.service.IService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Programator;
import model.Verificator;

import java.io.IOException;

public class LoginController {

    IService service;

    public void setService(IService service) {
        this.service=service;

    }
    @FXML
    private Button buttonLogInProgrammer;


    @FXML
    private Button buttonLogInTester;

    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField textFieldPassword;


    @FXML
    void handleLogInProgrammer(ActionEvent event) throws Exception {

        String username=textFieldUsername.getText();
        String password=textFieldPassword.getText();
        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/Programator.fxml"));
        Stage userStage=new Stage();
        AnchorPane root = loader.load();
        ProgramatorController ctrl = loader.getController();
        Programator programator=service.loginProgramator(username, password,ctrl);
        if (service.loginProgramator(username, password,ctrl) != null)
        {
            try {
                //setup
                Scene scene = new Scene(root);
                userStage.setScene(scene);
                userStage.setTitle("Programator");
                userStage.show();
                Stage currentStage=(Stage)buttonLogInProgrammer.getScene().getWindow();//workaround pentru obitnerea scenei curente :P
                currentStage.close();
                ctrl.setStage(currentStage);
                ctrl.setService(service, programator.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            MessageAlert.showErrorMessage(null,"Invalid credentials");
        }
    }

    public void handleLogInTester(ActionEvent actionEvent) throws Exception {
        String username=textFieldUsername.getText();
        String password=textFieldPassword.getText();
        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/Verificator.fxml"));
        Stage userStage=new Stage();
        AnchorPane root = loader.load();
        VerificatorController ctrl = loader.getController();
        Verificator verificator=service.loginVerificator(username, password,ctrl);
        if (service.loginVerificator(username, password,ctrl) != null)
        {
            try {
                //setup
                Scene scene = new Scene(root);
                userStage.setScene(scene);
                userStage.setTitle("Verificator");
                userStage.show();
                Stage currentStage=(Stage)buttonLogInProgrammer.getScene().getWindow();//workaround pentru obitnerea scenei curente :P
                currentStage.close();
                ctrl.setStage(currentStage);
                ctrl.setService(service, verificator.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            MessageAlert.showErrorMessage(null,"Invalid credentials");
        }
    }
}
