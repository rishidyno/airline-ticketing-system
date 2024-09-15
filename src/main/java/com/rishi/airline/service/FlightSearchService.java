package com.rishi.airline.service;

import com.rishi.airline.exception.SearchException;
import com.rishi.airline.model.booking.Booking;
import com.rishi.airline.model.booking.BookingDate;
import com.rishi.airline.model.booking.Seat;
import com.rishi.airline.model.flight.Flight;
import com.rishi.airline.model.search.SearchRequest;
import com.rishi.airline.model.search.SearchResponse;
import com.rishi.airline.repository.BookingAndCancellingRepository;
import com.rishi.airline.repository.FlightRepository;
import com.rishi.airline.utility.FlightSearchUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.rishi.airline.utility.FlightSearchUtils.generateSeats;

@Service
public class FlightSearchService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private BookingAndCancellingRepository bookingAndCancellingRepository;

    private static final Logger logger = LoggerFactory.getLogger(FlightSearchService.class);

    public List<SearchResponse> searchFlights(SearchRequest request) {
        String src = request.getSource();
        String destination = request.getDestination();
        String date = request.getDate();

        logger.info("Searching flights for route: {} -> {} on {}", src, destination, date);

        String dayOfFlight = FlightSearchUtils.getDayOfWeekFromDate(date);

        List<Flight> availableFlights = flightRepository.findBySourceAndDestinationAndScheduleContaining(src, destination, dayOfFlight);
        if (availableFlights.isEmpty()) {

            throw new SearchException("No flights available for the selected route.");
        }

        List<SearchResponse> availableFlightResponses = new ArrayList<>();

        // Step 2: Check the availability in bookings_by_date collection
        Optional<BookingDate> bookingDateOptional = bookingAndCancellingRepository.findByDate(date);

        for (Flight flight : availableFlights) {
            int totalSeats = flight.getTotalSeats();
            int bookedSeats = 0;

            List<Seat> availableSeats = new ArrayList<>();

            if (bookingDateOptional.isPresent()) {
                BookingDate bookingDate = bookingDateOptional.get();

                // Check if any bookings exist for this flight type and flight number
                var flightTypeBookings = bookingDate.getFlightTypeBookings().stream()
                        .filter(ftb -> ftb.getFlightType().equals(flight.getFlightType()))
                        .findFirst();

                if (flightTypeBookings.isPresent()) {
                    var flightNumberBooking = flightTypeBookings.get().getFlightNumberBookings().stream()
                            .filter(fnb -> fnb.getFlightNumber().equals(flight.getFlightNumber()))
                            .findFirst();

                    if (flightNumberBooking.isPresent()) {
                        bookedSeats = flightNumberBooking.get().getTotalBookedSeats();
                        if(totalSeats > bookedSeats) {
                            availableSeats = getAvailableSeats(flightNumberBooking.get().getBookings(), flight.getFlightType());
                        }
                    }else{
                        availableSeats = generateSeats(flight.getFlightType());
                    }
                }else{
                    availableSeats = generateSeats(flight.getFlightType());
                }
            }else{
                availableSeats = generateSeats(flight.getFlightType());
            }

            // Step 3: Check if there are available seats for booking
            int availableSeatsCount = totalSeats - bookedSeats;
            if (availableSeatsCount > 0) {
                // Step 4: Add the flight to the response list
                SearchResponse response = new SearchResponse(
                        flight.getFlightType(),
                        flight.getFlightNumber(),
                        flight.getDepartureTime(),
                        src,
                        destination,
                        flight.getDuration(),
                        availableSeatsCount,
                        availableSeats
                );
                availableFlightResponses.add(response);
            }
        }

        if (availableFlightResponses.isEmpty()) {
            logger.warn("No flights found for the selected route on {}", date);
            throw new SearchException("No available seats for the selected route on the given date.");
        }

        return availableFlightResponses;
    }

    private List<Seat> getAvailableSeats(List<Booking> booking, String flightType) {

        List<Seat> seatsInTypeFlight = generateSeats(flightType);
        List<Seat> allBookedSeatsInTypeFlight = getAllBookedSeatsInAFlight(booking);

        seatsInTypeFlight.removeAll(allBookedSeatsInTypeFlight);

        return seatsInTypeFlight;
    }

    private List<Seat> getAllBookedSeatsInAFlight(List<Booking> booking) {

        List<Seat> allBookedSeatsInTypeFlight = new ArrayList<Seat>();
        for(Booking singlebooking : booking){
            allBookedSeatsInTypeFlight.addAll(singlebooking.getBookedSeats());
        }
        return allBookedSeatsInTypeFlight;
    }
}
