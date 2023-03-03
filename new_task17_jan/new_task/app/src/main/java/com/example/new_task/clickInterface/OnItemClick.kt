package com.example.new_task.clickInterface

import com.example.new_task.entity.Chat

interface OnItemClick {
    fun OnClickItemList(chat: Chat, pos:Int)
}