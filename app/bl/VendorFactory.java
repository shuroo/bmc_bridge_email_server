package bl;

import model.*;
import exceptions.*;
/**
 * Class to implement Factory design pattern - to return the appropriate Vendor upon a given email address suffix
 */
public class VendorFactory {

//        public static AbstractVendor createVendor(String emailAddress) throws NoSuchVendorException{
//            int suffixPosition = emailAddress.indexOf("@");
//            String suffix = emailAddress.substring(suffixPosition+1,emailAddress.length()).toLowerCase();
//            System.out.println("Detected Email Suffix:"+suffix);
//            System.out.println("*******************");
//            if (!suffix.equals(Constants.GMAIL_SUFFIX) && !suffix.equals(Constants.WALLA_SUFFIX) && !suffix.equals(Constants.YAHOO_SUFFIX)){
//                System.out.println("**Invalid suffix detected..**");
//                throw new NoSuchVendorException("Unknown email vendor: " + suffix);
//
//            }
//            else if (suffix.equals(Constants.GMAIL_SUFFIX)) {
//                System.out.println("**GMAIL_SUFFIX**");
//                return new GmailVendor(Constants.GMAIL_VENDOR_NAME);
//            }
//            else if (suffix.equals(Constants.WALLA_SUFFIX)) {
//                System.out.println("**WALLA_SUFFIX**");
//                return new WallaVendor(Constants.WALLA_VENDOR_NAME);
//            }
//            else {// if (suffix.equals(Constants.YAHOO_SUFFIX)) {
//                System.out.println("**YAHOO_SUFFIX**");
//                return new YahooVendor(Constants.YAHOO_VENDOR_NAME);
//            }
//
//        }

    public static AbstractVendor createVendor(String emailAddress) throws NoSuchVendorException{
        int suffixPosition = emailAddress.indexOf("@");
        String suffix = emailAddress.substring(suffixPosition+1,emailAddress.length());
        switch (suffix.toLowerCase()) {
            case Constants.GMAIL_SUFFIX:
                System.out.println("**GMAIL_SUFFIX**");
                return new GmailVendor(Constants.GMAIL_VENDOR_NAME);
            case Constants.WALLA_SUFFIX:
                System.out.println("**WALLA_SUFFIX**");
                return new WallaVendor(Constants.WALLA_VENDOR_NAME);
            case Constants.YAHOO_SUFFIX:
                System.out.println("**YAHOO_SUFFIX**");
                return new YahooVendor(Constants.YAHOO_VENDOR_NAME);
            default:
                System.out.println("**Invalid suffix detected..**");
                throw new NoSuchVendorException("Unknown email vendor detected: " + suffix+", Aborting");
        }
    }

    }




