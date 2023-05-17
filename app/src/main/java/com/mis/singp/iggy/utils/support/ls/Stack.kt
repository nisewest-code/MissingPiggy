package com.mis.singp.iggy.utils.support.ls

class Stack <T>{

    private val elements : MutableList<T> = mutableListOf()

    fun isEmpty() = elements.isEmpty()

    fun size() = elements.size

    fun push(item : T) = elements.add(item)

    fun pop(): T? {

        val item : T? = elements.lastOrNull()

        if (!isEmpty()){

            elements.removeAt(elements.size -1)

        }

        return item

    }

    fun peek() : T? = elements.lastOrNull()

    override fun toString(): String = elements.toString()

}