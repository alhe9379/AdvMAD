//
//  ApiHandler.swift
//  Lab5
//
//  Created by Alex Herman on 2/27/22.


// [DEPRECATED] [DEPRECATED] [DEPRECATED] [DEPRECATED] [DEPRECATED] [DEPRECATED]
import Foundation

class ApiHandler {
// CANT USE ASYNC ON MY XCODE VERSION
// Following a tutorial with a different type of async
// https://www.youtube.com/watch?v=yYMgwEwy6Ok
    
    var dogPics = [String]()
    
    init(){
        dogPics = []
    }
    
    func handleApiCall(blocker: DispatchGroup) {
        //let group = DispatchGroup()
        let urlString = "https://dog.ceo/api/breeds/image/random"
        guard let url = URL(string: urlString)
        else {
            print("url error")
            return
        }
        
        
        URLSession.shared.dataTask(with: url) { (data, res, error) in
            do {
                blocker.enter()
                let ApiResponse = try JSONDecoder().decode(DogPic.self, from: data!)
                DispatchQueue.main.async{
                    //print("hii")
                    if(ApiResponse.status == "success"){
                        print(ApiResponse.message)
                        self.dogPics.append(ApiResponse.message)
                    } else { print("api error") }
                    print("before leave")
                    blocker.leave()
                }
            } catch {}
        }.resume()
    
//        group.notify(queue: .main){
//            print("dun")
//        }
    }
}
// [DEPRECATED] [DEPRECATED] [DEPRECATED] [DEPRECATED] [DEPRECATED] [DEPRECATED]
