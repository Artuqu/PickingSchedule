package com.ocado.ExtendedCases2;

import com.ocado.PickingApp;

import java.io.IOException;

public class OptimizeOrderValue extends PickingApp {

    public static void main(String[] args) throws IOException {

        String storeFileName = "src/main/resources/test/optimize-order-value/store.json";
        String ordersFileName = "src/main/resources/test/optimize-order-value/orders.json";
        String output = "src/main/resources/test/optimize-order-value/myOutput.txt";
        storeFulfillment(storeFileName, ordersFileName, output);

    }

}
