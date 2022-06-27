package model;

/**
 *  This class is a subclass of part that contains parts that were outsourced.s
 */
public class Outsourced extends Part{
    private String companyName;


    /** Constructor
     * @param id id of Outsourced object.
     * @param name name of Outsourced object.
     * @param price price of Outsourced object.
     * @param stock stock of Outsourced object.
     * @param min min stock of Outsourced object.
     * @param max max stock of Outsourced object.
     * @param companyName machineId of Outsourced object.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) { // constructor
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**Sets company name
     * @param companyName name you wish the company name to be
     */
    // setters
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** Get name for company
     * @return companyName
     */
    // getters
    public String getCompanyName() {
        return companyName;
    }

}
