# JetPack FoodRecipe

Used Dagger-Hilt, kotlin coroutines, roomdatabase, offline caching, coil and much more


<table>
  <tr>
      <td><img src="https://user-images.githubusercontent.com/45313305/191195203-f7179744-dd78-4125-9b54-7b95c53f41e7.jpg" width=360 height=640></td>
     <td><img src="https://user-images.githubusercontent.com/45313305/191197437-aa5e8385-b8c8-41d7-9307-52a959554eae.jpg" width=360 height=640></td>
  </tr>
<tr>
    <td><img src="https://user-images.githubusercontent.com/45313305/193030600-a350e021-fdb2-4849-a340-041274bf4190.jpg" width=360 height=640></td>
   <td><img src="https://user-images.githubusercontent.com/45313305/193028622-b9793a44-3803-4fe7-aa9b-6a6194ffe2d9.gif" width=360 height=640></td>
  </tr>
<tr>
    <td><img src="https://user-images.githubusercontent.com/45313305/176989466-cad5377f-248f-41c0-a682-7f0305aedd09.jpg" width=360 height=640></td>
    <td><img src="https://user-images.githubusercontent.com/45313305/176989468-2102e7b9-abc6-4022-b0db-c5db7b260054.jpg" width=360 height=640></td> 
</tr>
<tr>
   <td><img src="https://user-images.githubusercontent.com/45313305/176989348-ae00aa0b-3935-4ad1-ab0e-8797f09ce94d.jpg" width=360 height=640></td>
   <td><img src="https://user-images.githubusercontent.com/45313305/176989460-86cc928f-383a-46fa-81f4-18c154a5be3d.jpg" width=360 height=640></td>
</tr>

 </table>

Tech Stack.
Kotlin - Kotlin is a programming language that can run on JVM. Google has announced Kotlin as one of its officially supported programming languages in Android Studio; and the Android community is migrating at a pace from Java to Kotlin.

Jetpack components:

Jetpack Compose - Jetpack Compose is Android’s modern toolkit for building native UI. It simplifies and accelerates UI development on Android. Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs.
Android KTX - Android KTX is a set of Kotlin extensions that are included with Android Jetpack and other Android libraries. KTX extensions provide concise, idiomatic Kotlin to Jetpack, Android platform, and other APIs.
AndroidX - Major improvement to the original Android Support Library, which is no longer maintained.
Lifecycle - Lifecycle-aware components perform actions in response to a change in the lifecycle status of another component, such as activities and fragments. These components help you produce better-organized, and often lighter-weight code, that is easier to maintain.
ViewModel -The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way.
LiveData - LiveData is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services. This awareness ensures LiveData only updates app component observers that are in an active lifecycle state.
Room database - The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
Preferences DataStore - Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers. DataStore uses Kotlin coroutines and Flow to store data asynchronously, consistently, and transactional.
Kotlin Coroutines - A concurrency design pattern that you can use on Android to simplify code that executes asynchronously.

Retrofit - Retrofit is a REST client for Java/ Kotlin and Android by Square inc under Apache 2.0 license. Its a simple network library that is used for network transactions. By using this library we can seamlessly capture JSON response from web service/web API.

GSON - JSON Parser,used to parse requests on the data layer for Entities and understands Kotlin non-nullable and default parameters.

Kotlin Flow - In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.

Dagger Hilt - A dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.

Ramcosta Navigation Library - A KSP library that processes annotations and generates code that uses Official Jetpack Compose Navigation under the hood. It hides the complex, non-type-safe and boilerplate code you would have to write otherwise.

Logging Interceptor - logs HTTP request and response data.

Coil- An image loading library for Android backed by Kotlin Coroutines.

Timber- A logger with a small, extensible API which provides utility on top of Android's normal Log class.

CI/CD:

GitHub Actions - GitHub Actions makes it easy to automate all your software workflows, now with world-class CI/CD. Build, test, and deploy your code right from GitHub. Make code reviews, branch management, and issue triaging work the way you want.
