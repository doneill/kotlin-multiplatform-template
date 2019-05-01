//
//  ViewController.swift
//  ios-app
//

import UIKit
import main

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 230, height: 21))
        label.center = CGPoint(x: 160, y: 285)
        label.textAlignment = .center
        label.font = label.font.withSize(16)
        
        //To display multiple lines in label
        label.numberOfLines = 0
        label.lineBreakMode = .byWordWrapping
        label.text = DateHelperKt.getDate()
        view.addSubview(label)
    }


}

