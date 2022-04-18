package main;

public class Sorting {

	public static void quickSort(int[] array, int low, int high) {
		if (low >= high)
			return;

		int pivot = array[high];

		int leftPointer = partition(array, low, high, pivot);

		quickSort(array, low, leftPointer - 1);

		quickSort(array, leftPointer + 1, high);

	}

	public static int partition(int[] array, int low, int high, int pivot) {
		int leftPointer = low;
		int rightPointer = high - 1;

		while (leftPointer < rightPointer) {
			while (array[leftPointer] <= pivot && leftPointer < rightPointer) {
				leftPointer++;
			}
			while (array[rightPointer] >= pivot && leftPointer < rightPointer) {
				rightPointer--;
			}
			swap(array, leftPointer, rightPointer);
		}

		if (array[leftPointer] > array[high]) {
			swap(array, leftPointer, high);
		} else {
			leftPointer = high;
		}

		return leftPointer;
	}

	private static void swap(int[] array, int index1, int index2) {
		int temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

	// sorting for DOUBLE
	public static void dquickSort(double[] array, int low, int high) {
		if (low >= high)
			return;

		double pivot = array[high];

		int leftPointer = partition(array, low, high, pivot);

		dquickSort(array, low, leftPointer - 1);

		dquickSort(array, leftPointer + 1, high);

	}

	public static int partition(double[] array, int low, int high, double pivot) {
		int leftPointer = low;
		int rightPointer = high - 1;

		while (leftPointer < rightPointer) {
			while (array[leftPointer] <= pivot && leftPointer < rightPointer) {
				leftPointer++;
			}
			while (array[rightPointer] >= pivot && leftPointer < rightPointer) {
				rightPointer--;
			}
			swap(array, leftPointer, rightPointer);
		}

		if (array[leftPointer] > array[high]) {
			swap(array, leftPointer, high);
		} else {
			leftPointer = high;
		}

		return leftPointer;
	}

	private static void swap(double[] array, int index1, int index2) {
		double temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

}
