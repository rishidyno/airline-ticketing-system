package com.rishi.airline.controller;

import com.rishi.airline.model.flight.Flight;
import com.rishi.airline.service.FlightService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Flight Management API", description = "API for managing flights including adding, retrieving, updating, and deleting flights.")
@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @ApiOperation(
            value = "Add a new flight",
            notes = "Adds a new flight to the system with provided flight details."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added the flight", response = Flight.class),
//            @ApiResponse(code = 400, message = "Invalid flight details. Check if the input data is correct.", response = Exception.class),
//            @ApiResponse(code = 500, message = "Internal Server Error. Something went wrong while adding the flight.", response = Exception.class)
    })
    @PostMapping("/add-single-flight")
    public ResponseEntity<Flight> addFlight(
            @ApiParam(value = "Flight details including flight number, schedule, and route", required = true)
            @RequestBody Flight flight) {
        Flight newFlight = flightService.addFlight(flight);
        return ResponseEntity.ok(newFlight);
    }

    @ApiOperation(
            value = "Add default flights",
            notes = "Adds a set of default flights to the system. Typically used for initial setup."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Default flights added successfully"),
//            @ApiResponse(code = 400, message = "Error adding default flights", response = ErrorResponse.class),
//            @ApiResponse(code = 500, message = "Internal Server Error. Something went wrong while adding default flights.", response = ErrorResponse.class)
    })
    @PostMapping("/add-default-flights")
    public ResponseEntity<String> addDefaultFlights() {
        try {
            flightService.addDefaultFlights();
            return ResponseEntity.ok("Default flights added successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding default flights: " + e.getMessage());
        }
    }

    @ApiOperation(
            value = "Get all flights",
            notes = "Retrieves a list of all flights available in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the list of flights", response = Flight.class, responseContainer = "List"),
//            @ApiResponse(code = 500, message = "Internal Server Error. Something went wrong while retrieving flights.", response = ErrorResponse.class)
    })
    @GetMapping("/get-all-flights")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @ApiOperation(
            value = "Get flight by flight number",
            notes = "Retrieves details of a specific flight by its flight number."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the flight details", response = Flight.class),
//            @ApiResponse(code = 404, message = "Flight not found for the given flight number", response = ErrorResponse.class),
//            @ApiResponse(code = 500, message = "Internal Server Error. Something went wrong while retrieving the flight.", response = ErrorResponse.class)
    })
    @GetMapping("/get-flight-by-flight-number/{id}")
    public ResponseEntity<Flight> getFlightByFlightNumber(
            @ApiParam(value = "Flight number of the flight to retrieve", required = true)
            @PathVariable String id) {
        Flight flight = flightService.getFlightByFlightNumber(id);
        return ResponseEntity.ok(flight);
    }

    @ApiOperation(
            value = "Update flight details",
            notes = "Updates the details of an existing flight specified by flight number."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the flight details", response = Flight.class),
//            @ApiResponse(code = 400, message = "Invalid flight details. Check if the input data is correct.", response = ErrorResponse.class),
//            @ApiResponse(code = 404, message = "Flight not found for the given flight number", response = ErrorResponse.class),
//            @ApiResponse(code = 500, message = "Internal Server Error. Something went wrong while updating the flight.", response = ErrorResponse.class)
    })
    @PutMapping("/update-flight/{id}")
    public ResponseEntity<Flight> updateFlight(
            @ApiParam(value = "Flight number of the flight to update", required = true)
            @PathVariable String id,
            @ApiParam(value = "Updated flight details", required = true)
            @RequestBody Flight flightDetails) {
        Flight updatedFlight = flightService.updateFlight(id, flightDetails);
        return ResponseEntity.ok(updatedFlight);
    }

    @ApiOperation(
            value = "Delete flight",
            notes = "Deletes an existing flight specified by flight number."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted the flight"),
//            @ApiResponse(code = 404, message = "Flight not found for the given flight number", response = ErrorResponse.class),
//            @ApiResponse(code = 500, message = "Internal Server Error. Something went wrong while deleting the flight.", response = ErrorResponse.class)
    })
    @DeleteMapping("/delete-flight/{id}")
    public ResponseEntity<Void> deleteFlight(
            @ApiParam(value = "Flight number of the flight to delete", required = true)
            @PathVariable String id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}
