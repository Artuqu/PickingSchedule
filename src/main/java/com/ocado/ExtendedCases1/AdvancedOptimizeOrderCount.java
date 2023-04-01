package com.ocado.ExtendedCases1;


import com.ocado.PickingApp;

import java.io.IOException;

public class AdvancedOptimizeOrderCount extends PickingApp {

    public static void main(String[] args) throws IOException {

        String storeFileName = "src/main/resources/test/advanced-optimize-order-count/store.json";
        String ordersFileName = "src/main/resources/test/advanced-optimize-order-count/orders.json";
        String output = "OUTPUT";
        storeFulfillment(storeFileName, ordersFileName, output);

    }
}
