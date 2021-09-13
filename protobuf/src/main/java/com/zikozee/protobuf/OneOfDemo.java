package com.zikozee.protobuf;

import com.zikozee.models.Credentials;
import com.zikozee.models.EmailCredentials;
import com.zikozee.models.PhoneOTP;

/**
 * @author : zikoz
 * @created : 31 Aug, 2021
 */

public class OneOfDemo {

    public static void main(String[] args) {
        EmailCredentials emailCredentials = EmailCredentials.newBuilder()
                .setEmail("zikozee@gmail.com")
                .setPassword("admin123")
                .build();


        PhoneOTP phoneOTP = PhoneOTP.newBuilder()
                .setNumber(123123123)
                .setCode(456)
                .build();

        Credentials credentials = Credentials.newBuilder()
                .setPhoneMode(phoneOTP)
                .setEmailMode(emailCredentials) // only one should be set else the last set will override
                .build();


        // useful when we need provide alternative or default or errors

        login(credentials);
    }


    private static void login(Credentials credentials){

        switch (credentials.getModeCase()){
            case EMAILMODE:
                System.out.println(credentials.getEmailMode());
                break;
            case PHONEMODE:
                System.out.println(credentials.getPhoneMode());
                break;
            default:
                System.out.println();
                break;
        }

    }
}
