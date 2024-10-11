package com.example.dxdproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KMutableProperty0

class DistributionPointsActivity : AppCompatActivity() {

    private lateinit var tvRacaSelecionada: TextView
    private lateinit var tvPontosRestantes: TextView

    private var pontosRestantes = 27

    private var forca = 8
    private var destreza = 8
    private var constituicao = 8
    private var inteligencia = 8
    private var sabedoria = 8
    private var carisma = 8

    // Modificadores raciais, a serem ajustados de acordo com a raça
    private var modForca = 0
    private var modDestreza = 0
    private var modConstituicao = 0
    private var modInteligencia = 0
    private var modSabedoria = 0
    private var modCarisma = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distribution_points)

        tvRacaSelecionada = findViewById(R.id.tv_raca_selecionada)
        tvPontosRestantes = findViewById(R.id.tv_pontos_restantes)

        // Recebendo a raça selecionada da MainActivity
        val selectedRace = intent.getStringExtra("selected_race") ?: "Desconhecida"
        tvRacaSelecionada.text = "Raça Selecionada: $selectedRace"

        // Definindo modificadores raciais com base na raça selecionada
        setRaceModifiers(selectedRace)

        tvPontosRestantes.text = "Pontos Restantes: $pontosRestantes"

        // Botões de aumentar/diminuir Força
        setupAttributeButtons(R.id.btn_aumentar_forca, R.id.btn_diminuir_forca, ::forca, ::modForca)

        // Botões de aumentar/diminuir Destreza
        setupAttributeButtons(R.id.btn_aumentar_destreza, R.id.btn_diminuir_destreza, ::destreza, ::modDestreza)

        // Botões de aumentar/diminuir Constituição
        setupAttributeButtons(R.id.btn_aumentar_constituicao, R.id.btn_diminuir_constituicao, ::constituicao, ::modConstituicao)

        // Botões de aumentar/diminuir Inteligência
        setupAttributeButtons(R.id.btn_aumentar_inteligencia, R.id.btn_diminuir_inteligencia, ::inteligencia, ::modInteligencia)

        // Botões de aumentar/diminuir Sabedoria
        setupAttributeButtons(R.id.btn_aumentar_sabedoria, R.id.btn_diminuir_sabedoria, ::sabedoria, ::modSabedoria)

        // Botões de aumentar/diminuir Carisma
        setupAttributeButtons(R.id.btn_aumentar_carisma, R.id.btn_diminuir_carisma, ::carisma, ::modCarisma)

        // Botão Salvar
        val btnSalvar: Button = findViewById(R.id.btn_salvar)
        btnSalvar.setOnClickListener {
            // Salvar os dados no SQLite
            val dbHelper = DatabaseHelper(this)
            dbHelper.saveCharacter(
                selectedRace,
                forca + modForca,
                destreza + modDestreza,
                constituicao + modConstituicao,
                inteligencia + modInteligencia,
                sabedoria + modSabedoria,
                carisma + modCarisma,
                pontosRestantes
            )

            // Navegar para a tela de resumo
            val intent = Intent(this, SummaryActivity::class.java).apply {
                putExtra("selected_race", selectedRace)
                putExtra("attribute_points", intArrayOf(forca, destreza, constituicao, inteligencia, sabedoria, carisma))
                putExtra("attributes", arrayOf("Força", "Destreza", "Constituição", "Inteligência", "Sabedoria", "Carisma"))
                putExtra("points_remaining", pontosRestantes)
            }
            startActivity(intent)
            finish() // Opcional: para fechar a tela atual
        }
    }

    private fun setupAttributeButtons(btnIncreaseId: Int, btnDecreaseId: Int, attribute: KMutableProperty0<Int>, modifier: KMutableProperty0<Int>) {
        val btnIncrease: Button = findViewById(btnIncreaseId)
        val btnDecrease: Button = findViewById(btnDecreaseId)

        btnIncrease.setOnClickListener {
            if (pontosRestantes > 0 && attribute.get() < 15) {
                pontosRestantes -= custoAumento(attribute.get()) // Deduz o custo de aumento dos pontos restantes
                attribute.set(attribute.get() + 1)
                updateUI()
            }
        }

        btnDecrease.setOnClickListener {
            if (attribute.get() > 8) {
                attribute.set(attribute.get() - 1)
                pontosRestantes += custoAumento(attribute.get()) // Adiciona o custo de diminuição dos pontos restantes
                updateUI()
            }
        }
    }

    private fun setRaceModifiers(selectedRace: String) {
        when (selectedRace) {
            "AnaoDaMontanha" -> {
                modForca = 2
                modConstituicao = 2
            }
            "Draconato" -> {
                modForca = 2
                modCarisma = 1
            }
            // Adicione outros casos de raças aqui
            else -> {
                // Modificadores padrão
                modForca = 0
                modDestreza = 0
                modConstituicao = 0
                modInteligencia = 0
                modSabedoria = 0
                modCarisma = 0
            }
        }
    }

    private fun updateUI() {
        tvPontosRestantes.text = "Pontos Restantes: $pontosRestantes"
        findViewById<TextView>(R.id.tv_forca).text = "Força: ${forca + modForca}"
        findViewById<TextView>(R.id.tv_destreza).text = "Destreza: ${destreza + modDestreza}"
        findViewById<TextView>(R.id.tv_constituicao).text = "Constituição: ${constituicao + modConstituicao}"
        findViewById<TextView>(R.id.tv_inteligencia).text = "Inteligência: ${inteligencia + modInteligencia}"
        findViewById<TextView>(R.id.tv_sabedoria).text = "Sabedoria: ${sabedoria + modSabedoria}"
        findViewById<TextView>(R.id.tv_carisma).text = "Carisma: ${carisma + modCarisma}"
    }

    private fun custoAumento(valor: Int): Int {
        return when (valor) {
            8 -> 1 // Custo para aumentar de 8 a 9 é 1 ponto
            9 -> 2 // Custo para aumentar de 9 a 10 é 2 pontos
            10 -> 3 // Custo para aumentar de 10 a 11 é 3 pontos
            11 -> 4 // Custo para aumentar de 11 a 12 é 4 pontos
            12 -> 5 // Custo para aumentar de 12 a 13 é 5 pontos
            13 -> 7 // Custo para aumentar de 13 a 14 é 7 pontos
            14 -> 9 // Custo para aumentar de 14 a 15 é 9 pontos
            else -> 0 // Não deve ser possível aumentar abaixo de 8 ou acima de 15
        }
    }
}
