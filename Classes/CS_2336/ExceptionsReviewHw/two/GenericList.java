import java.util.ArrayList;

class GenericList<T> {
    private ArrayList<T> list;

    public GenericList(ArrayList<T> initialList) {
        this.list = new ArrayList<>(initialList);
    }

    public void setList(T element) {
        list.add(element);
    }

    public ArrayList<T> getList() {
        return new ArrayList<>(list);
    }

    public void insertionSort() {
        for (int i = 1; i < list.size(); i++) {
            T key = list.get(i);
            int j = i - 1;

            while (j >= 0 && ((Comparable<T>) list.get(j)).compareTo(key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, key);
        }
    }

    public boolean binarySearch(T target) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = ((Comparable<T>) list.get(mid)).compareTo(target);

            if (cmp == 0) {
                return true;
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }
}