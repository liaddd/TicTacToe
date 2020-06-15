package com.liad.tictactoe.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.liad.tictactoe.R
import com.liad.tictactoe.utils.Constants
import com.liad.tictactoe.utils.extensions.toast
import kotlinx.android.synthetic.main.fragment_game_pref.*

class GamePrefFragment : Fragment(), View.OnClickListener {

    private lateinit var mainContainer: LinearLayout
    private lateinit var boardSizeET: EditText
    private lateinit var buttonsArr: Array<Array<Button>>
    private lateinit var startRestartButton: Button
    private lateinit var currentTurnTV: TextView
    private var isX = false
    private var roundPlayedCounter = 0

    companion object {
        fun newInstance(): GamePrefFragment = GamePrefFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_game_pref, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        mainContainer = board_container
        boardSizeET = fragment_game_pref_choose_board_size
        startRestartButton = fragment_game_pref_start_game_button
        currentTurnTV = fragment_game_current_turn_text_view
        fragment_game_pref_start_game_button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fragment_game_pref_start_game_button -> {
                if (startRestartButton.text == Constants.RESTART) {
                    restartBoard()
                } else {
                    val boardSize = boardSizeET.text.toString()
                    if (!isValidInput(boardSize)) return
                    startGame(boardSize)
                }
            }
            else -> {
                val button = v as Button
                if (button.text.toString().isNotEmpty())
                    toast(context!!, "Already chosen\nplease try again")
                else {
                    roundPlayedCounter++
                    button.text = if (isX) Constants.X else Constants.O
                    button.setTextColor(resources.getColor(if (isX) android.R.color.holo_blue_light else android.R.color.holo_red_light))
                    isX = !isX
                    checkForWinner()
                    updateCurrentTurn(isX)
                }
            }
        }
    }

    private fun updateCurrentTurn(isX: Boolean) {
        currentTurnTV.visibility = View.VISIBLE
        currentTurnTV.text = if (isX) "X its your turn" else "O its your turn"
    }

    private fun startGame(boardSize : String) {
        boardSizeET.visibility = View.INVISIBLE
        drawBoard(boardSize.toInt())
        startRestartButton.text = Constants.RESTART
        updateCurrentTurn(isX)
    }

    private fun restartBoard() {
        mainContainer.removeAllViews()
        boardSizeET.text = null
        boardSizeET.visibility = View.VISIBLE
        currentTurnTV.visibility = View.GONE
        startRestartButton.text = Constants.START
    }

    // TODO Liad - add winner logic
    private fun checkForWinner() {
        var counter = 0
        for (row in buttonsArr.withIndex()) {
            //Log.d("Liad" , "row: ${row.index}")
            for (column in row.value.withIndex()) {
                Log.d("Liad", "column: ${column.value.text}")
                if (column.value.text == Constants.X) {
                    counter++
                    Log.d("Liad", "counter: $counter")
                }
            }
        }
    }

    private fun isValidInput(boardSize: String): Boolean {
        if (boardSize.isEmpty()) {
            toast(context!!, "please insert board size")
            return false
        }
        if (boardSize.toInt() < 3) {
            toast(context!!, "Minimum number is 3")
            return false
        }
        return true
    }

    private fun drawBoard(boardSize: Int) {
        buttonsArr = Array(boardSize) { Array(boardSize) { Button(context) } }
        mainContainer.removeAllViews()

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        for (row in buttonsArr) {
            val rowContainer = LinearLayout(context)
            for (j in row.withIndex()) {
                params.weight = 1f
                params.height = 400
                val button = j.value
                button.layoutParams = params
                button.textSize = 32f
                button.id = j.index
                rowContainer.addView(button)
                button.setOnClickListener(this)
            }
            mainContainer.addView(rowContainer)
        }
    }
}
