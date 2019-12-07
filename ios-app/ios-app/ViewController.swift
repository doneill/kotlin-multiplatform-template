//
//  ViewController.swift
//  ios-app
//

import UIKit
import SharedCode

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        let date = DateHelperKt.getDate()
        
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 230, height: 42))
        label.center = CGPoint(x: 160, y: 285)
        label.textAlignment = .center
        label.font = label.font.withSize(16)
        label.textColor = UIColor.black
        label.numberOfLines = 0
        label.lineBreakMode = .byWordWrapping
        label.text = date
        view.addSubview(label)
    }
    
}

