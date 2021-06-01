import java.util.Objects;
import java.util.Random;

/**
 * TO-DO: contains generic data object,
 * 
 * @author Brig Lowell
 *
 */
public class HashObject<T> {
	
	//HashObject<T> object;
	private int numProbes;
	private int dupCount;
	private T obj;
	private int objKey;
	Random rand = new Random();
	
	public HashObject(T obj) {
		this.obj = obj;
		objKey = getKey();
		dupCount = 0;
		numProbes = 0;
	}
	
	/**
	 * @return hashcode key stored in object
	 */
	public int getKey(){
		
		return obj.hashCode();
	}
	
	/**
	 * increments number of Probes
	 */
	public void incrementNumProbes(){
		numProbes++;
	}
	
	/**
	 * increments number of duplicates
	 */
	public void incrementDupCount() {
		dupCount++;
	}
	
	/**
	 * @return duplicate count
	 */
	public int getDupCount() {
		return dupCount;
	}
	
	/**
	 * @return number of probes
	 */
	public int getNumProbes() {
		return numProbes;
	}
	
	@Override
	   public int hashCode()
	   {
	      return Objects.hash(obj);
	   }
	
	@Override
	public boolean equals(Object o) {
//		if (object == null || object.getClass() != this.getClass()) {
//			return false;
//		}
//		return ((HashObject)object).objKey == this.objKey;
		
		if(o == this) {
			return true;
		}
		if (!(o instanceof HashObject)) {
			return false;
		}
		
		HashObject object = (HashObject)o;
		return (objKey == object.getKey());
		
	}
	
	@Override
	public String toString() {
		
		return obj.toString(); 
	}
}
