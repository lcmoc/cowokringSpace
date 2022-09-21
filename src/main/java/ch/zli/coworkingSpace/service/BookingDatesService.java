package ch.zli.coworkingSpace.service;

import ch.zli.coworkingSpace.model.BookingDatesEntity;
import ch.zli.coworkingSpace.repository.BookingDatesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookingDatesService {
    private final BookingDatesRepository repository;

    public BookingDatesService(BookingDatesRepository bookingDatesRepository) {
        this.repository = bookingDatesRepository;
    }

    public List<BookingDatesEntity> loadAll() {
        log.info("Executing find all booking dates ...");
        return (List<BookingDatesEntity>) repository.findAll();
    }

    public Optional<BookingDatesEntity> loadOne(Long bookingId) {
        log.info("Executing find booking date with id " + bookingId + " ...");
        return repository.findById(bookingId);
    }

    public BookingDatesEntity create(BookingDatesEntity bookingDate) {
        log.info("Executing create booking date with id " + bookingDate.getId() + " ...");
        return repository.save(bookingDate);
    }

    @Transactional
    public BookingDatesEntity update(BookingDatesEntity updateBookingDate) {
        log.info("Executing update booking date with id " + updateBookingDate.getId() + " ...");
        return repository.save(updateBookingDate);
    }

    public void delete(Long bookingDateId) {
        log.info("Executing delete game with id " + bookingDateId + " ...");
        repository.deleteById(bookingDateId);
    }
}
