package com.app.wallpaper.Interface

import android.icu.text.Transliterator
import android.view.View
import com.app.wallpaper.Modal.categories

interface OnCategoriesListner {

    fun categorieslistner(categories: categories,position: Int)

}