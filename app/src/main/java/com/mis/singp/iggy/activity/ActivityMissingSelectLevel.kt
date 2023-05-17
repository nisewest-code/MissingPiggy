package com.mis.singp.iggy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mis.singp.iggy.databinding.ActivityMissingSelectLevelBinding
import com.mis.singp.iggy.utils.pref.PrefMissingPiggy

class ActivityMissingSelectLevel : AppCompatActivity() {
    private lateinit var binding: ActivityMissingSelectLevelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMissingSelectLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelectPiggyHard.setOnClickListener {
            PrefMissingPiggy.saveLevel(this, "hard")
            startActivity(Intent(this, MissingPlayBoardActivity::class.java))
        }
        binding.btnSelectPiggyEasy.setOnClickListener {
            PrefMissingPiggy.saveLevel(this, "easy")
            startActivity(Intent(this, MissingPlayBoardActivity::class.java))
        }
    }
}