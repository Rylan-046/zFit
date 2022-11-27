package edu.cmis.zfit.util;

import edu.cmis.zfit.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class BinarySearchTreeTest {
    BinarySearchTree<ComparableActivity> binarySearchTree;
    
    @BeforeEach
    public void setup() {
        binarySearchTree = new BinarySearchTree<>();
        Instant currentTime = Instant.now();

        binarySearchTree.add(
                new ComparableActivity(
                        new BurnActivity(
                                UUID.randomUUID().toString(),
                                225,
                                122,
                                70,
                                new BloodPressure(120, 180),
                                120,
                                38,
                                62,
                                BurnActivityType.WALKING,
                                4800,
                                new DateRange(
                                        currentTime,
                                        currentTime.plus(25, ChronoUnit.MINUTES)
                                )
                        )));

        binarySearchTree.add(
                new ComparableActivity(
                        new BurnActivity(
                                UUID.randomUUID().toString(),
                                456,
                                122,
                                70,
                                new BloodPressure(120, 180),
                                120,
                                38,
                                62,
                                BurnActivityType.RUNNING,
                                4800,
                                new DateRange(
                                        currentTime,
                                        currentTime.plus(25, ChronoUnit.MINUTES)
                                )
                        )));

        binarySearchTree.add(
                new ComparableActivity(
                        new BurnActivity(
                                UUID.randomUUID().toString(),
                                675,
                                122,
                                70,
                                new BloodPressure(120, 180),
                                120,
                                38,
                                62,
                                BurnActivityType.RESTING,
                                4800,
                                new DateRange(
                                        currentTime,
                                        currentTime.plus(25, ChronoUnit.MINUTES)
                                )
                        )));

        binarySearchTree.add(
                new ComparableActivity(
                        new BurnActivity(
                                UUID.randomUUID().toString(),
                                112,
                                122,
                                70,
                                new BloodPressure(120, 180),
                                120,
                                38,
                                62,
                                BurnActivityType.RUNNING,
                                4800,
                                new DateRange(
                                        currentTime,
                                        currentTime.plus(25, ChronoUnit.MINUTES)
                                )
                        )));

        binarySearchTree.add(
                new ComparableActivity(
                        new BurnActivity(
                                UUID.randomUUID().toString(),
                                423,
                                122,
                                70,
                                new BloodPressure(120, 180),
                                120,
                                38,
                                62,
                                BurnActivityType.WEIGHT_LIFTING,
                                4800,
                                new DateRange(
                                        currentTime,
                                        currentTime.plus(25, ChronoUnit.MINUTES)
                                )
                        )));

        binarySearchTree.add(
                new ComparableActivity(
                        new BurnActivity(
                                UUID.randomUUID().toString(),
                                311,
                                122,
                                70,
                                new BloodPressure(120, 180),
                                120,
                                38,
                                62,
                                BurnActivityType.RUNNING,
                                4800,
                                new DateRange(
                                        currentTime,
                                        currentTime.plus(25, ChronoUnit.MINUTES)
                                )
                        )));

        binarySearchTree.add(
                new ComparableActivity(
                        new BurnActivity(
                                UUID.randomUUID().toString(),
                                368,
                                122,
                                70,
                                new BloodPressure(120, 180),
                                120,
                                38,
                                62,
                                BurnActivityType.WALKING,
                                4800,
                                new DateRange(
                                        currentTime,
                                        currentTime.plus(25, ChronoUnit.MINUTES)
                                )
                        )));

        binarySearchTree.add(
                new ComparableActivity(
                        new BurnActivity(
                                UUID.randomUUID().toString(),
                                590,
                                122,
                                70,
                                new BloodPressure(120, 180),
                                120,
                                38,
                                62,
                                BurnActivityType.RESTING,
                                4800,
                                new DateRange(
                                        currentTime,
                                        currentTime.plus(25, ChronoUnit.MINUTES)
                                )
                        )));
    }

    @Test
    public void findMinValueTest() {
        Assertions.assertEquals(112, binarySearchTree.findMinValue().getActivity().calories());
    }

    @Test
    public void findMaxValueTest() {
        Assertions.assertEquals(675, binarySearchTree.findMaxValue().getActivity().calories());
    }
}