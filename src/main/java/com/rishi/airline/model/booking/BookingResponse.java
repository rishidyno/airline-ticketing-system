package com.rishi.airline.model.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private String bookingId;
    private String date;
    private String flightType;
    private String flightNumber;
    private List<Seat> seatsBooked;
    private String source;
    private String destination;
    private String departureTime;
    private String duration;
    private String message;
}
