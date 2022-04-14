package main;

public class Sorting {
	public static void quickSort(int[] array, int s, int e) {
		if (s>=e) return ;
		int m= partition(array,s,e);
		quickSort(array,s,m-1);
		quickSort(array,m+1,e);

	}
	
	public static int partition(int[] array, int s, int e) {
		int pivot = array[e];
		int p=s;
		int q=s;
		
		while (q<e) {
			if (array[q]<pivot){
				int tmp= array[p];
				array[p]= array[q];
				array[q] = tmp;
				p++;
				q++;
			}else {
				q++;
			}
		}
		int tmp= array[e];
		array[e]= array[p];
		array[p] = tmp;
		return p;
	}
}
