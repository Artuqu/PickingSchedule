package com.ocado.ExtendedCases1;


import com.ocado.PickingApp;

import java.io.IOException;

public class AnyOrderLengthIsOk extends PickingApp {

    public static void main(String[] args) throws IOException {

        String storeFileName = "src/main/resources/test/any-order-length-is-ok/store.json";
        String ordersFileName = "src/main/resources/test/any-order-length-is-ok/orders.json";
        String output = "OUTPUT";
        storeFulfillment(storeFileName, ordersFileName, output);

    }
}
