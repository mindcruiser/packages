// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:flutter/material.dart';

import '../../billing_client_wrappers.dart';

/// The error message shown when the map represents billing result is invalid from method channel.
///
/// This usually indicates a serious underlining code issue in the plugin.
@visibleForTesting
const String kInvalidBillingResultErrorMessage =
    'Invalid billing result map from method channel.';

/// Params containing the response code and the debug message from the Play Billing API response.
@immutable
class BillingResultWrapper implements HasBillingResponse {
  /// Constructs the object with [responseCode], [subResponseCode], and
  /// [debugMessage].
  const BillingResultWrapper({
    required this.responseCode,
    this.subResponseCode = 0,
    this.debugMessage,
  });

  /// Response code returned in the Play Billing API calls.
  @override
  final BillingResponse responseCode;

  /// Sub-response code returned by the Play Billing API.
  ///
  /// Possible values are `0` when no more specific response code applies, `1`
  /// for insufficient funds, and `2` when the user is ineligible.
  final int subResponseCode;

  /// Debug message returned in the Play Billing API calls.
  ///
  /// Defaults to `null`.
  /// This message uses an en-US locale and should not be shown to users.
  // TODO(stuartmorgan): Make this non-nullable, since the underlying native
  // object's property is annotated as @NonNull.
  final String? debugMessage;

  @override
  bool operator ==(Object other) {
    if (other.runtimeType != runtimeType) {
      return false;
    }

    return other is BillingResultWrapper &&
        other.responseCode == responseCode &&
        other.subResponseCode == subResponseCode &&
        other.debugMessage == debugMessage;
  }

  @override
  int get hashCode => Object.hash(responseCode, subResponseCode, debugMessage);
}
