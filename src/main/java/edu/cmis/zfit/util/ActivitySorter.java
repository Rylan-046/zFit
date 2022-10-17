package edu.cmis.zfit.util;

import edu.cmis.zfit.model.Activity;

import java.util.ArrayList;
import java.util.List;

public interface ActivitySorter {
    List<Activity> sortByDate(ArrayList<Activity> activityList);
}
