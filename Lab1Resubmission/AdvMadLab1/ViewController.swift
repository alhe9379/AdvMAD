//
//  ViewController.swift
//  AdvMadLab1
//
//  Created by Alex Herman on 1/31/22.
//
//https://www.youtube.com/watch?time_continue=172&v=xJU634A14u4&feature=emb_title

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    @IBAction func instagramButton(_ sender: UIButton) {
        let application = UIApplication.shared
        let appPath = "second://"
        let appURL = URL(string: appPath)!
        let fallbackURL = URL(string: "https://instagram.com/")!
        
        if application.canOpenURL(appURL) {
            application.open(appURL, options: [:], completionHandler: nil)
        }
        
        else{
            application.open(fallbackURL)
        }
    }
    
}

