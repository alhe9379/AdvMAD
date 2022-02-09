//
//  SearchController.swift
//  Lab3
//
//  Created by Alex Herman on 2/9/22.
//

import UIKit

class SearchController: UITableViewController, UISearchResultsUpdating {
    var allColors = [String]()
    var filteredColors = [String]()
    
    func updateSearchResults(for searchController: UISearchController) {
        let searchString = searchController.searchBar.text
        filteredColors.removeAll(keepingCapacity: true)
        if searchString?.isEmpty == false {
            let searchfilter: (String) -> Bool = { name in
                let range = name.range(of: searchString!, options: .caseInsensitive)
                return range != nil
            }
            
            let matches = allColors.filter(searchfilter)
            filteredColors.append(contentsOf: matches)
        }
        tableView.reloadData()
    }
    

    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.register(UITableViewCell.self, forCellReuseIdentifier: "cell")
        // Do any additional setup after loading the view.
    }
    

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return filteredColors.count
    }


    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        var cellConfig = cell.defaultContentConfiguration()
        cellConfig.text = filteredColors[indexPath.row]
        cell.contentConfiguration = cellConfig
        return cell
    }
 
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
            let alert = UIAlertController(title: "Row selected", message: "You selected \(filteredColors[indexPath.row])", preferredStyle: .alert)
            let okAction = UIAlertAction(title: "OK", style: .default, handler: nil)
            alert.addAction(okAction)
            present(alert, animated: true, completion: nil)
            tableView.deselectRow(at: indexPath, animated: true) //deselects the row that had been chosen
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
