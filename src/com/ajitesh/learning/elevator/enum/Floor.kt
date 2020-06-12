package com.ajitesh.learning.elevator.enum

enum class Floor (var floorNumber: Int) {
    ZERO (0),
    ONE (1),
    TWO (2),
    THREE (3),
    FOUR (4),
    FIVE (5),
    SIX (6),
    SEVEN (7),
    EIGHT (8),
    NINE (9),
    TEN (10),
    ELEVEN(11),
    TWELVE (12)
}

fun main(args: Array<String>) {
    val list = listOf<Floor>(Floor.SIX, Floor.THREE, Floor.SEVEN)
    list.forEach {println(it)}
    //val newList = list.sortedWith(Comparator { e1, e2 -> e1.floorNumber.compareTo(e2.floorNumber)})
    val newList = list.sorted()
    println("")
    newList.forEach {println(it)}

    println(Floor.SEVEN > Floor.TWELVE)
}