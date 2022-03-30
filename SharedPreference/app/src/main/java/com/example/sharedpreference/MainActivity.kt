package com.example.sharedpreference

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedpreference.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /**
         * sharedPreferencesをインスタンス　getSharedPreferencesメソッドを呼出し。
         */
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

        /**
         * ボタン押下時に、カスタムクラスをsharedPreferencesに保存　要素は部位とトレーニング名とした。
         */
        binding.memoButton.setOnClickListener {
            val trainingName = binding.trainingNameEditText.text.toString()
            val trainingPositionSpinnerIndex = spinner.selectedItemPosition
            val trainingPosition = TrainingMenu.TrainingPosition.indexFor(trainingPositionSpinnerIndex)
            val gson = Gson()
            val trainingMenuInfo: String = gson.toJson(TrainingMenu(trainingName, trainingPosition))
            sharedPreferences.edit().putString("training", trainingMenuInfo).apply()
        }

        /**
         * 保存内容を表示ボタンを押下すると保存内容を表示する
         * sharedPreferencesクラスのメソッドのgetStringで保存内容を取得（String型)
         * GsonクラスのメソッドのfromJsonでString型をカスタムクラスのTrainingMenu型に変換
         * textViewにtrainingMenu(TrainingMenu型)をString型に変換して表示する
         */
        binding.displayButton.setOnClickListener {
            val trainingMenuInfo = sharedPreferences.getString("training", null)
            val trainingMenu = Gson().fromJson(trainingMenuInfo, TrainingMenu::class.java) as TrainingMenu
            binding.textView.setText(trainingMenu.toString())
        }
    }
}