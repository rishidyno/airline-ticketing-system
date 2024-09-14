package com.rishi.airline.repository;

import com.rishi.airline.model.flight.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends MongoRepository<Flight, String> {

    Optional<Flight> findByFlightNumber(String flightNumber);

    // Find flights by source, destination, and day of flight or "Daily"
//    @Query("{ 'source': ?0, 'destination': ?1, $or: [ { 'schedule': 'Daily' }, { 'schedule': { $in: [?2] } } ] }")
    List<Flight> findBySourceAndDestinationAndScheduleContaining(String source, String destination, String dayOfFlight);
}
