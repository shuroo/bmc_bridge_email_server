package model;

/**
 * Class to represent the Gmail Vendor.
 *
 * @author Shiri Rave
 * @date 13/05/2025
 */

public class GmailVendor extends AbstractVendor{

    String vendorName = Constants.GMAIL_VENDOR_NAME; //'gmail';

    @Override
    public String getVendorName(){
        System.out.println("this.vendorName:"+this.vendorName);
        return this.vendorName;
    }

    @Override
    protected void setVendorName(String vendorName){
        this.vendorName = vendorName;
    }
    public GmailVendor(String vendorName) throws RuntimeException{
        super(vendorName);
        setVendorName(vendorName);
    }
}
