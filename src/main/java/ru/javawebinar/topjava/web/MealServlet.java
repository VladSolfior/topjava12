package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * V.B. on
 * 11-Jan-18.
 */
public class MealServlet extends HttpServlet {

    public static final int DEF_CALORIES_PER_DAY = 2000;

    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        List<Meal> meals = MealsUtil.initMeals();
        List<MealWithExceed> filteredWithExceeded = MealsUtil.getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, DEF_CALORIES_PER_DAY);
        Comparator<MealWithExceed> dateComparator = Comparator.comparing(MealWithExceed::getDateTime);
        Comparator<MealWithExceed> caloriesComparator = Comparator.comparing(MealWithExceed::getCalories);
        filteredWithExceeded.sort(dateComparator.thenComparing(caloriesComparator));
        req.setAttribute("meals", filteredWithExceeded);
//        resp.sendRedirect("meals.jsp");
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);

    }
}
