package com.app.wallpaper.Adaptor

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.wallpaper.Fragment.*

class viewpager_adaptor(var fragment: FragmentManager, var tabcount: Int) :FragmentPagerAdapter(fragment) {

    override fun getCount(): Int {
        return tabcount
    }

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> categories()
            1 ->Recent()
            2->Random()
            3->WeeklyPropular()
            4->MonthlyPropular()
            5->MostPropular()
            else->Recent()


        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position==0){
                "CATEGORIES"
        }else if (position==1){
            "RECENT"
        }else if (position==2){
            "RENDOM"
        }else if (position==3){
            "WEEKLYPROPULAR"
        }else if (position==4){
            "MONTHLYPROPULAR"
        }else {
            "MOSTPROPULAR"
        }
    }
}