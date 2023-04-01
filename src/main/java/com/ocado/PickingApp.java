package com.ocado;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ocado.oders.Orders;
import com.ocado.store.Store;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
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





        System.out.println(store.getPickers());
        System.out.println(orders.get(0));


    }
}
