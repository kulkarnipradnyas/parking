package com.parkinglot.parkinglotfees.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParkingUtils {

    public static String getFormattedDate(LocalDateTime currentDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
    }

}
