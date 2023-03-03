package com.example.new_task.listUnity

import android.content.Context
import com.example.new_task.R
import com.example.new_task.entity.*

class ImgList(var context: Context) {

    fun imageData(imgList: MutableList<ImageSlider>) {
        imgList.add(
            ImageSlider(
                1,
                "https://img.freepik.com/free-vector/flat-webinar-template-second-hand-flea-market-event_23-2149539126.jpg?w=1380&t=st=1672037524~exp=1672038124~hmac=283770f49ff1d746e105937a9dcee9974c8e7600a11591f720324137455b16b9"
            )
        )
        imgList.add(
            ImageSlider(
                2,
                "https://img.freepik.com/free-vector/furniture-sale-landing-page_23-2148934519.jpg?w=1380&t=st=1672037316~exp=1672037916~hmac=d45981bb07d22ea651fd41205148fb5e30814f0a80d09b4dba6f35429f63bf44"
            )
        )
        imgList.add(
            ImageSlider(
                3,
                "https://img.freepik.com/free-vector/living-room-interior-design-realistic-vector-background-sofa-window-betroom_528282-230.jpg?w=1380&t=st=1672037405~exp=1672038005~hmac=efeaab4115cdb06568991b3fb59a5012fab386f6b9d9d154d450b87259b33f6d"
            )
        )
        imgList.add(
            ImageSlider(
                4,
                "https://img.freepik.com/free-vector/realistic-home-interior-template-with-sofa-pillows-bar-modern-wicker-hanging-chair-lamps-plant-flowerpot-green-background-illustration_1284-51319.jpg?w=1380&t=st=1672037274~exp=1672037874~hmac=15f6e21eda63aa855324968f5755090feb2b1f8820574a0090030078120a2db6"
            )
        )
    }

    fun categoryData(categoryList: MutableList<Category>) {
        categoryList.add(
            Category(
                1,
                "https://img.freepik.com/free-photo/empty-modern-room-with-furniture_23-2149178879.jpg?w=740&t=st=1672029615~exp=1672030215~hmac=0396ebd0843e729d594565e3f4c617a5bd05ec512c50ee69dd5033ed35f46354",
                context.getString(
                    R.string.table
                )
            )
        )
        categoryList.add(
            Category(
                2,
                "https://img.freepik.com/free-psd/living-room-wall-psd-japandi-interior_53876-109285.jpg?w=740&t=st=1672029449~exp=1672030049~hmac=3794cde1c74c9be97b1127c834e4b8edbe3b99e29c728be81dfca93b0ca5f5af",
                context.getString(
                    R.string.tvfurnitures
                )
            )
        )
        categoryList.add(
            Category(
                3,
                "https://img.freepik.com/free-photo/simple-chair-with-metallic-high-legs-room-with-gray-walls_181624-25886.jpg?w=1380&t=st=1672029312~exp=1672029912~hmac=eaac88611c9dedc17898afac26c19937be38a7d777806ed288051605a5d651cb",
                context.getString(
                    R.string.sofa
                )
            )
        )
        categoryList.add(
            Category(
                4,
                "https://img.freepik.com/free-psd/chair-pillow_176382-873.jpg?w=740&t=st=1672026762~exp=1672027362~hmac=39b382931fd7622b3c4d89fb5d73564ed2a2ce2301ea5203fd9e4d274e1e5133",
                context.getString(
                    R.string.accent_chairs
                )
            )
        )
        categoryList.add(
            Category(
                5,
                "https://img.freepik.com/free-psd/wooden-furniture-with-decorative-objects-lamp-interior-design-ideas_176382-1517.jpg?w=740&t=st=1672026842~exp=1672027442~hmac=b1d03b64294f625c48a9e6ebc2f3f5f3cf9b6f8726da722daae909f610f41abd",
                context.getString(
                    R.string.furniture
                )
            )
        )
        categoryList.add(
            Category(
                6,
                "https://img.freepik.com/free-photo/empty-rocking-chair_74190-3611.jpg?w=740&t=st=1672026910~exp=1672027510~hmac=12b9f1a1cfcd859dc368f44a90add870305e323698a5903d17a9cbdd2e30bacd",
                context.getString(
                    R.string.table_wood_pine
                )
            )
        )

    }

