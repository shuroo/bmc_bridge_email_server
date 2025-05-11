package model;

public abstract class AbstractVendor {

    String vendorName;
    String configPrefix;


    protected AbstractVendor(String vendorName) throws RuntimeException{
        this.vendorName = vendorName;
        this.configPrefix = buildConfigPrefix();
    }

    private String buildConfigPrefix() throws RuntimeException{
        if(vendorName.isEmpty()){
            throw new RuntimeException("Illegal vendor name, aborting.");
        }
        return "mail."+vendorName;
    }

    public String getConfigPrefix(){
        return this.configPrefix;
    }

    public String getVendorName(){
        return this.vendorName;
    }


}
