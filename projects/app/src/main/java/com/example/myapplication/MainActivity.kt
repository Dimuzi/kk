package com.example.myapplication


import android.app.Activity
import android.widget.Spinner
import android.view.View
import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
import android.content.Intent




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Получение ссылок на кнопки навигационной панели
        val btnMain = findViewById<Button>(R.id.btnMain)
        val btnShop = findViewById<Button>(R.id.btnShop)
        val btnGame = findViewById<Button>(R.id.btnGame)

        // Установка обработчиков событий для кнопок
        btnMain.setOnClickListener {
            navigateTo(MainActivity::class.java)
        }
        btnShop.setOnClickListener {
            navigateTo(ShopActivity::class.java)
        }
        btnGame.setOnClickListener {

        }

    }

    fun navigateTo(activity: Class<out Activity>) {
        // Создаем намерение
        val intent = Intent(this, activity)

        // Запускаем намерение
        startActivity(intent)
    }

    // Метод для обработки нажатия на кнопку
    fun onFilterButtonClick(view: View) {
        // Получаем макет диалогового окна
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_layout, null)

        // Создаем диалоговое окно
        val builder = AlertDialog.Builder(this)
            .setTitle("Выберите опции фильтрации")
            .setView(dialogLayout)
            .setPositiveButton("Применить") { dialog, _ ->
                // Обработка выбора опций
                handleFilterOptions(dialogLayout)
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }

        // Показываем диалоговое окно
        val dialog = builder.create()
        dialog.show()
    }

    // Метод для обработки выбранных опций
    // Ваша активность

    private fun handleFilterOptions(dialogLayout: View) {
        val spinner1 = dialogLayout.findViewById<Spinner>(R.id.spinner1)
        val spinner2 = dialogLayout.findViewById<Spinner>(R.id.spinner2)
        val spinner3 = dialogLayout.findViewById<Spinner>(R.id.spinner3)
        val spinner4 = dialogLayout.findViewById<Spinner>(R.id.spinner4)

        // Получите выбранные значения из Spinner'ов
        val selectedOption1 = spinner1.selectedItem.toString()
        val selectedOption2 = spinner2.selectedItem.toString()
        val selectedOption3 = spinner3.selectedItem.toString()
        val selectedOption4 = spinner4.selectedItem.toString()

    }

    fun onSearchButtonClick(view: android.view.View) {
        // При нажатии на кнопку, вызывается эта функция

        // Здесь вы можете добавить код для чтения JSON-файла и обработки данных
        val inputStream = resources.openRawResource(R.raw.almaty)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)

        val cardsContainer = findViewById<LinearLayout>(R.id.cardsContainer)
        cardsContainer.removeAllViews()

        // Добавляем карточки на основе данных из JSON-файла
        for (i in 0 until jsonArray.length()) {
            val cardData = jsonArray.optJSONObject(i)
            val title = cardData.optString("name_ru")
            val fullDescription = cardData.optString("description_ru") // Полное описание

            // Создаем макет карточки
            val cardLayout = LayoutInflater.from(this).inflate(R.layout.card_layout, null) as LinearLayout

            // Находим TextView и Button в макете и устанавливаем текст
            val titleTextView = cardLayout.findViewById<TextView>(R.id.titleTextView)
            val descriptionTextView = cardLayout.findViewById<TextView>(R.id.partialDescriptionTextView)
            val detailsButton = cardLayout.findViewById<Button>(R.id.detailsButton)

            titleTextView.text = title

            // Ограниченное описание, которое будет изменяться при раскрытии/сворачивании
            val partialDescription = cardData.optString("description_ru").substring(0, 50) + "..."

            descriptionTextView.text = partialDescription

            var isExpanded = false // Флаг для отслеживания состояния описания

            // Обработчик события для кнопки "Подробнее"
            detailsButton.setOnClickListener {
                // Переключаем состояние описания при каждом нажатии
                isExpanded = !isExpanded

                if (isExpanded) {
                    // Если описание раскрыто, показываем полное описание
                    descriptionTextView.text = fullDescription
                } else {
                    // Если описание свернуто, показываем ограниченное описание
                    descriptionTextView.text = partialDescription
                }
            }

            // Добавляем карточку в контейнер
            cardsContainer.addView(cardLayout)
        }

    }

}


