//
//  ViewController.swift
//  ios-app
//

import UIKit
import JDOCommon

class ViewController: UIViewController {
    
    @IBOutlet weak var navBar: UINavigationBar!
    @IBOutlet weak var dateView: UITextView!
    @IBOutlet weak var weatherView: UITextView!
    
    weak var kmpQuery: KmpModelQueries!
    
    internal var DEGREE: String = "\u{00B0}"
    internal var api = RestApi()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let driver = KmpDriverFactory()
        let db = KmpDriverFactoryKt.createDb(kmpDriverFactory: driver)
        kmpQuery = db.kmpModelQueries

        getWeather()
        
        let date = DateUtilsKt.getDate()
        dateView.text = date
    }
    
    
    func getWeather() {
        api.getWeather(
            success: { data in
                self.parseResponse(response: data)
        }, failure: {
            self.handleError($0.message)

        })
    }
    
    func parseResponse(response: WeatherResponse) {
        let name = response.name
        let temp = response.main.temp
        let id = response.id

        kmpQuery.insertWeather(id: KotlinLong(value: id), name: name, latest_temp: Double(temp), timestamp: DateUtilsKt.getDate())
        let results = kmpQuery.selectAll().executeAsList()

        for result in results {
            print(result)
        }

        let feelsLikeTemp = response.main.feels_like
        let tempMin = response.main.temp_min
        let tempMax = response.main.temp_max
        let pressure = response.main.pressure
        let humidity = response.main.humidity

        let formatted = String(format: "%@\n\nTemp: %.1f\(DEGREE)\n Feels Like: %.1f\(DEGREE)\nMax: %.1f\(DEGREE)\nMin: %.1f\(DEGREE)\nPressure: \(pressure)\nHumidity: \(humidity)",
                               name, temp, feelsLikeTemp, tempMax, tempMin)

        weatherView.text = formatted
    }

    internal func handleError(_ error: String?){
        let message = error ?? "An unknown error occurred. Retry?"
        print(message)
        
    }
    
}
