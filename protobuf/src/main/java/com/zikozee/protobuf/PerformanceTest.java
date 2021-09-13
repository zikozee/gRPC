package com.zikozee.protobuf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Int32Value;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zikozee.json.JPerson;
import com.zikozee.models.Person;

/**
 * @author : zikoz
 * @created : 30 Aug, 2021
 */

public class PerformanceTest {

    public static void main(String[] args) {

        //json
        JPerson person = new JPerson();
        person.setName("sam");
        person.setAge(10);
        ObjectMapper objectMapper = new ObjectMapper();

        Runnable json = () ->{
            try {
                byte[] bytes = objectMapper.writeValueAsBytes(person);
                System.out.println(bytes.length);
                JPerson person1 = objectMapper.readValue(bytes, JPerson.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };


        // protobuf
        Person sam = Person.newBuilder()
                .setName("sam")
                .setAge(Int32Value.newBuilder().setValue(10).build())
                .build();


        Runnable proto = () -> {
            try{
                byte[] bytes = sam.toByteArray();
                System.out.println(bytes.length);
                Person sam1 = Person.parseFrom(bytes);
            }catch (InvalidProtocolBufferException e){
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 1; i++) {
            runPerformanceTest(json, "JSON");
            runPerformanceTest(proto, "PROTOBUF");
        }
    }


    private static void runPerformanceTest(Runnable runnable, String method){
        long time1 = System.currentTimeMillis();

        for (int i = 0; i < 1; i++) {
            runnable.run();
        }

        long time2 = System.currentTimeMillis();

        System.out.println(method + " : "  + (time2 - time1) + "ms");
    }

}
