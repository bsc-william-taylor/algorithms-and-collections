package part1;

import java.util.*;

@SuppressWarnings("unchecked")
public class BinarySearchTree<E> extends AbstractSet<E> {		
	// A single reference to the root of the tree
	private Entry<E> root;
	// and the size of the tree itself
	private int size;        
   
	/**
	 * Default Constructor
	 */
    public BinarySearchTree() {
        root = new Entry<E>(null);
        size = 0;  
    }

    /**
     * This is a copy constructor which will copy an existing
     * tree into the current tree.
     * 
     * @param otherTree the tree you want to copy from
     */
    public BinarySearchTree(BinarySearchTree<? extends E> otherTree) {
         root = copy(otherTree.root, null);
         size = otherTree.size;  
    }

    /**
     * This function copy's the a tree into the current one. This function
     * is recursive as well.
     * 
     * @param p The root entry for the tree to copy
     * @param parent the roots parents
     * @returns the copied root
     */
    protected Entry<E> copy(Entry<? extends E> p, Entry<E> parent) {
        if (p != null) {
            Entry<E> q = new Entry<E> (p.element, parent);
            q.left = copy(p.left, q);
            q.right = copy(p.right, q);
            return q;
        } 
        
        return null;
    }
    
    /**
     * Here we override the Object equals function
     * for our own use
     */
    @Override
    public boolean equals(Object obj) {
    	// If the class type does'nt match return false
        if (!(obj instanceof BinarySearchTree)) {
            return false;
        }
        
        // go through the entire structure checking each node
        return equals(root, ((BinarySearchTree<? extends E>)obj).root);
    } 
    
    /**
     * This function checks a node with another node to see
     * if it matches
     * 
     * @param p Our first node we will use to compare against q
     * @param q Our second node we will use to compare against p
     * @return true if the to nodes are equal
     */
    public boolean equals(Entry<E> p, Entry<? extends E> q) {
    	boolean match = false;
    	// then call the entries equals method
		if (p.equals(q)) {
			match = true;
	    }
	    
		if(p.isInternal() && q.isInternal()) {
			// then check the left and right subtrees
		    if (equals(p.left, q.left) && equals (p.right, q.right) ){
	    	    match = true;
		    }
		}
                   
        // then return the result
        return match;     
    }
    
    /**
     * Recursively gets the height of its tree
     * and its subtrees.
     * 
     * @param e The subtree to get the height of
     * @return the height of the tree and its subtrees
     */
    public int getTreeHeight(Entry<E> e) {
    	// if we are at the last node
    	if(e.isExternal()) {
    		// return 0
    		return 0;
    	} else {
    		// else add 1 and add it to the largest of the 2 sub trees
    		return  1 + Math.max(
        		getTreeHeight(e.right), 
        		getTreeHeight(e.left)
        	);
    	}
    }
    
    /**
     * Returns the entire height of the tree
     * 
     * @return the height of the tree or -1 if empty
     */
    public int getHeight() {
    	if(root.isInternal()) {
    		return getTreeHeight(root);
    	} else {
    		return -1;
    	}
    }
    
    /**
     * Returns the values contained in the nodes
     * via a breadth width traversal
     */
    @Override
    public String toString() {
    	String str = "";
        if (root != null) {
	        Queue<Entry<E>> queue = new LinkedList<Entry<E>>();
	        queue.add(root);
	        while(!queue.isEmpty()) {
	        	Entry<E> node = queue.remove();
	            if(node.left != null) {
	            	queue.add(node.left);
	            } 
	            
	            if(node.right != null) {
	            	queue.add(node.right);
	            }
	            
	            str += node.toString();
	        }
        }
        
        return str;
    }
 
    /**
     * Returns a iterator for the tree
     */
    @Override
    public Iterator<E> iterator() {
         return new TreeIterator();
    }

    /**
     * checks to see if the value is contained in the tree
     */
    @Override
    public boolean contains(Object obj) {
        return(getEntry(obj).isInternal());
    } 

