package com.zikozee.protobuf;

import com.zikozee.models.BodyStyle;
import com.zikozee.models.Car;
import com.zikozee.models.Dealer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zikoz
 * @created : 31 Aug, 2021
 */

public class MapDemo {

    public static void main(String[] args) {
        Car accord = Car.newBuilder()
                .setMake("Honda")
                .setModel("Accord")
                .setYear(2020)
                .setBodyStyle(BodyStyle.COUPE)
                .build();

        Car civic = Car.newBuilder()
                .setMake("Honda")
                .setModel("Civic")
                .setYear(2005)
                .build();

//        Map<Integer, Car> map = new HashMap<>();
//        map.put(2005, civic);
//        map.put(2020, accord);

        Dealer dealer = Dealer.newBuilder()
                .putModel(2005, civic)
                .putModel(2020, accord)
//                .putAllModel(map)
                .build();


        System.out.println(dealer.getModelOrThrow(2005));
        System.out.println(dealer.getModelOrDefault(2019, accord));
        System.out.println(dealer.getModelMap());
    }
}
