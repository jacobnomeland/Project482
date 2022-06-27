/**
 *@author Jacob Nomeland
 */
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import main.GenerateIdNumber;
import main.Staging;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * This class is the form where you can add parts to our list
 */
public class AddPartForm implements Initializable {
    private Staging staging = new Staging();

    @FXML
    private TextField IDTxt;

    @FXML
    private RadioButton inHouseRadioButton;

    @FXML
    private TextField invTxt;

    @FXML
    private Label machineOrCompanyNameLbl;

    @FXML
    private TextField machineOrCompanyNameTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private TextField priceTxt;

    @FXML
    private ToggleGroup sourcingTG;

    /**
     * populates label when radio button is pressed
     * @param event  not used
     */
    @FXML
    void onActionInHouseRadioButton(ActionEvent event) {
        machineOrCompanyNameLbl.setText("Machine ID");
    }

    /**
     * @param event not used
     * populates label when radio button is pressed
     */
    @FXML
    void onActionOutsourcedRadioButton(ActionEvent event) {
        machineOrCompanyNameLbl.setText("Company Name");
    }


    /** On action, cancels the form.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException { // sends you back to main menu
        // creating a confirmation confirm dialog box on cancel
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will clear all text field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            staging.stagingMethod(event, "/view/MainForm.fxml");
        }
    }

    /** On action, saves the form. There is a NumberFormatException error in this code. This is caused by typing
     * a string in an int field. In the future instead of typing a machine number or a company name,
     * you could choose from a dropdown of existing machines and/or company names.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {   // creating a new part and adding it to the inventory list
        Part part = null;
        try{
            if(Integer.parseInt(this.minTxt.getText()) > Integer.parseInt(this.maxTxt.getText())){
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Error Dialog");
                alertError.setContentText("Your min must be lower than your max.");
                alertError.showAndWait();
                return;
            }
            if(Integer.parseInt(this.invTxt.getText()) < Integer.parseInt(this.minTxt.getText()) || Integer.parseInt(this.minTxt.getText()) > Integer.parseInt(this.maxTxt.getText())){
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Error Dialog");
                alertError.setContentText("Your inventory must be between your min and max.");
                alertError.showAndWait();
                return;
            }
            if(inHouseRadioButton.isSelected()){
                part = new InHouse(0,
                        this.nameTxt.getText(),
                        Double.parseDouble(this.priceTxt.getText()),
                        Integer.parseInt(this.invTxt.getText()),
                        Integer.parseInt(this.minTxt.getText()),
                        Integer.parseInt(this.maxTxt.getText()),
                        Integer.parseInt(this.machineOrCompanyNameTxt.getText())
                );
            }else if(outsourcedRadioButton.isSelected()){
                part = new Outsourced(0,
                        this.nameTxt.getText(),
                        Double.parseDouble(this.priceTxt.getText()),
                        Integer.parseInt(this.invTxt.getText()),
                        Integer.parseInt(this.minTxt.getText()),
                        Integer.parseInt(this.maxTxt.getText()),
                        this.machineOrCompanyNameTxt.getText()
                );
            }else{
                System.out.println("error occurred");
            }
            part.setId(GenerateIdNumber.getNewPartNumber());

            Inventory.addPart(part);
            staging.stagingMethod(event, "/view/MainForm.fxml");
        }catch(NumberFormatException e){
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error Dialog");
            alertError.setContentText("Please enter valid values in text fields!\n" + e);
            alertError.showAndWait();
        }
    }

    /** Initialize the class (runs first).
     * @param url unused
     * @param resourceBundle unused
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // runs on open
        System.out.println("Add part form opened");

    }
}
