package br.edu.ifsp.scl.pdm.dados

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Configuracao(val numeroDados: Int = 1, val numeroFaces: Int = 6) : Parcelable
