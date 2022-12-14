package edu.cmis.zfit.util;

import edu.cmis.zfit.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ActivityQuickSorterTest {
    private ActivitySorter activitySorter = new ActivityQuickSorter();
    private ArrayList<Activity> activityList;

    @BeforeEach
    public void setup() {
        activityList = new ArrayList<>();
        Instant currentTime = Instant.now();
        String userId = "lex@gmail.com";

        activityList.add(new BurnActivity(
                        UUID.randomUUID().toString(),
                        userId,
                        250,
                        125,
                        65,
                        new BloodPressure(78, 125),
                        88,
                        120,
                        64,
                        BurnActivityType.RUNNING,
                        5200,
                        new DateRange(
                                currentTime.plus(4, ChronoUnit.HOURS),
                                currentTime.plus(8, ChronoUnit.HOURS)
                        )
                )
        );

        activityList.add(new ConsumptionActivity(
                        UUID.randomUUID().toString(),
                        userId,
                        1250,
                        75,
                        65,
                        new BloodPressure(84, 145),
                        234,
                        72,
                        58,
                        Instant.now(),
                        ConsumptionActivityType.EATING
                )
        );

        activityList.add(new ConsumptionActivity(
                        UUID.randomUUID().toString(),
                        userId,
                        675,
                        78,
                        64,
                        new BloodPressure(68, 122),
                        634,
                        66,
                        67,
                        Instant.now().minus(8, ChronoUnit.HOURS),
                        ConsumptionActivityType.EATING
                )
        );

        activityList.add(new ConsumptionActivity(
                        UUID.randomUUID().toString(),
                        userId,
                        442,
                        72,
                        68,
                        new BloodPressure(58, 135),
                        160,
                        55,
                        70,
                        Instant.now().minus(15, ChronoUnit.HOURS),
                        ConsumptionActivityType.DRINKING
                )
        );

        activityList.add(new BurnActivity(
                        UUID.randomUUID().toString(),
                        userId,
                        250,
                        125,
                        65,
                        new BloodPressure(69, 128),
                        88,
                        120,
                        65,
                        BurnActivityType.RUNNING,
                        5200,
                        new DateRange(
                                currentTime.plus(1, ChronoUnit.HOURS),
                                currentTime.plus(2, ChronoUnit.HOURS)
                        )
                )
        );
    }

    @Test
    public void sortByDateTest() {
        listActivities(activityList, false);
        listActivities(activitySorter.sortByDate(activityList), true);
    }

    public void listActivities(List<Activity> activityList, boolean sorted) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println("sorted='" + sorted + "'");

        for (Activity activity : activityList) {
            System.out.println("id='" + activity.id() + "', date='" + formatter.format(Date.from(activity.date())) + "'");
        }
    }
}