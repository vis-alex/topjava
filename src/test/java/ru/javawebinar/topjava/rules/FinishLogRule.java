package ru.javawebinar.topjava.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.util.Date;

public class FinishLogRule implements TestRule {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Override
    public Statement apply(Statement base, Description description) {
       return new Statement() {
           @Override
           public void evaluate() throws Throwable {
               base.evaluate();
               log.info("\n\nTests Report : {} \n", LogRule.testsRepost);
           }
       };
    }
}
