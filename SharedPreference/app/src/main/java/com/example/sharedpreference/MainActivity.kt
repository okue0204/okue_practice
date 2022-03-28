package com.example.sharedpreference

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.sharedpreference.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sharedPreferences = getSharedPreferences("trainingMemo", MODE_PRIVATE)

        val trainingPositionList: List<TrainingMenu.TrainingPosition> = listOf(
            TrainingMenu.TrainingPosition.CHEST,
            TrainingMenu.TrainingPosition.SHOULDER,
            TrainingMenu.TrainingPosition.ARM,
            TrainingMenu.TrainingPosition.BACK,
            TrainingMenu.TrainingPosition.ABS,
            TrainingMenu.TrainingPosition.LEG
        )

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, trainingPositionList)
        val spinner = binding.trainingPositionSpinner
        spinner.adapter = spinnerAdapter

        binding.memoButton.setOnClickListener {
            val trainingName = binding.trainingNameEditText.text.toString()
            val trainingPositionSpinnerIndex = spinner.selectedItemPosition
            val trainingPosition = TrainingMenu.TrainingPosition.indexFor(trainingPositionSpinnerIndex)
            val gson = Gson()
            val jsonString = gson.toJson(TrainingMenu(trainingName, trainingPosition))
            sharedPreferences.edit().putString("training", jsonString).apply()
        }
        val trainingMemo = sharedPreferences.getString("training", null)
        binding.textView.setText(trainingMemo)
    }
}