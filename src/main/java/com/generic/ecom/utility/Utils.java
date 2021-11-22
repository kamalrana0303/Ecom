package com.generic.ecom.utility;

import java.util.Random;
import java.util.stream.IntStream;

public class Utils {

    public static String  generateUniqueProductId(){
        String alphanumeric="ABSCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random= new Random();
        String uniqueId = "";
        for (int x = 0; x < 5; x++) {
            int i = random.nextInt(alphanumeric.length());
            uniqueId = uniqueId + alphanumeric.charAt(i);
        }
        return uniqueId;
    }
}
