package com.ocado.BasicCases1;

import com.ocado.PickingApp;

import java.io.IOException;

public class LogicBomb extends PickingApp {


    public static void main(String[] args) throws IOException {
        String storeFileName = "src/main/resources/test/logic-bomb/store.json";
        String ordersFileName = "src/main/resources/test/logic-bomb/orders.json";
        String output = "src/main/resources/test/logic-bomb/output.txt";
        storeFulfillment(storeFileName, ordersFileName, output);
    }
}
