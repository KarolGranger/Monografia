package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws InterruptedException {
		// Reading file and setting training data
		ArrayList<Float[]> itemsDataset = File.fileParser("banknote.csv");
		// Randomizing the samples
		Collections.shuffle(itemsDataset);
		// itemsDataset.forEach(x -> System.out.println(Arrays.toString(x)));
		// Set numbers of folds
		int nFolds = 6;
		int nTrheads = 1;
		// Slipt dataset in n folds
		List<List<Float[]>> folds = new ArrayList<>();
		folds = File.splitFolds(itemsDataset, nFolds);

		long start = System.currentTimeMillis();

		System.out.println("-------Algoritmo Sequencial-------------");
		DecisionTreeSequential dtS;
		for (int i = 0; i < nTrheads; i++) {
			for (int j = 0; j < folds.size(); j++) {
				// Create n Decision Trees
				dtS = new DecisionTreeSequential(folds, j);
			}
		}
		long finish = System.currentTimeMillis();
		double seq = (finish - start) / 1000.0;
		System.out.println(seq + " segundos!");
		System.out.println("Acurácia: " + (DecisionTreeSequential.getAccurate() / folds.size()) / nTrheads / 7);

		System.out.println("-------Algoritmo Paralelo-------------");
		start = System.currentTimeMillis();
		ExecutorService exec = Executors.newFixedThreadPool(nTrheads);
		DecisionTreeParallel dtP = null;
		for (int i = 0; i < nTrheads; i++) {
			for (int j = 0; j < folds.size(); j++) {
				// Create n Decision Trees
				dtP = new DecisionTreeParallel(folds, j);
				exec.execute(dtP);
			}
		}
		exec.shutdown(); // Disable new tasks from being submitted

		try {
			// Wait a while for existing tasks to terminate
			while (!exec.awaitTermination(1200, TimeUnit.SECONDS)) {
				System.err.println("Pool did not terminate");
			}

		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			exec.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
		System.out.println("Acurácia: " + DecisionTreeParallel.getAccurate() / folds.size() / nTrheads);
		finish = System.currentTimeMillis();
		double par = (finish - start) / 1000.0;
		System.out.println(par + " segundos!");
		System.out.println(seq / par + " speedup!");
	}
}