    fun allFurnitureData(furnitureList: MutableList<AllFurniture>) {
        furnitureList.add(
            AllFurniture(
                1,
                "https://img.freepik.com/free-vector/orange-modern-kitchen-interior_1284-15416.jpg?w=1380&t=st=1672029997~exp=1672030597~hmac=81d301db177465bdc0d89d6f77dc9993eec90f482825ad62ee067824979d0f7b",
                context.getString(R.string.kitchen),
                2.5f
            )
        )
        furnitureList.add(
            AllFurniture(
                2,
                "https://img.freepik.com/free-vector/home-interior-background-style_52683-44164.jpg?w=1380&t=st=1672032095~exp=1672032695~hmac=15ffac36db827a636ee905a408104d59549645f1d2186b8bc0023e3465465f09",
                context.getString(R.string.homeFurniture),
                3.2f
            )
        )
        furnitureList.add(
            AllFurniture(
                3,
                "https://img.freepik.com/free-vector/principals-office-school-with-desk-chairs-bookcase-showcase-with-sport-trophies-cartoon-empty-interior-headmaster-cabinet-meeting-talking-with-teachers-pupils-parents_107791-3108.jpg?w=1480&t=st=1672031974~exp=1672032574~hmac=a2b8abfc3ae666a8e8cef7bd841dbab56bd5cec99817991aa69d718a4640d956",
                context.getString(R.string.Office),
                2.7f
            )
        )
        furnitureList.add(
            AllFurniture(
                4,
                "https://img.freepik.com/free-vector/home-interior-wallpaper-video-conferencing_52683-43652.jpg?w=1380&t=st=1672032030~exp=1672032630~hmac=9e1fbfa5dc41236a478077541bfe19493bd3c3f7936d849be6f10996c3b2c5b2",
                context.getString(R.string.livingRoom),
                4.3f
            )
        )
        furnitureList.add(
            AllFurniture(
                5,
                "https://img.freepik.com/free-vector/grocery-store-trading-room-interior-cartoon-vector-with-shopping-baskets-near-cash-counter-desk_1441-2894.jpg?w=1480&t=st=1672032180~exp=1672032780~hmac=3717d278fa71d47c91cd17b25f7e128589c8914de4737173d50be41cf27efe98",
                context.getString(R.string.mall),
                2.4f
            )
        )
    }

