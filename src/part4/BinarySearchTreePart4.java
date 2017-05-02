package part4;

import java.io.*;
import java.util.Random;

@SuppressWarnings("unchecked")
public class BinarySearchTreePart4 implements Runnable {
	private static final String FILENAME = "tree.ser";
	private static final Integer RAND_START = 1000;
	private static final Integer INSERTIONS = 300;
	private static final Integer RAND_STOP = 0;
	
	private BinarySearchTree<Integer> fileTree;
	private BinarySearchTree<Integer> tree;
	
	public static void main(String args[]) {
		new Thread(new BinarySearchTreePart4()).run();
	}

	@Override
	public void run() {
		try {
			writeTree();
			readTree();
			
			System.out.println("Trees Equal ? : " + tree.equals(fileTree));
			System.out.println("Trees Height: " + tree.getHeight());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writeTree() throws IOException {
		tree = new BinarySearchTree<Integer>(true);
		
		for(int i = 0; i < INSERTIONS; i++) {
			tree.add(randomInteger(RAND_STOP, RAND_START));
		}
		
		System.out.println("Tree created at runtime: " + tree.toString());
		
		FileOutputStream fileOut = new FileOutputStream(FILENAME);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(tree);
        out.flush();
        out.close();
        fileOut.close();
	}
	
	private void readTree() throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream(FILENAME);
        ObjectInputStream in = new ObjectInputStream(fileIn);
		fileTree = (BinarySearchTree<Integer>)in.readObject();
        in.close();
        fileIn.close();
        
        System.out.println("Tree created from file:  " + fileTree.toString());
	}
	
	public int randomInteger(int min, int max) {
	    return new Random().nextInt((max-min)+1)+min;
	}
}
