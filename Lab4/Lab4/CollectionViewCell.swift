//
//  CollectionViewCell.swift
//  Lab4
//
//  Created by Alex Herman on 2/20/22.
//

import UIKit

class CollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var cellImageView: UIImageView!
    @IBOutlet private weak var cellTitleLabel: UILabel!
    
    func configure(with cellTitle: String){
        cellTitleLabel.text = cellTitle
        cellImageView.image = UIImage(named: cellTitle)
    }
    
}
