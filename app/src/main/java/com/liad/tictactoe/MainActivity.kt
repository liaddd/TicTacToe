package com.liad.tictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.liad.tictactoe.fragments.GamePrefFragment
import com.liad.tictactoe.utils.extensions.changeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeFragment(
            supportFragmentManager,
            GamePrefFragment.newInstance(),
            R.id.main_activity_frame_layout
        )
    }
}
