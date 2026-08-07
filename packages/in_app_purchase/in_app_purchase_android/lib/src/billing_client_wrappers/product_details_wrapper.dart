// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:flutter/foundation.dart';

import '../../billing_client_wrappers.dart';

/// Dart wrapper around [`com.android.billingclient.api.ProductDetails`](https://developer.android.com/reference/com/android/billingclient/api/ProductDetails).
///
/// Contains the details of an available product in Google Play Billing.
/// Represents the details of a one-time or subscription product.
@immutable
class ProductDetailsWrapper {
  /// Creates a [ProductDetailsWrapper] with the given purchase details.
  const ProductDetailsWrapper({
    required this.description,
    required this.name,
    this.oneTimePurchaseOfferDetails,
    this.oneTimePurchaseOfferDetailsList,
    required this.productId,
    required this.productType,
    this.subscriptionOfferDetails,
    required this.title,
  });

  /// Textual description of the product.
  final String description;

  /// The name of the product being sold.
  ///
  /// Similar to [title], but does not include the name of the app which owns
  /// the product. Example: 100 Gold Coins.
  final String name;

  /// The offer details of a one-time purchase product.
  ///
  /// [oneTimePurchaseOfferDetails] is only set for [ProductType.inapp]. Returns
  /// null for [ProductType.subs].
  final OneTimePurchaseOfferDetailsWrapper? oneTimePurchaseOfferDetails;

  /// All purchase options and offers for a one-time product.
  ///
  /// This is only set for [ProductType.inapp].
  final List<OneTimePurchaseOfferDetailsWrapper>?
      oneTimePurchaseOfferDetailsList;

  /// The product's id.
  final String productId;

  /// The [ProductType] of the product.
  final ProductType productType;

  /// A list containing all available offers to purchase a subscription product.
  ///
  /// [subscriptionOfferDetails] is only set for [ProductType.subs]. Returns
  /// null for [ProductType.inapp].
  final List<SubscriptionOfferDetailsWrapper>? subscriptionOfferDetails;

  /// The title of the product being sold.
  ///
  /// Similar to [name], but includes the name of the app which owns the
  /// product. Example: 100 Gold Coins (Coin selling app).
  final String title;

  @override
  bool operator ==(Object other) {
    if (other.runtimeType != runtimeType) {
      return false;
    }

    return other is ProductDetailsWrapper &&
        other.description == description &&
        other.name == name &&
        other.oneTimePurchaseOfferDetails == oneTimePurchaseOfferDetails &&
        listEquals(other.oneTimePurchaseOfferDetailsList,
            oneTimePurchaseOfferDetailsList) &&
        other.productId == productId &&
        other.productType == productType &&
        listEquals(other.subscriptionOfferDetails, subscriptionOfferDetails) &&
        other.title == title;
  }

  @override
  int get hashCode {
    return Object.hash(
      description.hashCode,
      name.hashCode,
      oneTimePurchaseOfferDetails.hashCode,
      oneTimePurchaseOfferDetailsList.hashCode,
      productId.hashCode,
      productType.hashCode,
      subscriptionOfferDetails.hashCode,
      title.hashCode,
    );
  }
}

/// Translation of [`com.android.billingclient.api.ProductDetailsResponseListener`](https://developer.android.com/reference/com/android/billingclient/api/ProductDetailsResponseListener.html).
///
/// Returned by [BillingClient.queryProductDetails].
@immutable
class ProductDetailsResponseWrapper implements HasBillingResponse {
  /// Creates a [ProductDetailsResponseWrapper] with the given purchase details.
  const ProductDetailsResponseWrapper({
    required this.billingResult,
    required this.productDetailsList,
    this.unfetchedProductList = const <UnfetchedProductWrapper>[],
  });

  /// The final result of the [BillingClient.queryProductDetails] call.
  final BillingResultWrapper billingResult;

  /// A list of [ProductDetailsWrapper] matching the query to [BillingClient.queryProductDetails].
  final List<ProductDetailsWrapper> productDetailsList;

  /// Products that the Play Billing service could not return.
  final List<UnfetchedProductWrapper> unfetchedProductList;

  @override
  BillingResponse get responseCode => billingResult.responseCode;

  @override
  bool operator ==(Object other) {
    if (other.runtimeType != runtimeType) {
      return false;
    }

    return other is ProductDetailsResponseWrapper &&
        other.billingResult == billingResult &&
        listEquals(other.productDetailsList, productDetailsList) &&
        listEquals(other.unfetchedProductList, unfetchedProductList);
  }

  @override
  int get hashCode =>
      Object.hash(billingResult, productDetailsList, unfetchedProductList);
}

/// A product that could not be returned by a product-details query.
@immutable
class UnfetchedProductWrapper {
  /// Creates an unfetched product result.
  const UnfetchedProductWrapper({required this.productId});

  /// The product identifier that could not be fetched.
  final String productId;

  @override
  bool operator ==(Object other) {
    return other is UnfetchedProductWrapper && other.productId == productId;
  }

  @override
  int get hashCode => productId.hashCode;
}

/// Recurrence mode of the pricing phase.
enum RecurrenceMode {
  /// The billing plan payment recurs for a fixed number of billing period set
  /// in billingCycleCount.
  finiteRecurring,

  /// The billing plan payment recurs for infinite billing periods unless
  /// cancelled.
  infiniteRecurring,

  /// The billing plan payment is a one time charge that does not repeat.
  nonRecurring,
}
