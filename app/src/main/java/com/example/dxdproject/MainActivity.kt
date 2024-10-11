package com.example.dxdproject

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var raceListView: ListView
    private val races = arrayOf(
        "Anão da Montanha",
        "Draconato",
        "Humano",
        "Meio-Orc",
        "Elfo",
        "Halfling",
        "Gnomo da Floresta",
        "Anão",
        "Halfling Robusto",
        "Gnomo das Rochas",
        "Alto Elfo",
        "Tiefling",
        "Anão da Colina",
        "Elfo da Floresta",
        "Meio Elfo",
        "Drow",
        "Halfling Pés Leves",
        "Gnomo"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        raceListView = findViewById(R.id.race_list_view)

        // Configurando o adapter para o ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, races)
        raceListView.adapter = adapter

        // Configurando o clique na lista de raças
        raceListView.setOnItemClickListener { _, _, position, _ ->
            val selectedRace = races[position]
            val intent = Intent(this, DistributionPointsActivity::class.java)
            intent.putExtra("selected_race", selectedRace) // Passa a raça selecionada para a próxima atividade
            startActivity(intent)
        }
    }
}
