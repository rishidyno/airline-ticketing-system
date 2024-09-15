package com.rishi.airline.model.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {


    private String bookingId; // Unique identifier for the booking (generated at booking time)
    private String bookingDate; // The date on which

    private List<Seat> bookedSeats; // List of seats booked under this booking
}
