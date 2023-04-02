package com.ocado.basicCases1;

import com.ocado.PickingApp;

import java.io.IOException;

public class IsfEndTime extends PickingApp {




    public static void main(String[] args) throws IOException {

        String storeFileName = "src/main/resources/test/isf-end-time/store.json";
        String ordersFileName = "src/main/resources/test/isf-end-time/orders.json";
        String output = "src/main/resources/test/isf-end-time/myOutput.txt";
        storeFulfillment(storeFileName, ordersFileName, output);

    }

}
