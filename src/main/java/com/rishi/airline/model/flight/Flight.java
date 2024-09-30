package com.rishi.airline.model.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "flights")
public class Flight {

    @Id
    private String id;

    @NotNull(message = "Flight number is required")
    @Indexed(unique = true)
    private String flightNumber; // Unique identifier for a specific flight

    @NotNull(message = "Flight type is required")
    private String flightType; // A320 or A380

    @NotNull(message = "Source is required")
    private String source;

    @NotNull(message = "Destination is required")
    private String destination;

    @NotNull(message = "Schedule is required")
    private List<String> schedule; // Days of operation, e.g., ["Monday", "Wednesday", "Friday"]

    @NotNull(message = "Departure time is required")
    private String departureTime; // "HH:mm" format

    @NotNull(message = "Duration is required")
    private String duration; // Duration in minutes

    @NotNull(message = "Total seats is required")
    private Integer totalSeats; // Total number of seats available on this flight

    @NotNull(message = "Seat configuration is required")
    private SeatConfiguration seatConfiguration;
}
