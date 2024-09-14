package com.rishi.airline.controller;

import com.rishi.airline.model.search.SearchRequest;
import com.rishi.airline.model.search.SearchResponse;
import com.rishi.airline.service.FlightSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightSearchController {

    @Autowired
    private FlightSearchService flightSearchService;

    @PostMapping("/search")
    public ResponseEntity<List<SearchResponse>> searchFlights(@RequestBody SearchRequest searchRequest) {
        List<SearchResponse> availableFlights = flightSearchService.searchFlights(searchRequest);
        return ResponseEntity.ok(availableFlights);
    }
}
