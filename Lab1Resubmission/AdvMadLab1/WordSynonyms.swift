//
//  WordSynonyms.swift
//  AdvMadLab1
//
//  Created by Alex Herman on 1/31/22.
//

import Foundation

struct WordSynonyms: Decodable {
    let word : String
    let synonyms: [String]
}
