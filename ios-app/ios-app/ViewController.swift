//
//  ViewController.swift
//  ios-app
//

import UIKit
import SharedCode

class ViewController: UIViewController {
    
    @IBOutlet weak var navBar: UINavigationBar!
    @IBOutlet weak var dateView: UITextView!
    @IBOutlet weak var weatherView: UITextView!
    
    internal var DEGREE: String = "\u{00B0}"
    internal var api = RestApi()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let apiKey = "YOUR-API-KEY-GOES-HERE"
        getWeather(apiKey: apiKey)
        
        let date = DateHelperKt.getDate()
        dateView.text = date
    }
    
    
    func getWeather(apiKey: String) {
        api.getWeatherMain(
            location: "60.970734,-149.098876",
            apiKey: apiKey,
            success: { data in
                self.parseResponse(response: data)
        }, failure: {
            self.handleError($0.message)

        })
    }
    
    func parseResponse(response: Main) {
        let temp = response.temp
        let feelsLikeTemp = response.feels_like
        let tempMin = response.temp_min
        let tempMax = response.temp_max
        let pressure = response.pressure
        let humidity = response.humidity
        
        let formatted = String(format: "Girdwood Weather\nTemp: %.2f\(DEGREE)\n Feels Like: %.2f\(DEGREE)\nMax: %.2f\(DEGREE)\nMin: %.2f\(DEGREE)\nPressure: \(pressure)\nHumidity: \(humidity)",
                               temp, feelsLikeTemp, tempMax, tempMin)
        
        weatherView.text = formatted
    }

    internal func handleError(_ error: String?){
        let message = error ?? "An unknown error occurred. Retry?"
        print(message)
        
    }
    
}
