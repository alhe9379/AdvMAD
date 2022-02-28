//
//  ViewController.swift
//  Lab5
//
//  Created by Alex Herman on 2/26/22.
//
// https://dog.ceo/dog-api/

import UIKit

class ViewController: UIViewController {
    let blocker = DispatchGroup()
    var dogPics = [String]()
    var curPicIndex = 0
    @IBOutlet weak var imageView: UIImageView!
    
    //var dogPicHandler = ApiHandler()
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    @IBAction func apiCallButton(_ sender: UIButton) {
        //print("in apiCallButton")
        //blocker.enter()
        //dogPicHandler.handleApiCall(blocker: blocker)
        //blocker.leave()
        //print("After handleApiCall")
        //print(dogPicHandler.dogPics)
        
        blocker.enter()
        handleApiCall()
        blocker.notify(queue: .main){
            print("in notify")
            //print(self.dogPicHandler.dogPics[0])
            print(self.dogPics)
            self.curPicIndex = self.dogPics.count - 1
            self.imageView.image = nil
            self.imageView.load( urlString: self.dogPics[self.dogPics.count - 1] )
        }
    }
    
    @IBAction func forwardButton(_ sender: UIButton) {
        if curPicIndex < self.dogPics.count - 1 {
            self.imageView.image = nil
            self.imageView.load( urlString: self.dogPics[curPicIndex + 1] )
            curPicIndex = curPicIndex + 1
        }
    }
    
    @IBAction func backButton(_ sender: UIButton) {
        if curPicIndex > 0 {
            self.imageView.image = nil
            self.imageView.load( urlString: self.dogPics[curPicIndex - 1] )
            curPicIndex = curPicIndex - 1
        }
    }
    
    // CANT USE ASYNC ON MY XCODE VERSION
    // Following a tutorial with a different type of async
    // https://www.youtube.com/watch?v=yYMgwEwy6Ok
    func handleApiCall() {
        //let group = DispatchGroup()
        let urlString = "https://dog.ceo/api/breeds/image/random"
        guard let url = URL(string: urlString)
        else { print("url error"); return }
        
        URLSession.shared.dataTask(with: url) { (data, res, error) in
            do {
                //self.blocker.enter()
                let ApiResponse = try JSONDecoder().decode(DogPic.self, from: data!)
                DispatchQueue.main.async{
                    //print("hii")
                    if(ApiResponse.status == "success"){
                        print(ApiResponse.message)
                        self.dogPics.append(ApiResponse.message)
                    } else { print("api error") }
                    print("before leave")
                    self.blocker.leave()
                }
            } catch {}
        }.resume()
    }
}

//https://www.hackingwithswift.com/example-code/uikit/how-to-load-a-remote-image-url-into-uiimageview
extension UIImageView {
    func load(urlString: String) {
        guard let url = URL(string: urlString) else { return }
        DispatchQueue.global().async { [weak self] in
            if let data = try? Data(contentsOf: url) {
                if let image = UIImage(data: data) {
                    DispatchQueue.main.async {
                        self?.image = image
                    }
                }
            }
        }
    }
}

