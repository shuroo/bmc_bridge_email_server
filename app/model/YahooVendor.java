package model;

/**
 * Class to represent the Yahoo Vendor.
 *
 * @author Shiri Rave
 * @date 13/05/2025
 */
public class YahooVendor extends AbstractVendor{

    String vendorName = Constants.YAHOO_VENDOR_NAME; //'yahoo';

    @Override
    public void setVendorName(String vendorName){
        this.vendorName = vendorName;
    }
    @Override
    public String getVendorName(){
        System.out.println("this.vendorName:"+this.vendorName);
        return this.vendorName;
    }

    public YahooVendor(String vendorName) throws RuntimeException{
        super(vendorName);
        setVendorName(vendorName);
    }
}
