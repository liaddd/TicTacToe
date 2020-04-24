package com.liad.tictactoe.utils.extensions

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


fun changeFragment(
    supportFragmentManager: FragmentManager,
    fragment: Fragment,
    @IdRes container: Int,
    args: Bundle? = null,
    addToBackStack: Boolean = false
) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragment.arguments = args
    fragmentTransaction.replace(container, fragment, fragment::class.java.simpleName)
    if (addToBackStack) fragmentTransaction.addToBackStack(null)
    fragmentTransaction.commit()
}

fun toast(context: Context, message: String = "", duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, message, duration).show()
}