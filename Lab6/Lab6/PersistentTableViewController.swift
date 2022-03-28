//
//  PersistentTableViewController.swift
//  Lab6
//
//  Created by Alex Herman on 3/24/22.
//

import UIKit

class PersistentTableViewController: UITableViewController {
    var deletedItems = [String]()
    var items = [String]()
    var favoritesSelected = [String]()
    var plistData = DataHandler()
    let dataFile = "data4.plist"
    //let deletedDataFile = "deletedData.plist"
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        self.navigationItem.leftBarButtonItem = self.editButtonItem
        
        plistData.loadData(fileName: dataFile)
        items = plistData.getItems()
        
                //subscribe to the UIApplicationWillResignActiveNotification notification
        NotificationCenter.default.addObserver(self, selector: #selector(self.applicationWillResignActive(_:)), name: UIApplication.willResignActiveNotification, object: nil)
    }

    
    @objc func applicationWillResignActive(_ notification: Notification){
        plistData.clearData()
        
        for i in items{
            self.plistData.addItem(newItem: i)
        }
        
        plistData.saveData(fileName: dataFile)
    }
    // MARK: - Table view data source

    @IBAction func referesh(_ sender: UIBarButtonItem) {
        for i in deletedItems{
            items.append(i)
        }
        
        deletedItems = []
        self.tableView.reloadData()
    }
    
    @IBAction func addItem(_ sender: UIBarButtonItem) {
        let addalert = UIAlertController(title: "New Item", message: "Add a new item", preferredStyle: .alert)
                //add textfield to the alert
                addalert.addTextField(configurationHandler: {(UITextField) in })
                let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: nil)
                addalert.addAction(cancelAction)
                let addItemAction = UIAlertAction(title: "Add", style: .default, handler: {(UIAlertAction)in
                    // adds new item
                    let newitem = addalert.textFields![0] //gets textfield
                    if newitem.text?.isEmpty == false {
                        let newItem = newitem.text! //gets textfield text
                        self.items.append(newItem) //adds textfield text to array
                        //self.groceryData.addItem(newItem: newGroceryItem) // add to data handler
                        //self.plistData.addItem(newItem: newItem)
                        self.tableView.reloadData()
                    }
                })
                addalert.addAction(addItemAction)
                present(addalert, animated: true, completion: nil)
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
     // #warning Incomplete implementation, return the number of rows
        return items.count
     }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath)
        var cellConfig = cell.defaultContentConfiguration()
        cellConfig.text = items[indexPath.row]
        cell.contentConfiguration = cellConfig
        return cell
    }

    /*
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier", for: indexPath)

        // Configure the cell...

        return cell
    }
    */

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let numItems = items.count
        //let numDeletedItems = deletedItems.count
        var j = 0
        
        favoritesSelected.append(items[indexPath.row])
        if(favoritesSelected.count >= numItems/2){
            for i in items {
                if(!favoritesSelected.contains(i)){
                    print(i)
                    deletedItems.append(i)
                    //plistData.deleteItem(index: j)
                }
                j += 1
            }
            items = favoritesSelected
            favoritesSelected = []
            self.tableView.reloadData()
        }
        
        let alertController = UIAlertController(title: "iOScreator", message:
                                                    "Select your " + String(numItems/2) + " favorite items! " + "Selected: " + String(favoritesSelected.count), preferredStyle: .alert)
            alertController.addAction(UIAlertAction(title: "Dismiss", style: .default))

            self.present(alertController, animated: true, completion: nil)
    }
    
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            deletedItems.append(items[indexPath.row])
            items.remove(at: indexPath.row)
            //plistData.deleteItem(index: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
