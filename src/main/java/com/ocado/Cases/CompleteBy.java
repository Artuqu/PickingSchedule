package com.ocado.Cases;


import com.ocado.PickingApp;

import java.io.IOException;

public class CompleteBy extends PickingApp {

    public static void main(String[] args) throws IOException {

        String storeFileName = "src/main/resources/test/complete-by/store.json";
        String ordersFileName = "src/main/resources/test/complete-by/orders.json";
        String output = "OUTPUT";
        storeFulfillment(storeFileName, ordersFileName, output);

    }
}
