package com.example.new_task.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClient.ProductType
import com.android.billingclient.api.QueryProductDetailsParams.Product
import com.example.new_task.R
import com.example.new_task.activity.DataActivity
import com.example.new_task.activity.MapsActivity
import com.example.new_task.databinding.FragmentSettingFragmentsBinding
import com.google.firebase.crashlytics.internal.model.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import okhttp3.internal.immutableListOf


class SettingFragments : Fragment(), PurchasesUpdatedListener {

    private val purchasesUpdatedListener = PurchasesUpdatedListener { _, _ ->
        Toast.makeText(requireContext(), "this ", Toast.LENGTH_SHORT).show()
    }
    private lateinit var billingClient: BillingClient
    private var productDetails: ProductDetails? = null


/*
    val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
        .setProductList(
            ImmutableList.from(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId("testing")
                    .setProductType(ProductType.SUBS)
                    .build())
        ).build()
*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSettingFragmentsBinding.inflate(LayoutInflater.from(requireContext()))


        // setUpBillingClint()
        binding.btnDataActivity.setOnClickListener {
            startActivity(Intent(requireContext(), DataActivity::class.java))
        }
        binding.btnLocation.setOnClickListener {
            startActivity(Intent(requireContext(), MapsActivity::class.java))
        }
        binding.btnBuy.setOnClickListener {
            makePurchase(it)
        }

        return binding.root
    }


    //purchases update
    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        if (billingResult.responseCode == BillingResponseCode.OK && purchases != null) {
            for (i in purchases) {
                completePurchase(i)
                handlingPurchaseHistory(i)
                i.accountIdentifiers
                i.developerPayload
                i.orderId
                i.products
                i.purchaseTime
                i.purchaseToken
            }

        } else if (billingResult.responseCode == BillingResponseCode.USER_CANCELED) {
            Log.e(TAG, "onPurchasesUpdated: CANCELED")
        } else {
            Log.e(TAG, "onPurchasesUpdated: Error")
        }
    }

    private fun completePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            val token = purchase.purchaseToken
            Toast.makeText(requireContext(), "purchase", Toast.LENGTH_SHORT).show()
        }

    }

    //In-App Porches
    private fun setUpBillingClint() {
        billingClient = BillingClient.newBuilder(requireContext())
            .setListener(this)
            .enablePendingPurchases()
            .build()
        startConnection()
    }

    private fun startConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                Toast.makeText(requireContext(), "disconnect", Toast.LENGTH_SHORT).show()
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingResponseCode.OK) {
                    // queryProduct()
                }
            }
        })
    }

    //launcher
    private fun makePurchase(view: View) {
        // queryProduct()
        setUpBillingClint()
        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                immutableListOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("testing")
                        .setProductType(ProductType.INAPP)
                        .build()
                )
            ).build()
        //details
        billingClient.queryProductDetailsAsync(queryProductDetailsParams) { result, list ->
            var mes = result.debugMessage
            Log.e(TAG, "makePurchase: $list")
        }
        try {
            val billingFlowParams: BillingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(
                    immutableListOf(
                        BillingFlowParams.ProductDetailsParams
                            .newBuilder()
                            .setProductDetails(
                                productDetails!!
                            )
                            .build()
                    )
                ).build()
            var billingResult =
                billingClient.launchBillingFlow(requireActivity(), billingFlowParams)

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }

    private  fun handlingPurchaseHistory(purchase: Purchase) {
          val purchase :Purchase = purchase
            var consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()

        val params = QueryPurchaseHistoryParams.newBuilder()
            .setProductType(ProductType.INAPP)

    }

}


