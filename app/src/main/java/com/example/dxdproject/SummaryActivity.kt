package com.example.dxdproject

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SummaryActivity : AppCompatActivity() {

    private lateinit var selectedRaceTextView: TextView
    private lateinit var attributesTextView: TextView
    private lateinit var pointsRemainingTextView: TextView // Adicione um TextView para pontos restantes
    private lateinit var hitPointsTextView: TextView // Adicione um TextView para pontos de vida

    // Definindo os modificadores raciais
    private val racialModifiers = mapOf(
        "Anão da Montanha" to intArrayOf(2, 0, 2, 0, 0, 0), // Força, Destreza, Constituição...
        "Humano" to intArrayOf(1, 1, 1, 1, 1, 1),
        "Draconato" to intArrayOf(2, 0, 0, 0, 0, 1),
        "Meio-Orc" to intArrayOf(2, 0, 1, 0, 0, 0),
        "Elfo" to intArrayOf(0, 2, 0, 0, 0, 0), // Destreza
        "Halfling" to intArrayOf(0, 2, 0, 0, 0, 0), // Destreza
        "Gnomo da Floresta" to intArrayOf(0, 1, 0, 0, 0, 2), // Inteligência
        "Gnomo das Rochas" to intArrayOf(0, 0, 1, 0, 0, 0), // Constituição
        "Alto Elfo" to intArrayOf(0, 0, 0, 1, 0, 0), // Inteligência
        "Tiefling" to intArrayOf(0, 0, 0, 0, 0, 2), // Carisma
        "Anão da Colina" to intArrayOf(0, 0, 1, 0, 1, 0), // Constituição, Sabedoria
        "Elfo da Floresta" to intArrayOf(0, 0, 0, 0, 1, 0), // Sabedoria
        "Meio-Elfo" to intArrayOf(0, 0, 0, 0, 0, 2), // Carisma
        "Drow" to intArrayOf(0, 0, 0, 0, 0, 1), // Carisma
        "Halfling Pés-Leves" to intArrayOf(0, 1, 0, 0, 0, 1) // Destreza, Carisma
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        selectedRaceTextView = findViewById(R.id.selected_race_text_view)
        attributesTextView = findViewById(R.id.attributes_text_view)
        pointsRemainingTextView = findViewById(R.id.points_remaining_text_view) // Inicialize o TextView
        hitPointsTextView = findViewById(R.id.hit_points_text_view) // Inicialize o TextView de pontos de vida

        // Recebe os dados da Intent
        val selectedRace = intent.getStringExtra("selected_race")
        val attributePoints = intent.getIntArrayExtra("attribute_points")
        val attributes = intent.getStringArrayExtra("attributes")
        val pointsRemaining = intent.getIntExtra("points_remaining", 0) // Recebe os pontos restantes

        // Exibe os dados
        selectedRaceTextView.text = "Raça Selecionada: $selectedRace"

        // Aplica os modificadores raciais aqui
        val racialModifiers = getRacialModifiers(selectedRace)
        val totalPointsWithModifiers = attributePoints?.mapIndexed { index, points ->
            points + racialModifiers[index]
        }?.toIntArray() ?: IntArray(0)

        attributesTextView.text = formatAttributes(totalPointsWithModifiers, attributes)

        // Calcular e exibir os pontos de vida
        val hitPoints = calculateHitPoints(totalPointsWithModifiers)
        hitPointsTextView.text = "Pontos de Vida: $hitPoints" // Atualize o TextView com os pontos de vida

        // Exibe os pontos restantes
        pointsRemainingTextView.text = "Pontos Restantes: $pointsRemaining" // Atualize o TextView com os pontos restantes
    }

    private fun getRacialModifiers(selectedRace: String?): IntArray {
        return when (selectedRace) {
            "Anão da Montanha" -> intArrayOf(2, 0, 2, 0, 0, 0) // Exemplo de modificadores
            "Humano" -> intArrayOf(1, 1, 1, 1, 1, 1)
            "Draconato" -> intArrayOf(2, 0, 0, 0, 0, 1)
            "Meio-Orc" -> intArrayOf(2, 0, 1, 0, 0, 0)
            "Elfo" -> intArrayOf(0, 2, 0, 0, 0, 0)
            "Halfling" -> intArrayOf(0, 2, 0, 0, 0, 0)
            "Gnomo da Floresta" -> intArrayOf(0, 1, 0, 0, 0, 2)
            "Gnomo das Rochas" -> intArrayOf(0, 0, 1, 0, 0, 0)
            "Alto Elfo" -> intArrayOf(0, 0, 0, 1, 0, 0)
            "Tiefling" -> intArrayOf(0, 0, 0, 0, 0, 2)
            "Anão da Colina" -> intArrayOf(0, 0, 1, 0, 1, 0)
            "Elfo da Floresta" -> intArrayOf(0, 0, 0, 0, 1, 0)
            "Meio-Elfo" -> intArrayOf(0, 0, 0, 0, 0, 2)
            "Drow" -> intArrayOf(0, 0, 0, 0, 0, 1)
            "Halfling Pés-Leves" -> intArrayOf(0, 1, 0, 0, 0, 1)
            else -> IntArray(6) { 0 } // Nenhum modificador
        }
    }

    private fun calculateHitPoints(attributePoints: IntArray?): Int {
        val constitutionIndex = 2 // Considerando que a Constituição é o terceiro atributo (índice 2)
        val baseHitPoints = 10 // Valor base de PV, pode ser alterado conforme a regra do seu jogo
        val constitutionBonus = attributePoints?.get(constitutionIndex) ?: 0
        return baseHitPoints + constitutionBonus
    }

    private fun formatAttributes(attributePoints: IntArray?, attributes: Array<String>?): String {
        return attributes?.mapIndexed { index, attribute ->
            "$attribute: ${attributePoints?.get(index)}"
        }?.joinToString("\n") ?: ""
    }
}
