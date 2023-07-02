package model;
/**
 * class to define Outsourced parts
 */
public class Outsourced extends Part{

    private String companyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Company Name getter
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Company Name setter
     * @param companyName - sets the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
