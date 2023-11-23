package org.golde.plu.aifinal.homedepot;

public class ProductLookupException extends Exception {

        public ProductLookupException(String message) {
            super(message);
        }

        public ProductLookupException(String message, Throwable cause) {
            super(message, cause);
        }

        public ProductLookupException(Throwable cause) {
            super(cause);
        }

        public ProductLookupException() {
            super();
        }
}
