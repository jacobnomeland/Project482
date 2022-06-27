package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/** This class is the main class that starts the program */
public class Main extends Application {

    /**
     * The start method is the first thing to run in the Main class
     * @param stage sets the stage
     * @throws Exception is needed to load a scene
     */
    @Override
    public void start(Stage stage) throws Exception { // starting the program
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setTitle("Inventory Project");
        stage.setScene(new Scene(root, 1000, 500));
        stage.show();
    }

    /**
     * javadocs are located at Project482\javadoc. main method is used for launching the program. I was also used for initializing the parts and products.
     * @param args launches program
     */
    public static void main(String[] args) { // initialing stuff
        Part inHouse1 = new InHouse(GenerateIdNumber.getNewPartNumber(), "motherboard", 300, 100, 50, 300, 1);
        Part inHouse2 = new InHouse(GenerateIdNumber.getNewPartNumber(), "cpu", 500, 252, 100, 500, 3);
        Part inHouse3 = new InHouse(GenerateIdNumber.getNewPartNumber(), "gpu", 1000, 5, 1, 12, 5);
        Part inHouse4 = new InHouse(GenerateIdNumber.getNewPartNumber(), "ram", 20, 11, 2, 25, 7);
        Inventory.addPart(inHouse1);
        Inventory.addPart(inHouse2);
        Inventory.addPart(inHouse3);
        Inventory.addPart(inHouse4);

        Part outsourced1 = new Outsourced(GenerateIdNumber.getNewPartNumber(), "motherboard", 300, 3, 1, 5, "acer");
        Part outsourced2 = new Outsourced(GenerateIdNumber.getNewPartNumber(), "cpu", 500, 200, 100, 300, "dell");
        Part outsourced3 = new Outsourced(GenerateIdNumber.getNewPartNumber(), "gpu", 1000, 30, 25, 50, "aspire");
        Part outsourced4 = new Outsourced(GenerateIdNumber.getNewPartNumber(), "ram", 50, 99, 50, 100, "hp");
        Inventory.addPart(outsourced1);
        Inventory.addPart(outsourced2);
        Inventory.addPart(outsourced3);
        Inventory.addPart(outsourced4);

        Product product1 = new Product(GenerateIdNumber.getNewProductNumber(), "Dell desktop", 600, 2, 1, 5);
        product1.addAssociatedPart(inHouse1);
        product1.addAssociatedPart(inHouse2);
        product1.addAssociatedPart(outsourced3);
        product1.addAssociatedPart(outsourced4);
        Inventory.addProduct(product1);
        Product product2 = new Product(GenerateIdNumber.getNewProductNumber(), "Samsung desktop", 500, 2, 1, 4);
        product2.addAssociatedPart(inHouse4);
        product2.addAssociatedPart(inHouse3);
        product2.addAssociatedPart(outsourced2);
        product2.addAssociatedPart(outsourced1);
        Inventory.addProduct(product2);

        switch(-1){ // testing to see if all inventory part and methods work
            case 0: // adding part (has to be inHouse or outsourced because part is abstract)
                Inventory.addPart(new Outsourced(GenerateIdNumber.getNewPartNumber(), "power", 340, 20, 12, 50, "Microsoft"));
                break;
            case 1: // lookup part with id, returns object
                System.out.println(Inventory.lookupPart(1));
                System.out.println(Inventory.lookupPart(1).getName());
                break;
            case 2: // lookup part with string name (returns observable list)
                System.out.println(Inventory.lookupPart("cpu"));
                break;
            case 3: // shows deletion works (it will delete a part on table), returns boolean
                System.out.println(Inventory.deletePart(inHouse4));
                break;
            case 4: // shows updating a part
                break;
            case 5: // get all parts (returns observable list)
                System.out.println(Inventory.getAllParts());
                break;
            case 6: // deletes selected part
                System.out.println(product1.deleteAssociatedPart(inHouse1));
                break;
            default:
                System.out.println("No test for parts selected");
                break;
        }

        launch(args);
    }

}
