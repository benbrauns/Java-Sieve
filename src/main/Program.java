package main;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;

public class Program {

	
	static int maxPrime = 100_000_000;
	static boolean[] nums;
	static int maxRoot = (int)Math.sqrt(maxPrime);
	static int prevKey = 2;
	static int sampleSize = 100;
	static int warmupBuffer = 10;
	static void populateNums() {
		nums = new boolean[(maxPrime / 3) - 3];
	}
	
	static int getNum(int i) {
		return (5 + (3 * i) + (-1 * (i % 2)));
	}
	
	static void findNextKey() {
		//the non-primes will be marked true
		//so we use true values to skip
		for (int i = 0; i < nums.length; i+=1) {
			if (nums[i]) {
				continue;
			}
			else if (getNum(i) > prevKey) {
				prevKey = getNum(i);
				break;
			}
		}
	}
		
	static void checkMults() {
		//marking the non primes true
		int j = 4;
		for (int i = 5; i <= (maxPrime - 8) / prevKey; i+=j) {
			int index = (int)(((i * prevKey) - 4) / 3);
			nums[index] = true;
			if (j == 2) {
				j = 4;
			}
			else {
				j = 2;
			}
		}
	}	
	static long findPrimes() {
		prevKey = 2;
		long startTime = System.currentTimeMillis();
		populateNums();
		
		while (prevKey < maxRoot) {
			findNextKey();
			checkMults();
		}
		long endTime = System.currentTimeMillis();
		//System.out.println(primesArray().length);
		return endTime - startTime;
	}	
	static void printAverage(ArrayList<Long> times) {
		long total = 0;
		for (long time : times) {
			total += time;
		}
		long average = total / times.size();
		long minutes = (average/1000) / 60;
		long seconds = (average/1000) % 60;
		System.out.println(average + " Milliseconds");
		System.out.println("Average time: " + minutes + " Minutes and " + seconds + " Seconds");
	}	
	static int countPrimes() {
		int count = 0;
		for (boolean val : nums) {
			if (!val) count += 1;
		}
		return count;
	}
	static int[] primesArray() {
		int[] primes = new int[countPrimes() + 2];
		primes[0] = 2;
		primes[1] = 3;
		int primesIndex = 2;
		for (int i = 0; i < nums.length; i++) {
			if (!nums[i]) {
				primes[primesIndex] = getNum(i);
				primesIndex += 1;
			}
		}
		return primes;
	}
	static void printPrimesArray() {
		for (int val : primesArray()) {
			System.out.println(val);
		}
	}
	static void printPrimeVals() {
		for (int i = 0; i < nums.length; i++) {
			if (!nums[i]) {
				System.out.println(getNum(i));
			}
		}
	}
	public static void main(String[] args) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		
		ArrayList<Long> times = new ArrayList<Long>();
		for (int i = 0; i < sampleSize + warmupBuffer; i++) {
			if (i >= warmupBuffer)  {
				times.add(findPrimes());
				System.out.println(i + 1 - warmupBuffer);
			}
		}
		printAverage(times);
		System.out.println(countPrimes());
		//printPrimesArray();
		//printPrimeVals();
	}

}
