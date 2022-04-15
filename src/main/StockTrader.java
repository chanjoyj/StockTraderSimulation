package main;

import java.io.FileNotFoundException;
import java.util.Random;

import menuController.MenuController;

//Main runner
public class StockTrader {
	public static void main(String[] args) throws FileNotFoundException {
		StockData.loadData();
		TraderRecords.loadRecords();
		System.out.println();
		SetToday.inputDate();
		MenuController.startMenu();

		// ===== For testing Sorting =====
		// Random rand = new Random();
		// int[] numbers = new int[10];

		// for (int i = 0; i < numbers.length; i++) {
		// 	numbers[i] = rand.nextInt(100);
		// }

		// System.out.println("Before:");
		// printArray(numbers);

		// Sorting.quickSort(numbers, 0, numbers.length-1);

		// System.out.println("\nAfter:");
		// printArray(numbers);
	}

	// ===== For testing Sorting =====
	// private static void printArray(int[] numbers) {
	// 	for (int i = 0; i < numbers.length; i++) {
	// 	  System.out.println(numbers[i]);
	// 	}
	//   }
}
