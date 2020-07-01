package ru.skillbranch.skillarticles.extensions

fun String?.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int>{
    val result = mutableListOf<Int>()
    if(substr == "") return result
    var res: Int = this?.indexOf(substr, 0, ignoreCase) ?: -1
    while (res >= 0) {
        result.add(res)
        res = this?.indexOf(substr, res + substr.length, ignoreCase) ?: -1
    }
    return result
}