package ru.skillbranch.skillarticles.markdown.spans

import android.graphics.*
import android.text.style.ReplacementSpan
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.annotation.VisibleForTesting
import ru.skillbranch.skillarticles.markdown.Element


class BlockCodeSpan(
    @ColorInt
    private val textColor: Int,
    @ColorInt
    private val bgColor: Int,
    @Px
    private val cornerRadius: Float,
    @Px
    private val padding: Float,
    private val type: Element.BlockCode.Type
) : ReplacementSpan() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var rect = RectF()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var path = Path()

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val textWidth = paint.measureText(text.toString(), start, end)
        paint.forBackground {
            when (type) {
                Element.BlockCode.Type.SINGLE -> {
                    rect.set(
                        0f,
                        top + padding,
                        canvas.width.toFloat(),
                        bottom - padding
                    )
                    canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
                }
                Element.BlockCode.Type.START -> {
                    rect.set(0f, top + padding, canvas.width.toFloat(), bottom.toFloat())
                    val corners = floatArrayOf(
                        cornerRadius, cornerRadius, cornerRadius, cornerRadius,
                        0f, 0f, 0f, 0f
                    )

                    path.reset()
                    path.addRoundRect(rect, corners, Path.Direction.CW)
                    canvas.drawPath(path, paint)
                }
                Element.BlockCode.Type.END -> {
                    rect.set(0f, top.toFloat(), canvas.width.toFloat(), bottom - padding)
                    val corners = floatArrayOf(
                        0f, 0f, 0f, 0f,
                        cornerRadius, cornerRadius, cornerRadius, cornerRadius
                    )

                    path.reset()
                    path.addRoundRect(rect, corners, Path.Direction.CW)
                    canvas.drawPath(path, paint)
                }
                Element.BlockCode.Type.MIDDLE ->{
                    rect.set(
                        0f,
                        top.toFloat(),
                        canvas.width.toFloat(),
                        bottom.toFloat()
                    )
                    canvas.drawRect(rect, paint)
                }
            }
        }
        paint.forText {
            canvas.drawText(text, start, end, x + padding, y.toFloat(), paint)
        }
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        val originAscent = (paint.fontMetrics?.ascent ?: -30f) * 0.85f
        val originDescent = (paint.fontMetrics?.descent ?: 10f) * 0.85f


        paint.forText {
            when (type) {
                Element.BlockCode.Type.START -> {
                    fm?.ascent = (originAscent - 2 * padding).toInt()
                    fm?.descent = (originDescent).toInt()
                }
                Element.BlockCode.Type.END -> {
                    fm?.ascent = (originAscent).toInt()
                    fm?.descent = (originDescent + 2 * padding).toInt()
                }
                Element.BlockCode.Type.MIDDLE -> {
                    fm?.ascent = (originAscent).toInt()
                    fm?.descent = (originDescent).toInt()
                }
                Element.BlockCode.Type.SINGLE -> {
                    fm?.ascent = (originAscent - 2 * padding).toInt()
                    fm?.descent = (originDescent + 2 * padding).toInt()
                }
            }
        }
        return 0
    }

    private inline fun Paint.forText(block: () -> Unit) {
        val oldColor = color
        val oldStyle = typeface?.style ?: 0
        val oldSize = textSize
        val oldFont = typeface

        color = textColor
        typeface = Typeface.create(Typeface.MONOSPACE, oldStyle)
        textSize *= 0.85f
        block()
        color = oldColor
        typeface = oldFont
        textSize = oldSize
    }

    private inline fun Paint.forBackground(block: () -> Unit) {
        val oldColor = color
        val oldStyle = style

        color = bgColor
        style = Paint.Style.FILL

        block()
        color = oldColor
        style = oldStyle
    }
}
