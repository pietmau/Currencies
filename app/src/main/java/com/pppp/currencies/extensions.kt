package com.pppp.currencies

fun <E> List<E>.swap(position: Int): List<E> {
    val list = toMutableList()
    val item = list.removeAt(position)
    list.add(0, item)
    return list.toList()
}
