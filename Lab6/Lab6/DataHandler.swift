//
//  DataHandler.swift
//  Lab6
//
//  Created by Alex Herman on 3/27/22.
//

import Foundation

class DataHandler {
    
    var data = [String]()
    var deletedData = [String]()

    func dataFileURL(_ filename:String) -> URL? {
        let urls = FileManager.default.urls(for:.documentDirectory, in: .userDomainMask)
        
        let url = urls.first?.appendingPathComponent(filename)
        
        return url
    }

    func loadData(fileName: String){
        //url of data file
        let fileURL = dataFileURL(fileName)
        //if the data file exists, use it
        if FileManager.default.fileExists(atPath: (fileURL?.path)!){
            do {
                //creates a data buffer with the contents of the plist
                let urldata = try Data(contentsOf: fileURL!)
                //create an instance of PropertyListDecoder
                let decoder = PropertyListDecoder()
                //decode the data using the structure of the Favorite class
                data = try decoder.decode([String].self, from: urldata)
            } catch {
                print("no file")
                }
        }
        else {
            print("file does not exist")
        }
    }

    func saveData(fileName: String){
        //url of data file
        let fileURL = dataFileURL(fileName)
        do {
            //create an instance of PropertyListEncoder
            let encoder = PropertyListEncoder()
            //set format type to xml
            encoder.outputFormat = .xml
            let encodedData = try encoder.encode(data)
            //write encoded data to the file
            try encodedData.write(to: fileURL!)
        } catch {
            print("write error")
        }
    }

    func getItems() -> [String]{
        return data
    }

    func addItem(newItem: String){
        data.append(newItem)
    }

    func deleteItem(index: Int){
        data.remove(at: index)
    }
    
    func clearData(){
        data = []
    }
}
