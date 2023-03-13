package com.parkinglot.parkinglotfees.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

    public static String getFormattedDate(LocalDateTime dateTime, String pattern) {
        String dateFormat = pattern != null ? pattern : "dd-MMM-yyyy HH:mm:ss";
        LocalDateTime currentDateTime = dateTime != null ? dateTime : LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }

    public static List<Integer> convertStringIntervalToInt(String feeInterval) {
        String[] feeArray = feeInterval.replaceAll("\\[", "")
                .replaceAll("]", "")
                .split(",");
        List<Integer> newList = new ArrayList<Integer>(feeArray.length);
        for (String myInt : feeArray) {
            Integer convertedValue = myInt.trim().equalsIgnoreCase("Infinity") ? Integer.MAX_VALUE : Integer.valueOf(myInt.trim());
            newList.add(convertedValue);
        }
        return newList;
    }

}
