//
//  DetailVC.swift
//  Lab4
//
//  Created by Alex Herman on 2/20/22.
//

import UIKit

class DetailVC: UIViewController {
    
    @IBOutlet weak var backButton: UIButton!
    var imageName : String?
    
    @IBOutlet weak var image: UIImageView!
    
    @IBAction func share(_ sender: UIBarButtonItem) {
        var imageArray = [UIImage]()
                imageArray.append(image.image!)
                let shareScreen = UIActivityViewController(activityItems: imageArray, applicationActivities: nil)
                shareScreen.modalPresentationStyle = .popover
                shareScreen.popoverPresentationController?.barButtonItem = sender
                present(shareScreen, animated: true, completion: nil)
    }
    
    override func viewDidLoad() {
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if let name = imageName {
            image.image = UIImage(named: name)
        }
    }
    
}

