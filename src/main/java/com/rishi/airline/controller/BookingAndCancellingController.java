package com.rishi.airline.controller;

import com.rishi.airline.model.booking.BookingRequest;
import com.rishi.airline.model.booking.BookingResponse;
import com.rishi.airline.model.booking.CancellationRequest;
import com.rishi.airline.service.BookingAndCancellingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingAndCancellingController {

    @Autowired
    private BookingAndCancellingService bookingAndCancellingService;

    @PostMapping("/book")
    public ResponseEntity<BookingResponse> bookTicket(@RequestBody BookingRequest bookingRequest) {
        BookingResponse response = bookingAndCancellingService.bookTicket(bookingRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelBooking(@RequestBody CancellationRequest cancellationRequest) {
        String response = bookingAndCancellingService.cancelTicket(cancellationRequest);
        return ResponseEntity.ok(response);
    }
}
