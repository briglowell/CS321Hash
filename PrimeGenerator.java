import java.util.Random;

/**
 * Class that generates twin prime numbers in a given range
 * @author Brig Lowell
 *
 */
public class PrimeGenerator {
	
	static int prime1;
	static int prime2;
	static Random rand = new Random();
	
	
	public PrimeGenerator(int min, int max) {
		prime1 = -1;
		prime2 = -1;
		findTwinPrimes(min, max);
	}
	
	
	/**
	 * Method for determining if integer number is prime. True= prime, false= notPrime
	 * @param num int to check if prime
	 * @return boolean true/false
	 */
	public static boolean isPrime(int num) {
		boolean retVal = false;
		int a = rand.nextInt(num-2);				//first random number
		a += 2;										//guarantees number greater than 1
		int a2 = rand.nextInt(num-2);				//second random number > 1 
		a2 += 2;
		
		int b = a%num;								//starts as a%num since first in binary array always 1
		int c = a2%num;								//starts as a2%num
//		if (num<=1) {								//number input can't be less than 1 
//			retVal = false;
//			return retVal;
//		}
		int n[] = convertToBinary(num-1);			//binary representation of number-1 for formula
		
		/*Runs the calculation using formula: random^(num-1)mod(num), 
		 *by breaking the number down into binary, then calculating on each bit.
		 *This allows the calculation to be done on large numbers.
		 **/
		for (int i = 1; i < n.length; i++) {		//Calculations based on 0 or 1 in array, starts at n[1], n[0] always a 1
			if(n[i] == 1) {							//if number in binary array is 1
				b = (int) (Math.pow(b, 2)*a%num);
				c = (int) (Math.pow(c, 2)*a2%num);
				
			}else if (n[i] == 0){					//if number in binary array is 0
				b = (int) ((Math.pow(b, 2))%num);
				c = (int) (Math.pow(c, 2))%num;
			}
		}
		
		/* If calculated number = 1 then number is considered prime with a
		 1/10^26 false prime rate, since two instances are run */ 
		if(b == 1 || c == 1) {						
			retVal = true;
		}else{
			retVal = false;
		}
		return retVal;
	}
	
	
	/**
	 * Finds First set of Twin primes from input range. Returns the greater of the twinprimes.
	 * primes are -1 if twin primes do not exist in range.
	 * @param min
	 * @param max
	 * @return prime2 the bigger of the twin primes.
	 */
	public static int findTwinPrimes(int min, int max){
		int flag = 0;
		int temp;
		
		
		if (min < 0) min = min*-1;					//turns min positive
		if (max < 0) max = max*-1;					//turns max positive
		if(min <= 1) min += 2;
		
		if(min > max) {								//switches min and max if min>max, POSSIBLY NEED TO CHANGE
			temp = min;
			min = max;
			max = temp;
		}
		
		if(min%2 == 0) min++;						//primes are odd so can skip evens.
			
		
		for(int i = min; i < max-1; i+=2) {
			if (isPrime(i) && isPrime(i+2) && flag == 0) {
				prime1 = i;
				prime2 = i+2;
				flag = 1;
				i = max;
			}
		}
		
		if(prime1 == -1 || prime2 == -1) {
			throw new IndexOutOfBoundsException("No prime numbers in Range" + min + "  " + max);
		}
//		System.out.println("Prime1 is: " + prime1 + " Prime2 is: " + prime2);
		return prime1;
	}
	
	/**
	 * Converts integer number to exact size binary array
	 * @param num
	 * @return binary array
	 */
	public static int[] convertToBinary(int num) {
		int binary[];					 
		int temp[] = new int[64];					//representing maximum 64bit
		int temp2[] = new int[64];
		int temp3[] = new int[64];
		int j;
		int index = 0;
		int count = 0;
		
		while(num > 0) {							//loop to create reversed binary order array
			temp[index++] = num%2;
			num = num/2;
		}
		
		j = 0;
		for(int i = temp.length-1; i>= 0; i--) {	//loop to reverse order to correct order for binary array
			temp2[j] = temp[i];
			j++;
		}
		
		j = 0;
		int k = 0;
		while(temp2[j] == 0 && j < temp2.length) {		//shifts binary of number to beginning of array
			temp2[j] = temp2[j+1];
			if (temp2[j] == 1) {
				for(int i = j+1; i < temp2.length; i++) {
					temp3[k] = temp2[i];
					count++;
					k++;
				}
			}
			j++;
		}
		
		binary = new int[count];					//creates the binary array of exact size for binary conversion
		for(int i = 0; i < binary.length; i++) {
			binary[i] = temp3[i];
		}
		
			
		return binary;
	}
}
