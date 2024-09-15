# Airline Ticketing System

## Overview

The Airline Ticketing System is a Spring Boot application designed to handle flight bookings, cancellations, and flight management. It supports features such as flight search, booking, cancellation, and flight information retrieval. The application uses MongoDB as the database and provides RESTful APIs for interacting with the system.

## Features

- **Flight Management**: Add, update, retrieve, and delete flights.
- **Flight Search**: Search for available flights based on source, destination, and date.
- **Booking**: Book seats on available flights.
- **Cancellation**: Cancel existing bookings.
- **Default Data**: Add default flights for initial setup.

## Technologies Used

- **Spring Boot**: Framework for building the application.
- **MongoDB**: NoSQL database for storing flight and booking data.
- **Swagger**: API documentation and testing.
- **Docker**: Containerization for deployment.

## Setup Instructions

### Prerequisites

- Java 17 or later
- MongoDB (local or server instance)
- Maven or Gradle (for building the project)
- Docker (optional, for containerization)

### Cloning the Repository

```bash
git clone https://github.com/rishidyno/airline-ticketing-system.git
cd airline-ticketing-system
```

### Configuration

1. **MongoDB Configuration**: Update the MongoDB connection settings in `src/main/resources/application.properties` or `application.yml`.

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/airline_db
```

2. **Swagger Documentation**: Swagger UI is available at `/swagger-ui.html`.

### Building and Running the Application

#### Using Maven

```bash
mvn clean install
mvn spring-boot:run
```

#### Using Gradle

```bash
./gradlew build
./gradlew bootRun
```

#### Using Docker (Optional)

1. **Build Docker Image**

```bash
docker build -t airline-ticketing-system .
```

2. **Run Docker Container**

```bash
docker run -p 8080:8080 airline-ticketing-system
```

## API Documentation

### Flight Management

#### Add a New Flight

- **Endpoint**: `POST /api/v1/flights/add-single-flight`
- **Description**: Adds a new flight to the system.
- **Request Body**:

```json
{
  "flightType": "A320",
  "source": "Delhi",
  "destination": "Hyderabad",
  "schedule": [
    "Monday",
    "Wednesday",
    "Friday"
  ],
  "departureTime": "08:30",
  "duration": "110",
  "totalSeats": {
    "$numberInt": "180"
  },
  "seatConfiguration": {
    "rows": {
      "$numberInt": "30"
    },
    "seatsPerRow": {
      "$numberInt": "6"
    },
    "seatLabels": [
      "A",
      "B",
      "C",
      "D",
      "E",
      "F"
    ]
  }
}
```

- **Responses**:
    - `200 OK`: Successfully added the flight.
    - `400 Bad Request`: Invalid flight details.
    - `500 Internal Server Error`: Error adding the flight.

#### Add Default Flights

- **Endpoint**: `POST /api/v1/flights/add-default-flights`
- **Description**: Adds a set of default flights for initial setup.
- **Responses**:
    - `200 OK`: Default flights added successfully.
    - `400 Bad Request`: Error adding default flights.
    - `500 Internal Server Error`: Error adding default flights.

#### Get All Flights

- **Endpoint**: `GET /api/v1/flights/get-all-flights`
- **Description**: Retrieves a list of all flights.
- **Responses**:
    - `200 OK`: Successfully retrieved the list of flights.
    - `500 Internal Server Error`: Error retrieving the list of flights.

#### Get Flight by Flight Number

- **Endpoint**: `GET /api/v1/flights/get-flight-by-flight-number/{id}`
- **Description**: Retrieves details of a specific flight by flight number.
- **Responses**:
    - `200 OK`: Successfully retrieved the flight details.
    - `404 Not Found`: Flight not found for the given flight number.
    - `500 Internal Server Error`: Error retrieving the flight.

#### Update Flight Details

- **Endpoint**: `PUT /api/v1/flights/update-flight/{id}`
- **Description**: Updates the details of an existing flight.
- **Request Body**:

[//]: # (```json)

[//]: # ({)

[//]: # (  "flightNumber": "A001",)

[//]: # (  "route": "Delhi → Bangalore",)

[//]: # (  "schedule": ["Monday", "Tuesday", "Thursday"])

[//]: # (})

[//]: # (```)

- **Responses**:
    - `200 OK`: Successfully updated the flight details.
    - `400 Bad Request`: Invalid flight details.
    - `404 Not Found`: Flight not found for the given flight number.
    - `500 Internal Server Error`: Error updating the flight.

#### Delete Flight

- **Endpoint**: `DELETE /api/v1/flights/delete-flight/{id}`
- **Description**: Deletes an existing flight by flight number.
- **Responses**:
    - `204 No Content`: Successfully deleted the flight.
    - `404 Not Found`: Flight not found for the given flight number.
    - `500 Internal Server Error`: Error deleting the flight.

### Booking and Cancellation

#### Book a Ticket

- **Endpoint**: `POST /api/v1/bookings/book`
- **Description**: Books seats on a specified flight.
- **Request Body**:

```json
{
  "date": "string",
  "flightNumber": "string",
  "flightType": "string",
  "seats": [
    {
      "row": 0,
      "seat": "string"
    }
  ]
}
```

- **Responses**:
    - `200 OK`: Booking successful.
    - `400 Bad Request`: Invalid booking details.
    - `404 Not Found`: Flight or date not found.
    - `500 Internal Server Error`: Error during booking.

#### Cancel a Booking

- **Endpoint**: `POST /api/v1/bookings/cancel`
- **Description**: Cancels an existing booking.
- **Request Body**:

```json
{
  "bookingId": "string",
  "date": "string",
  "flightNumber": "string",
  "flightType": "string"
}
```

- **Responses**:
    - `200 OK`: Cancellation successful.
    - `400 Bad Request`: Invalid cancellation details.
    - `404 Not Found`: Booking not found.
    - `500 Internal Server Error`: Error during cancellation.

### Flight Search

#### Search Flights

- **Endpoint**: `POST /api/v1/flights/search`
- **Description**: Searches for available flights based on the search criteria.
- **Request Body**:

```json
{
  "source": "Delhi",
  "destination": "Mumbai",
  "date": "2024-09-15"
}
```

- **Responses**:
    - `200 OK`: List of available flights.
    - `400 Bad Request`: Invalid search criteria.
    - `500 Internal Server Error`: Error during search.

## Error Handling

- **Common Errors**:
    - `400 Bad Request`: The request could not be understood or was missing required parameters.
    - `404 Not Found`: The requested resource could not be found.
    - `500 Internal Server Error`: A generic error occurred on the server.

## Contributing

We welcome contributions to improve the Airline Ticketing System. To contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/new-feature`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Push to the branch (`git push origin feature/new-feature`).
6. Create a new Pull Request.

[//]: # (## License)

[//]: # ()
[//]: # (This project is licensed under the MIT License - see the [LICENSE]&#40;LICENSE&#41; file for details.)

## Contact

For any questions or issues, please contact:

- **Author**: `Rishi` 
- **Username**: `rishidyno`

---
