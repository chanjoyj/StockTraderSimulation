package main;

/*
* This class uses the quicksort sort algorithm to sort through a list of integers 
*/
public class Sorting {

	/*
	 * Method takes an array, the first index of the array, and the last index of
	 * the array. This will effectively allow the algorithm to divide the array to a
	 * pivot number, numbers lower than the pivot, and numbers larger than the pivot
	 * and use recursion on those last two arrays. i.e.
	 * lowIndex = 0;
	 * highIndex = array.length - 1
	 * 
	 * In our case, we shall always have the pivot being the highIndex.
	 * Afterwards, values are recursively organised on the left and right of the
	 * pivot.
	 */
	public static void quickSort(int[] array, int lowIndex, int highIndex) {
		if (lowIndex >= highIndex)
			return;

		int pivot = array[highIndex];

		int leftPointer = partition(array, lowIndex, highIndex, pivot);

		// <!-- Recursion Section -->

		// Sorts to left side of pivot. Since the leftPointer is already at the index
		// with the pivot number, we just -1 to get the highest index
		quickSort(array, lowIndex, leftPointer - 1);
		// Sorts to right side of pivot. Since the leftPointer is already at the index
		// with the pivot number, we just +1 to get the lowest index
		quickSort(array, leftPointer + 1, highIndex);

	}

	/*
	 * Method will create 2 pointers at each end of the array excluding the pivot,
	 * and will be comparing values to the pivot. If certain conditions are met at
	 * either sides, the numbers will be swapped, and the pointers will continue to
	 * converge.
	 * 
	 * Once the pointers converge, the pivot number will replace the value
	 * at the conversion, resulting in an ordered list.
	 */
	public static int partition(int[] array, int lowIndex, int highIndex, int pivot) {
		int leftPointer = lowIndex;
		int rightPointer = highIndex - 1;

		// Moving pointers from either sides and swapping when conditions are met.
		while (leftPointer < rightPointer) {
			// Leaves when larger than pivot
			while (array[leftPointer] <= pivot && leftPointer < rightPointer) {
				leftPointer++;
			}
			// Leaves when smaller than pivot
			while (array[rightPointer] >= pivot && leftPointer < rightPointer) {
				rightPointer--;
			}
			swap(array, leftPointer, rightPointer);
		}

		// In case lowIndex was set to a number larger than the last value within the
		// array.
		if (array[leftPointer] > array[highIndex]) {
			// Swaps pivot number with conversion
			swap(array, leftPointer, highIndex);
		} else {
			leftPointer = highIndex;
		}

		return leftPointer;
	}

	/*
	 * Swaps numbers at the specified indexes. Temp holds the number to be swapped
	 * as the number at index1 gets replaced with index2. index2 is then changed to
	 * the stored temp variable
	 */
	private static void swap(int[] array, int index1, int index2) {
		int temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
	//DOUBLE
	public static void dquickSort(double[] array, int lowIndex, int highIndex) {
		if (lowIndex >= highIndex)
			return;

		double pivot = array[highIndex];

		int leftPointer = partition(array, lowIndex, highIndex, pivot);

		// <!-- Recursion Section -->

		// Sorts to left side of pivot. Since the leftPointer is already at the index
		// with the pivot number, we just -1 to get the highest index
		dquickSort(array, lowIndex, leftPointer - 1);
		// Sorts to right side of pivot. Since the leftPointer is already at the index
		// with the pivot number, we just +1 to get the lowest index
		dquickSort(array, leftPointer + 1, highIndex);

	}

	/*
	 * Method will create 2 pointers at each end of the array excluding the pivot,
	 * and will be comparing values to the pivot. If certain conditions are met at
	 * either sides, the numbers will be swapped, and the pointers will continue to
	 * converge.
	 * 
	 * Once the pointers converge, the pivot number will replace the value
	 * at the conversion, resulting in an ordered list.
	 */
	public static int partition(double[] array, int lowIndex, int highIndex, double pivot) {
		int leftPointer = lowIndex;
		int rightPointer = highIndex - 1;

		// Moving pointers from either sides and swapping when conditions are met.
		while (leftPointer < rightPointer) {
			// Leaves when larger than pivot
			while (array[leftPointer] <= pivot && leftPointer < rightPointer) {
				leftPointer++;
			}
			// Leaves when smaller than pivot
			while (array[rightPointer] >= pivot && leftPointer < rightPointer) {
				rightPointer--;
			}
			swap(array, leftPointer, rightPointer);
		}

		// In case lowIndex was set to a number larger than the last value within the
		// array.
		if (array[leftPointer] > array[highIndex]) {
			// Swaps pivot number with conversion
			swap(array, leftPointer, highIndex);
		} else {
			leftPointer = highIndex;
		}

		return leftPointer;
	}

	/*
	 * Swaps numbers at the specified indexes. Temp holds the number to be swapped
	 * as the number at index1 gets replaced with index2. index2 is then changed to
	 * the stored temp variable
	 */
	private static void swap(double[] array, int index1, int index2) {
		double temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

}
