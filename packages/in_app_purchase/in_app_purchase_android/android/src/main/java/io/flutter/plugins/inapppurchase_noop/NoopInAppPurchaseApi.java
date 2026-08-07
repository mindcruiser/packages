// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.inapppurchase;

import androidx.annotation.NonNull;
import io.flutter.plugins.inapppurchase.Messages.FlutterError;
import io.flutter.plugins.inapppurchase.Messages.InAppPurchaseApi;
import io.flutter.plugins.inapppurchase.Messages.PlatformAlternativeBillingOnlyReportingDetailsResponse;
import io.flutter.plugins.inapppurchase.Messages.PlatformBillingChoiceMode;
import io.flutter.plugins.inapppurchase.Messages.PlatformBillingClientFeature;
import io.flutter.plugins.inapppurchase.Messages.PlatformBillingConfigResponse;
import io.flutter.plugins.inapppurchase.Messages.PlatformBillingFlowParams;
import io.flutter.plugins.inapppurchase.Messages.PlatformBillingResult;
import io.flutter.plugins.inapppurchase.Messages.PlatformPendingPurchasesParams;
import io.flutter.plugins.inapppurchase.Messages.PlatformProductDetailsResponse;
import io.flutter.plugins.inapppurchase.Messages.PlatformProductType;
import io.flutter.plugins.inapppurchase.Messages.PlatformPurchasesResponse;
import io.flutter.plugins.inapppurchase.Messages.PlatformQueryProduct;
import io.flutter.plugins.inapppurchase.Messages.Result;
import java.util.List;

/** Provides the in-app purchase channel without a purchase service implementation. */
final class NoopInAppPurchaseApi implements InAppPurchaseApi {
  static final String ERROR_CODE = "BILLING_UNAVAILABLE";
  private static final String ERROR_MESSAGE =
      "In-app purchases are unavailable because this Android build has no purchase service implementation.";

  NoopInAppPurchaseApi() {}

  @Override
  public @NonNull Boolean isReady() {
    return false;
  }

  @Override
  public void startConnection(
      @NonNull Long callbackHandle,
      @NonNull PlatformBillingChoiceMode billingMode,
      @NonNull PlatformPendingPurchasesParams pendingPurchasesParams,
      @NonNull Result<PlatformBillingResult> result) {
    unavailable(result);
  }

  @Override
  public void endConnection() {}

  @Override
  public void getBillingConfigAsync(@NonNull Result<PlatformBillingConfigResponse> result) {
    unavailable(result);
  }

  @Override
  public @NonNull PlatformBillingResult launchBillingFlow(@NonNull PlatformBillingFlowParams params) {
    throw unavailableError();
  }

  @Override
  public void acknowledgePurchase(
      @NonNull String purchaseToken, @NonNull Result<PlatformBillingResult> result) {
    unavailable(result);
  }

  @Override
  public void consumeAsync(
      @NonNull String purchaseToken, @NonNull Result<PlatformBillingResult> result) {
    unavailable(result);
  }

  @Override
  public void queryPurchasesAsync(
      @NonNull PlatformProductType productType,
      @NonNull Result<PlatformPurchasesResponse> result) {
    unavailable(result);
  }

  @Override
  public void queryProductDetailsAsync(
      @NonNull List<PlatformQueryProduct> products,
      @NonNull Result<PlatformProductDetailsResponse> result) {
    unavailable(result);
  }

  @Override
  public @NonNull Boolean isFeatureSupported(@NonNull PlatformBillingClientFeature feature) {
    return false;
  }

  @Override
  public void isAlternativeBillingOnlyAvailableAsync(
      @NonNull Result<PlatformBillingResult> result) {
    unavailable(result);
  }

  @Override
  public void showAlternativeBillingOnlyInformationDialog(
      @NonNull Result<PlatformBillingResult> result) {
    unavailable(result);
  }

  @Override
  public void createAlternativeBillingOnlyReportingDetailsAsync(
      @NonNull Result<PlatformAlternativeBillingOnlyReportingDetailsResponse> result) {
    unavailable(result);
  }

  private static FlutterError unavailableError() {
    return new FlutterError(ERROR_CODE, ERROR_MESSAGE, null);
  }

  private static <T> void unavailable(@NonNull Result<T> result) {
    result.error(unavailableError());
  }
}
