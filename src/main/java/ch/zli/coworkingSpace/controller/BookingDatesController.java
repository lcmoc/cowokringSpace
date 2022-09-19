package ch.zli.coworkingSpace.controller;

import ch.zli.coworkingSpace.model.BookingDatesEntity;
import ch.zli.coworkingSpace.service.BookingDatesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/booking")
@Tag(name = "Booking Dates", description = "Booking management endpoints")
public class BookingDatesController {
    private final BookingDatesService bookingDatesService;

    public BookingDatesController(BookingDatesService bookingDatesService) {
        this.bookingDatesService = bookingDatesService;
    }

    @Operation(
            summary = "Get all bookings",
            description = "Loads all bookings from database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @GetMapping
    List<BookingDatesEntity> loadAll() {
        return bookingDatesService.loadAll();
    }

}
