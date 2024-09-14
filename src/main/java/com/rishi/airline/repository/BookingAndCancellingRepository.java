package com.rishi.airline.repository;

import com.rishi.airline.model.booking.BookingDate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BookingAndCancellingRepository extends MongoRepository<BookingDate, String> {

    Optional<BookingDate> findByDate(String date);
}
