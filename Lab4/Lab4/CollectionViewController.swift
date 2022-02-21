//
//  CollectionViewController.swift
//  Lab4
//
//  Created by Alex Herman on 2/20/22.
//
//  https://www.youtube.com/watch?v=TQOhsyWUhwg
//  https://www.youtube.com/watch?v=6bAHxFEZi3o

import UIKit

class CollectionViewController: UICollectionViewController {

    var imgName: String = "1"
    let dataSource: [String] = ["1", "2", "3", "4", "5", "6", "7", "8"]
    //let imageSource: [UIImage] = [UIImage(named: "1")!, UIImage(named: "2")!, UIImage(named: "3")!, UIImage(named: "4")!, UIImage(named: "5")!, UIImage(named: "6")!, UIImage(named: "7")!, UIImage(named: "8")!]
    
    override func viewDidLoad() {
        //print(HeaderCollectionReusableView.identifier)
        //self.collectionView.register(HeaderCollectionReusableView.self, forSupplementaryViewOfKind: UICollectionView.elementKindSectionHeader, withReuseIdentifier: HeaderCollectionReusableView.identifier)
        super.viewDidLoad()
    }

    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return dataSource.count
    }
    
    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        var cell = UICollectionViewCell()
        
        if let customCell = collectionView.dequeueReusableCell(withReuseIdentifier: "Cell", for: indexPath) as? CollectionViewCell {
            customCell.configure(with: dataSource[indexPath.row])
            
            cell = customCell
        }
        
        //let customHeader = collectionView.dequeueReusableCell(withReuseIdentifier: HeaderCollectionReusableView, for: indexPath)
        
        return cell
    }
    
    override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print("Selected cell: \(dataSource[indexPath.row])")
        imgName = (dataSource[indexPath.row])
        
    }
    
//    override func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, at indexPath: IndexPath) -> UICollectionReusableView {
//        let headerId = "header"
//        let headerView = collectionView.dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: headerId, for: indexPath)
//        return headerView
//    }
//
    override func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, at indexPath: IndexPath) -> UICollectionReusableView {
        print(kind)
        var header = HeaderCollectionReusableView()
        var footer = FooterCollectionReusableView()
        if kind == UICollectionView.elementKindSectionHeader{
            header = collectionView.dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: "header", for: indexPath) as! HeaderCollectionReusableView
            header.headerLabel.text = "Dogs r Kool"
            return header
        }
        
        if kind == UICollectionView.elementKindSectionFooter{
            footer = collectionView.dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: "footer", for: indexPath) as! FooterCollectionReusableView
            footer.footerLabel.text = "Dogsssss"
            return footer
        }
        print("uh oh")
        return footer
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, referenceSizeForHeaderInSection section: Int) -> CGSize {
        return CGSize(width: view.frame.size.width, height: 200)
    }
    
    @IBAction func unwind(segue: UIStoryboardSegue) {
        if segue.identifier=="doneSegue"{
            print("hi2")
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "toDetail"{
            let indexPath = collectionView?.indexPath(for: sender as! CollectionViewCell)
            let detailVC = segue.destination as! DetailVC
            //detailVC.imageName = imgName
            detailVC.imageName = dataSource[(indexPath?.row)!]
        }
    }

    
}