    fun shopNameData(shopList: MutableList<ShopList>) {
        shopList.add(
            ShopList(
                1,
                "https://img.freepik.com/free-vector/retro-furniture-logo_23-2148464123.jpg?w=740&t=st=1672036336~exp=1672036936~hmac=e6330984d1217f2d60451d83dbac8537d5556aea8b673c59464ba3496a07e4d8",
                "Luxurious Lumber",
                "furniture, household equipment or related materials and having a variety of different ...",
                2.5f,
                "BL-91, Plot No. 13/24",
                "2.5",
                123f
            )
        )
        shopList.add(
            ShopList(
                2,
                "https://img.freepik.com/free-vector/minimalist-furniture-logo-template-design_23-2148467616.jpg?w=740&t=st=1672036357~exp=1672036957~hmac=2fa016f51e352d283394ef2d63f313a999ca5c2d9728e00b30f19e50dab08725",
                "Luxury Leather Chairs",
                "furniture, household equipment or related materials and having a variety of different ...",
                3.5f,
                "BL-91, Plot No. 13/24",
                "3.5",
                234f
            )
        )
        shopList.add(
            ShopList(
                3,
                "https://img.freepik.com/free-vector/furniture-logo-concept_23-2148619623.jpg?w=740&t=st=1672036394~exp=1672036994~hmac=d71d26712779c0a2c0e382ba8d19b96082d0624b5a675e1736cad4ec2ef80970",
                "Meoble’s & Marbles",
                "furniture, household equipment or related materials and having a variety of different ...",
                4.5f,
                "BL-91, Plot No. 13/24",
                "4.5",
                452f
            )
        )
        shopList.add(
            ShopList(
                4,
                "https://img.freepik.com/free-vector/furniture-logo-with-minimalist-elements_23-2148628260.jpg?w=740&t=st=1672036453~exp=1672037053~hmac=658c4a7467134c3575910f06c8e8c97f91be03f9511360c94c9a8cf000f29adb",
                "Minimal Home",
                "furniture, household equipment or related materials and having a variety of different ...",
                1.5f,
                "BL-91, Plot No. 13/24",
                "1.5",
                123.5f
            )
        )
        shopList.add(
            ShopList(
                5,
                "https://img.freepik.com/free-vector/retro-furniture-logo_23-2148452344.jpg?w=740&t=st=1672036474~exp=1672037074~hmac=e40d30bebe5ec1538fae2077d8a359cb913cd33d7d479f56f0f2796dbea21253",
                "Oakley",
                "furniture, household equipment or related materials and having a variety of different ...",
                3.5f,
                "BL-91, Plot No. 13/24",
                "3.5",
                321.3f
            )
        )
        shopList.add(
            ShopList(
                6,
                "https://img.freepik.com/free-vector/elegant-furniture-logo-template_23-2148470895.jpg?w=740&t=st=1672036499~exp=1672037099~hmac=6f39f170d93736b042f21c8290c6a551daaf065b0f242df1005c472c7a61f000",
                "Odense Timber",
                "furniture, household equipment or related materials and having a variety of different ...",
                4.5f,
                "BL-91, Plot No. 13/24",
                "4.5",
                78.56f
            )
        )
        shopList.add(
            ShopList(
                7,
                "https://img.freepik.com/free-vector/minimalist-furniture-logo-template-with-illustration_23-2148619518.jpg?w=740&t=st=1672036527~exp=1672037127~hmac=7036b4c074fbe3e04d480fd83039a4f60b0d1faaaf076ee8b7795f5e7eb0af45",
                "Platinum Furniture",
                "furniture, household equipment or related materials and having a variety of different ...",
                3.5f,
                "BL-91, Plot No. 13/24",
                "3.5",
                123.4f
            )
        )
        shopList.add(
            ShopList(
                8,
                "https://img.freepik.com/free-vector/furniture-company-logo-business-template-branding-design-xx-home-interior_53876-140604.jpg?w=740&t=st=1672036554~exp=1672037154~hmac=163512a021544a8c49b09208410d131e471a812be59463f39ca95b414422312c",
                "Premium Store",
                "furniture, household equipment or related materials and having a variety of different ...",
                2.5f,
                "BL-91, Plot No. 13/24",
                "2.5",
                78.56f
            )
        )
        shopList.add(
            ShopList(
                9,
                "https://img.freepik.com/free-vector/retro-furniture-logo-template-theme_23-2148467615.jpg?w=740&t=st=1672036586~exp=1672037186~hmac=d68013421e6e0b13fd8e33bb4e86f735dec63d668042bb8ba5bf05b66cf41b3c",
                "Prescott Fusion",
                "furniture, household equipment or related materials and having a variety of different ...",
                1.5f,
                "BL-91, Plot No. 13/24",
                "1.5",
                123.56f
            )
        )
        shopList.add(
            ShopList(
                10,
                "https://img.freepik.com/free-vector/furniture-logo-concept_23-2148629242.jpg?w=740&t=st=1672036617~exp=1672037217~hmac=687728913d3c4cedca520e7c997a0514321da1de560437242dfee71607150ae2",
                "Quality Construct",
                "furniture, household equipment or related materials and having a variety of different ...",
                3.5f,
                "BL-91, Plot No. 13/24",
                "3.5",
                345.2f
            )
        )
    }

