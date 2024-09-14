package com.rishi.airline.model.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightTypeBooking {

    private String flightType; // e.g., "A320" or "A380"
    private List<FlightNumberBooking> flightNumberBookings; // List of flights and their respective bookings
}
