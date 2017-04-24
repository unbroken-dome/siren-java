package org.unbrokendome.siren.ap;

import java.io.IOException;


public final class RuntimeIOException extends RuntimeException {

    public RuntimeIOException(IOException cause) {
        super(cause);
    }


    @Override
    public synchronized IOException getCause() {
        return (IOException) super.getCause();
    }
}
