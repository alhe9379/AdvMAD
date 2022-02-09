//
//  DetailVC.swift
//  Lab3
//
//  Created by Alex Herman on 2/8/22.
//

import UIKit

class DetailVC: UIViewController {
    var colorRGB: String?
    
    @IBOutlet weak var label: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let button = UIButton(frame: CGRect(x: 100, y: 100, width: 100, height: 50))
        button.setTitle("Done", for: .normal)
        button.backgroundColor = .systemBlue
        button.center = view.center
        button.addTarget(self, action: #selector(buttonAction), for: .touchUpInside)
        self.view.addSubview(button)
        
        let rgbValues = colorRGB!.split() {$0 == ","}
        
        if(rgbValues.count == 3){
            let red: Double? = Double(rgbValues[0])
            let green: Double? = Double(rgbValues[1])
            let blue: Double? = Double(rgbValues[2])
            if((red ?? green ?? blue) != nil){
                let color = UIColor(red: CGFloat(red!), green: CGFloat(green!), blue: CGFloat(blue!), alpha: 255)
                view.backgroundColor = color
            }
            else {
//                let label = UILabel(frame: CGRect(x: 0, y: 0, width: 200, height: 21))
//                label.center = view.center
//                label.textAlignment = .center
//                label.text = "Not a valid color"
//
//                self.view.addSubview(label)
                
            }//label.text = "Not a valid color" }
        }
        
            else {
                
//            let label = UILabel(frame: CGRect(x: 0, y: 0, width: 200, height: 21))
//            label.center = view.center
//            label.textAlignment = .center
//            label.text = "Not a valid color"
//
//            self.view.addSubview(label)
                
            }//label.text = "Not a valid color" }
    
        }
    
    @objc func buttonAction(sender: UIButton!) {
        self.dismiss(animated: true)
        //print("Button tapped")
    }

}
