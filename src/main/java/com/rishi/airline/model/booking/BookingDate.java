package com.rishi.airline.model.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings_by_date")
public class BookingDate {

    @Id
    private String id;
    private String date; // The date for which bookings are being tracked

    private List<FlightTypeBooking> flightTypeBookings; // List of flight types (A320, A380) and their bookings
}
