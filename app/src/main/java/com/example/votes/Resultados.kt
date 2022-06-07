package com.example.votes

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.votes.databinding.ActivityResultadosBinding

class Resultados : AppCompatActivity() {
    private lateinit var binding: ActivityResultadosBinding
    private lateinit var listVFinalPartidos: MutableList<Int>
    private lateinit var listTVPartidos: MutableList<TextView>
    private lateinit var listPorcPartidos: MutableList<TextView>
    private var resVotos: Int = 0
    private var numMayor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportActionBar?.hide()
        obtenerDatos()
        calcularDatos()
        aplicarEstilo()
    }

    fun obtenerDatos() {
        listVFinalPartidos = intent.getIntegerArrayListExtra("VOTOS") as MutableList<Int>
        resVotos = intent.getIntExtra("TOTAL", 0)
        with(binding) {
            listTVPartidos = mutableListOf(tvAlianzaV, tvPANV, tvPRDV, tvPRIV, tvPTV, tvVerdeV)
            listPorcPartidos = mutableListOf(tvAlianzaP, tvPANP, tvPRDP, tvPRIP, tvPTP, tvVerdeP)
        }
    }

    fun calcularDatos() {
        numMayor = listVFinalPartidos[0]
        listVFinalPartidos.forEachIndexed { i, element ->
            if (element > numMayor) numMayor = element
            mostraDato(listTVPartidos[i], element)
            val prc = porcentajePartido(element, resVotos)
            mostraDato(listPorcPartidos[i], prc)
        }
    }

    private fun aplicarEstilo() {
        listVFinalPartidos.indices.asSequence()
            .filter { index -> numMayor.equals(listVFinalPartidos[index]) }
            .forEach { index -> estiloResultado(listTVPartidos[index]) }
    }

    val porcentajePartido = { votosP: Int, totalV: Int -> ((votosP * 100) / totalV).toDouble() }

    val mostraDato = { tv: TextView, prc: Any -> tv.text = prc.toString() }

    val estiloResultado = { tv: TextView -> tv.setTypeface(null, Typeface.BOLD) }

    //private fun message(str: String) = Toast.makeText(this, str, Toast.LENGTH_SHORT).show()

}