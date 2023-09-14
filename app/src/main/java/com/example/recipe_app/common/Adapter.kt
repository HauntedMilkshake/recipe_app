package com.example.recipe_app.common

interface Adapter<T, K> {
    fun adapt(t: T): K
}