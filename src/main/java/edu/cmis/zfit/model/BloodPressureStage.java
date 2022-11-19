package edu.cmis.zfit.model;

public enum BloodPressureStage {
    NORMAL(new Range(0f, 120f), new Range(0f, 80f)),
    ELEVATED(new Range(120f, 129f), new Range(0f, 80f)),
    HYPERTENSION_I(new Range(130f, 139f), new Range(80f, 89f)),
    HYPERTENSION_II(new Range(140f, null), new Range(90f, null)),
    HYPERTENSIVE_CRISIS(new Range(180f, null), new Range(120f, null));

    private Range systolicRange;
    private Range diastolicRange;

    BloodPressureStage(Range systolicRange, Range diastolicRange) {
        this.systolicRange = systolicRange;
        this.diastolicRange = diastolicRange;
    }

    public Range getSystolicRange() {
        return systolicRange;
    }

    public Range getDiastolicRange() {
        return diastolicRange;
    }
}
