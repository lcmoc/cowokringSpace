package ch.zli.coworkingSpace.repository;

import ch.zli.coworkingSpace.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String findByEmail);
}
