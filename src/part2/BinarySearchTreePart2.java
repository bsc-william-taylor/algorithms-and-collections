package part2;

import java.util.Iterator;
import java.util.Random;

/**
 * The main test class which tests the BinarySeachTree class.
 * 
 * @author William Taylor : B00235610
 *
 */
public class BinarySearchTreePart2 implements Runnable {
	// a enum for the types of test we want to perform 
	private enum TEST_TYPE {
		TEST_1, TEST_2, NONE
	};
	
	// Some testing settings
	private static final TEST_TYPE TEST_VERSTION = TEST_TYPE.TEST_1;
	private static final Integer RAND_START = 100000;
	private static final Integer INSERTIONS = 50;
	private static final Integer RAND_STOP = 0;
	
	/**
	 * Entry point for the application
	 * @param args program arguments as a string array
	 */
	public static void main(String args[]) {
		new Thread(new BinarySearchTreePart2()).run();
	}

	/**
	 * The main function that is called when we start
	 * the test.
	 */
	@Override
	public void run() {
		switch(TEST_VERSTION) {
			case TEST_1: test1(); break;
			case TEST_2: test2(); break;
		
			default: System.out.println("No tests selected"); break;
		}
	}
	
	private void test2() {
		BinarySearchTreeOld<Integer> oldTree = new BinarySearchTreeOld<Integer>();
		BinarySearchTree<Integer> newTree = new BinarySearchTree<Integer>(true);
		
		long oldTime = 0;
		long newTime = 0;
		
		Integer[] numList = { 0, 50, 25, 34, 56, 78, 12, 78, 90 };
		
		long start = System.nanoTime();
		for(Integer num : numList) {
			oldTree.add(num);
		}
		long stop = System.nanoTime();
		oldTime = stop - start;
		
		start = System.nanoTime();
		for(Integer num : numList) {
			newTree.add(num);
		}
		stop = System.nanoTime();
		newTime = stop - start;
		
		System.out.println("Insertion time for new tree : " + newTime + " ns");
		System.out.println("Insertion time for old tree : " + oldTime + " ns");
		start = System.nanoTime();
		for(Integer num : numList) {
			oldTree.remove(num);
		}
		stop = System.nanoTime();
		oldTime = stop - start;
		
		start = System.nanoTime();
		for(Integer num : numList) {
			newTree.remove(num);
		}
		stop = System.nanoTime();
		newTime = stop - start;
		
		System.out.println("Deletion time for new tree : " + newTime + " ns");
		System.out.println("Deletion time for old tree : " + oldTime + " ns");
		
	}
	
	private void test1() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>(true);
		
		String insertionOrder = "";
		

		for(int i = 0; i < INSERTIONS; i++) {
			int nm = randomInteger(RAND_STOP, RAND_START);
			if(tree.add(nm)) {
				insertionOrder += ""+nm;
				insertionOrder += ", ";
			} 
			
			
		}
		
		insertionOrder += 50+", ";
		insertionOrder += 45+", ";
		
		tree.add(50);
		tree.add(45);
		
		tree.remove(50);
		
		System.out.print("In-order : ");
		Iterator<Integer> iter = tree.iterator();
		
		Integer size = 0;
		while(iter.hasNext()) {
			System.out.print(iter.next() + ", ");
			size++;
		}
		
		System.out.println();
				
		System.out.println("Insertion Order : " + insertionOrder);
		System.out.println("Binary Tree in BWT : " + tree.toString());
		System.out.println("Tree Height : " + tree.getHeight());
		System.out.println("Contains 5 : " + tree.contains(5));
		System.out.println("Matches : " + tree.equals(tree));
	}
	
	public int randomInteger(int min, int max) {
	    return new Random().nextInt((max-min)+1)+min;
	}
}
