package bl;

import model.*;
/**
 * Class to implement Factory design pattern - to return the appropriate Vendor upon a given email address suffix
 */
public class VendorFactory {

        // todo: change to 'Vendor' maybe?
        public static AbstractVendor createVendor(String emailAddress) throws RuntimeException{
            int suffixPosition = emailAddress.indexOf("@");
            String suffix = emailAddress.substring(suffixPosition,emailAddress.length());
            switch (suffix.toLowerCase()) {
                case "gmail.com":
                    return new GmailVendor(Constants.GMAIL_VENDOR_NAME);
   //                  todo: and expand this..
   //             case "b":
   //                 return new ProductB();

                default:
                    throw new IllegalArgumentException("Unknown email vendor: " + suffix);
            }
        }
    }




