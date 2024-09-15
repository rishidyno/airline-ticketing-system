package com.rishi.airline.controller;

import com.rishi.airline.model.booking.BookingRequest;
import com.rishi.airline.model.booking.BookingResponse;
import com.rishi.airline.model.booking.CancellationRequest;
import com.rishi.airline.service.BookingAndCancellingService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Booking and Cancellation API", description = "API for booking and cancelling flight tickets.")
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingAndCancellingController {

    @Autowired
    private BookingAndCancellingService bookingAndCancellingService;

    @ApiOperation(
            value = "Book a flight ticket",
            notes = "Book a flight ticket by providing booking details. Returns booking confirmation details."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully booked the ticket",
                    response = BookingResponse.class),
//            @ApiResponse(code = 400, message = "Invalid booking request. Check if the input data is correct.",
//                    response = BookingResponse.class),
//            @ApiResponse(code = 500, message = "Internal Server Error. Something went wrong while processing the booking.",
//                    response = BookingResponse.class)
    })
    @PostMapping("/book")
    public ResponseEntity<BookingResponse> bookTicket(
            @ApiParam(value = "Booking details including flight, seats, and passenger information", required = true)
            @RequestBody BookingRequest bookingRequest) {
        BookingResponse response = bookingAndCancellingService.bookTicket(bookingRequest);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(
            value = "Cancel a booked flight ticket",
            notes = "Cancel a previously booked flight ticket by providing booking ID and cancellation details."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully cancelled the booking",
                    response = BookingResponse.class),
//            @ApiResponse(code = 400, message = "Invalid cancellation request. Check if the input data is correct or if the booking ID is valid.",
//                    response = BookingResponse.class),
//            @ApiResponse(code = 404, message = "Booking not found for the given ID",
//                    response = BookingResponse.class),
//            @ApiResponse(code = 500, message = "Internal Server Error. Something went wrong while processing the cancellation.",
//                    response = BookingResponse.class)
    })
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelBooking(
            @ApiParam(value = "Cancellation details including booking ID and seat information", required = true)
            @RequestBody CancellationRequest cancellationRequest) {
        String response = bookingAndCancellingService.cancelTicket(cancellationRequest);
        return ResponseEntity.ok(response);
    }
}
