package com.ocado.ExtendedCases2;

import com.ocado.PickingApp;

import java.io.IOException;

public class AdvancedOptimizeOrderValue extends PickingApp {

    public static void main(String[] args) throws IOException {

        String storeFileName = "src/main/resources/test/advanced-optimize-order-value/store.json";
        String ordersFileName = "src/main/resources/test/advanced-optimize-order-value/orders.json";
        String output = "OUTPUT";
        storeFulfillment(storeFileName, ordersFileName, output);

    }
}
