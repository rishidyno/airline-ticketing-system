package com.rishi.airline.model.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancellationRequest {
    private String bookingId;
    private String date; // Date of the flight
    private String flightType; // Flight type (e.g., A320, A380)
    private String flightNumber; // Flight number (e.g., A001)
}
