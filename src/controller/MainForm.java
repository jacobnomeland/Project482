package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import main.Staging;

/**
 * This class is a form used to decide how you want to alter your parts and product.
 */
public class MainForm implements Initializable {
    private Staging staging = new Staging();

    // Parts
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Double> partPricePerUnitCol;
    @FXML
    private TableView<Part> partTableView;

    // Products
    @FXML
    private TableColumn<Product, Integer> productIDCol;
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Double> productPricePerUnitCol;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TextField searchPartTxt;
    @FXML
    private TextField searchProductTxt;

    /** Search part through id, if not exist, then name. There is a NumberFormat exception used here. It is used to
     * search for part based on id first. If there is a NumberFormatException (ex: a string entered), it will then
     * look for part based on part name. In the future you could have 2 different searches, or you could expand search by searching by price.
     * @param event unused
     */
    // Part table things //////////////////////////////////////////////////////////
    @FXML
    void onActionSearchPart(ActionEvent event) { // searching part by either id or name/partial name (searches id first)
        try{ // try looking up parts through id first
            partTableView.setItems(FXCollections.observableArrayList(Inventory.lookupPart(Integer.parseInt(searchPartTxt.getText()))));
        }catch (NumberFormatException e){ // if there is a number NumberFormatException, then we will search through part name
            partTableView.setItems(Inventory.lookupPart(searchPartTxt.getText()));
        }
    }

    /** On action, sends you to AddPartForm.fxml
     * @param event used to change scene
     * @throws IOException used to change scene
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException { // sends you to AddPartForm.fxml
        staging.stagingMethod(event, "/view/AddPartForm.fxml");
    }

    /** On action, delete part from inventory.
     * @param event  unused
     */
    @FXML
    void onActionDeletePart(ActionEvent event) { // takes, selected row, and deletes from inventory
        // creating a confirmation confirm dialog box
        Part part = (Part)partTableView.getSelectionModel().getSelectedItem(); // highlighted row on the table
        if(part != null){ // if nothing is selected, it wont delete a part
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete selected part?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Inventory.deletePart(part);
            }
        }
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle("Error Dialog");
        alertError.setContentText("Please select a part to delete!");
        alertError.showAndWait();
    }

    /** On action, sends selected part to ModifyPartForm.fxml.
     * @param event used to change scene
     * @throws IOException used to change scene
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException { // switch to ModifyPartForm and brings selected row data with it
        Part part = (Part)partTableView.getSelectionModel().getSelectedItem();
        if(part != null){ // return if nothing is selected (he did it in webinar but i notice no difference whether its there or not)
            ModifyPartForm.getModifyPartForm(part);
            staging.stagingMethod(event, "/view/ModifyPartForm.fxml");;
        }
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle("Error Dialog");
        alertError.setContentText("Please select a part to modify!");
        alertError.showAndWait();
    }

    /** On action, search product through id first, then product name. There is a NumberFormat exception used here. It is used to
     * search for product based on id first. If there is a NumberFormatException (ex: a string entered), it will then
     * look for product based on part name. In the future you could have 2 different searches, or you could expand search by searching by price.
     * @param event  unused
     */
    // Product table things /////////////////////////////////////////////////////////////
    @FXML
    void onActionSearchProduct(ActionEvent event) { // searching product by either id or name/partial name (searches id first)
        try{ // try looking up products through id first
            productTableView.setItems(FXCollections.observableArrayList(Inventory.lookupProduct(Integer.parseInt(searchProductTxt.getText()))));
        }catch (NumberFormatException e){ // if there is a number NumberFormatException, then we will search through product name
            productTableView.setItems(Inventory.lookupProduct(searchProductTxt.getText()));
        }
    }

    /** On action, opens AddProductForm.fxml
     * @param event used to change scene
     * @throws IOException used to change scene
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException { // sends you to AddProductForm.fxml
        staging.stagingMethod(event, "/view/AddProductForm.fxml");
    }

    /** On action, deletes product from inventory.
     * @param event unused
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) { // takes, selected row, and deletes from inventory
        Product product = (Product)productTableView.getSelectionModel().getSelectedItem(); // highlighted row on the table

        if(product != null){
            // creating a confirmation confirm dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete selected product?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                if(productTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()){
                    Inventory.deleteProduct(product);
                    if(product == null){ // return if nothing is selected (he did it in webinar but i notice no difference whether its there or not)
                        return;
                    }
                }else{
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.setTitle("Error Dialog");
                    alertError.setContentText("You cannot delete a product that has associated parts.");
                    alertError.showAndWait();
                }
            }
        }
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle("Error Dialog");
        alertError.setContentText("Please select a product to delete!");
        alertError.showAndWait();
    }

    /** On action, opens ModifyProductForm and brings the selected product info with it.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException { // switch to ModifyProductForm and brings selected row data with it
        Product product = (Product)productTableView.getSelectionModel().getSelectedItem();

        if(product != null){
            ModifyProductForm.getModifyProductForm(product);
            staging.stagingMethod(event, "/view/ModifyProductForm.fxml");
        }

        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle("Error Dialog");
        alertError.setContentText("Please select a product to modify!");
        alertError.showAndWait();
    }

    /** Exits program.
     * @param event unused
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /** First thing to run in program.
     * @param url unused
     * @param resourceBundle unused
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // runs on open
        System.out.println("Main form opened");

        // populating observable list table
        partTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());

        // These have to match exactly with the part class's "getters" ex: getId = id or getName = name
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // These have to match exactly with the product class's "getters" ex: getId = id or getName = name
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
