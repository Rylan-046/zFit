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

        activityList.add(new BurnActivity(
                        UUID.randomUUID().toString(),
                        250,
                        125,
                        65,
                        100,
                        88,
                        120,
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
                        1250,
                        75,
                        65,
                        95,
                        234,
                        72,
                        Instant.now(),
                        ConsumptionActivityType.EATING
                )
        );

        activityList.add(new ConsumptionActivity(
                        UUID.randomUUID().toString(),
                        675,
                        78,
                        64,
                        99,
                        634,
                        66,
                        Instant.now().minus(8, ChronoUnit.HOURS),
                        ConsumptionActivityType.EATING
                )
        );

        activityList.add(new ConsumptionActivity(
                        UUID.randomUUID().toString(),
                        442,
                        72,
                        68,
                        100,
                        160,
                        55,
                        Instant.now().minus(15, ChronoUnit.HOURS),
                        ConsumptionActivityType.DRINKING
                )
        );

        activityList.add(new BurnActivity(
                        UUID.randomUUID().toString(),
                        250,
                        125,
                        65,
                        100,
                        88,
                        120,
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