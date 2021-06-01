import java.util.Random;
import java.util.NoSuchElementException;

/**
 * TO-DO: use array for hashtable and need hashtable array of hash object
 * 
 * @author Brig Lowell
 *
 */
public class HashTable {
	
	private static int tableSize;
	private static int minRange = 95789;
	private static int maxRange = 95791;
	
	Random rand = new Random();
	private static int prime;
	private HashObject[] hashTable;
	private boolean isLinear;
	private HashObject Deleted = new HashObject(-1);
	
	private int totalProbeCount;
	private int totalInsertCount;
	private int totalDuplicates;
	private int insertAttempts;
	
	
	/**
	 * Constructor for building HashTable
	 * @param min range number
	 * @param max range number
	 * @param hashflag for determining to use linear or double hash
	 */
	public HashTable(int min, int max, int hashflag) {

		prime = PrimeGenerator.findTwinPrimes(min , max);
		tableSize = prime + 2;
		totalProbeCount = 0;
		totalDuplicates = 0;
		totalInsertCount = 0;
		insertAttempts = 0;
		hashTable = new HashObject[tableSize];
		
		if (hashflag ==1) isLinear = true;
		else isLinear = false;
	}
	
	
	/**
	 * Searches and returns index where object is stored
	 * Throws exception if object is not found
	 * @param obj HashObject
	 * @return retVal index of object
	 */
	public int search(HashObject obj) {
		
		int retVal = 0;
		int i = 0;
		int keyHash = obj.getKey();
		int hashVal = keyHash % tableSize;
		if (hashVal < 0) hashVal += tableSize;
		int secondHashVal = hashVal % prime;
		if (secondHashVal < 0) secondHashVal += tableSize;
		
		
		while (hashTable[hashVal] != null && i != tableSize && (!hashTable[hashVal].equals(obj))) {
	
			i++;
			if (isLinear) {
				hashVal = (hashVal + i) % tableSize;
			}else {
				
				hashVal = (hashVal + i*secondHashVal) % tableSize;
			}
			
		}
		if(hashTable[hashVal].equals(obj)) {
			retVal = hashVal;
		}
		else if(i == tableSize || hashTable[hashVal] == null) {
			throw new NoSuchElementException("Object: " + obj + " is not in table.");
		}
		
		return retVal;
	}
	
	/**
	 * @param obj hashobject deleted
	 */
	public void delete(HashObject obj) {
		int index = search(obj);
		hashTable[index] = Deleted;
	}
	
	/**
	 * @param obj
	 * @return 
	 */
	public boolean insert(HashObject obj) {
		boolean retVal = false;
		insertAttempts++;

		int hashVal = hash(obj);
		if (totalInsertCount == tableSize) {
			throw new IndexOutOfBoundsException("List is full, cannot insert.");
		}
		else if(hashTable[hashVal] == null || hashTable[hashVal].equals(Deleted)) {
			hashTable[hashVal] = obj;
			totalInsertCount++;
			retVal = true;
			return retVal;
		}
	
		return retVal;
	}
	
	/**
	 * Runs a hash on the hash object's key. will run linear or double hash.
	 * Checks for duplicates and increments duplicate if found, otherwise increments probe count.
	 * @param hash object's key
	 * @return primaryHashValue
	 */
	public int hash(HashObject obj) {
		
		int i = 0;
		int keyHash = obj.getKey();
		
		int primaryHashValue = keyHash % tableSize;
		if (primaryHashValue < 0) primaryHashValue += tableSize;
		int secondaryHashValue = keyHash % prime;
		if (secondaryHashValue < 0) secondaryHashValue += tableSize;
//		insertAttempts++;
		
		
		while (hashTable[primaryHashValue] != null && hashTable[primaryHashValue] != Deleted && i != tableSize) {
			
			if(hashTable[primaryHashValue].equals(obj)) {
				hashTable[primaryHashValue].incrementDupCount();
				//obj.resetNumProbes(obj);
				totalDuplicates++;
				return primaryHashValue;
				
			}else {
				obj.incrementNumProbes();
//				totalProbeCount++;
				
				i++;
				
				if (isLinear) {
					primaryHashValue = (primaryHashValue + i) % tableSize;
				}else {
					primaryHashValue = (primaryHashValue + (i*secondaryHashValue)) % tableSize;
				}
			}
		}
		
		obj.incrementNumProbes();
		totalProbeCount += obj.getNumProbes();
		
		return primaryHashValue;
	}
	
	/**
	 * @return totalProbeCount
	 */
	public int getProbeCountTotal() {
		return totalProbeCount;
	}
	
	/**
	 * @return totalInsertCount
	 */
	public int getTotalInsertCount() {
		return totalInsertCount;
	}
	
	/**
	 * @return insertAttempts
	 */
	public int getInsertAttempts() {
		return insertAttempts;
	}
	
	/**
	 * @return tableSize
	 */
	public int getTableSize() {
		return tableSize;
	}
	
	public int getTotalDuplicates() {
		return totalDuplicates;
	}
	
	/**
	    * @return the hashTable
	    */
	   public HashObject[] getTable()
	   {
	      return hashTable;
	   }
}