    /**
     * adds a new node to the tree with element being the value,
     * but remember duplicate entries are not allowed
     */
    @Override
    public boolean add(E element) {
    	boolean insertionSuccess = false;
    	if (element == null) { 
    		throw new NullPointerException(); 
    	} 
    	
    	if (root.isExternal()) {
	        root.makeInternal(element);     
	        insertionSuccess = true;
	    } else {
	        Entry<E> temp = root;
	        Integer comp = 0;
	
	        while(true) {
	            comp = ((Comparable<E>)element).compareTo(temp.element);
	            
	            if(comp < 0) {
	                if(!temp.left.isExternal()) {
	                    temp = temp.left;
	                } else {
	                    temp.left.makeInternal(element);
	                    insertionSuccess = true;
	                } 
	            }
	            
	            if(comp > 0) {
	                if(!temp.right.isExternal()) {
	                    temp = temp.right;
	                } else {
	                    temp.right.makeInternal(element);
	                    insertionSuccess = true;
	                }
	            }
	             
	            if (comp == 0 || insertionSuccess) {
	                break;
	            }
	        } 
        }
    	
    	if(insertionSuccess) {
        	size++;
        }
    	
    	return insertionSuccess;
    }

    
    /**
     * removes a entry from the list
     */
    @Override
    public boolean remove(Object obj) {
    	boolean success = true;
        Entry<E> e = getEntry(obj);
        if (e.isExternal()) {
        	success = false;
        } else {        
        	deleteEntry(e);       
        }
        
        return success;
    }

    /**
     * 
     * @param obj
     * @return
     */
	protected Entry<E> getEntry(Object obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		
		Entry<E> returnNode = root;
        Entry<E> entry = root;
        Integer comp;
        
        while (true) {
        	if(entry.isExternal()) {
        		returnNode = entry;
            	break;
        	} else {
        		comp = ((Comparable<E>)obj).compareTo (entry.element);
            	if (comp == 0) {
                	returnNode = entry;
                	break;
                } else if (comp < 0) {
                	entry = entry.left;
                } else if (comp > 0) {
                	entry = entry.right;
                }
        	}        	
        } 
        
        return returnNode;
    }
	
	/**
	 * This function deletes a node from the binary tree
	 * this function should also succeed providing
	 * a node is send to the function via a getEntry function.
	 * 
	 * @param p The entry to delete
	 * @return This function returns its replacement in the structure
	 */
    protected Entry<E> deleteEntry(Entry<E> p) {
    	Entry<E> replacement = null;
    	
        if (p.left.isInternal() && p.right.isInternal()) {
            Entry<E> s = successor(p);
            p.element = s.element;
            p = s;
        }
        
        if (p.left.isInternal()) {
            replacement = p.left;
        } else {
            replacement = p.right;
        }
        
        if (replacement != null) {
            replacement.parent = p.parent;
            if (p.parent == null) {
                root = replacement;
            } else if (p == p.parent.left) {
                p.parent.left  = replacement;
            } else {
                p.parent.right = replacement;
            }
        } else if (p.parent == null) {
            root.makeExternal();
        } else {
            if (p == p.parent.left) {
                p.parent.left.makeExternal();
            } else {
                p.parent.right.makeExternal();        
            }
        }
        
        size--;
        return p;
    }

    /**
     * This function returns the next successor to a given entry.
     * 
     * @param e The entry/node to find the successor for
     * @return the entrys success as a reference to the object
     */
    protected Entry<E> successor(Entry<E> e) {
    	Entry<E> value = null;
        if (e != null && e.isInternal()) {
        	if (e.right.isInternal()) {
                Entry<E> p = e.right;
                while (p.left.isInternal()) { 
                    p = p.left;
                }

                value = p;
            } else {
                Entry<E> p = e.parent;
                Entry<E> ch = e;
                while (p != null && p.isInternal() && ch == p.right) {
                    ch = p;
                    p = p.parent;
                } 
                
                value = p;
            }
        } 
        
        return value;
    }

    /**
     * returns the size of the binary search tree
     */
    @Override
    public int size() {
        return size;
    }
    
    /**
     * This is our node class which represents a node in 
     * the data structure.
     * 
     * @author William Taylor : B00235610
     *
     * @param <E> The type that the node will store
     */
    public static class Entry<E> {		
    	// The parent of the node
        private Entry<E> parent;
        // The right sub tree for the node
        private Entry<E> right;
        // The left sub tree for the node
        private Entry<E> left;
        // the value or element being stored
        private E element;
        
        /**
         *  This is the constructor for a internal node as it accepts
         *  a value for the node.
         *  
         * @param element The value to be stored in this node
         * @param parent the parent to the node
         */
        public Entry(E element, Entry<E> parent) {
            this.element = element;
            this.parent = parent;
        }
        
