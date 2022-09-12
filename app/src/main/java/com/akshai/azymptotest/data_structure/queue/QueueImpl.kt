package com.akshai.azymptotest.data_structure.queue

class QueueImpl<T> : Queue<T> {

    private val storage: ArrayList<T> = arrayListOf()

    override val count: Int
        get() = storage.size
    override val isEmpty: Boolean
        get() = count == 0

    override fun peek(): T? {
        return storage.getOrNull(0)
    }

    override fun enqueue(element: T) {
        storage.add(element)
    }

    override fun dequeue(): T? {
        return if (isEmpty) null else return storage.first()
    }
}