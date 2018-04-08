package com.example.reddy.careerfair;


import android.util.Log;

import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;

/**
 * Created by reddy on 08-04-2018.
 */

public class SendEmail {

    public static void main(String[] args) throws SparkPostException {
        String API_KEY = "bfec5c89e03eed77a9c047f0b6c06d4d83697918";
        Client sparky = new Client(API_KEY);

        sparky.sendMessage(
                "lappy176@gmail.com",
                "lappy176@gmail.com",
                "Oh hey!",
                "Testing SparkPost - the world's most awesomest email service!",
                "<p>Testing SparkPost - the world's most awesomest email service!</p>");
    }

}
