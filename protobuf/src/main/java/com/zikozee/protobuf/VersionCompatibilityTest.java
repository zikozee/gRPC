package com.zikozee.protobuf;

import com.zikozee.models.Television;
import com.zikozee.models.Type;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author : zikoz
 * @created : 31 Aug, 2021
 */

public class VersionCompatibilityTest {

    public static void main(String[] args) throws IOException {

        Path pathV1 = Paths.get("tv-v1");
        Path pathV2 = Paths.get("tv-v2");

        Television television = Television.newBuilder()
                .setBrand("sony")
                .setModel(2016)
                .setType(Type.OLED)
                .build();



        Files.write(pathV2, television.toByteArray());

        //
/*
        byte[] bytes = Files.readAllBytes(pathV1);
        System.out.println(Television.parseFrom(bytes));

 */
    }
}
