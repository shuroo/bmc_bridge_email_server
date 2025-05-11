package model;

public class GmailVendor extends AbstractVendor{

    String vendorName = Constants.GMAIL_VENDOR_NAME; //'gmail';

//    @Inject
//    @Override
//    private GmailVendor(String vendorName,Configuration config){
//        super(vendorName, config);
//    }
//

    public GmailVendor(String vendorName) throws RuntimeException{
        super(vendorName);
    }
}
