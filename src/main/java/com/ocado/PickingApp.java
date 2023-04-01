package com.ocado;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ocado.oders.Orders;
import com.ocado.store.Picker;
import com.ocado.store.Store;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PickingApp {


    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
//        solve invalid definition for LocalTime
        objectMapper.registerModule(new JavaTimeModule());

        FileReader storeFile = new FileReader("src/main/resources/test/advanced-allocation/store.json");
        FileReader ordersFile = new FileReader("src/main/resources/test/advanced-allocation/orders.json");


        Store store = objectMapper.readValue(storeFile, Store.class);
        List<Orders> orders = new ArrayList<>(Arrays.asList(objectMapper.readValue(ordersFile, Orders[].class)));


        List<Picker> pickersList = new ArrayList<>();
        for (int i = 0; i < store.getPickers().size(); i++) {
            String pikerName = store.getPickers().get(i);
            String orderId = orders.get(i).getOrderId();
            LocalTime startPickingTime = store.getPickingStartTime();
            pickersList.set(i, new Picker(pikerName, orderId, startPickingTime));
        }




        System.out.println(store.getPickers());
        System.out.println(orders.get(0));


    }
}
