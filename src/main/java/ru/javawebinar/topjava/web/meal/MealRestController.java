package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.*;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Controller
public class MealRestController {

    @Autowired
    MealService mealService;

    public Meal save(Meal meal) {
        return mealService.save(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        mealService.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id) {
        return mealService.get(id, AuthorizedUser.id());
    }

    public Meal update(Meal meal) {
        checkNew(meal);
        checkNotFoundWithId(mealService.get(meal.getId(), AuthorizedUser.id()), meal.getId());
        return mealService.save(meal, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAll() {
        return MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltered(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"), request);
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"), request);
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"), request);
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"), request);
        return MealsUtil.getWithExceeded(
                mealService.getAll(AuthorizedUser.id())
                        .stream()
                        .filter(meal -> isBetween(meal.getTime(),
                                Objects.isNull(startTime) ? LocalTime.MIN : startTime,
                                Objects.isNull(endTime) ? LocalTime.MAX : endTime))
                        .filter(meal -> isBetween(meal.getDate(), Objects.isNull(startDate) ? LocalDate.MIN : startDate,
                                Objects.isNull(endDate) ? LocalDate.MAX : endDate
                        ))
                        .collect(Collectors.toList())
                , MealsUtil.DEFAULT_CALORIES_PER_DAY
        );
    }
}