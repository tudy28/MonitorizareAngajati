package controller;

import bugtracing.service.IService;
import bugtracing.service.Observer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Bug;
import model.Solutie;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class VerificatorController extends UnicastRemoteObject implements Observer, Serializable {

    public VerificatorController() throws RemoteException {
    }

    ObservableList<Bug> modelBuguri = FXCollections.observableArrayList();

    ObservableList<Solutie> modelSolutii = FXCollections.observableArrayList();

    IService service;
    Long userID;
    Stage stage;

    @FXML
    private TextField textFieldDenumireBug;

    @FXML
    private TextArea textAreaDescriereBug;

    @FXML
    private TextArea textAreaSolution;

    //-----------------------TABELA BUGURI--------------------------------------------
    @FXML
    private TableView<Bug> tableViewBuguri;

    @FXML
    private TableColumn<Bug, String> tableBuguriColumnNumeBug;

    @FXML
    private TableColumn<Bug, String> tableBuguriColumnDescriereBug;

    //-----------------------------------------------------------------------------------

    //-----------------------TABELA SOLUTII BUGURI--------------------------------------------
    @FXML
    private TableView<Solutie> tableViewSolutii;

    @FXML
    private TableColumn<Solutie, String> tableSolutiiColumnNumeBug;

    @FXML
    private TableColumn<Solutie, String> tableSolutiiColumnDescriereBug;

    //-----------------------------------------------------------------------------------



    void initTables() throws Exception {
        tableBuguriColumnNumeBug.setCellValueFactory(new PropertyValueFactory<Bug, String>("numeBug"));
        tableBuguriColumnDescriereBug.setCellValueFactory(new PropertyValueFactory<Bug, String>("descriereBug"));
        modelBuguri.setAll(StreamSupport.stream(service.findAllBuguriNerezolvate().spliterator(), false).collect(Collectors.toList()));
        tableViewBuguri.setItems(modelBuguri);

        tableSolutiiColumnNumeBug.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBugRezolvat().getNumeBug()));
        tableSolutiiColumnDescriereBug.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBugRezolvat().getDescriereBug()));
        modelSolutii.setAll(StreamSupport.stream(service.findAllSolutii().spliterator(), false).collect(Collectors.toList()));
        tableViewSolutii.setItems(modelSolutii);

    }


    public void setService(IService service, Long id) throws Exception {
        this.service = service;
        this.userID = id;
        initTables();
        textAreaSolution.setEditable(false);


    }

    public void setStage(Stage currentStage) {
        this.stage = currentStage;
    }

    public void handleAdaugaBug(ActionEvent actionEvent) throws Exception {
        String denumireBug = textFieldDenumireBug.getText();
        String descriereBug = textAreaDescriereBug.getText();

        if ( !denumireBug.equals("") && !descriereBug.equals("")) {
            Bug bug = new Bug(denumireBug, descriereBug);
            service.adaugaBug(bug);
            textAreaDescriereBug.clear();
            textFieldDenumireBug.clear();
            MessageAlert.showMessage(stage, Alert.AlertType.CONFIRMATION,"Succes","Bugul a fost adaugat cu succes!");
        } else {
            MessageAlert.showErrorMessage(stage, "Denumirea/Descrierea bugului nu poate fi goala!");
        }

    }

    public void handleFillSolution(MouseEvent mouseEvent) throws Exception{
        String solution = tableViewSolutii.getSelectionModel().getSelectedItem().getRezolvare();
        textAreaSolution.setText(solution);
    }

    public void handleLogOut(ActionEvent actionEvent) throws Exception{
        Stage currentStage=(Stage)textAreaSolution.getScene().getWindow();//workaround pentru obitnerea scenei curente :P
        service.logout(userID);
        currentStage.close();
        stage.show();
    }
}
