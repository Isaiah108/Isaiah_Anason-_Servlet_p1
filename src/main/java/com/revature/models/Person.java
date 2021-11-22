package com.revature.models;

import com.revature.annotations.PrimaryKey;

/**
 * Model class to demonstrate how ORM will detect invalid annotation setup
 */
public class Person {
    @PrimaryKey(isSerial = false)
    private int id;
    @PrimaryKey(isSerial = false)
    private int SSN;
}
