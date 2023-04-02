package com.ocado.basicCases1;


import com.ocado.PickingApp;

import java.io.IOException;

public class CompleteBy extends PickingApp {

    public static void main(String[] args) throws IOException {

        String storeFileName = "src/main/resources/test/complete-by/store.json";
        String ordersFileName = "src/main/resources/test/complete-by/orders.json";
        String output = "src/main/resources/test/complete-by/myOutput.txt";
        storeFulfillment(storeFileName, ordersFileName, output);

    }
}
