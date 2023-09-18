package com.example.quizmastercheezycode.activities.utils

object ColorPicker {
    val colors = arrayOf(
//        "#3EB9DF",
//        "#3685BC",
//        "#D36280",
//        "#E44F55",
//        "#FA8056",
//        "#818BCA",
//        "#7D659F",
//        "#51BAB3",
//        "#4FB66C",
//        "#E3AD17",
//        "#627991",
//        "#EF8EAD",
//        "#B5BFC6"
        "#FF5733",   // Bright Red-Orange
        "#FFD700",   // Bright Gold
        "#32CD32",   // Bright Lime Green
        "#FF00FF",   // Bright Magenta
        "#00FFFF",   // Bright Cyan
        "#FF6347",   // Bright Tomato
        "#FFA500",   // Bright Orange
        "#9932CC",   // Bright Purple
        "#00FF00",   // Bright Green
        "#FF1493"    // Bright Deep Pink
    )
    var currentColorIndex = 0

    fun getColor(): String {
        currentColorIndex = (currentColorIndex + 1) % colors.size
        return colors[currentColorIndex]
    }
}