import UIKit

class WordViewController: UIViewController, UIPickerViewDataSource, UIPickerViewDelegate {
    @IBOutlet weak var picker: UIPickerView!
    
    var wordData = DataLoader()
    var words = [String]()
    var synonyms = [String]()
    let filename = "words"
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 2
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if component == 0 {
            return words.count
        } else {
            return synonyms.count
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        //print("hi")

        if component == 0 {
            return words[row]
        } else {
            return synonyms[row]
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        //checks which component was picked
        if component == 0 {
            synonyms = wordData.getSynonyms(index: row) //gets the albums for the selected artist
            picker.reloadComponent(1) //reloads the album component
            picker.selectRow(0, inComponent: 1, animated: true) //set the album component back to 0
        }
        let wordrow = pickerView.selectedRow(inComponent: 0) //gets the selected row for the artist
        let synonymrow = pickerView.selectedRow(inComponent: 1) //gets the selected row for the album
        //choiceLabel.text="You like \(albums[albumrow]) by \(artists[artistrow])"
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        picker.dataSource = self
        picker.delegate = self
        wordData.loadData(filename: filename)
        words = wordData.getWords()
        synonyms = wordData.getSynonyms(index: 0)
    }
    


}
