package br.edu.ifsp.scl.pdm.dados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.scl.pdm.dados.databinding.ActivityMainBinding
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    private lateinit var activivityMainBinding: ActivityMainBinding
    private lateinit var geradorRandomico: Random
    private var config: Configuracao = Configuracao()

    private  lateinit var settingsActivityLauncher: ActivityResultLauncher <Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activivityMainBinding.root)

        geradorRandomico = Random(System.currentTimeMillis())

        activivityMainBinding.jogarDadoBt.setOnClickListener{
            if(config.numeroFaces < 7 && config.numeroFaces > 0){
                if(config.numeroDados == 2){
                    val resultado: Int = geradorRandomico.nextInt(1..6)
                    val resultado2: Int = geradorRandomico.nextInt(1..6)

                    "A(s) face(s) sorteado(as) foi(ram) $resultado $resultado2".also { activivityMainBinding.resultadoTv.text = it }

                    val nomeImagem = "dice_${resultado}"
                    val nomeImagem2 = "dice_${resultado2}"

                    activivityMainBinding.resultadoIv.setImageResource(
                        resources.getIdentifier(nomeImagem, "mipmap", packageName)
                    )
                    activivityMainBinding.resultadoIv2.setImageResource(
                        resources.getIdentifier(nomeImagem2, "mipmap", packageName)
                    )

                    if(resultado!=0){
                        activivityMainBinding.resultadoIv.visibility = VISIBLE
                    }else{
                        activivityMainBinding.resultadoIv.visibility = GONE
                    }
                    if(resultado2!=0){
                        activivityMainBinding.resultadoIv2.visibility = VISIBLE
                    }else{
                        activivityMainBinding.resultadoIv2.visibility = GONE
                    }
                }else {
                    activivityMainBinding.resultadoIv.visibility = VISIBLE
                    activivityMainBinding.resultadoIv2.visibility = GONE
                    val resultado: Int = geradorRandomico.nextInt(1..6)
                    "A(s) face(s) sorteado(as) foi(ram) $resultado".also { activivityMainBinding.resultadoTv.text = it }
                    val nomeImagem = "dice_${resultado}"
                    activivityMainBinding.resultadoIv.setImageResource(
                        resources.getIdentifier(nomeImagem, "mipmap", packageName)
                    )
                }
            }else{
                "Número de faces inválido!".also { activivityMainBinding.resultadoTv.text = it }
                activivityMainBinding.resultadoIv.visibility = GONE
                activivityMainBinding.resultadoIv2.visibility = GONE
            }
        }

        settingsActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == RESULT_OK){
                if(result.data != null){
                    val configuracao: Configuracao? = result.data?.getParcelableExtra<Configuracao>(Intent.EXTRA_USER)
                    if (configuracao != null) {
                        config = configuracao
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.settingsMi){
            val settingsIntent = Intent (this, SettingsActivity::class.java)
            settingsActivityLauncher.launch(settingsIntent)
            return true
        }
        return false
    }
}


