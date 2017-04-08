package part3;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

/**
 * The main test class which tests the BinarySeachTree class.
 * 
 * @author William Taylor : B00235610
 *
 */
public class BinarySearchTreePart3 implements Runnable {
	private enum TEST_TYPE {
		SEARCH_AFTER_INSERTIONS_AND_DELETIONS,
		SEARCH_RECENT_INSERTED_ITEMS,
		UNSUCCESSFUL_SEARCH_TEST,
		SUCCESSFUL_SEARCH_TEST,
	}
	
	private static final TEST_TYPE TYPE = TEST_TYPE.SEARCH_AFTER_INSERTIONS_AND_DELETIONS;
	private static final Integer SIZE = 1000000;

	/**
	 * Entry point for the application
	 * @param args program arguments as a string array
	 */
	public static void main(String args[]) {
		new Thread(new BinarySearchTreePart3()).run();
	}

	/**
	 * The main function that is called when we start
	 * the test.
	 */
	@Override
	public void run() {
		switch(TYPE) {
			case SEARCH_AFTER_INSERTIONS_AND_DELETIONS: {
				System.out.println("SEARCH_AFTER_INSERTIONS_AND_DELETIONS test selected"); 
				System.out.println();
				
				searchAfterInsertsAndDeletesTest(SIZE); break;
			}
			
			case SEARCH_RECENT_INSERTED_ITEMS: {
				System.out.println("SEARCH_RECENT_INSERTED_ITEMS test selected");
				System.out.println();
				
				recentItemsTest(SIZE); break;
			}
			
			case UNSUCCESSFUL_SEARCH_TEST: {
				System.out.println("UNSUCCESSFUL_SEARCH_TEST selected"); 
				System.out.println(); 
				
				unsuccessfulSearchTest(SIZE); break;
			}
			
			case SUCCESSFUL_SEARCH_TEST: {
				System.out.println("SUCCESSFUL_SEARCH_TEST selected"); 
				System.out.println(); 
				
				successfulSearchTest(SIZE); break;
			}
			
			default: System.out.println("No tests selected"); break;
		}	
	}
	 
	private BinarySearchTree<Item> treePartA = new BinarySearchTree<Item>(true);
	private BinarySearchTree<Item> treePartB = new BinarySearchTree<Item>();
	private TreeSet<Item> treePartC = new TreeSet<Item>();

	private void searchAfterInsertsAndDeletesTest(Integer size) {
		final Integer insertionCount = 100;
		final Integer deletionCount = 100;
		
		ArrayList<Item> itemsToInsert = new ArrayList<Item>();
		ArrayList<Item> itemsToDelete = new ArrayList<Item>();
		Integer itemToFind = randomInteger(0, size-1);
		Item.resetCompCount();
		Item findValue = null;;
		for(int i = 0; i < size; i++) {
			int num = randomInteger(0, 1000000);
			
			if(i == itemToFind) {
				findValue = new Item(num);
			}
			
			if(i < deletionCount && i < insertionCount) {
				itemsToInsert.add(new Item(randomInteger(0, 1000000)));
				itemsToDelete.add(new Item(num));
			}
			
			treePartA.add(new Item(num));
			treePartB.add(new Item(num));
			treePartC.add(new Item(num));
		}
		
		for(Item item : itemsToDelete) {
			treePartA.remove(item);
			treePartB.remove(item);
			treePartC.remove(item);
		}
		
		for(Item item : itemsToInsert) {
			treePartA.add(item);
			treePartB.add(item);
			treePartC.add(item);
		}
		
		Item.resetCompCount();
		treePartA.contains(findValue);
		System.out.println("Comparisons for Tree A " + Item.getCompCount());
		
		Item.resetCompCount();
		treePartB.contains(findValue);		
		System.out.println("Comparisons for Tree B " + Item.getCompCount());
		
		Item.resetCompCount();
		treePartC.contains(findValue);
		System.out.println("Comparisons for Tree C " + Item.getCompCount());
	}
	
	private void recentItemsTest(Integer size) {
		Item.resetCompCount();
		ArrayList<Item> valueArray = new ArrayList<Item>();
		for(int i = 0; i < size; i++) {
			int num = randomInteger(0, 1000000);
			
			if(i >= size-(size/100)*10) {
				valueArray.add(new Item(num));
			}
			
			treePartA.add(new Item(num));
			treePartB.add(new Item(num));
			treePartC.add(new Item(num));
		}
		
		Item findValue = valueArray.get(randomInteger(0, ((size/100)*10)-1));
		
		Item.resetCompCount();
		treePartA.contains(findValue);
		System.out.println("Comparisons for Tree A " + Item.getCompCount());
		
		Item.resetCompCount();
		treePartB.contains(findValue);		
		System.out.println("Comparisons for Tree B " + Item.getCompCount());
		
		Item.resetCompCount();
		treePartC.contains(findValue);
		System.out.println("Comparisons for Tree C " + Item.getCompCount());
	}
	
	private void unsuccessfulSearchTest(Integer size) {
		Item.resetCompCount();
		Item findValue = new Item(randomInteger(0, size));
		for(int i = 0; i < size; i++) {
			int num = findValue.value();
			do {
				num = randomInteger(0, size);
			} while(num == findValue.value());
			
			treePartA.add(new Item(num));
			treePartB.add(new Item(num));
			treePartC.add(new Item(num));
		}
		
		Item.resetCompCount();
		treePartA.contains(findValue);
		System.out.println("Comparisons for Tree A " + Item.getCompCount());
		
		Item.resetCompCount();
		treePartB.contains(findValue);		
		System.out.println("Comparisons for Tree B " + Item.getCompCount());
		
		Item.resetCompCount();
		treePartC.contains(findValue);
		System.out.println("Comparisons for Tree C " + Item.getCompCount());
	}

	private void successfulSearchTest(Integer size) {
		Integer itemToFind = randomInteger(0, size-1);
		Item.resetCompCount();
		Item findValue = null;
		for(int i = 0; i < size; i++) {
			int num = randomInteger(0, size);
			
			treePartA.add(new Item(num));
			treePartB.add(new Item(num));
			treePartC.add(new Item(num));
			
			if(i == itemToFind) {
				findValue = new Item(num);
			}
		}
		
		Item.resetCompCount();
		treePartA.contains(findValue);
		System.out.println("Comparisons for Tree A " + Item.getCompCount());
		
		Item.resetCompCount();
		treePartB.contains(findValue);		
		System.out.println("Comparisons for Tree B " + Item.getCompCount());
		
		Item.resetCompCount();
		treePartC.contains(findValue);
		System.out.println("Comparisons for Tree C " + Item.getCompCount());
	}
	
	public int randomInteger(int min, int max) {
	    return new Random().nextInt((max-min)+1)+min;
	}
}
