package model;

/**
 * Abstract class to implement factory design pattern,
 * Represents a vendor prototype.
 *
 * @author Shiri Rave
 * @date 13/05/2025
 */

public abstract class AbstractVendor {

    private String vendorName;
    private String configPrefix;


    public AbstractVendor(String vendorName) throws RuntimeException{
        setVendorName(vendorName);
        this.configPrefix = buildConfigPrefix();
    }


    protected void setVendorName(String vendorName){
        this.vendorName = vendorName;
    }

    private String buildConfigPrefix() throws RuntimeException{
        if(getVendorName().isEmpty()){
            throw new RuntimeException("Illegal vendor name, aborting.");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.MAIL_PREFIX);
        sb.append(getVendorName());
        return sb.toString();
    }

    public String getConfigPrefix(){
        return this.configPrefix;
    }

    protected String getVendorName(){
        return this.vendorName;
    }


}
