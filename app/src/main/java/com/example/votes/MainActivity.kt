package com.example.votes


import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.votes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listTextPartidos: MutableList<TextView>
    private lateinit var listImagePartidos: MutableList<ImageView>
    private lateinit var listCardPartidos: MutableList<CardView>
    private lateinit var resultadosVotos: MutableList<Int>

    private var alianza = 0
    private var pan = 0
    private var prd = 0
    private var pri = 0
    private var pt = 0
    private var pv = 0

    private val TEXTO_NUMV = "Votos: "
    private var TOTAL_VOTOS = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportActionBar?.hide()
        inicializarElementos()
        detectarElemento()
        binding.btnResultados.setOnClickListener {
            votoTotal(obtenerVotos())
            if (validarTotalVotos()) mostrarResultados()
            else message("Vota al menos una vez")
        }
    }

    private fun inicializarElementos() = with(binding) {
        listCardPartidos = mutableListOf(crdAlianza, crdPan, crdPrd, crdPRI, crdPT, crdVerde)
        listImagePartidos = mutableListOf(imgAlianza, imgPan, imgPrd, imgPRI, imgPT, imgVerde)
        listTextPartidos = mutableListOf(tvAlianzaR, tvPANR, tvPrdR, tvPRIR, tvPTR, tvVerdeR)
    }

    private fun mostrarResultados() {
        Intent(this@MainActivity, Resultados::class.java).apply {
            putExtra("VOTOS", obtenerVotos() as ArrayList)
            putExtra("TOTAL", TOTAL_VOTOS)
            startActivity(this)
        }
    }

    private fun detectarElemento() {
        listTextPartidos.indices.forEach { index ->
            listCardPartidos[index].setOnClickListener {
                listTextPartidos[index].text = "$TEXTO_NUMV ${sumarVotos(index)}"
            }
            listImagePartidos[index].setOnClickListener {
                listTextPartidos[index].text = "$TEXTO_NUMV ${sumarVotos(index)}"
            }
        }
    }

    private fun sumarVotos(index: Int): Int {
        return when (index) {
            0 -> sumaAlianza()
            1 -> sumaPan()
            2 -> sumaPrd()
            3 -> sumaPri()
            4 -> sumaPt()
            5 -> sumaPv()
            else -> 0
        }
    }

    //Lambda functions, la variable es una función
    val sumaAlianza = { ++alianza }
    val sumaPan = { ++pan }
    val sumaPrd = { ++prd }
    val sumaPri = { ++pri }
    val sumaPt = { ++pt }
    val sumaPv = { ++pv }


    private fun obtenerVotos(): MutableList<Int> {
        resultadosVotos = mutableListOf(alianza, pan, prd, pri, pt, pv)
        return resultadosVotos
    }

    private fun validarTotalVotos() = TOTAL_VOTOS > 0

    private fun votoTotal(lst: MutableList<Int>) {
        TOTAL_VOTOS = lst.reduce(operation = { acc, voto -> acc + voto })
    }

    private fun message(str: String) = makeText(this, str, Toast.LENGTH_SHORT).show()

}