    /*
        fun chatImg(chatList: MutableList<Chat>){
            chatList.add(Chat(1,"https://img.freepik.com/free-vector/retro-furniture-logo_23-2148464123.jpg?w=740&t=st=1672036336~exp=1672036936~hmac=e6330984d1217f2d60451d83dbac8537d5556aea8b673c59464ba3496a07e4d8","Luxurious Lumber","furniture,  ...",))
            chatList.add(Chat(2,"https://img.freepik.com/free-vector/minimalist-furniture-logo-template-design_23-2148467616.jpg?w=740&t=st=1672036357~exp=1672036957~hmac=2fa016f51e352d283394ef2d63f313a999ca5c2d9728e00b30f19e50dab08725","Luxury Leather Chairs","furniture,  ..."))
            chatList.add(Chat(3,"https://img.freepik.com/free-vector/furniture-logo-concept_23-2148619623.jpg?w=740&t=st=1672036394~exp=1672036994~hmac=d71d26712779c0a2c0e382ba8d19b96082d0624b5a675e1736cad4ec2ef80970","Meoble’s & Marbles","furniture,different ..."))
            chatList.add(Chat(4,"https://img.freepik.com/free-vector/furniture-logo-with-minimalist-elements_23-2148628260.jpg?w=740&t=st=1672036453~exp=1672037053~hmac=658c4a7467134c3575910f06c8e8c97f91be03f9511360c94c9a8cf000f29adb","Minimal Home","furniture,  different ..."))
            chatList.add(Chat(5,"https://img.freepik.com/free-vector/retro-furniture-logo_23-2148452344.jpg?w=740&t=st=1672036474~exp=1672037074~hmac=e40d30bebe5ec1538fae2077d8a359cb913cd33d7d479f56f0f2796dbea21253","Oakley","furniture,  different ..."))
            chatList.add(Chat(6,"https://img.freepik.com/free-vector/elegant-furniture-logo-template_23-2148470895.jpg?w=740&t=st=1672036499~exp=1672037099~hmac=6f39f170d93736b042f21c8290c6a551daaf065b0f242df1005c472c7a61f000","Odense Timber","furniture, household  ..."))
            chatList.add(Chat(7,"https://img.freepik.com/free-vector/minimalist-furniture-logo-template-with-illustration_23-2148619518.jpg?w=740&t=st=1672036527~exp=1672037127~hmac=7036b4c074fbe3e04d480fd83039a4f60b0d1faaaf076ee8b7795f5e7eb0af45","Platinum Furniture","furniture, household  different ..."))
            chatList.add(Chat(8,"https://img.freepik.com/free-vector/furniture-company-logo-business-template-branding-design-xx-home-interior_53876-140604.jpg?w=740&t=st=1672036554~exp=1672037154~hmac=163512a021544a8c49b09208410d131e471a812be59463f39ca95b414422312c","Premium Store","furniture,  different ..."))
            chatList.add(Chat(9,"https://img.freepik.com/free-vector/retro-furniture-logo-template-theme_23-2148467615.jpg?w=740&t=st=1672036586~exp=1672037186~hmac=d68013421e6e0b13fd8e33bb4e86f735dec63d668042bb8ba5bf05b66cf41b3c","Prescott Fusion","furniture,  different ..."))
            chatList.add(Chat(10,"https://img.freepik.com/free-vector/furniture-logo-concept_23-2148629242.jpg?w=740&t=st=1672036617~exp=1672037217~hmac=687728913d3c4cedca520e7c997a0514321da1de560437242dfee71607150ae2","Quality Construct","furniture, household  different ..."))
        }*/

    fun chatListData(chatListData: ArrayList<Chat>) {
        chatListData.add(Chat("First Recode", "Jeni", "jeni@gmail.com"))
        chatListData.add(Chat("Second Recode", "Abc", "abc@gmail.com"))
        chatListData.add(Chat("Third Recode", "Xyz", "xyz@gmail.com"))
    }

}