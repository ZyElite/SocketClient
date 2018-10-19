package com.zy.service


fun main(args: Array<String>) {
    val bubble = intArrayOf(42, 20, 17, 13, 28, 14, 23, 15)
    bubble_sort(bubble)
    for (i in bubble.indices) {
        print("${bubble[i]}  ")
    }
    println()
    val select = intArrayOf(42, 20, 17, 13, 28, 14, 23, 15)
    select_sort(select)
    for (i in select.indices) {
        print("${select[i]}  ")
    }
    println()
    val insert = intArrayOf(42, 20, 17, 13, 28, 14, 23, 15)
    insert_sort(insert)
    for (i in insert.indices) {
        print("${insert[i]}  ")
    }
}

fun bubble_sort(arr: IntArray) {
    var temp: Int
    var flag: Boolean
    for (i in arr.indices) {
        flag = false
        for (j in 0 until arr.size - 1) {
            if (arr[j] > arr[j + 1]) {
                temp = arr[j]
                arr[j] = arr[j + 1]
                arr[j + 1] = temp
                flag = true
            }
        }
        if (!flag) break
    }

}


fun select_sort(arr: IntArray) {
    var temp: Int
    var tm: Int
    for (i in arr.indices) {
        temp = i
        for (j in i + 1 until arr.size) {
            if (arr[j] < arr[temp]) {
                temp = j
            }
        }
        if (temp != i) {
            tm = arr[i]
            arr[i] = arr[temp]
            arr[temp] = tm
        }
    }
}

fun insert_sort(arr: IntArray) {
    var temp: Int
    for (i in 0 until arr.size - 1) {
        for (j in i + 1 downTo 1) {
            if (arr[j] < arr[j - 1]) {
                temp = arr[j - 1]
                arr[j - 1] = arr[j]
                arr[j] = temp
            } else {
                break
            }
        }
    }

}

fun bubbleSort(arr: IntArray) {
    var temp: Int//临时变量
    for (i in 0 until arr.size - 1) {   //表示趟数，一共arr.length-1次。
        for (j in arr.size - 1 downTo i + 1) {
            if (arr[j] < arr[j - 1]) {
                temp = arr[j]
                arr[j] = arr[j - 1]
                arr[j - 1] = temp
            }
        }
    }
}