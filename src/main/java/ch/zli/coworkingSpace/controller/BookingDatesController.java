package ch.zli.coworkingSpace.controller;

import ch.zli.coworkingSpace.model.BookingDatesEntity;
import ch.zli.coworkingSpace.service.BookingDatesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
public class BookingDatesController {
    private final BookingDatesService bookingDatesService;

    @Autowired
    public BookingDatesController(BookingDatesService bookingDatesService) {
        this.bookingDatesService = bookingDatesService;
    }

    @Operation(
            summary = "Get all Booking Dates",
            description = "Loads all Booking Dates from database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @GetMapping("/bookings")
    public ResponseEntity<Iterable<BookingDatesEntity>> getBookingDates() {
        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookingDatesService.loadAll());
    }

    @Operation(
            summary = "Get one specific booking date by id",
            description = "Loads one specific booking date by id from database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @GetMapping("/bookings/{id}")
    public ResponseEntity<Optional<BookingDatesEntity>>
    getBookingDate(@PathVariable long id) {
        Optional<BookingDatesEntity> bookingDate = bookingDatesService.loadOne(id);

        if (bookingDate.isPresent()) {
            System.out.println("Accessing single booking date, HTTP: 200");
            return ResponseEntity
                    .status(HttpStatus.OK)  // HTTP 200
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bookingDate);
        } else {
            System.out.println("Accessing single booking date, HTTP: 404");
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Create a new booking date",
            description = "Creates a new booking date in database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bookings")
    public ResponseEntity<BookingDatesEntity>
    addBookingDate(@RequestBody BookingDatesEntity bookingDate) {
        System.out.println("booking date created");

        bookingDatesService.create(bookingDate);
        return ResponseEntity
                .status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookingDate);
    }

    @Operation(
            summary = "Delete an existing booking date",
            description = "Deletes one specific and existing booking date in database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<?>
    deleteBookingDate(@PathVariable long id) {
        Optional<BookingDatesEntity> bookingDate = bookingDatesService.loadOne(id);

        if (bookingDate.isPresent()) {
            System.out.println("removed bookingDate");
            bookingDatesService.delete(id);
            return ResponseEntity.noContent().build();  // HTTP 204
        } else {
            return ResponseEntity.notFound().build();   // HTTP 404
        }
    }

    @Operation(
            summary = "Update an existing booking date",
            description = "Updates one specific and existing booking date in database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/bookings/{id}")
    public ResponseEntity<BookingDatesEntity>
    updateBookingDate(@RequestBody BookingDatesEntity bookingDate) {

        bookingDatesService.create(bookingDate);
        return ResponseEntity.status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookingDate);
    }
}
