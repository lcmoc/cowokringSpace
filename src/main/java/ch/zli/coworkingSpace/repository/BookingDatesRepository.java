package ch.zli.coworkingSpace.repository;

import ch.zli.coworkingSpace.model.BookingDatesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface BookingDatesRepository extends CrudRepository<BookingDatesEntity, Long> {
}
