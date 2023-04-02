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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
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

//          solve invalid definition for LocalTime
        objectMapper.registerModule(new JavaTimeModule());

//          initialize writer
        BufferedWriter data = new BufferedWriter(new FileWriter(output));

//          initialize or option
        StringBuilder OR = new StringBuilder();
        OR.append("\nOR \n\n");
        boolean orEnabled = false;

//          read data from files
        FileReader storeFile = new FileReader(storeFileName);
        FileReader ordersFile = new FileReader(ordersFileName);

//          add data to objects
        Store store = objectMapper.readValue(storeFile, Store.class);
        List<Orders> ordersList = new ArrayList<>(Arrays.asList(objectMapper.readValue(ordersFile, Orders[].class)));

//          enable sorting to get max efficiency
//          if you want to optimize order to get max value need to switch OrderValueSorter to second place
        ordersList.sort(new CompleteBySorter()
                .thenComparing(new OrderValueSorter().reversed())
                .thenComparing(new DurationTimeSorter()));

//          create Picker object
        List<Picker> pickersList = new ArrayList<>();
        for (int i = 0; i < store.getPickers().size(); i++) {
            String pikerName = store.getPickers().get(i);
            LocalTime startPickingTime = store.getPickingStartTime();
            LocalTime endPickingTime = store.getPickingEndTime();
            pickersList.add(i, new Picker(pikerName, null, startPickingTime, endPickingTime));
        }


        int size = ordersList.size();
        int orAppend = 0;
        int j = 0;
        while (size > 0) {
            for (int i = 0; i < pickersList.size(); i++) {

                String pickerName = pickersList.get(i).getPicker();
                LocalTime pickerStartTime = pickersList.get(i).getPickingStartTime();

                if (j < ordersList.size()) {

                    pickersList.get(i).setOrderId(ordersList.get(j).getOrderId());
                    String orderId = pickersList.get(i).getOrderId();
//                      get end time after task
                    LocalTime pikerTimeAfterTask = pickerStartTime.plusMinutes(ordersList.get(j).getPickingTime().toMinutes());
                    LocalTime endPickingTime = pickersList.get(0).getPickingEndTime();
                    LocalTime endCompleteTime = ordersList.get(j).getCompleteBy();


//                        check if completing time is out of picker time
                    if (pikerTimeAfterTask.isAfter(endPickingTime)) {
                        size = 0;
                        break;
//                        switch piker if there is no time
                    } else if (pikerTimeAfterTask.isAfter(endCompleteTime)) {
                        if (i == pickersList.size() - 1 && pickersList.size() > 1) {
                            i = -1;
//                            switch task if picker has no time existed
                        } else if (i < pickersList.size() - 1 && pickersList.size() > 1) {
                            j++;
                            size--;
                        } else {
                            size = 0;
                        }
                    } else {

                        data.write(pickerName + " " + orderId + " " + pickerStartTime + "\n");
                        if (orEnabled) {
                            OR.append(pickerName + " " + orderId + " " + pickerStartTime + "\n");
                        }

                        pickerStartTime = pickerStartTime.plusMinutes(ordersList.get(j).getPickingTime().toMinutes());
                        pickersList.get(i).setPickingStartTime(pickerStartTime);


                        //                      check if there is substitute task may be completed in same time
                        if (j + 1 < ordersList.size()) {
                            pickerStartTime = pickerStartTime.minusMinutes(ordersList.get(j).getPickingTime().toMinutes());
                            LocalTime pikerTimeAfterSubstituteTask = pickerStartTime.plusMinutes(ordersList.get(j + 1).getPickingTime().toMinutes());
                            LocalTime endCompleteSubstituteTime = ordersList.get(j + 1).getCompleteBy();

                            if (pikerTimeAfterSubstituteTask.isAfter(endCompleteSubstituteTime) == pikerTimeAfterTask.isAfter(endCompleteTime)) {
                                pickersList.get(i).setOrderId(ordersList.get(j + 1).getOrderId());
                                orderId = pickersList.get(i).getOrderId();
                                orEnabled = true;
                                if (orAppend == 0) {
//                                    OR.append(data);
                                    OR.append(pickerName + " " + orderId + " " + pickerStartTime + "\n");
                                } else {
                                    OR.append(pickerName + " " + orderId + " " + pickerStartTime + "\n");
                                }
                                ordersList.remove(j + 1);
                                size--;
                                orAppend = 1;
                            }
                        }


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
        if (orEnabled) {
            data.flush();
            data.write(OR.toString());
        }
        data.close();
    }
}
