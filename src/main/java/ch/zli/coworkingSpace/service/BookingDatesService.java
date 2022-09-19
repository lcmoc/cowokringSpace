package ch.zli.coworkingSpace.service;

import ch.zli.coworkingSpace.model.BookingDatesEntity;
import ch.zli.coworkingSpace.repository.BookingDatesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class BookingDatesService {
    private final BookingDatesRepository repository;


    public BookingDatesService(BookingDatesRepository bookingDatesRepository) {
        this.repository = bookingDatesRepository;
    }

    public List<BookingDatesEntity> loadAll() {
        log.info("Executing find all booking dates ...");
        return repository.findAll();
    }

    public Optional<BookingDatesEntity> loadOne(UUID gameId) {
        log.info("Executing find booking date with id " + gameId + " ...");
        return repository.findById(gameId);
    }

    public BookingDatesEntity create(BookingDatesEntity bookingDate) {
        log.info("Executing create booking date with id " + bookingDate.getId() + " ...");
        return repository.save(bookingDate);
    }

    public BookingDatesEntity update(BookingDatesEntity updateBookingDate) {
        log.info("Executing update booking date with id " + updateBookingDate.getId() + " ...");

        /*val entry = Optional<GameEntity> = repository.findById(updatedGame.getId());

        if(entry.isPresent()) {
            return repository.save(updatedGame);
        }
         */

        //present if erstellen
        // transaction erstellen

        return repository.save(updateBookingDate);
    }

    public void delete(UUID bookingDateId) {
        log.info("Executing delete game with id " + bookingDateId + " ...");
        repository.deleteById(bookingDateId);
    }
}
