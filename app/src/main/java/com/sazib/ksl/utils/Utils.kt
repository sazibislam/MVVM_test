package com.sazib.ksl.utils

object Utils {

  fun getColorCode(value: Int): String = when (value) {
    0 -> "#CD42FD"
    else -> "#EF2375"
  }
}