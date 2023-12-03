package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView


class ShopActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

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


        recyclerView = findViewById(R.id.recycler_view)

        // Создаем список товаров
        val items = listOf(
            Item("Обувь", "Описание", R.drawable.obuf),
            Item("Билет в Египет", "Описание", R.drawable.egipet),
            Item("Рюкзак", "Описание", R.drawable.rykzak),
            Item("Куртка", "Описание", R.drawable.kyrtka)
        )

        // Создаем адаптер
        val adapter = ItemAdapter(items)

        // Устанавливаем адаптер
        recyclerView.adapter = adapter

        // Настраиваем менеджер компоновки
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun navigateTo(activity: Class<out Activity>) {
        // Создаем намерение
        val intent = Intent(this, activity)

        // Запускаем намерение
        startActivity(intent)
    }
}



class Item(
    val title: String,
    val description: String,
    val imageResId: Int // Идентификатор ресурса изображения
)

class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.titleTextView.text = item.title
        holder.descriptionTextView.text = item.description
        holder.itemImageView.setImageResource(item.imageResId)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)
}