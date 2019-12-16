

import java.util.*;
import java.io.*;

public class CarClassifier{
	public static void main(String args[]) {
	
		
		String trainingFileName = "Training_Data.txt";
		String keyWordsFileName = "Key_Words.txt";
		Scanner keyWordsContents = null;
		Scanner trainingFileContents = null;
		String review;
		String rating;
		int reviewCount = 0;
		
		
		// in case file path is incorrect, or if file cannot be read
		try {
		
			File trainingFile = new File(trainingFileName);
			trainingFileContents = new Scanner(trainingFile);
			File keyWordsFile = new File(keyWordsFileName);
			keyWordsContents = new Scanner(keyWordsFile);
			
			trainingFileContents.useDelimiter("::"); 
		
		}
		
		catch(IOException ex) {
			
			System.err.println("An IOException was caught!  Make sure file is accessable.");
		}
		
		
		LinkedList keyWords = new LinkedList();
		SearchWordTree goodWords = new SearchWordTree();
		SearchWordTree badWords = new SearchWordTree();
		
		
		while (keyWordsContents.hasNext()) {
			keyWords.insert(keyWordsContents.next());
			
		}
		
		//keyWords.printList();
		
		ClassifiedReview reviewTotals = new ClassifiedReview();
		
		
		
		
		while (trainingFileContents.hasNext()) {
			
			reviewCount++;
			review = trainingFileContents.next();
			rating = trainingFileContents.next();
		  
			ClassifiedReview reviewObject = new ClassifiedReview(review, rating, keyWords, goodWords, badWords);
	    
		}
		//System.out.println(reviewCount);
		//goodWords.inorder();
		//evalKeyWords(goodWords, badWords, keyWords);

		System.out.println("Program is training.");
		
		ClassifiedReview.evalKeyWords(goodWords, badWords, keyWords);
		
		System.out.println("Training is complete.");
		System.out.println();
		System.out.println("Imput your car review: ");
		
		//--------------------
		//Start of testing
		//--------------------
		
		Scanner inputScan = new Scanner(System.in);
		
		Stack<String> inputReview = new Stack<String>();
		
		String s= inputScan.nextLine();
		s = s.toLowerCase().replaceAll("\\p{Punct}+", "").replaceAll("\\s+", " ");
		
		//start off by getting the ratios between the number of positive/negative review and total
		double goodProb = ClassifiedReview.getPosNum() / ClassifiedReview.getReviewNum();
		double badProb = ClassifiedReview.getNegNum() / ClassifiedReview.getReviewNum();
		
		Scanner word = new Scanner(s);
		while(word.hasNext()) {
			
			String currWord = word.next();
			/*for(int i = 0; i < keyWords.size(); i++) {//.......for inside for loop, fix this please
				if(keyWords.get(i).getKeyWord().compareTo(currWord) == 0) {
					keyWords.get(i).getGoodInstance();
					keyWords.get(i).getBadInstance();
				}
			}*/
			//LinkedList (keyWords) method to intake curr goodProb double, curWord String, return new double value.
			inputReview.push(currWord);
		}
		while(!inputReview.empty()) {
			System.out.println(inputReview.pop());
		}
		
		
		
	}
	
	

}
class ClassifiedReview{
	
	String review;
	String rating;
	LinkedList keyWords;
	SearchWordTree goodWords;
	SearchWordTree badWords;
	static int reviewNum = 0; //keeps track of number of review
	static int posNum = 0; //number of positive review
	static int negNum = 0; //number of negative review
	
	
	//SearchWordTree reviewTree = new SearchWordTree();
	
	public ClassifiedReview(String review, String rating, LinkedList keyWords, SearchWordTree goodWords, SearchWordTree badWords) {
		
		this.keyWords = keyWords;
		this.review = review;
		this.rating = rating;
		this.goodWords = goodWords;
		this.badWords = badWords;
			
		
		//cleans up input, sets everything to lowercase, removes punctuations and extra spaces
		review = review.toLowerCase().replaceAll("\\p{Punct}+", "").replaceAll("\\s+", " ");
		rating = rating.toLowerCase().replaceAll("\\p{Punct}+", "").replaceAll("\\s+", " ");
		
		if(rating.compareTo("positive") == 0) {
			posNum++;
		}
		else {
			negNum++;
		}
		reviewNum++;
		
		
		Scanner scan = new Scanner(review);
		
		while(scan.hasNext()) {
			String currWord = scan.next();
			if(rating.compareTo("positive") == 0) {
				//System.out.println(currWord);
				goodWords.insertWord(currWord);
			}
			else {
				badWords.insertWord(currWord);
			}
			
		}
		
		//System.out.println(reviewTree.searchOccur("i"));

		//reviewTree.inorder();
		//System.out.println(reviewNum + " " + posNum + " " + negNum);
		
		//System.out.println("\n");
		
	}
	public ClassifiedReview() {
		
	}
	static void evalKeyWords(SearchWordTree g, SearchWordTree b, LinkedList k) {
		
		
		SearchWordTree goodTree;
		SearchWordTree badTree;
		LinkedList keyWords;
		
		
		goodTree = g;
		badTree = b;
		keyWords = k;
		
		
		for(int i = 0; i < keyWords.size(); i++) {
			keyWords.get(i);
			String currWord = keyWords.get(i).getKeyWord();
			int goodOccur = goodTree.searchOccur(currWord); 
			int badOccur = badTree.searchOccur(currWord);
			
			keyWords.get(i).addGood(goodOccur);
			keyWords.get(i).addBad(badOccur);
			
			//System.out.println(currWord + " " + goodOccur + " " + badOccur);
		}
		
		
	}
	
