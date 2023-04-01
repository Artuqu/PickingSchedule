package com.ocado.BasicCases1;

import com.ocado.PickingApp;

import java.io.IOException;

public class OptimizeOrderCount extends PickingApp {

    public static void main(String[] args) throws IOException {
        String storeFileName = "src/main/resources/test/optimize-order-count/store.json";
        String ordersFileName = "src/main/resources/test/optimize-order-count/orders.json";
        String output = "OUTPUT";
        storeFulfillment(storeFileName, ordersFileName, output);
    }
}
