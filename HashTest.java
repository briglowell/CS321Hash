import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * TO DO: Driver Class
 * @author Brig Lowell
 *
 */
public class HashTest {
	
	/*adjust these min and max range values to check 
	 *for twin primes, and set table size to greater prime
	 */
	static int minTableRange = 95500;
	static int maxTableRange = 96000;

	//instantiates tables, alpha, and debug variables
	static HashTable linearTable;
	static HashTable doubleTable;
	static double alpha;
	static int debug;

	//counter variables
	static int insertCount = 0;
	static int numDuplicates = 0;
	static int insertAttempts = 0;
	
	static String argType;

	public static void main(String args[]) {
		
		alpha = Double.parseDouble(args[1]);
//		debug = Integer.parseInt(args[2]);
		
		
		linearTable = new HashTable(minTableRange, maxTableRange, 1);
		doubleTable = new HashTable(minTableRange, maxTableRange, 2);
		
		/*
		 * Creates linear and double hashTable of random Integers.
		 */
		if(Integer.parseInt(args[0]) == 1) {
			HashObject intObj;
			Random rand = new Random();
			argType = "Integer";
			
			try {
				double n = (linearTable.getTableSize()*alpha);

				while(insertCount <= n) {
					intObj = new HashObject(rand.nextInt());
					insertAttempts++;
					
					hashItUp(linearTable, doubleTable, intObj);

				}
				
			}catch(IndexOutOfBoundsException e){
				System.out.println(e);
			}catch(NoSuchElementException e) {
				System.out.println(e);
			}
			System.out.println();
		}
		
		/*
		 * Creates linear and double hashTables of the currentTime in milliseconds 
		 */
		if(Integer.parseInt(args[0]) == 2) {
			HashObject longObj; 
			argType = "Long";
			
			try {
				double n = (linearTable.getTableSize()*alpha);

				while(insertCount <= n) {
					longObj = new HashObject(System.currentTimeMillis());
					insertAttempts++;
					
					hashItUp(linearTable, doubleTable, longObj);
					
				}
				
			}catch(IndexOutOfBoundsException e){
				System.out.println(e);
			}catch(NoSuchElementException e) {
				System.out.println(e);
			}
			
		}
		
		/*
		 * Creates linear and double hashTables using parsed strings from a file 
		 */
		if(Integer.parseInt(args[0]) == 3) {
			
			HashObject stringObj;
			File file = new File("word-list");
			argType = "word-list";
	
			try {
				Scanner scan = new Scanner(file);
				double n = (linearTable.getTableSize()*alpha);
				
				while(insertCount <= n) {
					stringObj = new HashObject(scan.nextLine());
					insertAttempts++;
					
					hashItUp(linearTable, doubleTable, stringObj);
					
				}
				scan.close();
				
			}catch(FileNotFoundException e) {
				System.out.println("File is not found");
			}catch(IndexOutOfBoundsException e){
				System.out.println(e);
			}catch(NoSuchElementException e) {
				System.out.println(e);
			}catch(IOException e) {
				e.printStackTrace();
			}
			
		}
		
		

//		System.out.println("Duplicates =  "  + numDuplicates);
//		System.out.println("Insert Attempts =  " + insertAttempts);
//		System.out.println();
//		System.out.println("Lin getprobecounttotal " + linearTable.getProbeCountTotal());
//		System.out.println("Dub getprobecounttotal " + doubleTable.getProbeCountTotal());
//		System.out.println();
//		System.out.println("Lin getTotalInsertCount " + linearTable.getTotalInsertCount());
//		System.out.println("Dub getTotalInsertCount " + doubleTable.getTotalInsertCount());
//		System.out.println();
//		System.out.println("LINEAR INSERT ATTEMPTS: " + linearTable.getInsertAttempts());
//		System.out.println("DOUBLE INSERT ATTEMPTS: " + doubleTable.getInsertAttempts());
		
		
		if (args.length == 3){
			
			if(Integer.parseInt(args[2]) == 0) {
				double lProbeRatio = (double)linearTable.getProbeCountTotal()/linearTable.getTotalInsertCount();
				double dProbeRatio = (double)doubleTable.getProbeCountTotal()/doubleTable.getTotalInsertCount();
				
				System.out.println("A good table size is found: " + linearTable.getTableSize());
				System.out.println("Data source type: " + argType +"\n\n");
				System.out.println("Using Linear Hashing....");
				System.out.println("Input " + insertAttempts + ", of which " + 
									linearTable.getTotalDuplicates() + " duplicates");
				System.out.println("load factor = " + alpha + ", Avg. no. of probes " + lProbeRatio + "\n\n");
				System.out.println("Using Double Hashing....");
				System.out.println("Input " + insertAttempts + ", of which " + 
									doubleTable.getTotalDuplicates() + " duplicates");
				System.out.println("load factor = " + alpha + ", Avg. no. of probes " + dProbeRatio + "\n\n");
				
			}
			
           if (Integer.parseInt(args[2]) == 1){
        	   
              File linDump = new File("linear-dump");
              File doubDump = new File("double-dump");
              
              try{
            	  
                 BufferedWriter linWriter;
                 BufferedWriter doubWriter;
                 int i = 0;
                 int j = 0;
                 
                 if (linDump.canRead()){
                    linDump.delete();
                 }
                 
                 if (doubDump.canRead()){
                    doubDump.delete();
                 }
                 
                 if (!linDump.createNewFile()){
                	 
                    System.out.println("linear-dump file not created / already exists?");
                 }
                 if (!doubDump.createNewFile()){
                	 
                    System.out.println("double-dump file not created / already exists?");
                 }
                 
                 linWriter = new BufferedWriter(new FileWriter("linear-dump"));
                 doubWriter = new BufferedWriter(new FileWriter("double-dump"));
                 
                 for(HashObject obj1 : linearTable.getTable()){
                	 
                    if (obj1 != null){
                    	
                       linWriter.write("table[" + i + "]: " + obj1.toString() + " " + obj1.getNumProbes() + " " + obj1.getDupCount() + "\n");
                    }
                    i++;
                 }
                 linWriter.close();
                 for(HashObject obj2 : doubleTable.getTable()){
                	 
                    if (obj2 != null){
                    	
                       doubWriter.write("table[" + j + "]: " + obj2.toString() + " " + obj2.getNumProbes() + " " + obj2.getDupCount() + " \n");
                    }
                    j++;
                 }
                 doubWriter.close();
              }catch (IOException e)
               {
            	  e.printStackTrace();
               }
           }
        }
	}
	
	/**
	 * Insert objects into both specified tables if true, 
	 * otherwise doesn't insert and increments duplicate count.
	 * @param linTab Linear Hash Table
	 * @param doubleTab Double Hash Table
	 * @param obj Hash Object used in table
	 */
	public static void hashItUp(HashTable linTab, HashTable doubleTab, HashObject obj) {
		if(linTab.insert(obj)) {
			//doubleTab.insert(obj);
			insertCount++;
		}else {
			numDuplicates++;
		}
		if(doubleTab.insert(obj)) {
			
		}
		
	}
}
