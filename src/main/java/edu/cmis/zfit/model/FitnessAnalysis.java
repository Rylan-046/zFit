package edu.cmis.zfit.model;

public record FitnessAnalysis(UserProfile userProfile,
                              HeartRating heartRating,
                              BMIRating bmiRating,
                              ExerciseRating exerciseRating,
                              float bodyMassIndex
                              ) {
}