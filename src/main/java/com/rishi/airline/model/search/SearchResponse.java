package com.rishi.airline.model.search;

import com.rishi.airline.model.booking.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private String flightType;
    private String flightNumber;
    private String departureTime;
    private String source;
    private String destination;
    private String duration;
    private int availableSeatsCount;
    private List<Seat> availableSeats;
}
