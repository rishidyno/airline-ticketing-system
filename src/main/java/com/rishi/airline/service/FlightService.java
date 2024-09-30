package com.rishi.airline.service;

import com.rishi.airline.exception.FlightNotFoundException;
import com.rishi.airline.model.flight.Flight;
import com.rishi.airline.model.flight.SeatConfiguration;
import com.rishi.airline.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    // Add a new flight
    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }


    // Add default 10 flights (5 A320, 5 A380)
    public void addDefaultFlights() {
        List<Flight> flights = Arrays.asList(
                new Flight(null, "A001", "A320", "Delhi", "Hyderabad", Arrays.asList("Monday", "Wednesday", "Friday"), "08:30", "110", 180, new SeatConfiguration(30, 6, Arrays.asList("A", "B", "C", "D", "E", "F"))),
                new Flight(null, "A002", "A320", "Delhi", "Bangalore", Arrays.asList("Tuesday", "Thursday", "Saturday"), "09:45", "135", 180, new SeatConfiguration(30, 6, Arrays.asList("A", "B", "C", "D", "E", "F"))),
                new Flight(null, "A003", "A320", "Delhi", "Mumbai", Arrays.asList("Monday", "Wednesday", "Friday"), "13:30", "135", 180, new SeatConfiguration(30, 6, Arrays.asList("A", "B", "C", "D", "E", "F"))),
                new Flight(null, "A004", "A320", "Patna", "Delhi", Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"), "10:15", "90", 180, new SeatConfiguration(30, 6, Arrays.asList("A", "B", "C", "D", "E", "F"))),
                new Flight(null, "A005", "A320", "Delhi", "San Francisco", List.of("Sunday"), "22:00", "945", 180, new SeatConfiguration(30, 6, Arrays.asList("A", "B", "C", "D", "E", "F"))),
                new Flight(null, "B001", "A380", "Delhi", "Hyderabad", Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"), "11:30", "110", 450, new SeatConfiguration(45, 10, Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"))),
                new Flight(null, "B002", "A380", "Delhi", "Bangalore", Arrays.asList("Monday", "Tuesday", "Thursday", "Friday"), "14:00", "135", 450, new SeatConfiguration(45, 10, Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"))),
                new Flight(null, "B003", "A380", "Delhi", "Mumbai", List.of("Daily"), "13:00", "125", 450, new SeatConfiguration(45, 10, Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"))),
                new Flight(null, "B004", "A380", "Patna", "Delhi", Arrays.asList("Tuesday", "Thursday", "Saturday"), "09:00", "90", 450, new SeatConfiguration(45, 10, Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"))),
                new Flight(null, "B005", "A380", "Delhi", "San Francisco", Arrays.asList("Wednesday", "Friday"), "23:00", "930", 450, new SeatConfiguration(45, 10, Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"))) );
        flightRepository.saveAll(flights);
    }

    // Get flight by Flight number
    public Flight getFlightByFlightNumber(String number) {
        return flightRepository.findByFlightNumber(number)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with id: " + number));
    }

    // Get all flights
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // Update flight details
    public Flight updateFlight(String id, Flight flightDetails) {
        Flight existingFlight = flightRepository.findByFlightNumber(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with id: " + id));

        // Update flight details
        existingFlight.setFlightNumber(flightDetails.getFlightNumber());
        existingFlight.setFlightType(flightDetails.getFlightType());
        existingFlight.setSource(flightDetails.getSource());
        existingFlight.setDestination(flightDetails.getDestination());
        existingFlight.setSchedule(flightDetails.getSchedule());
        existingFlight.setDepartureTime(flightDetails.getDepartureTime());
        existingFlight.setDuration(flightDetails.getDuration());
        existingFlight.setTotalSeats(flightDetails.getTotalSeats());
        existingFlight.setSeatConfiguration(flightDetails.getSeatConfiguration());

        return flightRepository.save(existingFlight);
    }

    // Delete a flight
    public void deleteFlight(String id) {
        Flight flight = flightRepository.findByFlightNumber(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with id: " + id));
        flightRepository.delete(flight);
    }
}
