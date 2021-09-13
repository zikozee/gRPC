package com.zikozee.protobuf;

import com.zikozee.models.Person;

/**
 * @author : zikoz
 * @created : 31 Aug, 2021
 */

public class DefaultValueDemo {

    public static void main(String[] args) {

        Person person = Person.newBuilder().build();

        System.out.println("City : " + person.getAddress().getCity());
        System.out.println("City Exist: " + person.hasAddress());
    }
}
