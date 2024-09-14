package com.rishi.airline.model.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CanceledBooking {

    private String bookingId;
    private String cancellationId;
    private String cancellationDate;

    private List<Seat> canceledSeats;
}
