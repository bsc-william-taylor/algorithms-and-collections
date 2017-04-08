package part3;

public class Item implements Comparable<Item> {	
	/**
	 * Provides an immutable integer valued item that counts comparisons.
	 */
	
	private static long compCount = 0;
	
	private final Integer value;
	
	/**
	 * Constructor - creates an Item and sets its value
	 * @param value - the value for the Item
	 */
	public Item(Integer value) {
		this.value = value;
	}
	
	/**
	 * The value of this Item
	 * @return the Item's value
	 */
	public Integer value() {
		return value;
	}
	
	/**
	 * Compares the value of this Item with that of other according to
	 * the contract for Comparable.
	 * Increments the count of comparisons.
	 */
	@Override
	public int compareTo(Item other) {
		compCount++;
		return value.compareTo(other.value);
	}
	
	/**
	 * Returns the total number of comparisons performed on instances of type
	 * Item since the counter was last reset (or the total if it has never been reset).
	 * @return the count of calls to compareTo() for type Item
	 */
	public static long getCompCount() {
		return compCount;
	}
	
	public static void resetCompCount() {
		compCount = 0;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	@Override
	public boolean equals(Object that) {
		compCount++;
		if (this == that) return true;
		if (!(that instanceof Item)) return false;
		Item other = (Item)that;
		return value.equals(other.value);
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
}