// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.inapppurchase;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;

/** Registers the no-op Android implementation of the in-app purchase channel. */
public class InAppPurchasePlugin implements FlutterPlugin {

  private NoopInAppPurchaseApi api;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
    setUpMethodChannel(binding.getBinaryMessenger());
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
    Messages.InAppPurchaseApi.setUp(binding.getBinaryMessenger(), null);
    api = null;
  }

  private void setUpMethodChannel(BinaryMessenger messenger) {
    api = new NoopInAppPurchaseApi();
    Messages.InAppPurchaseApi.setUp(messenger, api);
  }
}
