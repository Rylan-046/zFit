package edu.cmis.zfit.util;

import edu.cmis.zfit.model.Activity;

import java.util.ArrayList;
import java.util.List;


/**
 * I utilize the Quick Sort to recursively sort activities by date in ascending order, using a divide and conquer
 * approach with partitions.
 * The computational efficiency for quicksort is:
 *
 *  Best-Case - O(n log n)
 *  Worst-Case - O(n^2)
 *  Average-Case - O(n log n)
 *
 */
public class ActivityQuickSorter implements ActivitySorter {
    @Override
    public List<Activity> sortByDate(ArrayList<Activity> activityList) {
        List<Activity> sortedActivityList = (List<Activity>) activityList.clone();
        sortByDate(sortedActivityList, 0, activityList.size() - 1);

        return sortedActivityList;
    }

    // Divides the list based on low and high pivots
    private int partition(List<Activity> activityList, int low, int high) {
        Activity activityPartition = activityList.get(high);
        int smallerIndex = (low - 1);
        for (int largerIndex = low; largerIndex < high; largerIndex++) {
            // Check if date of activity at largerIndex is less than current activity partition
            if (activityList.get(largerIndex).date().compareTo(activityPartition.date()) <= 0) {
                smallerIndex++;
                swap(activityList, largerIndex, smallerIndex, largerIndex);
            }
        }

        swap(activityList, high, smallerIndex + 1, high);

        return smallerIndex + 1;
    }

    /**
     * Swap activities within list based on designated parameters
     * @param activityList activity list to perform swap operation
     * @param high target index for larger value
     * @param smallerIndex source index for smaller value
     * @param largerIndex source index for larger value
     */
    private void swap(List<Activity> activityList, int high, int smallerIndex, int largerIndex) {
        Activity tmpActivity = activityList.get(smallerIndex);
        activityList.set(smallerIndex, activityList.get(largerIndex));
        activityList.set(high, tmpActivity);
    }

    private void sortByDate(List<Activity> activityList, int low, int high) {
        if (low < high) {
            // Create initial bounded partition
            int activityPartition = partition(activityList, low, high);

            // Sort each partition recursively
            sortByDate(activityList, low, activityPartition - 1);
            sortByDate(activityList, activityPartition + 1, high);
        }
    }
}
