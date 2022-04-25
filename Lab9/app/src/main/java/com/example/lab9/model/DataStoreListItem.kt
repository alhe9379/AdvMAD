package com.example.lab9.model

data class DataStoreListItem (val question: String, val answer: String) {
    constructor(): this("", ""){}
}