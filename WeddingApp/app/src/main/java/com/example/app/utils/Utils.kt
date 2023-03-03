package com.example.app.utils

import android.content.Context
import com.example.app.model.Electronic
import com.example.app.model.Food
import com.example.app.model.Item
import com.example.weddingapp.R
import java.text.DecimalFormat
import java.text.NumberFormat

class Utils {

    companion object {

        fun getFormettedNumber(number: Int): String {

            val formatter: NumberFormat = DecimalFormat("00")
            val s: String = formatter.format(number)
            return s
        }

         fun getCategoryData(context: Context): ArrayList<Item> {
            var mainitemList = ArrayList<Item>()

            mainitemList.add(
                Item(
                    1,
                    context.getString(R.string.Shirts),
                    context.getString(R.string._Shirts_image)
                )
            )
            mainitemList.add(
                Item(
                    2,
                    context.getString(R.string.Watch),
                    context.getString(R.string._Watch_Images)
                )
            )
            mainitemList.add(
                Item(
                    3,
                    context.getString(R.string.Shoes),
                    context.getString(R.string._Shoes_image)
                )
            )
            mainitemList.add(
                Item(
                    4,
                    context.getString(R.string.Leptop),
                    context.getString(R.string._Leptop_image)
                )
            )
            mainitemList.add(
                Item(
                    5,
                    context.getString(R.string.Mobile),
                    context.getString(R.string._Mobile_images)
                )
            )
            mainitemList.add(
                Item(
                    6,
                    context.getString(R.string.Car),
                    context.getString(R.string._CAR_Imges)
                )
            )
            mainitemList.add(
                Item(
                    7,
                    context.getString(R.string.Medicines),
                    context.getString(R.string._Medicines_Image)
                )
            )
            mainitemList.add(
                Item(
                    8,
                    context.getString(R.string.Furniture),
                    context.getString(R.string._Furniture_Images)
                )
            )


            return mainitemList

        }

         fun getElectronicData(context: Context):ArrayList<Electronic> {
            var electronicList = ArrayList<Electronic>()

            electronicList.add(
                Electronic(
                    1,
                    context.getString(R.string._Wshing),
                    2.5,
                    R.drawable.ic_star,
                    context.getString(R.string._Wshing_Image)
                )
            )
            electronicList.add(
                Electronic(
                    1,
                    context.getString(R.string._Freezer),
                    2.5,
                    R.drawable.ic_star,
                    context.getString(
                        R.string._Freeezer_Image
                    )
                )
            )
            electronicList.add(
                Electronic(
                    1,
                    context.getString(R.string._Ac),
                    2.5,
                    R.drawable.ic_star,
                    context.getString(R.string._Ac_Image)
                )
            )
            electronicList.add(
                Electronic(
                    1,
                    context.getString(R.string._Mobile),
                    2.5,
                    R.drawable.ic_star,
                    context.getString(
                        R.string._Mobile_Images
                    )
                )
            )
            electronicList.add(
                Electronic(
                    1,
                    context.getString(R.string._Computer),
                    2.5,
                    R.drawable.ic_star,
                    context.getString(
                        R.string._Computer_Images
                    )
                )
            )
            return electronicList

        }

         fun getfoodData(context: Context):ArrayList<Food>{
             var foodList = ArrayList<Food>().also {


                 it.add(
                     Food(
                         1,
                        context. getString(R.string._Amiras_Restaurant),
                         context.getString(R.string._Amiras_Restaurant_desc),
                       context.  getString(R.string._Amiras_iamges),2.2f,2.5f,R.drawable.ic_bullet,context.getString(R.string._amiras))
                 )
                 it.add(
                     Food(
                         1, context.getString(R.string._Surbhi_Restaurant), context.getString(R.string._Surbhi_Restaurant_desc), context.getString(
                             R.string._Sandwich_iamges),2.5f,2.5f,R.drawable.ic_bullet,context.getString(R.string._Surbhi)))

                 it.add(
                     Food(
                         1,
                         context. getString(R.string._Rajwadi_Restaurant),
                         context. getString(R.string._Rajwadi_Restaurant_desc),
                         context.getString(R.string._Burger_images),2.8f,2.1f,R.drawable.ic_bullet,context.getString(R.string._Rajwadi))

                 )
                 it.add(
                     Food(
                         1,
                         context.getString(R.string._Patel_Restaurant),
                         context. getString(R.string._Patel_Restaurant_desc),
                         context. getString(R.string._Dosha_images),2.4f,2.2f,R.drawable.ic_bullet,context.getString(R.string._Patel))

                 )
                 it.add(
                     Food(
                         1, context.getString(R.string._Muridher_Restaurant), context.getString(R.string._Muridher_Restaurant_desc),
                         context.getString(R.string._Powbhaji_images),2.3f,2.5f,R.drawable.ic_bullet,context.getString(R.string._Murlidhar))

                 )
             }
             return foodList
        }
    }
}