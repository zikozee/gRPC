package com.zikozee.protobuf;

import com.google.protobuf.Int32Value;
import com.zikozee.models.Address;
import com.zikozee.models.Car;
import com.zikozee.models.Person;

import java.util.Collections;
import java.util.List;

/**
 * @author : zikoz
 * @created : 31 Aug, 2021
 */

public class CompositionDemo {
    public static void main(String[] args) {
        Address address = Address.newBuilder()
                .setPostbox(123)
                .setStreet("main street")
                .setCity("Atlanta")
                .build();

        Car accord = Car.newBuilder()
                .setMake("Honda")
                .setModel("Accord")
                .setYear(2020)
                .build();

        Car civic = Car.newBuilder()
                .setMake("Honda")
                .setModel("Civic")
                .setYear(2005)
                .build();

        Person sam = Person.newBuilder()
                .setName("sam")
                .setAge(Int32Value.newBuilder().setValue(25).build())
//                .addCar(accord)
//                .addCar(civic)
                .addAllCar(List.of(accord, civic))
                .setAddress(address).build();


        System.out.println(sam);
        System.out.println(sam.hasAge());
    }
}
