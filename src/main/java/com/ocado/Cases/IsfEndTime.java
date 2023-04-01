package com.ocado.Cases;

import com.ocado.PickingApp;

import java.io.IOException;

public class IsfEndTime extends PickingApp {




    public static void main(String[] args) throws IOException {

        String storeFileName = "src/main/resources/test/isf-end-time/store.json";
        String ordersFileName = "src/main/resources/test/isf-end-time/orders.json";
        String output = "OUTPUT";
        storeFulfillment(storeFileName, ordersFileName, output);

    }

}
