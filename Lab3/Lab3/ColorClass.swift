//
//  ColorClass.swift
//  Lab3
//
//  Created by Alex Herman on 2/8/22.
//

import Foundation

class ColorClass {
    var colors = ["255,0,0", "0,255,0", "0,0,255", "0,0,0", "255,255,255", "10,100,50", "78,64,230", "15,210,5", "255,255,0"]
    
    func addColor(color: String) {
        colors.append(color)
    }
    
    func removeColor(index: Int) {
        colors.remove(at: index)
    }
}
	
