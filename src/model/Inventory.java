package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.Locale;

/**
 * This class keeps inventory off all parts and product.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Add part to inventory
     * @param newPart  part to add
     */
    // Part methods
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /** Lookup part based on id. Has error if none found.
     * @param partId id to search
     * @return part that is found
     */
    public static Part lookupPart(int partId){ // gets part based on id number
        for(Part part : allParts){
            if(part.getId() == partId){
                return part;
            }
        }

        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle("Error Dialog");
        alertError.setContentText("Please enter valid values in text fields!");
        alertError.showAndWait();

        return null;
    }

    /** Lookup part based on part name. If no part found, sends an error.
     * @param partName name you are searching for
     * @return parts that fit search
     */
    public static ObservableList<Part> lookupPart(String partName){ // lookup parts with an enhanced for loop (can find partial names too)
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        for(Part part : allParts){
            if(part.getName().toLowerCase().contains(partName.toLowerCase())){
                filteredParts.add(part);
            }
        }
        if(filteredParts.isEmpty()){    // error if found nothing
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error Dialog");
            alertError.setContentText("Please enter valid values in text fields!");
            alertError.showAndWait();
        }

        return filteredParts;
    }

    /** Updates part
     * @param index index of the part you want to replace
     * @param selectedPart the part values you will replace with the part
     */
    public static void updatePart(int index, Part selectedPart){  // takes a selected part and replaces it on the allParts based on index value
        allParts.set(index, selectedPart);
        System.out.println("updated part");
    }

    /** Deletes part
     * @param selectedPart part you want to delete
     * @return if it deleted or not
     */
    public static boolean deletePart(Part selectedPart){ // deletes part based on when the object matches in the for loop
        for(Part part: allParts){
            if(part == selectedPart){
                allParts.remove(selectedPart);
                return true;
            }
        }
        return false;
    }

    /** Gets all parts
     * @return ObservableList of all parts
     */
    public static ObservableList<Part> getAllParts() { // gets all parts
        return allParts;
    }


    /** Adds product to product list.
     * @param newProduct  product you want to add
     */
    // Product methods
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /** Lookup product based on id.
     * @param productId id of product
     * @return product object
     */
    public static Product lookupProduct(int productId){ // gets product based on id number
        for(Product product : allProducts){
            if(product.getId() == productId){
                return product;
            }
        }

        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle("Error Dialog");
        alertError.setContentText("Please enter valid values in text fields!");
        alertError.showAndWait();

        return null;
    }

    /** Lookup part based on Product name
     * @param productName  name you want to find
     * @return product object
     */
    public static ObservableList<Product> lookupProduct(String productName){ // lookup products with an enhanced for loop (can find productial names too)
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        for(Product product : allProducts){
            if(product.getName().toLowerCase().contains(productName.toLowerCase())){
                filteredProducts.add(product);
            }
        }
        if(filteredProducts.isEmpty()){    // error if found nothing
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error Dialog");
            alertError.setContentText("Please enter valid values in text fields!");
            alertError.showAndWait();
        }

        return filteredProducts;
    }

    /** Updating part
     * @param index index of product you want to update
     * @param selectedProduct the product that will update exisint product
     */
    public static void updateProduct(int index, Product selectedProduct){  // takes a selected product and replaces it on the allProducts based on index value
        allProducts.set(index, selectedProduct);
        System.out.println("updated product");
    }

    /** Deletes selected product
     * @param selectedProduct  product you want to delete
     * @return whether deleted or not
     */
    public static boolean deleteProduct(Product selectedProduct){ // deletes product based on when the object matches in the for loop
        for(Product product: allProducts){
            if(product == selectedProduct){
                allProducts.remove(selectedProduct);
                return true;
            }
        }
        return false;
    }
    public static ObservableList<Product> getAllProducts() { // gets all products
        return allProducts;
    }
}
