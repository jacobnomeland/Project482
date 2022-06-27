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
import model.*;

/**
 * This class is used to modify a selected part through a form and update it in inventory.
 */
public class ModifyPartForm implements Initializable {
    private Staging staging = new Staging();

    // Retrieving the part information from the main form
    private static Part part = null;        // setting up a blank part
    public static void getModifyPartForm(Part p){  // receiving get values for "p" and filling the values to "part"
        part = p;
    }

    @FXML
    private TextField IDTxt;

    @FXML
    private RadioButton inHouseRadioButton;

    @FXML
    private TextField invTxt;

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

    @FXML
    private Label machineOrCompanyNameLbl;

    /** On action, sets machineOrCompanyNameLbl to "machine ID".
     * @param event unused
     */
    @FXML
    void onActionInHouseRadioButton(ActionEvent event) { // populates label when radio button is pressed
        machineOrCompanyNameLbl.setText("Machine ID");
    }

    /** On action, sets machineOrCompanyNameLbl to "Company Name".
     * @param event unused
     */
    @FXML
    void onActionOutsourcedRadioButton(ActionEvent event) { // populates label when radio button is pressed
        machineOrCompanyNameLbl.setText("Company Name");
    }

    /** On action, cancels form. Gives a confirmation alert as well.
     * @param event used to change scene
     * @throws IOException used to change scene
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException { // takes you to main menu
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will clear all text field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            staging.stagingMethod(event, "/view/MainForm.fxml");
        }
    }

//    /** On action, updates part that was chosen to be modified and sends you to main menu.
//     * @param event used to change scene
//     * @throws IOException used to change scene
//     */
//    @FXML
//    void onActionSave(ActionEvent event) throws IOException { // populating a new object and replacing it with an existing one
//        if(Integer.parseInt(this.minTxt.getText()) >= Integer.parseInt(this.maxTxt.getText())){
//            Alert alertError = new Alert(Alert.AlertType.ERROR);
//            alertError.setTitle("Error Dialog");
//            alertError.setContentText("Your min must be lower than your max.");
//            alertError.showAndWait();
//            return;
//        }
//        if(Integer.parseInt(this.invTxt.getText()) < Integer.parseInt(this.minTxt.getText()) || Integer.parseInt(this.minTxt.getText()) > Integer.parseInt(this.maxTxt.getText())){
//            Alert alertError = new Alert(Alert.AlertType.ERROR);
//            alertError.setTitle("Error Dialog");
//            alertError.setContentText("Your inventory must be between the min and max.");
//            alertError.showAndWait();
//            return;
//        }
//
//        try {
//            // setting all the new values to the "part"
//            part.setId(Integer.parseInt(this.IDTxt.getText()));
//            part.setName(this.nameTxt.getText());
//            part.setPrice(Double.parseDouble(this.priceTxt.getText()));
//            part.setStock(Integer.parseInt(this.invTxt.getText()));
//            part.setMin(Integer.parseInt(this.minTxt.getText()));
//            part.setMax(Integer.parseInt(this.maxTxt.getText()));
//
//            if(inHouseRadioButton.isSelected()){
//                ((InHouse)part).setMachineId(Integer.parseInt(this.machineOrCompanyNameTxt.getText()));
//            }else if(outsourcedRadioButton.isSelected()){
//                ((Outsourced)part).setCompanyName( this.machineOrCompanyNameTxt.getText());
//            }else{
//                System.out.println("something went wrong");
//            }
//
//        }catch (NumberFormatException e){
//            Alert alertError = new Alert(Alert.AlertType.ERROR);
//            alertError.setTitle("Error Dialog");
//            alertError.setContentText("Please enter valid values in text fields!");
//            alertError.showAndWait();
//            return;
//        }
//
//        Inventory.updatePart(part.getId()-1,part);
//        staging.stagingMethod(event, "/view/MainForm.fxml");
//    }


    /** On action, updates part that was chosen to be modified and sends you to main menu. There is a
     * NumberFormatException error in this code. This is caused by typing a string in an int field. In the future
     * instead of typing a machine number or a company name, you could choose from a dropdown of existing machines
     * and/or company names. Or you could do an HTML equivalent of when you place a text-field input value to number only.
     * @param event used to change scene
     * @throws IOException used to change scene
     *
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException { // populating a new object and replacing it with an existing one
        if(Integer.parseInt(this.minTxt.getText()) >= Integer.parseInt(this.maxTxt.getText())){
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error Dialog");
            alertError.setContentText("Your min must be lower than your max.");
            alertError.showAndWait();
            return;
        }
        if(Integer.parseInt(this.invTxt.getText()) < Integer.parseInt(this.minTxt.getText()) || Integer.parseInt(this.minTxt.getText()) > Integer.parseInt(this.maxTxt.getText())){
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error Dialog");
            alertError.setContentText("Your inventory must be between the min and max.");
            alertError.showAndWait();
            return;
        }

        try {
            if(inHouseRadioButton.isSelected()){
                part = new InHouse(part.getId(),
                        this.nameTxt.getText(),
                        Double.parseDouble(this.priceTxt.getText()),
                        Integer.parseInt(this.invTxt.getText()),
                        Integer.parseInt(this.minTxt.getText()),
                        Integer.parseInt(this.maxTxt.getText()),
                        Integer.parseInt(this.machineOrCompanyNameTxt.getText())
                );
            }else if(outsourcedRadioButton.isSelected()){
                part = new Outsourced(part.getId(),
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

        }catch (NumberFormatException e){
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error Dialog");
            alertError.setContentText("Please enter valid values in text fields!\n" + e);
            alertError.showAndWait();
            return;
        }

        Inventory.updatePart(part.getId()-1,part);
        staging.stagingMethod(event, "/view/MainForm.fxml");
    }

    /** First to run in the program.
     * @param url unused
     * @param resourceBundle unused
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // runs on open
        System.out.println("Modify part opened");
        if(part instanceof InHouse){
            inHouseRadioButton.setSelected(true);
            machineOrCompanyNameLbl.setText("Machine ID");
            machineOrCompanyNameTxt.setText(Integer.toString( ((InHouse)part).getMachineId()));
        }else if(part instanceof Outsourced){
            outsourcedRadioButton.setSelected(true);
            machineOrCompanyNameLbl.setText("Company Name");
            machineOrCompanyNameTxt.setText(((Outsourced)part).getCompanyName());
        }else{
            System.out.println("something went wrong");
        }

        this.IDTxt.setText(Integer.toString(part.getId()));
        this.invTxt.setText(Integer.toString(part.getStock()));
        this.maxTxt.setText(Integer.toString(part.getMax()));
        this.minTxt.setText(Integer.toString(part.getMin()));
        this.priceTxt.setText(Double.toString(part.getPrice()));
        this.nameTxt.setText((part.getName()));

    }
}
