package ch.zli.coworkingSpace.service;

import ch.zli.coworkingSpace.model.BookingDatesEntity;
import ch.zli.coworkingSpace.model.UserEntity;
import ch.zli.coworkingSpace.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    public List<UserEntity> loadAll() {
        log.info("Executing find all booking dates ...");
        return (List<UserEntity>) repository.findAll();
    }

    public Optional<UserEntity> loadOne(Long id) {
        log.info("Executing find booking date with id " + id + " ...");
        return repository.findById(id);
    }

    public UserEntity create(UserEntity userEntity) {
        log.info("Executing create booking date with id " + userEntity.getId() + " ...");
        return repository.save(userEntity);
    }

    public UserEntity update(UserEntity updateUser) {
        log.info("Executing update booking date with id " + updateUser.getId() + " ...");

        /*val entry = Optional<GameEntity> = repository.findById(updatedGame.getId());

        if(entry.isPresent()) {
            return repository.save(updatedGame);
        }
         */

        //present if erstellen
        // transaction erstellen

        return repository.save(updateUser);
    }

    public void delete(Long userId) {
        log.info("Executing delete game with id " + userId + " ...");
        repository.deleteById(userId);
    }
}
