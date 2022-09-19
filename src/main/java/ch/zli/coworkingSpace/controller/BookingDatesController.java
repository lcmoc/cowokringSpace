package ch.zli.coworkingSpace.controller;

import ch.zli.coworkingSpace.exception.GameNotFoundException;
import ch.zli.coworkingSpace.model.BookingDatesEntity;
import ch.zli.coworkingSpace.repository.BookingDatesRepository;
import ch.zli.coworkingSpace.service.BookingDatesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
public class BookingDatesController {
    private final BookingDatesService bookingDatesService;

    @Autowired
    public BookingDatesController(BookingDatesService bookingDatesService) {
        this.bookingDatesService = bookingDatesService;
    }

    @GetMapping("/bookings")
    public ResponseEntity<Iterable<BookingDatesEntity>> getBookingDates() {
        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookingDatesService.loadAll());
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<Optional<BookingDatesEntity>>
    getJoke(@PathVariable long id) {
        Optional<BookingDatesEntity> bookingDate = bookingDatesService.loadOne(id);

        if (bookingDate.isPresent()) {
            System.out.println("Accessing single date, HTTP: 200");
            return ResponseEntity
                    .status(HttpStatus.OK)  // HTTP 200
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bookingDate);
        } else {
            System.out.println("Accessing single joke, HTTP: 404");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/bookings")
    public ResponseEntity<BookingDatesEntity>
    addJoke(@RequestBody BookingDatesEntity bookingDate) {
        System.out.println("booking created");

        bookingDatesService.create(bookingDate);
        return ResponseEntity
                .status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookingDate);
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<?>
    deleteJoke(@PathVariable long id) {
        Optional<BookingDatesEntity> bookingDate = bookingDatesService.loadOne(id);

        if (bookingDate.isPresent()) {
            System.out.println("removed bookingDate");
            bookingDatesService.delete(id);
            return ResponseEntity.noContent().build();  // HTTP 204
        } else {
            return ResponseEntity.notFound().build();   // HTTP 404
        }
    }

    @PutMapping("/bookings/{id}")
    public ResponseEntity<BookingDatesEntity>
    updateJoke(@RequestBody BookingDatesEntity bookingDate) {

        bookingDatesService.create(bookingDate);
        return ResponseEntity.status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookingDate);
    }
}
