package com.example.woapp

class Constants {
    companion object{
        fun defaultExerciseList(): ArrayList<ExerciseModel> {
            val exerciseList = ArrayList<ExerciseModel>()

            val jumpingJacks = ExerciseModel(1, "Jumping Jacks", R.drawable.jumpingjacks, false, false)
            exerciseList.add(jumpingJacks)

            val sideKick = ExerciseModel(2, "Side Kicks", R.drawable.sidekick, false, false)
            exerciseList.add(sideKick)

            val squat = ExerciseModel(3, "Squat", R.drawable.squat, false, false)
            exerciseList.add(squat)

            val pushUp = ExerciseModel(4, "Push Up", R.drawable.pushup, false, false)
            exerciseList.add(pushUp)

            val buttBridge = ExerciseModel(5, "Butt Bridge", R.drawable.buttbridge, false, false)
            exerciseList.add(buttBridge)

            val lunges = ExerciseModel(6, "Lunges", R.drawable.lunges, false, false)
            exerciseList.add(lunges)

            val chairStepUp = ExerciseModel(7, "Chair StepUp", R.drawable.chairstepup, false, false)
            exerciseList.add(chairStepUp)

            val wallSit = ExerciseModel(8, "Wall Sit", R.drawable.wallsit, false, false)
            exerciseList.add(wallSit)

            return exerciseList
        }
    }
}