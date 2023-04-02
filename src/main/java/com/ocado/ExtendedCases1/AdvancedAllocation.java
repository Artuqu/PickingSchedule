package com.ocado.ExtendedCases1;

import com.ocado.PickingApp;

import java.io.IOException;

public class AdvancedAllocation extends PickingApp {

    public static void main(String[] args) throws IOException {

        String storeFileName = "src/main/resources/test/advanced-allocation/store.json";
        String ordersFileName = "src/main/resources/test/advanced-allocation/orders.json";
        String output = "src/main/resources/test/advanced-allocation/myOutput.txt";
        storeFulfillment(storeFileName, ordersFileName, output);

    }
}
