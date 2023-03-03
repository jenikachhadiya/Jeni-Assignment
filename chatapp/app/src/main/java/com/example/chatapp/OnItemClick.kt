package com.example.chatapp

import com.example.chatapp.Chat


interface OnItemClick {
    fun OnClickItemList(chat: Chat, pos:Int)
}