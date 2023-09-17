package com.example.telegramapi.sorterts;

import com.example.telegramapi.entities.tests_data.TestEntity;

import java.util.Comparator;

public class TestEntitiesDateComparator implements Comparator<TestEntity> {
    @Override
    public int compare(TestEntity o1, TestEntity o2) {
        if (o1.getTestDate().before(o2.getTestDate())) return -1;
        else if (o2.getTestDate().before(o1.getTestDate())) return 1;
        return 0;
    }
}
