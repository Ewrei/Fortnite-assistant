package robin.vitalij.fortniteassitant.ui.subscription

import androidx.lifecycle.MutableLiveData
//import org.solovyev.android.checkout.*
//import org.solovyev.android.checkout.Checkout.EmptyListener
//import org.solovyev.android.checkout.Inventory.Products
import robin.vitalij.fortniteassitant.model.SubscriptionModel
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import java.util.*

private const val SUBSCRIPTION_ID = "subscription_fortnite"

class SubscriptionsViewModel(val preferenceManager: PreferenceManager) :
    BaseViewModel() {

    var data = MutableLiveData<SubscriptionModel>()

    private val subscriptionList: List<String> =
        listOf(SUBSCRIPTION_ID)

//    var checkout: ActivityCheckout? = null
//
//    private val inventoryCallbacks: MutableList<Inventory.Callback> = ArrayList()
//
//    private var mInventory: Inventory? = null
//
//    private var sku: Sku? = null
//
//    inner class PurchaseListener :
//        EmptyRequestListener<Purchase>() {
//        override fun onSuccess(purchase: Purchase) {
//            preferenceManager.setIsSubscription(true)
//            reloadInventory()
//        }
//
//        override fun onError(response: Int, e: java.lang.Exception) {
//            reloadInventory()
//        }
//    }
//
//    inner class InventoryCallback : Inventory.Callback {
//        override fun onLoaded(products: Products) {
//            val list = products.get(ProductTypes.SUBSCRIPTION).skus
//            if (list.isNotEmpty()) {
//                sku = list.first()
//                val subscriptionModel =
//                    SubscriptionModel(list.first().title, list.first().price, list.first())
//                data.value = subscriptionModel
//            }
//        }
//    }
//
//    fun reloadInventory() {
//        val request = Inventory.Request.create()
//        request.loadPurchases(ProductTypes.SUBSCRIPTION)
//        request.loadSkus(ProductTypes.SUBSCRIPTION, subscriptionList)
//        checkout!!.loadInventory(
//            request
//        ) { products ->
//            for (callback in inventoryCallbacks) {
//                callback.onLoaded(products)
//            }
//        }
//    }
//
//    fun onClick() {
//        checkout?.whenReady(object : EmptyListener() {
//            override fun onReady(requests: BillingRequests) {
//                requests.purchase(
//                    ProductTypes.SUBSCRIPTION,
//                    SUBSCRIPTION_ID,
//                    null,
//                    checkout!!.purchaseFlow
//                )
//            }
//        })
//    }
//
//    fun loadData() {
//        checkout?.start()
//        checkout?.createPurchaseFlow(PurchaseListener())
//        mInventory = checkout?.makeInventory()
//        mInventory?.load(
//            Inventory.Request.create()
//                .loadAllPurchases()
//                .loadSkus(ProductTypes.SUBSCRIPTION, SUBSCRIPTION_ID),
//            InventoryCallback()
//        )
//    }

    fun saveData(): Date {
        val date = Date()
        val instance = Calendar.getInstance()
        instance.time = date
        instance.add(Calendar.DAY_OF_MONTH, 4)// прибавляем 1 к установленной дате
        val newDate = instance.time // получаем измененную дату
        preferenceManager.setDisableAdvertising(newDate.time)
        return newDate
    }
}