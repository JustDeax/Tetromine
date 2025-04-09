package com.justdeax.tetramine.util

fun padArray2x4(array: Array<IntArray>): Array<IntArray> {
    return Array(2) { rowIndex ->
        val row = array.getOrNull(rowIndex) ?: intArrayOf()
        val paddedRow = IntArray(4)
        for (i in row.indices)
            if (i < 4) paddedRow[4 - row.size + i] = row[i]
        paddedRow
    }
}