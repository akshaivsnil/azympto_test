package com.akshai.azymptotest.data_structure.queue

interface Queue<T> {

    val count: Int
    val isEmpty : Boolean

    fun peek(): T?

    fun enqueue(element: T)

    fun dequeue(): T?
}