package com.rishi.airline.repository;

import com.rishi.airline.model.flight.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends MongoRepository<Flight, String> {

    Optional<Flight> findByFlightNumber(String flightNumber);

    List<Flight> findBySourceAndDestinationAndScheduleContaining(String source, String destination, String dayOfFlight);
}
