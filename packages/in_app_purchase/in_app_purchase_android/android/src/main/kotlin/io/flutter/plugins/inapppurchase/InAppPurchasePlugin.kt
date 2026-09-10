// Copyright 2013 The Flutter Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.inapppurchase

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware

/** Flutter 要求 main 中存在注册入口；实际能力由 SourceSet 在编译时选择。 */
class InAppPurchasePlugin private constructor(
    delegate: InAppPurchasePluginDelegate,
) : FlutterPlugin by delegate, ActivityAware by delegate {
    constructor() : this(InAppPurchasePluginDelegate())
}
