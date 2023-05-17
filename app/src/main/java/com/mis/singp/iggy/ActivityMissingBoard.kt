package com.mis.singp.iggy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.mis.singp.iggy.activity.ActivityMissingSelectLevel
import com.mis.singp.iggy.activity.MissingPlayBoardActivity
import com.mis.singp.iggy.activity.MissingSettingsBoardActivity
import com.mis.singp.iggy.databinding.ActivityMissingBoardBinding

class ActivityMissingBoard : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMissingBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMissingBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButton()
    }

    private fun initButton(){
        binding.btnPiggyExit.setOnClickListener(this)
        binding.btnPiggyPlay.setOnClickListener(this)
        binding.btnPiggySettings.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnPiggyExit -> { this.finish()}
            binding.btnPiggyPlay -> {startActivity(ActivityMissingSelectLevel::class.java)}
            binding.btnPiggySettings -> {startActivity(MissingSettingsBoardActivity::class.java)}
        }
    }

    private fun <T> startActivity(java: Class<T>) {
        startActivity(Intent(this, java))
    }


    override fun onResume() {
        super.onResume()
    }
}