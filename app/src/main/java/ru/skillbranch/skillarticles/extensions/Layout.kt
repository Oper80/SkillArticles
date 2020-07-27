package ru.skillbranch.skillarticles.extensions

import android.text.Layout

fun Layout.getLineHeight(line : Int) : Int {
    return getLineTop(line.inc()) - getLineBottom(line)
}

fun Layout.getLineTopWithoutPadding(line : Int): Int{
    return this.getLineTop(line) - if (line == 0) topPadding else 0
}

fun Layout.getLineBottomWithoutPadding(line : Int): Int{
    return this.getLineBottomWithoutSpacing(line) - if (line == lineCount.dec()) bottomPadding else 0
}

fun Layout.getLineBottomWithoutSpacing(line : Int): Int{
    val isLastLine = (line == lineCount.dec())
    val hasSpacing = (spacingAdd != 0f)
    return this.getLineBottom(line) + spacingAdd.toInt() * if (isLastLine || !hasSpacing) 0 else -1
}