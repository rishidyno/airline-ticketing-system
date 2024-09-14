package com.rishi.airline.utility;

import com.rishi.airline.model.booking.Seat;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FlightSearchUtils {

    public static String getDayOfWeekFromDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }


    public static List<Seat> generateSeats(String flightType) {
        List<Seat> seats = new ArrayList<>();

        if (flightType.equals("A320")) {
            // Example: A320 with 180 seats (30 rows, 6 seats per row - A, B, C, D, E, F)
            for (int row = 1; row <= 30; row++) {
                for (char seat : new char[]{'A', 'B', 'C', 'D', 'E', 'F'}) {
                    seats.add(new Seat(row, seat + ""));
                }
            }
        } else if (flightType.equals("A380")) {
            // Example: A380 with 500 seats (50 rows, 10 seats per row - A, B, C, D, E, F, G, H, J, K)
            for (int row = 1; row <= 50; row++) {
                for (char seat : new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K'}) {
                    seats.add(new Seat(row, seat + ""));
                }
            }
        }

        return seats;
    }
}
