package com.sbiktimirov.geekbrains.lessons.notes.extension

import androidx.lifecycle.MutableLiveData

@JvmName("plusAssignT")
operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(item: T) {
    val value = this.value ?: mutableListOf()
    value.add(item)
    this.value = value
}

@JvmName("minusAssignT")
operator fun <T> MutableLiveData<MutableList<T>>.minusAssign(item: T) {
    val value = this.value ?: mutableListOf()
    value.remove(item)
    this.value = value
}

operator fun <T> MutableLiveData<List<T>>.plusAssign(item: T) {
    val value = this.value ?: emptyList()
    this.value = value + listOf(item)
}

operator fun <T> MutableLiveData<List<T>>.minusAssign(item: T) {
    val value = this.value ?: emptyList()
    this.value = value - listOf(item)
}