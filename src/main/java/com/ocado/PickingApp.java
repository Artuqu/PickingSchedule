package com.ocado;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ocado.objects.Orders;
import com.ocado.objects.Picker;
import com.ocado.objects.Store;
import com.ocado.sorters.CompleteBySorter;
import com.ocado.sorters.DurationTimeSorter;
import com.ocado.sorters.OrderValueSorter;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class PickingApp {


    public static void main(String[] args) throws IOException {


        String storeFileName = "src/main/resources/test/advanced-allocation/store.json";
        String ordersFileName = "src/main/resources/test/advanced-allocation/orders.json";
        String output = "OUTPUT";
        storeFulfillment(storeFileName, ordersFileName, output);

    }

    public static void storeFulfillment(String storeFileName, String ordersFileName, String output) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedWriter data = new BufferedWriter(new FileWriter(output));
//        solve invalid definition for LocalTime
        objectMapper.registerModule(new JavaTimeModule());

        FileReader storeFile = new FileReader(storeFileName);
        FileReader ordersFile = new FileReader(ordersFileName);


        Store store = objectMapper.readValue(storeFile, Store.class);
        List<Orders> ordersList = new ArrayList<>(Arrays.asList(objectMapper.readValue(ordersFile, Orders[].class)));

//        enable sorting to get max efficiency
        ordersList.sort(new CompleteBySorter()
                .thenComparing(new DurationTimeSorter())
                .thenComparing(new OrderValueSorter()));


        List<Picker> pickersList = new ArrayList<>();
        for (int i = 0; i < store.getPickers().size(); i++) {
            String pikerName = store.getPickers().get(i);
            LocalTime startPickingTime = store.getPickingStartTime();
            LocalTime endPickingTime = store.getPickingEndTime();
            pickersList.add(i, new Picker(pikerName, null, startPickingTime, endPickingTime));
        }


        int size = ordersList.size();
        Duration lastTime = null;


        int j = 0;

        while (size > 0) {

            for (int i = 0; i < pickersList.size(); i++) {

                String pickerName = pickersList.get(i).getPicker();
                LocalTime pickerStartTime = pickersList.get(i).getPickingStartTime();

                if (j < ordersList.size()) {

                    pickersList.get(i).setOrderId(ordersList.get(j).getOrderId());
                    String orderId = pickersList.get(i).getOrderId();
//                    check end time for picker
                    LocalTime pikerTimeAfterTask = pickerStartTime.plusMinutes(ordersList.get(j).getDuration().toMinutes());
                    LocalTime endPickingTime = pickersList.get(0).getPickingEndTime();
                    LocalTime endCompleteTime = ordersList.get(j).getCompleteBy();

//                        check if completing time is out of picker time
                    if (pikerTimeAfterTask.isAfter(endPickingTime)) {
                        size = 0;
                        break;
//                        switch piker if there is on time
                    } else if (pikerTimeAfterTask.isAfter(endCompleteTime)) {
                        if (i == pickersList.size() - 1) {
                            i = 0;
                        } else {
                            i++;
                        }
                    } else {
                        //set order id for current picker

                        data.write(pickerName + " " + orderId + " " + pickerStartTime + "\n");

                        pickerStartTime = pickerStartTime.plusMinutes(ordersList.get(j).getDuration().toMinutes());
                        pickersList.get(i).setPickingStartTime(pickerStartTime);

                        lastTime = ordersList.get(j).getDuration();
                        ordersList.remove(j);
                        if (j > 0) {
                            j = 0;
                        }
                        size--;
                    }
                } else {
                    size = 0;
                }
            }
        }
        data.close();
    }
}
