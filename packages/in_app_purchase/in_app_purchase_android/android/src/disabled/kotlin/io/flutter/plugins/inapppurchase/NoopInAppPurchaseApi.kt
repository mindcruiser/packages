// Copyright 2013 The Flutter Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.inapppurchase

/** 以共享协议完成所有误调用；不可用结果不会触发 Dart 的断线重试。 */
class NoopInAppPurchaseApi : InAppPurchaseApi {
    private companion object {
        const val UNAVAILABLE_MESSAGE = "Billing is disabled for this application variant."
        const val NO_SUB_RESPONSE_CODE = 0L
        const val NO_COUNTRY_CODE = ""
        const val NO_EXTERNAL_TRANSACTION_TOKEN = ""

        val unavailable = PlatformBillingResult(
            responseCode = PlatformBillingResponse.BILLING_UNAVAILABLE,
            debugMessage = UNAVAILABLE_MESSAGE,
            subResponseCode = NO_SUB_RESPONSE_CODE,
        )
    }

    override fun isReady() = false

    override fun startConnection(
        callbackHandle: Long,
        billingMode: PlatformBillingChoiceMode,
        pendingPurchasesParams: PlatformPendingPurchasesParams,
        callback: (Result<PlatformBillingResult>) -> Unit,
    ) {
        callback(Result.success(unavailable))
    }

    override fun endConnection() = Unit

    override fun getBillingConfigAsync(callback: (Result<PlatformBillingConfigResponse>) -> Unit) {
        callback(Result.success(PlatformBillingConfigResponse(unavailable, NO_COUNTRY_CODE)))
    }

    override fun launchBillingFlow(params: PlatformBillingFlowParams) = unavailable

    override fun acknowledgePurchase(purchaseToken: String, callback: (Result<PlatformBillingResult>) -> Unit) {
        callback(Result.success(unavailable))
    }

    override fun consumeAsync(purchaseToken: String, callback: (Result<PlatformBillingResult>) -> Unit) {
        callback(Result.success(unavailable))
    }

    override fun queryPurchasesAsync(
        productType: PlatformProductType,
        callback: (Result<PlatformPurchasesResponse>) -> Unit,
    ) {
        callback(Result.success(PlatformPurchasesResponse(unavailable, emptyList())))
    }

    override fun queryProductDetailsAsync(
        products: List<PlatformQueryProduct>,
        callback: (Result<PlatformProductDetailsResponse>) -> Unit,
    ) {
        callback(Result.success(PlatformProductDetailsResponse(unavailable, emptyList(), emptyList())))
    }

    override fun isFeatureSupported(feature: PlatformBillingClientFeature) = false

    override fun isAlternativeBillingOnlyAvailableAsync(callback: (Result<PlatformBillingResult>) -> Unit) {
        callback(Result.success(unavailable))
    }

    override fun showAlternativeBillingOnlyInformationDialog(callback: (Result<PlatformBillingResult>) -> Unit) {
        callback(Result.success(unavailable))
    }

    override fun createAlternativeBillingOnlyReportingDetailsAsync(
        callback: (Result<PlatformAlternativeBillingOnlyReportingDetailsResponse>) -> Unit,
    ) {
        callback(Result.success(PlatformAlternativeBillingOnlyReportingDetailsResponse(
            unavailable, NO_EXTERNAL_TRANSACTION_TOKEN,
        )))
    }
}
