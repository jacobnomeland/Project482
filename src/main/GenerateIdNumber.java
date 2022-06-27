package main;

/**
 * This class generates a unique ID number.
 */
public class GenerateIdNumber {
    private static int partNumber = 0;
    private static int productNumber = 0;

    /** Generates a unique part number.
     * @return partNumber
     */
    public static int getNewPartNumber(){ // generates number for parts
        partNumber++;
        return partNumber;
    }

    /** Generates a unique product number.
     * @return productNumber
     */
    public static int getNewProductNumber(){ // generates number for products
        productNumber++;
        return productNumber;
    }

}
