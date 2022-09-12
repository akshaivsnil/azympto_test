package com.akshai.azymptotest.data_structure.stack


class StackImpl<T : Any> : Stack<T> {

    private val storage: ArrayList<T> = arrayListOf()

    override fun push(element: T) {
        storage.add(element)
    }

    override fun pop(): T? {
        return storage.removeLastOrNull()
    }

    override fun peek(): T? {
        return storage.lastOrNull()
    }

    override val count: Int
        get() = storage.size

}