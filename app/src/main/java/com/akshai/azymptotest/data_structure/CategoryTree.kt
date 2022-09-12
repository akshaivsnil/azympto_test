package com.akshai.azymptotest.data_structure

import com.akshai.azymptotest.data_structure.queue.QueueImpl

typealias Visitor<CatModel> = (CategoryTree<CatModel>) -> Unit

class CategoryTree<CatModel>(val value: CatModel)  {

    private val children: MutableList<CategoryTree<CatModel>> = mutableListOf()

    fun add(child: CategoryTree<CatModel>) = children.add(child)

    fun forEachDepthFirst(visit: Visitor<CatModel>) {
        visit(this)
        children.forEach {
            it.forEachDepthFirst(visit)
        }
    }


    fun forEachLevelOrder(visit: Visitor<CatModel>) {
        visit(this)

        val queue: QueueImpl<CategoryTree<CatModel>> = QueueImpl()
        children.forEach {
            queue.enqueue(it)
        }

        var node = queue.dequeue()
        while (node != null) {
            visit(node)

            node.children.forEach { queue.enqueue(it) }

            node = queue.dequeue()
        }
    }

    fun printEachLevel() {
        val queue: QueueImpl<CategoryTree<CatModel>> = QueueImpl()
        var nodesLeftInCurrentLevel = 0
        queue.enqueue(this)

        while (queue.isEmpty.not()) {
            nodesLeftInCurrentLevel = queue.count

            while (nodesLeftInCurrentLevel > 0) {
                val node = queue.dequeue()

                if (node != null) {
                    println("${node.value}")

                    node.children.forEach { queue.enqueue(it) }

                    nodesLeftInCurrentLevel--
                } else break
            }
        }
        println()
    }

}