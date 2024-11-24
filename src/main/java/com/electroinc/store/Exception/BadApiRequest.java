package com.electroinc.store.Exception;

public class BadApiRequest extends RuntimeException {
    public BadApiRequest(String meassage) {
        super(meassage);
    }

    public BadApiRequest() {
        super("Bad request ");
    }
}
