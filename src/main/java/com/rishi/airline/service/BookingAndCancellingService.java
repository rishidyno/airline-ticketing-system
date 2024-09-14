package com.rishi.airline.service;

import com.rishi.airline.exception.BookingException;
import com.rishi.airline.model.booking.*;
import com.rishi.airline.model.flight.Flight;
import com.rishi.airline.repository.BookingAndCancellingRepository;
import com.rishi.airline.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingAndCancellingService {

    @Autowired
    private BookingAndCancellingRepository bookingAndCancellingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Transactional
    public BookingResponse bookTicket(BookingRequest bookingRequest) {
        if (bookingRequest.getSeats().size() > 6) {
            throw new BookingException("Maximum 6 seats can be booked in a single booking.");
        }

        String bookingDate = bookingRequest.getDate();
        String flightType = bookingRequest.getFlightType();
        String flightNumber = bookingRequest.getFlightNumber();
        List<Seat> seatsToBook = bookingRequest.getSeats();

        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new BookingException("Flight not found"));

        BookingDate bookingDateDocument = bookingAndCancellingRepository.findByDate(bookingDate)
                .orElseGet(() -> new BookingDate(null, bookingDate, new ArrayList<>()));

        FlightTypeBooking flightTypeBooking = bookingDateDocument.getFlightTypeBookings().stream()
                .filter(ftb -> ftb.getFlightType().equals(flightType))
                .findFirst()
                .orElseGet(() -> {
                    FlightTypeBooking newFlightTypeBooking = new FlightTypeBooking(flightType, new ArrayList<>());
                    bookingDateDocument.getFlightTypeBookings().add(newFlightTypeBooking);
                    return newFlightTypeBooking;
                });

        FlightNumberBooking flightNumberBooking = flightTypeBooking.getFlightNumberBookings().stream()
                .filter(fnb -> fnb.getFlightNumber().equals(flightNumber))
                .findFirst()
                .orElseGet(() -> {
                    FlightNumberBooking newFlightNumberBooking = new FlightNumberBooking(flightNumber, 0, new ArrayList<>(), new ArrayList<>());
                    flightTypeBooking.getFlightNumberBookings().add(newFlightNumberBooking);
                    return newFlightNumberBooking;
                });

        for (Seat seat : seatsToBook) {
            boolean seatAlreadyBooked = flightNumberBooking.getBookings().stream()
                    .flatMap(booking -> booking.getBookedSeats().stream())
                    .anyMatch(bookedSeat -> bookedSeat.equals(seat));

            if (seatAlreadyBooked) {
                throw new BookingException("One or more seats are already booked: " + seat);
            }
        }

        String bookingId = "BKG-" + bookingDate + "-" + UUID.randomUUID();
        Booking newBooking = new Booking(bookingId, LocalDateTime.now().toString(), seatsToBook);
        flightNumberBooking.getBookings().add(newBooking);
        flightNumberBooking.setTotalBookedSeats(flightNumberBooking.getTotalBookedSeats() + seatsToBook.size());

        bookingAndCancellingRepository.save(bookingDateDocument);

        BookingResponse response = new BookingResponse();
        response.setBookingId(bookingId);
        response.setDate(bookingDate);
        response.setFlightType(flightType);
        response.setFlightNumber(flightNumber);
        response.setSeatsBooked(seatsToBook);
        response.setSource(flight.getSource());
        response.setDestination(flight.getDestination());
        response.setDepartureTime(flight.getDepartureTime());
        response.setDuration(flight.getDuration());
        response.setMessage("Booking successful");

        return response;
    }

    @Transactional
    public String cancelTicket(CancellationRequest cancellationRequest) {
        String date = cancellationRequest.getDate();
        String flightType = cancellationRequest.getFlightType();
        String flightNumber = cancellationRequest.getFlightNumber();
        String bookingId = cancellationRequest.getBookingId();

        BookingDate bookingDateDocument = bookingAndCancellingRepository.findByDate(date)
                .orElseThrow(() -> new BookingException("No bookings found for the given date."));

        FlightTypeBooking flightTypeBooking = bookingDateDocument.getFlightTypeBookings().stream()
                .filter(ftb -> ftb.getFlightType().equals(flightType))
                .findFirst()
                .orElseThrow(() -> new BookingException("No bookings found for the given flight type."));

        FlightNumberBooking flightNumberBooking = flightTypeBooking.getFlightNumberBookings().stream()
                .filter(fnb -> fnb.getFlightNumber().equals(flightNumber))
                .findFirst()
                .orElseThrow(() -> new BookingException("No bookings found for the given flight number."));

        Booking booking = flightNumberBooking.getBookings().stream()
                .filter(b -> b.getBookingId().equals(bookingId))
                .findFirst()
                .orElseThrow(() -> new BookingException("No booking found with the provided booking ID."));

        flightNumberBooking.getBookings().remove(booking);

        String cancellationId = "CNL-" + UUID.randomUUID();
        flightNumberBooking.getCanceledBookings().add(new CanceledBooking(
                booking.getBookingId(), cancellationId, LocalDateTime.now().toString(), booking.getBookedSeats()));

        flightNumberBooking.setTotalBookedSeats(flightNumberBooking.getTotalBookedSeats() - booking.getBookedSeats().size());

        bookingAndCancellingRepository.save(bookingDateDocument);

        return "Booking successfully canceled. Booking ID: " + bookingId;
    }
}
