# Kotlin Multiplatform Playground
This project is for experimenting with Kotlin Multiplatform. 

![kmp image](kmp-app.png)

## Dependencies
 - [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
 - [Kotlin Ktor](https://ktor.io/clients/index.html)
 - [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization)
 - [Open Weather Map](https://openweathermap.org/)
 
 ## Open Weather Map
 To access the OpenWeatherMap API you need an [API Key](http://openweathermap.org/appid).  
 
 ### Android
 To use your API Key, create a **gradle.properties** file in the root of the **app** module with a string value pair representing your API Key.  This file is not tracked in Git as it is for personal use.
 
 ```groovy
 OPENWEATHER_API_KEY = "YOUR-API-KEY"
 ```

### iOS
To use your API Key, modify the `apiKey` variable in `ViewController.swift`:

```swift
// replace with your api key
let apiKey = "YOUR-API-KEY-GOES-HERE"
```
## Blog Series
 - [Getting Started](http://gh.jdoneill.com/2019/12/06/kotlin-mulitplatform/)

## Licensing
A copy of the license is available in the repository's [LICENSE](LICENSE) file.
