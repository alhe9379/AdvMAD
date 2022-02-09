//
//  AddViewController.swift
//  Lab3
//
//  Created by Alex Herman on 2/9/22.
//

import UIKit

class AddViewController: UIViewController {

    var textFieldText = String()
    @IBOutlet weak var textField: UITextField!
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "doneSegue"{
            print("hi1")
            //only add a country if there is text in the textfield
            if textField.text?.isEmpty == false{
                textFieldText=textField.text!
            }
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
