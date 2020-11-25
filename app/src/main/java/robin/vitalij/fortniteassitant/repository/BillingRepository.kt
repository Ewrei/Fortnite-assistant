package robin.vitalij.fortniteassitant.repository

import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.showToast
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingRepository @Inject constructor(
    private val context: Context,
    private val preferenceManager: PreferenceManager
) {

    private var billingClient: BillingClient = BillingClient.newBuilder(context)
        .enablePendingPurchases()
        .setListener { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (purchase in purchases) {
                    handlePurchase(purchase)
                }
            } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                context.showToast(context.getString(R.string.purchases_user_canceled))
            }
        }.build()

    fun startConnection(isInformation: Boolean = false) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can quBillingResponseery purchases here.

                    val purchasesResult: Purchase.PurchasesResult =
                        billingClient.queryPurchases(BillingClient.SkuType.SUBS)

                    val purchasesResult2: Purchase.PurchasesResult =
                        billingClient.queryPurchases(BillingClient.SkuType.INAPP)

                    val isSubscription =
                        !purchasesResult.purchasesList.isNullOrEmpty() || !purchasesResult2.purchasesList.isNullOrEmpty()
                    preferenceManager.setIsSubscription(isSubscription)

                    if (isInformation) {
                        context.showToast(context.getString(if (isSubscription) R.string.purchases_message else R.string.purchases_message_no))
                    }
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            // Grant the item to the user, and then acknowledge the purchase
        } else if (purchase.purchaseState == Purchase.PurchaseState.PENDING) {
            // Here you can confirm to the user that they've started the pending
            // purchase, and to complete it, they should follow instructions that
            // are given to them. You can also choose to remind the user in the
            // future to complete the purchase if you detect that it is still
            // pending.
        }
    }
}