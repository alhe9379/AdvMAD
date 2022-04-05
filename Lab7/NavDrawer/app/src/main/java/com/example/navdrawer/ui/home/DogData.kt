package com.example.navdrawer.ui.home

import com.example.navdrawer.R

object DogData {
    val dogList = ArrayList<Dog>()

    init {
        dogList.add(Dog("Corgi"))
        dogList.add(Dog("Shiba Inu"))
        dogList.add(Dog("Grey Hound"))
        dogList.add(Dog("Weiner Dog"))
        dogList.add(Dog("Golden Retriever"))
    }
}