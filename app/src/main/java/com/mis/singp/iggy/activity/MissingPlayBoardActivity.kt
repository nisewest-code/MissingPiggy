package com.mis.singp.iggy.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.mis.singp.iggy.R
import com.mis.singp.iggy.databinding.ActivityMissingPlayBoardBinding
import com.mis.singp.iggy.utils.listener.GameTouchListener
import com.mis.singp.iggy.utils.model.Generator
import com.mis.singp.iggy.utils.model.ItemModel
import com.mis.singp.iggy.utils.model.ItemUtils
import com.mis.singp.iggy.utils.model.LevelModel
import com.mis.singp.iggy.utils.pref.PrefMissingPiggy
import com.mis.singp.iggy.utils.timer.Timer
import com.mis.singp.iggy.view.dialog.DialogMissingPiggyGameOver
import com.mis.singp.iggy.view.dialog.DialogMissingPiggyInfo
import com.mis.singp.iggy.view.support.Item

class MissingPlayBoardActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMissingPlayBoardBinding
    private lateinit var levelModel: LevelModel
    private var timer: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMissingPlayBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
        initGame()
    }

    private fun initGame(){
        val myDialogFragment = DialogMissingPiggyInfo(this::startGame)
        val manager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        myDialogFragment.show(transaction, "info_fruit")
    }

    private fun startGame(){
        levelModel = LevelModel()
        if (PrefMissingPiggy.isDopLife(this)){
            levelModel.life = 4
        }
        updateScore()
        updateLife()
        timer?.cancelTimer()
        timer = Timer(PrefMissingPiggy.getLevel(this)){
            Item(binding.gameFieldPiggy, ItemModel(
                Generator.generatorId(),
                (binding.gameFieldPiggy.x.toInt() .. (binding.gameFieldPiggy.width-300).toInt()).random().toFloat(),
                binding.gameFieldPiggy.y-binding.gameFieldPiggy.height/2,
                binding.gameFieldPiggy.height.toFloat()
            )
            ){ res, x, y ->
                val collision = ItemUtils.collision(binding.basket.x, binding.basket.y, x, y)
                if (collision){
                    when (res) {
                        R.drawable.negative -> {
                            levelModel.life -= 1
                            updateLife()
                            if (levelModel.life == 0){
                                PrefMissingPiggy.saveScore(this, levelModel.score)
                                timer?.cancelTimer()
                                val myDialogFragment = DialogMissingPiggyGameOver(levelModel.score, this::initGame)
                                val manager = supportFragmentManager
                                val transaction: FragmentTransaction = manager.beginTransaction()
                                myDialogFragment.show(transaction, "fruit_game_over")
                            }
                        }
                        R.drawable.bonus -> {
                            val k = when (PrefMissingPiggy.getLevel(this)) {
                                "easy" -> 1
                                else -> 2
                            }
                            levelModel.score += 1 * k
                            updateScore()
                        }
                        R.drawable.life -> {
                            levelModel.life += 1
                            updateLife()
                        }
                    }
                }
                collision
            }
        }
        timer?.startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancelTimer()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initButtons(){
        binding.btnBackPiggy.setOnClickListener(this)
        binding.btnArrowBackPiggy.setOnTouchListener(GameTouchListener{
            binding.basket.x -= 25
            if (binding.basket.x < 0f){
                binding.btnArrowBackPiggy.visibility = View.INVISIBLE
            }

            if (binding.btnArrowForwardPiggy.visibility == View.INVISIBLE && binding.basket.x<(binding.gameFieldPiggy.width-300)){
                binding.btnArrowForwardPiggy.visibility = View.VISIBLE
            }
        })
        binding.btnArrowForwardPiggy.setOnTouchListener(GameTouchListener{
            binding.basket.x += 25
            if (binding.basket.x > (binding.gameFieldPiggy.width-300)){
                binding.btnArrowForwardPiggy.visibility = View.INVISIBLE
            }
            if (binding.btnArrowBackPiggy.visibility == View.INVISIBLE && binding.basket.x>0){
                binding.btnArrowBackPiggy.visibility = View.VISIBLE
            }
        })
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnBackPiggy -> finish()

        }
    }

    private fun updateScore(){
        binding.scorePiggyLabel.text = "${levelModel.score}"
    }

    private fun updateLife(){
        binding.lifeLabelPiggy.text = "${levelModel.life}"
    }
}