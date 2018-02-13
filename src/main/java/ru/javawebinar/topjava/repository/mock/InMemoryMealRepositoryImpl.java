package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private Comparator<Meal> MEAL_COMPARATOR = Comparator.comparing(Meal::getDateTime);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        return repository.get(userId).put(meal.getId(), meal);

    }

    @Override
    public boolean delete(int id, int userId) {
        Meal found = repository.get(userId).values()
                .stream()
                .filter(meal -> meal.getId().equals(id))
                .findAny().orElse(null);
        return Objects.nonNull(found) && repository.get(userId).values().remove(found);
    }

    @Override
    public Meal get(int id, int userId) {
//        return repository.get(id);
        return repository.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
//        return repository.values();
        return repository.get(userId).values()
                .stream()
                .sorted(MEAL_COMPARATOR)
                .collect(Collectors.toList());

    }
}

