package com.ocado.extendedCases1;


import com.ocado.PickingApp;

import java.io.IOException;

public class AnyOrderLengthIsOk extends PickingApp {

    public static void main(String[] args) throws IOException {

        String storeFileName = "src/main/resources/test/any-order-length-is-ok/store.json";
        String ordersFileName = "src/main/resources/test/any-order-length-is-ok/orders.json";
        String output = "src/main/resources/test/any-order-length-is-ok/myOutput.txt";
        storeFulfillment(storeFileName, ordersFileName, output);

    }
}
