package com.rishi.airline.controller;

import com.rishi.airline.model.flight.Flight;
import com.rishi.airline.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    // Add a new flight
    @PostMapping("/add-single-flight")
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
        Flight newFlight = flightService.addFlight(flight);
        return ResponseEntity.ok(newFlight);
    }

    // Just for initially adding flight.
    @PostMapping("/add-default-flights")
    public ResponseEntity<String> addDefaultFlights() {
        try {
            flightService.addDefaultFlights();
            return ResponseEntity.ok("Default flights added successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding default flights: " + e.getMessage());
        }
    }

    // Get all flights
    @GetMapping("/get-all-flights")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    // Get flight by Flight number
    @GetMapping("/get-flight-by-flight-number{number}")
    public ResponseEntity<Flight> getFlightByFlightNumber(@PathVariable String id) {
        Flight flight = flightService.getFlightByFlightNumber(id);
        return ResponseEntity.ok(flight);
    }

    // Update flight details
    @PutMapping("/update-flight/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable String id, @RequestBody Flight flightDetails) {
        Flight updatedFlight = flightService.updateFlight(id, flightDetails);
        return ResponseEntity.ok(updatedFlight);
    }

    // Delete flight
    @DeleteMapping("/delete-flight/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}
