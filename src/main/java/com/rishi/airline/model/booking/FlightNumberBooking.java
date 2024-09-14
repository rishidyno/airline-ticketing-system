package com.rishi.airline.model.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightNumberBooking {

    private String flightNumber; // e.g., A001
    private Integer totalBookedSeats;
    private List<Booking> bookings; // List of booked and canceled bookings
    private List<CanceledBooking> canceledBookings; // List of canceled bookings
}

