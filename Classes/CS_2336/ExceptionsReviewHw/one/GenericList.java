import java.util.ArrayList;

public class GenericList <gen>{
    private ArrayList<gen> list = new ArrayList<gen>();
    private int size = 0;
    
    //default constructor
    public GenericList() {
    }
    

    //overloaded constructor
    public GenericList(ArrayList<gen> objects) {
        list = objects;
    }

    //setter
    public void setList(gen element) {
        this.list.add(element);
    }

    //getter
    public ArrayList<gen> getList() {
        return this.list;
    }

    //insertion sort
    // public ArrayList<gen> InsertionSort() {
    //     for (int i = 1; i < list.size(); i++) {
    //         gen currentElement = list.get(i);
    //         int k;
    //         for (k = i - 1; k >= 0 && list.get(k).toString().compareTo(currentElement.toString()) > 0; k--) {
    //             list.set(k + 1, list.get(k));
    //         }
    //         list.set(k + 1, currentElement);
    //     }
    //     return list;
    // }

    //insertion sort
    // works by comparing the current element to the previous element
    //while the previous element is greater than the current element, the previous element is moved up one position
    //works its way forwards while it also works its way backwards
    public ArrayList<gen> InsertionSort() {
        for (int i = 1; i < list.size(); i++) {
            gen currentElement = list.get(i);
            int k;
            for (k = i - 1; k >= 0 && ((Comparable<gen>) list.get(k)).compareTo(currentElement) > 0; k--) {
                list.set(k + 1, list.get(k));
            }
            list.set(k + 1, currentElement);
        }
        return list;
    }


    //binary search that returns a bool
    //works by comparing the key to the middle element
    //if the key is less than the middle element, the high is set to mid - 1
    //if the key is greater than the middle element, the low is set to mid + 1
    //if the key is equal to the middle element, return true
    //if the key is not found, return false
    public boolean BinarySearch(gen key) {
        int low = 0;
        int high = list.size() - 1;
        while (high >= low) {
            int mid = (low + high) / 2;
            if (key.toString().compareTo(list.get(mid).toString()) < 0) {
                high = mid - 1;
            }
            else if (key.toString().compareTo(list.get(mid).toString()) == 0) {
                return true;
            }
            else {
                low = mid + 1;
            }
        }
        return false;
    }
}