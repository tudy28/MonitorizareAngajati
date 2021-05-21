package controller;

import bugtracing.service.IService;
import bugtracing.service.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Bug;
import model.Solutie;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProgramatorController extends UnicastRemoteObject implements Observer, Serializable {

    public ProgramatorController() throws RemoteException {
    }

    ObservableList<Bug> modelBuguri = FXCollections.observableArrayList();


    IService service;
    Long userID;
    Stage stage;

    @FXML
    private TextArea textAreaSolutie;

    //-----------------------TABELA BUGURI--------------------------------------------
    @FXML
    private TableView<Bug> tableViewBuguri;

    @FXML
    private TableColumn<Bug, String> tableBuguriColumnNumeBug;

    @FXML
    private TableColumn<Bug, String> tableBuguriColumnDescriereBug;

    //-----------------------------------------------------------------------------------



    void initTables() throws Exception {
        tableBuguriColumnNumeBug.setCellValueFactory(new PropertyValueFactory<Bug, String>("numeBug"));
        tableBuguriColumnDescriereBug.setCellValueFactory(new PropertyValueFactory<Bug, String>("descriereBug"));
        modelBuguri.setAll(StreamSupport.stream(service.findAllBuguriNerezolvate().spliterator(), false).collect(Collectors.toList()));
        tableViewBuguri.setItems(modelBuguri);


    }


    public void setService(IService service, Long id) throws Exception {
        this.service = service;
        this.userID = id;
        initTables();

    }

    public void setStage(Stage currentStage) {
        this.stage = currentStage;

    }

    public void handleRezolvareBug(ActionEvent actionEvent) throws Exception {
        String rezolvareBug = textAreaSolutie.getText();
        Bug resolvedBug = tableViewBuguri.getSelectionModel().getSelectedItem();
        Solutie solutie= new Solutie(rezolvareBug,resolvedBug);
        resolvedBug.setStareBug("rezolvat");
        service.updateBug(resolvedBug,resolvedBug.getId());
        service.adaugaSolutie(solutie);
        MessageAlert.showMessage(stage, Alert.AlertType.INFORMATION,"Succes","Solutia ta a fost trimita la verificatori!");
    }

    public void handleLogOut(ActionEvent actionEvent) throws Exception{
        Stage currentStage=(Stage)textAreaSolutie.getScene().getWindow();//workaround pentru obitnerea scenei curente :P
        service.logout(userID);
        currentStage.close();
        stage.show();
    }

}
