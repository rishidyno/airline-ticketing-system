package com.rishi.airline.model.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "flights")
public class Flight {

    @Id
    private String id;
    private String flightNumber; // Unique identifier for a specific flight
    private String flightType; // A320 or A380
    private String source;
    private String destination;
    private List<String> schedule; // Days of operation, e.g., ["Monday", "Wednesday", "Friday"]
    private String departureTime; // "HH:mm" format
    private String duration; // Duration in minutes
    private int totalSeats; // Total number of seats available on this flight

    private SeatConfiguration seatConfiguration; // No need for @Embedded here
}
