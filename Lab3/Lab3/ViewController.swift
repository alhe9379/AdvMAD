//
//  ViewController.swift
//  Lab3
//
//  Created by Alex Herman on 2/7/22.
//
//  https://www.youtube.com/watch?v=C36sb5sc6lE

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var tableView: UITableView!
    @IBAction func addButton(_ sender: UIBarButtonItem) {
        let addVC = AddViewController()
        self.present(addVC, animated: true)
    }
    
    var searchController = UISearchController()
    let colorData = ColorClass()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        tableView.delegate = self
        tableView.dataSource = self
        
        let allColors = colorData.colors
        let resultsController = SearchController() //create an instance of our SearchResultsController class
        resultsController.allColors = allColors //set the allwords property to our words array
        searchController = UISearchController(searchResultsController: resultsController)
        //searchController.hidesNavigationBarDuringPresentation = false;
        //self.extendedLayoutIncludesOpaqueBars = !(self.navigationController?.navigationBar.isTranslucent)!
        
        searchController.searchBar.placeholder = "Enter a search term" //place holder text
        searchController.searchBar.sizeToFit() //sets appropriate size for the search bar
        tableView.tableHeaderView=searchController.searchBar //install the search bar as the table header
        
    }


}

extension ViewController: UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        //print(indexPath)
        let color = self.colorData.colors[indexPath.row]
        let colorDetailVC = DetailVC()
        colorDetailVC.colorRGB = color
        self.present(colorDetailVC, animated: true)
    }
    
    func tableView(_ tableView: UITableView, trailingSwipeActionsConfigurationForRowAt indexPath: IndexPath) -> UISwipeActionsConfiguration? {
        let delete = UIContextualAction(style: .destructive, title: "Delete") { (contextualAction, view, actionPerformed: (Bool) -> ()) in
            self.colorData.removeColor(index: indexPath.row)
            tableView.reloadData()
        }
        return UISwipeActionsConfiguration(actions: [delete])
    }
    
    @IBAction func unwind(segue: UIStoryboardSegue) {
        if segue.identifier=="doneSegue"{
            print("hi2")
            if let source = segue.source as? AddViewController {
                if source.textFieldText.isEmpty == false{
                        //continentsData.addCountry(index: selectedContinent, newCountry: source.addedCountry, newIndex: countryList.count)
                        //countryList.append(source.addedCountry)
                        //tableView.reloadData()
                    colorData.addColor(color: source.textFieldText)
                    tableView.reloadData()
                    print("yes")
                }
            }
        }
    }
}

extension ViewController: UITableViewDataSource {
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return colorData.colors.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        
        //cell.textLabel?.text = "Hello World"
        cell.textLabel?.text = colorData.colors[indexPath.row]
        
        return cell
    }

}
