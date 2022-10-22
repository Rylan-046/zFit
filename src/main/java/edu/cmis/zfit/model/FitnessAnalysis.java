package edu.cmis.zfit.model;

public record FitnessAnalysis(UserProfile userProfile,
                              HeartRating heartRating,
                              WeightRating weightRating,
                              ExerciseRating exerciseRating,
                              float bodyMassIndex,
                              CaloricRating caloricRating) {
}