	//LinkedList (keyWords) method to intake curr goodProb double, curWord String, return new double value.
	static double adjustPosProb(double gp, LinkedList k, SearchWordTree r) {
		double goodProb = gp;
		LinkedList keyWords = k;
		SearchWordTree review = r;
		
		//itterate through linked list, check num of occurences in word tree, if found, multiply by
		
		
		
		return goodProb;
	}
	
	static double adjustNegProb(double bp, LinkedList k, SearchWordTree r) {
		double badProb = bp;
		LinkedList keyWords = k;
		SearchWordTree review = r;
		
		return badProb;
		
	}
	
	
	
	
	static double getPosNum() {
		return (double)posNum;
	}
	static double getNegNum() {
		return (double)negNum;
	}
	static double getReviewNum() {
		return (double)reviewNum;
	}
}
	
	
	
class SearchWordTree{
	
	TreeNode root;


	public class TreeNode{
		
		String word;
		int occurences = 1;
		TreeNode left;
		TreeNode right;
		
		TreeNode(String word){
			this.word = word;
			left = null;
			right = null;
		}
		String getWord(){
			return word; 
		}
	}
	
		
	void insertWord(String word){
			//this.word = word;
		root = insertRec(root, word);
	}
	TreeNode insertRec(TreeNode root, String word){
		String curWord = word;
		TreeNode curRoot = root;
		
			
		//If tree is empty
		if(curRoot == null){
			curRoot = new TreeNode(curWord);
			return curRoot;
		}
		        // Otherwise, recur down the tree
		int value = curWord.compareTo(curRoot.word);
		if (value == 0) {
			curRoot.occurences++;
			//return cur;/////
		}
		else if (value < 0){
			curRoot.left = insertRec(curRoot.left, curWord); 
		}
		
		else if (value > 0){
			curRoot.right = insertRec(curRoot.right, curWord); 
		}
		//System.out.println(curRoot.getWord());
		return curRoot; 
	}
	int searchOccur(String keyWord) {
		TreeNode currNode = root;
		return searchOccurRec(keyWord, currNode);
		
	}
	int searchOccurRec(String keyWord, TreeNode currNode) {
		
		//value to see if it's the same word or similiar
		
		
		if(currNode == null) {
			return 0;
		}
		
		int compareValue = keyWord.compareTo(currNode.word);
		
		if(compareValue == 0 ) {
			return currNode.occurences;
		}
		else {
			if(compareValue < 0) {
				return searchOccurRec(keyWord, currNode.left);
			}
			else {
				return searchOccurRec(keyWord, currNode.right);
			}
		}
	}
	
	
	
	
	void inorder()  { 
	       inorderRec(root); 
	    } 
	  
	    // A utility function to do inorder traversal of BST 
	  	void inorderRec(TreeNode root) { 
	        if (root != null) { 
	        	inorderRec(root.left); 
	            System.out.println(root.word); 
	            //System.out.println(root.occurences);
	            inorderRec(root.right); 
	        } 
	    } 
		
}
	
	
class LinkedList{
			
		static LinkedNode head;
		static int size = 0;
			
		
		public class LinkedNode{
			
		
			String keyWord;
			LinkedNode next;
			int goodInstance = 1; //start at 1 for laplace correction
			int badInstance = 1;  //start at 1 for laplace correction
		
			public LinkedNode(String keyWord) {
				this.keyWord = keyWord;
				
			
			}
		
			void addGood(int occurences){
				goodInstance = goodInstance + occurences;
			}
		
			void addBad(int occurences){
				badInstance = badInstance + occurences;
			}
		
			String getKeyWord(){
				return keyWord;
					}
		
			int getGoodInstance(){
				return goodInstance;
			
			}
			int getBadInstace(){
				return badInstance;
			
			}

		
		
		}

		public int size() {
			return size;
		}

		
		public LinkedNode get(int i) {
			LinkedNode current = head;
			int index = i;
			while(index > 0) {
				current = current.next;
				index--;
			}
			return current;
		}
		
		public void insert(String keyWord) {
			
			LinkedNode newNode = new LinkedNode(keyWord);
			newNode.next = null;
			size++;
			
			if(head == null) {
				
				head = newNode;
			}
			
			else {
				LinkedNode current = this.head;
				while (current.next != null) {
					current = current.next;
				}
				current.next = newNode;
			}	
			
			
		}
		
		public static void printList() {
			LinkedNode currNode = head;
			while (currNode != null) {
				//System.out.print(currNode.getKeyWord() + " " + currNode.getGoodInstance() + "\n");
				currNode = currNode.next;
			}
		}
}




