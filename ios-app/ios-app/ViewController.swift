//
//  ViewController.swift
//  ios-app
//

import UIKit
import SharedCode

class ViewController: UIViewController {
    
    @IBOutlet weak var navBar: UINavigationBar!
    @IBOutlet weak var dateView: UITextView!

    override func viewDidLoad() {
        super.viewDidLoad()

        let date = DateHelperKt.getDate()
        dateView.text = date
    }
    
}

