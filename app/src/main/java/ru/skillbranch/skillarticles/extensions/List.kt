package ru.skillbranch.skillarticles.extensions

import kotlin.math.max
import kotlin.math.min

fun List<Pair<Int, Int>>.groupByBounds(bounds: List<Pair<Int, Int>>): List<List<Pair<Int, Int>>> {
    val result: MutableList<MutableList<Pair<Int, Int>>> = mutableListOf()
    repeat(bounds.size) { result.add(mutableListOf()) }
    bounds.forEachIndexed { index, bound ->
        this.forEach { res ->
            if ((res.first >= bound.first && res.first < bound.second) || (res.second > bound.first && res.second <= bound.second)) {
                result[index].add(
                    max(res.first, bound.first) to min(res.second, bound.second)
                )
            }
        }
    }
    return result
}
