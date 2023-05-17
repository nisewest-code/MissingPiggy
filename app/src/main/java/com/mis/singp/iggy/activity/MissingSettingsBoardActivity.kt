package com.mis.singp.iggy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.mis.singp.iggy.R
import com.mis.singp.iggy.databinding.ActivityMissingSettingsBoardBinding
import com.mis.singp.iggy.utils.pref.PrefMissingPiggy
import com.mis.singp.iggy.view.dialog.DialogBoutght

class MissingSettingsBoardActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMissingSettingsBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMissingSettingsBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateView()
    }

    private fun updateView(){
        initButtons()
        binding.scoreLabelPiggy.text = PrefMissingPiggy.getScore(this).toString()
        binding.labelBestScorePiggy.text = getString(R.string.best_score_missing, PrefMissingPiggy.getBestScore(this))
        val dopLife = PrefMissingPiggy.isDopLife(this)

        binding.containerLifeAddPiggy.visibility = if (dopLife) View.GONE else View.VISIBLE
        binding.btnBoughtLifeAddPiggy.text = getString(R.string.buy_missing, 100)
    }

    private fun initButtons(){
        binding.btnBackPiggy.setOnClickListener(this)
        binding.resetPiggyBtn.setOnClickListener(this)
        binding.btnBoughtLifeAddPiggy.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnBackPiggy -> finish()
            binding.resetPiggyBtn -> {
                PrefMissingPiggy.reset(this)
                updateView()
            }
            binding.btnBoughtLifeAddPiggy -> {
                val bonus = PrefMissingPiggy.getScore(this)
                if (bonus>=100){
                    PrefMissingPiggy.saveDopLife(this, true)
                    PrefMissingPiggy.buy(this)
                    updateView()
                } else {
                    val myDialogFragment = DialogBoutght()
                    val manager = supportFragmentManager
                    val transaction: FragmentTransaction = manager.beginTransaction()
                    myDialogFragment.show(transaction, "fruit_not")
                }
            }
        }
    }
}