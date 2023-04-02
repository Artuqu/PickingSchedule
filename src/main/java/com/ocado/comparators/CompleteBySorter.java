package com.ocado.comparators;

import com.ocado.objects.Orders;

import java.util.Comparator;

public class CompleteBySorter implements Comparator<Orders> {


    @Override
    public int compare(Orders o1, Orders o2) {
        return o1.getCompleteBy().compareTo(o2.getCompleteBy());
    }
}