        /**
         * This is the constructor for a external node as it accepts 
         * no value for the node and there for considers it a external
         * node
         * 
         * @param parent the parent to the node or null if its the root
         */
        public Entry(Entry<E> parent) {
        	this.parent = parent;
        	makeExternal();
        }
        
        /**
         * Here we overide the toString method 
         * which is used to get the nodes value
         * as a string.
         * 
         * @return The nodes value as a string
         */
        @Override
        public String toString() {
        	// if the node is external
        	if(isExternal()) {
        		// set the string as a hash to indicate its a external node
        		return "#, ";
        	} else {
        		// else call value of function to convert the value to a string
        		return String.valueOf(element) + ", ";
        	}
        }
        
        /**
         * This function is used to tell if a node
         * is external.
         * 
         * @return a boolean to indicate if this node is external
         */
        public boolean isExternal() {
        	return (element == null);
        }
        
        /**
         * This function is used to tell if a node
         * is internal.
         * 
         * @return a boolean to indicate if this node is internal
         */
        public boolean isInternal() {
        	return !(isExternal());
        }
        
        /**
         * This node will make the current node external
         * and in the future the call to isExternal() will
         * return true.
         * 
         * @return the value stored in the internal node
         */
        public E makeExternal() {
        	E v = element;
        	
    		element = null;
    		right = null;
    		left = null;
    		
        	return v;
        }
        
        /**
         * This function makes the node an internal node by setting a value
         * for the current node.
         * 
         * @param element the value to be stored in this new internal node
         */
        public void makeInternal(E element) {
        	if(isExternal()) {
        		this.element = element;
        		this.right = new Entry<E>(this);
        		this.left = new Entry<E>(this);
        	} else {
        		System.err.println("ERROR : is an internal node");
        	}
        }
        
        /**
         * Here we override the equals method to deal with
         * external nodes which will have external nodes (null).
         * 
         * @param obj the Entry to compare to
         * @return true if they match
         */
        @Override
        public boolean equals(Object obj) {
        	boolean equal = false;
        	if((obj instanceof Entry)) {
        		Entry<E> e = ((Entry<E>)obj);
        		if(e.isExternal()) {
        			// not really needed but does ensure external nodes are not equal to every other external node
        			Comparable<E> v = ((Comparable<E>)(e.parent.element));
        			if(v.compareTo(parent.element) == 0) {
        				equal = true;
        			}
        		} else {
        			Comparable<E> v = ((Comparable<E>)((Entry<E>)obj).element);
        			if(v.compareTo(element) == 0) {
        				equal = true;
        			}
        		}
        	}
        	
        	return equal;
        }
    }
    
    /**
     * The following is a custom iterator for our
     * external tree class.
     * 
     * @author William Taylor : B00235610
     *
     */
    protected class TreeIterator implements Iterator<E> {
    	// A reference to the last returned reference
		private Entry<E> lastReturned = null;
		// A reference to the next element to iterate to
		private Entry<E> next = null; 
	    
		/**
		 * Our default constructor for the iterator.
		 */
	    protected TreeIterator() { 
	    	if(root.isInternal()) {
		    	// make the iterator start at the root of the tree
	            next = root;
	            // then iterate through to the left most leaf
                while (next.left.isInternal()) {
                    next = next.left;
                }
	    	} else {
	    		next = null;
	    	}
        }

	    /**
	     * The following cheeks to see if the is a next
	     * available element.
	     */
        public boolean hasNext() {
            return(next != null);
        }

        /**
         * This function returns the next elements value
         */
        public E next() {
        	// if there isnt a next variable
            if (next == null) {
            	// throw an exception
                throw new NoSuchElementException();
            }
            
            // then get its next successor
            lastReturned = next;
            next = successor (next);      
            // and returns the last returned value
            return lastReturned.element;
        } 

        /**
         * Removes the current element from the binary search tree
         */
        public void remove() {
        	// providing there is a last returned element to go back on
            if (lastReturned == null) {
            	// if not throw an exception
                throw new IllegalStateException();
            }
 
            // set the next element to the previous element
            if (lastReturned.left.isInternal() && lastReturned.isInternal()) {
                next = lastReturned;
            }
            
            // and delete the current element from the binary tree
            deleteEntry(lastReturned);
            lastReturned = null; 
        }  
	}
}
