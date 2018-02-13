package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    AtomicInteger counter = new AtomicInteger(0);
    Map<Integer, User> userRepo = new ConcurrentHashMap<>();


    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        if (Objects.nonNull(userRepo.get(id))) {
            return Objects.nonNull(userRepo.remove(id));
        }
        return false;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        return userRepo.put(user.getId(), user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return Optional.ofNullable(userRepo.get(id)).orElseThrow(() -> new NotFoundException("not found User with id: " + id));
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> users = new ArrayList<>(userRepo.values());
        users.sort(Comparator.comparing(AbstractNamedEntity::getName));
        return users;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userRepo.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElseThrow(() -> new NotFoundException("not found User with email: " + email));
    }
}
