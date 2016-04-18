package anagram2;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
public class Anagram2 {
	public static ArrayList<String> wordList = new ArrayList<String>(50);
	public static ArrayList<String> keyList = new ArrayList<String>(50);
	static String wordOne = "";
	static String wordTwo = "";
	static int k =0;
	// The main Method here will call the file to load, and then each of the supporting methods.
	public static void main(String[] args) {
		loadFile();
		listAlphabetizer(keyList);
		anagramTester(keyList);
	}
	/* This method will load the file, creating the string of words to later use as an array.
	In doing so, it will create two separate lists. Should more than 50 applicable words
	be found, it will not use them at all. */
	public static void loadFile() {
		Scanner fileScanner = null;
		System.out.println("Please enter the location of the file: ");
		Scanner fileLoc = new Scanner(System.in);
		String fileName = fileLoc.nextLine();
		File inputFile = new File(fileName);
		try {
			fileScanner = new Scanner(inputFile);
			while (fileScanner.hasNext()) {
				if (k == 50) {
					System.out.println("There are more than 50 words here. Please truncate the file to allow for only 50 words of appropriate length.");
					System.exit(1);
				}
				wordOne = fileScanner.next();
				if (wordOne.length() < 13) {
					wordList.add(wordOne);
					wordOne = wordOne.toLowerCase();
					keyList.add(wordOne);
					k++;
				}
			}
		} catch (java.io.FileNotFoundException e) {
			System.out.println(e);
			System.exit(1);
		} finally {
			fileScanner.close();
			fileLoc.close();
		}
	}
	/* Here, the preconditions are the previously created lists for the Key and Words, while the 
	 * post conditions are sorted lists of each type, with the words in the Key list matching the
	 * words in the Word list, though sorted alphabetically inside each of their individual indexes. */
	public static void listAlphabetizer(ArrayList<String> keyList) {
		int keyLoc = 0;
		for (int i = 0; i < keyList.size(); i++) {
			String keyWord = "";
			char[] letters = keyList.get(i).toCharArray();
			Arrays.sort(letters);
			for (int n = 0; n < letters.length; n++) {
				if (Character.isLetter(letters[n])) {
					keyWord += letters[n];
				}
			}
			keyList.remove(i);
			keyList.add(i, keyWord);
		}
		for (int i = 0; i < keyList.size() - 1; i++) {
			for (int j = i; j < keyList.size(); j++) {
				if (keyList.get(i).equals(keyList.get(j))) {
					keyLoc = i;
					wordOne = keyList.get(j);
					wordTwo = wordList.get(j);
					wordList.remove(j);
					keyList.remove(j);
					keyList.add(keyLoc, wordOne);
					wordList.add(keyLoc, wordTwo);
				}
			}
		}
	}
	//Here, the program will print out each set of anagrams in order on a line, as well as in a file.
	public static void anagramTester(ArrayList<String> keyList2) {
		PrintWriter output = null;
		try {
			output = new PrintWriter(new FileWriter("output.txt"));
			System.out.println("Here is a list of the anagrams that were found in the file,");
			System.out.println("in order as they appear in the list:");
			output.println("Here is a list of all of the anagrams that were found in the file,");
			output.println("in order as they appear in the list:");
			for (int i = 0; i < wordList.size() - 1; i++) {
				int j = i + 1;
				if (keyList.get(i).equals(keyList.get(j))) {
					System.out.print(wordList.get(i) + " ");
					output.print(wordList.get(i) + " ");
				} else {
					System.out.println(wordList.get(i) + " ");
					output.println(wordList.get(i) + " ");			
				}
			}
			if (keyList.get(wordList.size()-1).equals(keyList.get(wordList.size()-2))) {
				System.out.print(wordList.get(wordList.size()- 1));
				output.print(wordList.get(wordList.size()-1));
			} else {
				System.out.println(wordList.get(wordList.size()- 1));
				output.println(wordList.get(wordList.size()- 1));
			}
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		} finally {
			output.close();
		}
	}
}
