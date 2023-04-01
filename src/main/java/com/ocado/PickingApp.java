package com.ocado;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ocado.oders.Orders;
import com.ocado.store.Picker;
import com.ocado.store.Store;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PickingApp {


    public static void main(String[] args) throws IOException {


        String storeFileName = "src/main/resources/test/advanced-allocation/store.json";
        String ordersFileName = "src/main/resources/test/advanced-allocation/orders.json";
        String output = "OUTPUT";
        storeFulfillment(storeFileName, ordersFileName, output);

    }

    public static void storeFulfillment(String storeFileName, String ordersFileName, String output) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
//        solve invalid definition for LocalTime
        BufferedWriter data = new BufferedWriter(new FileWriter(output));
        objectMapper.registerModule(new JavaTimeModule());

        FileReader storeFile = new FileReader(storeFileName);
        FileReader ordersFile = new FileReader(ordersFileName);


        Store store = objectMapper.readValue(storeFile, Store.class);
        List<Orders> ordersList = new ArrayList<>(Arrays.asList(objectMapper.readValue(ordersFile, Orders[].class)));

//        enable only for get max order value
        ordersList.sort(Comparator.comparing(Orders::getOrderValue).reversed());


        List<Picker> pickersList = new ArrayList<>();
        for (int i = 0; i < store.getPickers().size(); i++) {
            String pikerName = store.getPickers().get(i);
            LocalTime startPickingTime = store.getPickingStartTime();
            pickersList.add(i, new Picker(pikerName, null, startPickingTime));
        }


        int size = ordersList.size();
        Duration lastTime = null;

        while (size > 0) {

            for (int i = 0; i < pickersList.size(); i++) {

                String picker = pickersList.get(i).getPicker();
                LocalTime pickerStartTime = pickersList.get(i).getPickingStartTime();

                for (int j = 0; j < ordersList.size(); ) {

//                    check end time for picker
                    if (pickerStartTime.isAfter(store.getPickingEndTime())) {
                        size = 0;
                        break;
//                        check if completing time is out of picker time
                    } else if (pickerStartTime.plusMinutes(ordersList.get(j).getPickingTime().toMinutes()).isAfter(ordersList.get(j).getCompleteBy())) {
//                        if its second way of picking
//                        assert lastTime != null;
//                        if (!pickerStartTime.minusMinutes(lastTime.toMinutes()).isAfter(ordersList.get(j).getCompleteBy())) {
//                            data.write("\n" + "OR" + "\n\n" + picker + " " + ordersList.get(j).getOrderId() + " " + pickerStartTime.minusMinutes(lastTime.toMinutes()) + "\n");
//                            ordersList.remove(ordersList.get(j));
//                        }
                        size--;
                        break;
                    }


                    //set order id for current picker
                    pickersList.get(i).setOrderId(ordersList.get(j).getOrderId());
                    String orderId = pickersList.get(i).getOrderId();

                    data.write(picker + " " + orderId + " " + pickerStartTime + "\n");

                    pickerStartTime = pickerStartTime.plusMinutes(ordersList.get(j).getPickingTime().toMinutes());
                    pickersList.get(i).setPickingStartTime(pickerStartTime);

                    lastTime = ordersList.get(j).getPickingTime();
                    ordersList.remove(ordersList.get(j));
                    size--;
                    break;
                }
            }
        }
        data.close();
    }
}
