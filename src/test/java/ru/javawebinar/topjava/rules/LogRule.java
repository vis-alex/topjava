package ru.javawebinar.topjava.rules;

import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.util.Date;

public class LogRule extends TestName implements TestRule {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    public static StringBuilder testsRepost = new StringBuilder().append("\n");

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Date begin = new Date();
                base.evaluate();
                Date end = new Date();

                log.info("\nTest runtime : {} mc", end.getTime() - begin.getTime());

                testsRepost
                        .append("Test ")
                        .append(description.getMethodName())
                        .append(" - ")
                        .append(end.getTime() - begin.getTime())
                        .append(" mc\n");
            }
        };
    }
}
