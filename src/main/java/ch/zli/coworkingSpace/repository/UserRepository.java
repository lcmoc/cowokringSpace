package ch.zli.coworkingSpace.repository;

import ch.zli.coworkingSpace.model.BookingDatesEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<BookingDatesEntity, Long> {
}
