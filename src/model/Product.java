package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class holds all the values for what product should be
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Constructor
     * @param id id of product.
     * @param name name of product.
     * @param price price of product.
     * @param stock inventory of product.
     * @param min   min stock of product.
     * @param max   max stock of product.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {    // Constructor
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Setting the id
     * @param id the id to set
     */
    // Setters:
    public void setId(int id) {
        this.id = id;
    }

    /** Setting the name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Setting the price
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Setting the stock
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Setting the min
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Setting the max
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** Get id value
     * @return id
     */
    // Getters:
    public int getId() {
        return id;
    }

    /** Get name value
     * @return name value
     */
    public String getName() {
        return name;
    }

    /** get price value
     * @return price value
     */
    public double getPrice() {
        return price;
    }

    /** get stock value
     * @return stock value
     */
    public int getStock() {
        return stock;
    }

    /** get stock value
     * @return min value
     */
    public int getMin() {
        return min;
    }

    /**get max value
     * @return max value
     */
    public int getMax() {
        return max;
    }


    /** Add parts to the product.
     * @param part part you wish to add
     */
    public void addAssociatedPart(Part part){ // Adding part to the observable list
        associatedParts.add(part);
    }

    /** Delete part from product ObservableList
     * @param selectedAssociatedPart part you wish to delete
     * @return if it was deleted or not
     */
     public boolean deleteAssociatedPart(Part selectedAssociatedPart){  // Removing part to observable list
        for(Part part : getAllAssociatedParts()){
            if(selectedAssociatedPart == part){
                associatedParts.remove(selectedAssociatedPart);
                return true;
            }
        }
        return false;
     }

    /** Get all parts from product.
     * @return ObservableList of all parts
     */
    public ObservableList<Part> getAllAssociatedParts(){ // Getting all parts from observable list
        return associatedParts;
    }
}
