package ru.job4j.pools;

public class MergeSort {

    public static int[] sort(int[] array) {
        return sort(array, 0, array.length - 1);
    }

    private static int[] sort(int[] array, int from, int to) {

        if (from == to) {
            return new int[] { array[from] };
        }
        // попали сюда, значит в массиве более одного элемента
        // находим середину
        int mid = (from + to) / 2;
        // объединяем отсортированные части
        return merge(
                // сортируем левую часть
                sort(array, from, mid),
                // сортируем правую часть
                sort(array, mid + 1, to)
        );
    }

    // Метод объединения двух отсортированных массивов
    public static int[] merge(int[] left, int[] right) {
        int li = 0;
        int ri = 0;
        int resI = 0;
        int[] result = new int[left.length + right.length];
        while (resI != result.length) {
            if (li == left.length) {
                result[resI++] = right[ri++];
            } else if (ri == right.length) {
                result[resI++] = left[li++];
            } else if (left[li] <= right[ri]) {
                result[resI++] = left[li++];
            } else {
                result[resI++] = right[ri++];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = new int[] {4, 3, 8, 5, 2, 1, 0, 9, 6};


        int[] sortArray = ParallelMergeSort.sort(array);
        for (int i = 0; i < sortArray.length; i++) {
            System.out.println(sortArray[i]);
        }

    }
}