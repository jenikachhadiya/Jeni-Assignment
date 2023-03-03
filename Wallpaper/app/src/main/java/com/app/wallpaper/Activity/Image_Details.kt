package com.app.wallpaper.Activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.wallpaper.Adaptor.imgdetali_adaptor
import com.app.wallpaper.Interface.OnImgDetailsListner
import com.app.wallpaper.Modal.imgD
import com.app.wallpaper.databinding.ActivityImageDetailsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class Image_Details : AppCompatActivity() {
    lateinit var binding: ActivityImageDetailsBinding
    lateinit var Iadaptor: imgdetali_adaptor


    // private val firebaseAuth = FirebaseAuth.getInstance()
    // val database = Firebase.database
  //  private lateinit var myRef: DatabaseReference
   lateinit var db :FirebaseFirestore

    //private var Ilist = mutableListOf<imgD>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var tital = intent.getStringExtra("tag")
        binding.toolbar.setTitle(tital)


        //  FirebaseApp.initializeApp(this)
        //firebase
        // val myRef = database.getReference("wallpaper")
        ///dbReference = FirebaseDatabase.getInstance().reference.child("users")
         db = FirebaseFirestore.getInstance()

        db.collection("Abstract")
            .get()
            .addOnSuccessListener{
                result->
               /* var list = ArrayList<imgD>()
                val users = document.toObject(imgD::class.java)
                Log.d("TAG", "${document.id} => ${document.data}")
                //  val data  = value?.toObjects(imgD::class.java)
                list.add(users)
                Iadaptor = imgdetali_adaptor(this@Image_Details, list)
                binding.recView.layoutManager = LinearLayoutManager(this@Image_Details)
                binding.recView.adapter = Iadaptor*/

                Toast.makeText(this, "proccess", Toast.LENGTH_SHORT).show()
                var list = ArrayList<imgD>()
                val data  = result?.toObjects(imgD::class.java)
                list.addAll(data!!)
                for (i in list){
                    Log.e("jjjj","on CreateView"+i)

                    Iadaptor = imgdetali_adaptor(this@Image_Details, list)
                    binding.recView.layoutManager = LinearLayoutManager(this@Image_Details)
                    binding.recView.adapter = Iadaptor
                    //adapter.notifyDataSetChanged()
                    Toast.makeText(this, "thay gyu", Toast.LENGTH_SHORT).show()
                }
            }


            /*.addSnapshotListener { value, error ->
            Toast.makeText(this, "proccess", Toast.LENGTH_SHORT).show()
            var list = ArrayList<imgD>()
            val data  = value?.toObjects(imgD::class.java)
            list.addAll(data!!)
            for (i in list){
                Log.e("jjjj","on CreateView"+i)

                Iadaptor = imgdetali_adaptor(this@Image_Details, list)
                binding.recView.layoutManager = LinearLayoutManager(this@Image_Details)
                binding.recView.adapter = Iadaptor
                //adapter.notifyDataSetChanged()
                Toast.makeText(this, "thay gyu", Toast.LENGTH_SHORT).show()
            }

        }*/


        /*myRef = FirebaseDatabase.getInstance()
            .getReference()
            .child("wallpaper")
         myRef .child("wallpaper").child(imgD::img.toString()).get().addOnSuccessListener {
             Log.i("firebase", "Got value ${it.value}")
         }.addOnFailureListener{
             Log.e("firebase", "Error getting data", it)
         }

            var post = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Ilist = mutableListOf()

                        for (usersnapshot: DataSnapshot in snapshot.children) {
                            val user = usersnapshot.getValue(imgD::class.java)
                            Ilist.add(user!!)


                        }
                        binding.recView.layoutManager = GridLayoutManager(this@Image_Details, 2)
                        binding.recView.adapter = Iadaptor
                        binding.recView.adapter = imgdetali_adaptor(this@Image_Details, Ilist)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
        myRef.addValueEventListener(post)



*/


       // getuserData()

        /* FirebaseDatabase.getInstance()
             .getReference()
             .child("wallpaper")
             .addValueEventListener(object:ValueEventListener{
                 override fun onDataChange(snapshot: DataSnapshot) {
                     if (snapshot.exists()){
                         Ilist.clear()
                         for (snapshot1:DataSnapshot in snapshot.children)
                         {
                         //  var i = snapshot1.getValue(imgD::class.java)
                             var data = snapshot1.getValue<imgD>()
                             if (data != null) {
                                 Ilist.add(data)
                             }
                         }
                         Iadaptor = imgdetali_adaptor(this@Image_Details, Ilist)
                         binding.recView.layoutManager = GridLayoutManager(this@Image_Details, 2)
                         binding.recView.adapter = Iadaptor

                         Iadaptor.notifyDataSetChanged()
                     }
                 }

                 override fun onCancelled(error: DatabaseError) {
                     TODO("Not yet implemented")
                 }

             })

 */

        //   val  myRef = FirebaseDatabase.getInstance().reference.child("wallpaper")

        //val anotherDatabase = FirebaseDatabase.getInstance(FirebaseApp.getInstance("wallpaper"))
        // myRef.addValueEventListener(object : ValueEventListener {

        /*override fun onDataChange(dataSnapshot: DataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            Ilist = mutableListOf()

           // val value = dataSnapshot.getValue(imgD::class.java)
            val value = dataSnapshot.getValue<imgD>()
            Log.d(TAG, "Value is: " + value)

            for (value:DataSnapshot in dataSnapshot.children) {
                var data = value.getValue<imgD>()
                if (data != null) {
                    Ilist.add(data)
                }
            }

            Iadaptor = imgdetali_adaptor(this@Image_Details, Ilist)
            binding.recView.layoutManager = GridLayoutManager(this@Image_Details, 3)
            binding.recView.adapter = Iadaptor

        }

        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
            //  Log.w(TAG, "Failed to read value.", error.toException())
        }
    })
*/


        //dataset()


        binding.drwer.setOnClickListener {
            onBackPressed()
        }
       /* Iadaptor.Clicked(object : OnImgDetailsListner {
            override fun OnImgListner(position: Int) {
                Toast.makeText(applicationContext, "$position", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, ImgActivity::class.java))

            }

        })*/

    }

    private fun getuserData() {

       /* myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //  if (snapshot.exists()) {
              //  Ilist = mutableListOf()

             *//*   for (usersnapshot: DataSnapshot in snapshot.children) {
                    val user= usersnapshot.getValue(imgD::class.java)
                    Ilist.add(user!!)
                    //Picasso.get().load(user).into(Ilist);
                }*//*
                binding.recView.layoutManager = GridLayoutManager(this@Image_Details,2)
                binding.recView.adapter = Iadaptor
                binding.recView.adapter = imgdetali_adaptor(this@Image_Details, Ilist)
                //}
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })*/

    }

    /* private fun dataset() {
         Ilist.add(imgD(1, R.drawable.nimg))
         Ilist.add(imgD(1, R.drawable.nimg))
         Ilist.add(imgD(1, R.drawable.nimg))
         Ilist.add(imgD(1, R.drawable.nimg))
         Ilist.add(imgD(1, R.drawable.nimg))
         Ilist.add(imgD(1, R.drawable.nimg))
         Ilist.add(imgD(1, R.drawable.nimg))
         Ilist.add(imgD(1, R.drawable.nimg))
         Ilist.add(imgD(1, R.drawable.nimg))
         Ilist.add(imgD(1, R.drawable.nimg))
         Ilist.add(imgD(1, R.drawable.nimg))
         Ilist.add(imgD(1, R.drawable.nimg))
         Ilist.add(imgD(1, R.drawable.nimg))
         Ilist.add(imgD(1, R.drawable.nimg))

     }*/
}


