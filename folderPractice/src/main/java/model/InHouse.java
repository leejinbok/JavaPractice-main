package model;

/**
 * class to define in-House parts
 */
public class InHouse extends Part{

    private int machineId;
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * machineId getter
     * @return machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId - sets machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
