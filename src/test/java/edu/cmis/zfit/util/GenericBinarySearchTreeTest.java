package edu.cmis.zfit.util;

import edu.cmis.zfit.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class GenericBinarySearchTreeTest {
    GenBinarySearchTree<ComparableActivity> genBinarySearchTree;
    
    @BeforeEach
    public void setup() {
        genBinarySearchTree = new GenBinarySearchTree<>();
        Instant currentTime = Instant.now();

        genBinarySearchTree.add(
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

        genBinarySearchTree.add(
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

        genBinarySearchTree.add(
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

        genBinarySearchTree.add(
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

        genBinarySearchTree.add(
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

        genBinarySearchTree.add(
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

        genBinarySearchTree.add(
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

        genBinarySearchTree.add(
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
        System.out.println("sizeAroony: " + genBinarySearchTree.getSize());
        System.out.println("Min " + genBinarySearchTree.findMinValue());

//        Assertions.assertEquals(3, genBinarySearchTree.findMinValue());
    }

    @Test
    public void findMaxValueTest() {
        System.out.println("sizeAroony: " + genBinarySearchTree.getSize());
        System.out.println("Max " + genBinarySearchTree.findMaxValue());

//        Assertions.assertEquals(9, genBinarySearchTree.findMaxValue());
    }
}