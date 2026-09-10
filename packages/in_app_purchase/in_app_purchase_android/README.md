# in\_app\_purchase\_android

The Android implementation of [`in_app_purchase`][1].

## Usage

This package is [endorsed][2], which means you can simply use `in_app_purchase`
normally. This package will be automatically included in your app when you do,
so you do not need to add it to your `pubspec.yaml`.

However, if you `import` this package to use any of its APIs directly, you
should [add it to your `pubspec.yaml` as usual][3].

## Alternative/UserChoice Billing

Alternative and UserChoice billing from Google Play is exposed from this package.

Using the Alternative billing only feature requires Google Play app configuration, checking if the feature is available (`isAlternativeBillingOnlyAvailable`) and informing users that Google Play does not handle all aspects of purchase (`showAlternativeBillingOnlyInformationDialog`). After those calls then you can call `setBillingChoice` and respond when a user attempts a purchase.

[Google Play documentation for Alternative billing](https://developer.android.com/google/play/billing/alternative)

## Migrating to 0.5.0
To migrate to version 0.5.0 from 0.4.x or 0.3.0 from 0.2.x, have a look at the
[migration guide](migration_guide.md).

[1]: https://pub.dev/packages/in_app_purchase
[2]: https://flutter.dev/to/endorsed-federated-plugin
[3]: https://pub.dev/packages/in_app_purchase_android/install

## Notta local Billing isolation

The local Android 0.5.0 fork uses `billingMode` flavors. `play` contains the
original Java implementation and Kotlin translator with Billing Client 8.0.0.
`disabled` registers the same plugin and Pigeon API but returns billing unavailable
without linking the Billing SDK. Shared generated messages and the registration entry remain in `main`.
Flutter 3.38.5 validates that the entry file exists in main during plugin
registration, so the shared entry delegates FlutterPlugin and ActivityAware to
the flavor-specific InAppPurchasePluginDelegate. It contains no Billing types.
Consumers select `play` or `disabled` with `missingDimensionStrategy`; the example
selects `play`. Flavor configuration lives in `android/build.gradle.kts`.

Local patches preserve profileId forwarding and isolate Billing at compile time.
Past-purchase query errors use the native billing result rather than the legacy
outer status that is forced to OK; this does not change connection retry policy.
Dependencies remain local paths during stage one. No release tag has been created.
For future upstream upgrades, verify the clean official base and reapply only
required compatibility and isolation patches. Do not use task source filtering,
runtime switches, or R8 removal as substitutes for source-set isolation.

Validate `testPlayDebugUnitTest` and `testDisabledDebugUnitTest`, both app flavors,
their compile/runtime classpaths, and all DEX files in the final Intune APK/AAB.
The Intune dependency graph and binary must contain no Billing SDK.
