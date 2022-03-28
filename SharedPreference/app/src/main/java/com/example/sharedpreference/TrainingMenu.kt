package com.example.sharedpreference

data class TrainingMenu(val trainingName: String, val trainingPosition: TrainingPosition) {
    enum class TrainingPosition(val index: Int) {
        CHEST(0),
        SHOULDER(1),
        ARM(2),
        BACK(3),
        ABS(4),
        LEG(5);

        companion object {
            fun indexFor(trainingPositionSpinnerIndex: Int): TrainingPosition {
                var trainingPosition = CHEST
                for (training in values()) {
                    if (training.index == trainingPositionSpinnerIndex) {
                        trainingPosition = training
                    }
                }
                return trainingPosition
            }
        }
    }
}
