package com.rishi.airline.model.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatConfiguration {

    private int rows;
    private int seatsPerRow;
    private List<String> seatLabels; // e.g., ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"]
}
