package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import main.GenerateIdNumber;
import main.Staging;
import model.Inventory;
import model.Part;
import model.Product;
/**
 * This class is used to modify a selected product through a form and update it in inventory.
 */
public class ModifyProductForm implements Initializable {
    private Staging staging = new Staging();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();


    @FXML
    private TextField IDTxt;

    @FXML
    private TableView<Part> addPartTableView;

    @FXML
    private TableColumn<Part, Integer> addPartIDCol;

    @FXML
    private TableColumn<Part, Integer> addPartInventoryLevelCol;

    @FXML
    private TableColumn<Part, String> addPartNameCol;

    @FXML
    private TableColumn<Part, Double> addPartPricePerUnitCol;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TableView<Part> removeAssociatedPartTableView;

    @FXML
    private TableColumn<Part, Integer> removePartIDCol;

    @FXML
    private TableColumn<Part, Integer> removePartInventoryLevelCol;

    @FXML
    private TableColumn<Part, String> removePartNameCol;

    @FXML
    private TableColumn<Part, Double> removePartPricePerUnitCol;

    @FXML
    private TextField searchPartTxt;

    /** On action, add part to product ObservableList
     * @param event unused
     */
    @FXML
    void onActionAddPart(ActionEvent event) {
        Part part = (Part)addPartTableView.getSelectionModel().getSelectedItem();
        filteredParts.add(part);
    }

    /** On action, cancel form. Has confirmation alert, sends you to MainForm.fxml if yes.
     * @param event  used to change scene
     * @throws IOException used to change scene
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

    /** On action, remove part from product ObservableList
     * @param event  unused
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to remove associated part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Part part = (Part)removeAssociatedPartTableView.getSelectionModel().getSelectedItem();
            product.deleteAssociatedPart(part);
            filteredParts.remove(part);
        }
    }

    /** On action, add product to inventory. Sends you back to MainForm.fxml. There is a
     * NumberFormatException error in this code. This is caused by typing a string in an int field. In the future
     * instead of typing a machine number or a company name, you could choose from a dropdown of existing machines
     * and/or company names. Or you could do an HTML equivalent of when you place a text-field input value to number only.
     * @param event used to change scene
     * @throws IOException used to change scene
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            if (Integer.parseInt(this.minTxt.getText()) >= Integer.parseInt(this.maxTxt.getText())) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Error Dialog");
                alertError.setContentText("Your min must be lower than your max.");
                alertError.showAndWait();
                return;
            }
            if (Integer.parseInt(this.inventoryTxt.getText()) < Integer.parseInt(this.minTxt.getText()) || Integer.parseInt(this.minTxt.getText()) > Integer.parseInt(this.maxTxt.getText())) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Error Dialog");
                alertError.setContentText("Your inventory must be between your min and max.");
                alertError.showAndWait();
                return;
            }

            product.setId(Integer.parseInt(this.IDTxt.getText()));
            product.setName(this.nameTxt.getText());
            product.setPrice(Double.parseDouble(this.priceTxt.getText()));
            product.setStock(Integer.parseInt(this.inventoryTxt.getText()));
            product.setMin(Integer.parseInt(this.minTxt.getText()));
            product.setMax(Integer.parseInt(this.maxTxt.getText()));

            Inventory.updateProduct(product.getId()-1, product );


            staging.stagingMethod(event, "/view/MainForm.fxml");
        }catch(NumberFormatException e){
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error Dialog");
            alertError.setContentText("Please enter valid values in text fields!\n" + e);
            alertError.showAndWait();
        }
    }

    /** On action, searches part by id, then search by name. There is a NumberFormat exception used here. It is used to
     * search for product based on id first. If there is a NumberFormatException (ex: a string entered), it will then
     * look for product based on part name. In the future you could have 2 different searches, or you could expand search by searching by price.
     * @param event
     */
    @FXML
    void onActionSearchPart(ActionEvent event) { // searching part by either id or name/partial name (searches id first)
        try{ // try looking up parts through id first
            addPartTableView.setItems(FXCollections.observableArrayList(Inventory.lookupPart(Integer.parseInt(searchPartTxt.getText()))));
        }catch (NumberFormatException e){ // if there is a number NumberFormatException, then we will search through part name
            addPartTableView.setItems(Inventory.lookupPart(searchPartTxt.getText()));
        }
    }

    // Retrieving the product information from the main form
    private static Product product = null;        // setting up a blank product
    public static void getModifyProductForm(Product p){  // receiving get values for "p" and filling the values to "product"
        product = p;
    }

    /** First method to run on open.
     * @param url unused
     * @param resourceBundle unused
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // runs on open
        System.out.println("add product form opened");

        // Populating tables
        addPartTableView.setItems(Inventory.getAllParts());

        // These have to match exactly with the part class's "getters" ex: getId = id or getName = name
        addPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        filteredParts = product.getAllAssociatedParts();
        removeAssociatedPartTableView.setItems(filteredParts);

        // These have to match exactly with the part class's "getters" ex: getId = id or getName = name
        removePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        removePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        removePartInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        removePartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        this.IDTxt.setText(Integer.toString(product.getId()));
        this.inventoryTxt.setText(Integer.toString(product.getStock()));
        this.maxTxt.setText(Integer.toString(product.getMax()));
        this.minTxt.setText(Integer.toString(product.getMin()));
        this.priceTxt.setText(Double.toString(product.getPrice()));
        this.nameTxt.setText((product.getName()));

    }
}
