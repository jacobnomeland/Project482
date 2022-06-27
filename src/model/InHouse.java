package model;

/**
 * Subclass of parts.
 */
public class InHouse extends Part{
    private int machineId;


    /** Constructor
     * @param id id of InHouse object.
     * @param name name of InHouse object.
     * @param price price of InHouse object.
     * @param stock stock of InHouse object.
     * @param min min stock of InHouse object.
     * @param max max stock of InHouse object.
     * @param machineId machineId of InHouse object.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) { // constructor
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId the machineId to set
     */
    // Setters
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     */
    // Getters
    public int getMachineId() {
        return machineId;
    }
}
