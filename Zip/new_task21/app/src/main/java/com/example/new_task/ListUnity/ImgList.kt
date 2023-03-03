package com.example.new_task.ListUnity

import android.content.Context
import com.example.new_task.R
import com.example.new_task.entity.Category
import com.example.new_task.entity.ImageSlider

class ImgList(var context: Context) {

    fun imageData(imgList: MutableList<ImageSlider>){
        imgList.add(ImageSlider(1, "https://img.freepik.com/premium-photo/brown-sofa-wooden-table-living-room-interior-with-plant-concrete-wall_41470-3721.jpg?w=1380"))
        imgList.add(ImageSlider(2,"https://img.freepik.com/free-psd/picture-frame-mockup-psd-hanging-modern-living-room-home-decor-interior_53876-114536.jpg?w=1380&t=st=1671858433~exp=1671859033~hmac=3de55c931a83a72eb1d6183ea8e779c59d0b3ad4218b45a316f54a63ce347692"))
        imgList.add(ImageSlider(3,"https://img.freepik.com/premium-photo/white-wall-living-room-have-sofa-decoration-3d-rendering_41470-3282.jpg?w=1480"))
        imgList.add(ImageSlider(4,"https://img.freepik.com/free-photo/scandinavian-living-room-interior-design-zoom-background_53876-143147.jpg?w=1380&t=st=1671861004~exp=1671861604~hmac=201488e2fcd555081957fb2c4d8d1757a83de912a9d3ca6b0eedd13f286e8d4f"))
    }
    fun categoryData(categoryList: MutableList<Category> ){
        categoryList.add(Category(1,"https://img.freepik.com/free-psd/armchair-pillow_176382-870.jpg?w=826&t=st=1671861174~exp=1671861774~hmac=85f4e31e26ef8b72f28f728dc824ff4f913a11f3446e9428f09ed70c2af93937"))
        categoryList.add(Category(2,"https://img.freepik.com/free-psd/armchair-pillow_176382-870.jpg?w=826&t=st=1671861174~exp=1671861774~hmac=85f4e31e26ef8b72f28f728dc824ff4f913a11f3446e9428f09ed70c2af93937"))
        categoryList.add(Category(3,"https://img.freepik.com/free-psd/armchair-pillow_176382-870.jpg?w=826&t=st=1671861174~exp=1671861774~hmac=85f4e31e26ef8b72f28f728dc824ff4f913a11f3446e9428f09ed70c2af93937"))
        categoryList.add(Category(4,"https://img.freepik.com/free-psd/armchair-pillow_176382-870.jpg?w=826&t=st=1671861174~exp=1671861774~hmac=85f4e31e26ef8b72f28f728dc824ff4f913a11f3446e9428f09ed70c2af93937"))
        categoryList.add(Category(5,"https://img.freepik.com/free-psd/armchair-pillow_176382-870.jpg?w=826&t=st=1671861174~exp=1671861774~hmac=85f4e31e26ef8b72f28f728dc824ff4f913a11f3446e9428f09ed70c2af93937"))
        categoryList.add(Category(6,"https://img.freepik.com/free-psd/armchair-pillow_176382-870.jpg?w=826&t=st=1671861174~exp=1671861774~hmac=85f4e31e26ef8b72f28f728dc824ff4f913a11f3446e9428f09ed70c2af93937"))

    }

}