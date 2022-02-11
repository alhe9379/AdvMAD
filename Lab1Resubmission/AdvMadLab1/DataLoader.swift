//
//  DataLoader.swift
//  AdvMadLab1
//
//  Created by Alex Herman on 1/31/22.
//
// FROM CLASS NOTES ON GITHUB
// https://github.com/aileenjp/Spring21_AdvMobileAppDev/blob/master/week%2003/music/music/DataLoader.swift

import Foundation

class DataLoader {
    var allData = [WordSynonyms]()
    
    func loadData(filename: String){
        if let pathURL = Bundle.main.url(forResource: filename, withExtension:  "plist") {
            //initialize a property list decoder object
            let plistdecoder = PropertyListDecoder()
            do {
                let data = try Data(contentsOf: pathURL)
                //decodes the property list
                allData = try plistdecoder.decode([WordSynonyms].self, from: data)
            } catch {
                //handle error
                print("Cannot load data")
            }
        }
    }
    
    func getWords() -> [String]{
        var words = [String]()
        for w in allData {
            words.append(w.word)
        }
        return words
    }
    
    func getSynonyms(index: Int) -> [String] {
        return allData[index].synonyms
    }
}
