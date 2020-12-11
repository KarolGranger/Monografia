package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) {
		// Reading file and setting training data
		ArrayList<Float[]> itemsDataset = File.fileParser("banknote.csv");

		// Randomizing the samples
		Collections.shuffle(itemsDataset);
		// itemsDataset.forEach(x -> System.out.println(Arrays.toString(x)));
		// Set numbers of folds
		int nFolds = 4;

		// Slipt dataset in n folds
		List<List<Float[]>> folds = new ArrayList<>();
		folds = File.splitFolds(itemsDataset, nFolds);

		
		long start = System.currentTimeMillis();
		DecisionTree dt = null;
		for (int i = 0; i < folds.size(); i++) {
			//Create n Decision Trees
			dt = new DecisionTree();
			
			// Splitting dataset of train and test
			dt.crossValidation(folds, i);
			dt.run();
		}
		long finish = System.currentTimeMillis();
        double seq = ( finish - start ) / 1000.0;
        System.out.println("Acurácia: " + DecisionTree.getAccurate() / folds.size());
        System.out.println(seq + " segundos!");  
	}
}
