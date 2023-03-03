package com.example.app.listner

import com.example.app.model.Food

interface OnListitemClickListener {

    fun onCardClicked(pos:Int,food: Food)

}