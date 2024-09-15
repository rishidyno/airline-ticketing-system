package com.rishi.airline.controller;

import com.rishi.airline.model.search.SearchRequest;
import com.rishi.airline.model.search.SearchResponse;
import com.rishi.airline.service.FlightSearchService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Flight Search API", description = "API for searching available flights based on source, destination, and date.")
@RestController
@RequestMapping("/api/v1/flights")
public class FlightSearchController {

    @Autowired
    private FlightSearchService flightSearchService;

    @ApiOperation(
            value = "Search for available flights",
            notes = "Provide source, destination, and date to search for available flights. The system will return a list of available flights along with seat availability."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of available flights",
                    response = SearchResponse.class, responseContainer = "List"),
//            @ApiResponse(code = 404, message = "No available seats for the selected route on the given date",
//                    response = SearchResponse.class),
//            @ApiResponse(code = 400, message = "Invalid input data. Check if the provided source, destination, or date format is correct.",
//                    response = SearchResponse.class),
//            @ApiResponse(code = 500, message = "Internal Server Error. Something went wrong while processing the request.",
//                    response = Exception.class)
    })
    @PostMapping("/search")
    public ResponseEntity<List<SearchResponse>> searchFlights(
            @ApiParam(value = "Search criteria including source, destination, and date", required = true)
            @RequestBody SearchRequest searchRequest) {
        List<SearchResponse> availableFlights = flightSearchService.searchFlights(searchRequest);
        return ResponseEntity.ok(availableFlights);
    }
}
