package com.ocado.sorters;

import com.ocado.objects.Orders;

import java.util.Comparator;

public class OrderValueSorter implements Comparator<Orders> {


    @Override
    public int compare(Orders o1, Orders o2) {
        return o1.getOrderValue().compareTo(o2.getOrderValue());
    }
}
