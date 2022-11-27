package edu.cmis.zfit.model;

public class ComparableActivity implements Comparable<ComparableActivity> {
    private Activity activity;

    public ComparableActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int compareTo(ComparableActivity comparableActivity) {
        if(this.activity.calories() < comparableActivity.activity.calories()) {
            return -1;
        } else if(this.activity.calories() > comparableActivity.activity.calories()) {
            return 1;
        } else {
            return 0;
        }
    }

    public String toString() {
        return activity.toString();
    }
}
