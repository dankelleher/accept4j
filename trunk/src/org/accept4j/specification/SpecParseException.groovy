package org.accept4j.specification

/**
 * Copyright: Daniel Kelleher Date: 04.10.12 Time: 23:18
 */
class SpecParseException extends Exception {
    SpecParseException(Exception cause) {
        super(cause)
    }
    
    SpecParseException(String message) {
        super(message)
    }

    SpecParseException() {
    }
}
