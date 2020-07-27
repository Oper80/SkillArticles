package ru.skillbranch.skillarticles.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop


fun View.setMarginOptionally(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0){
    val lp = this.layoutParams as ViewGroup.MarginLayoutParams
    lp.setMargins(left, top, right, bottom)
    this.layoutParams = lp
}

fun View.setPaddingOptionally(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0){
    this.setPadding(left, top, right, bottom)
}
