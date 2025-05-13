package model;

/**
 * Class to represent the Walla Vendor.
 *
 * @author Shiri Rave
 * @date 13/05/2025
 */
public class WallaVendor extends AbstractVendor{

    String vendorName = Constants.WALLA_VENDOR_NAME; //'walla';

    @Override
    public void setVendorName(String vendorName){
        this.vendorName = vendorName;
    }
    @Override
    public String getVendorName(){
        System.out.println("this.vendorName:"+this.vendorName);
        return this.vendorName;
    }

    public WallaVendor(String vendorName) throws RuntimeException{
        super(vendorName);
        setVendorName(vendorName);
    }
}
