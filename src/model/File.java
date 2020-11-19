package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class File {
	private ArrayList<DataSetItem> dataSetTrain;
	private ArrayList<DataSetItem> dataSetTest;

	public ArrayList<DataSetItem> fileParser(String nameFile, int colunas) throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(nameFile)));
		String linha = null;
		ArrayList<DataSetItem> itemsDataSet = new ArrayList<DataSetItem>();
		DataSetItem itemDataSet;
		int i = 0;
		try {
			while ((linha = reader.readLine()) != null) {
				itemDataSet = new DataSetItem();
				float items[] = new float[colunas];
				String[] item = linha.split(",");
				while (i < colunas) {
					items[i] = Float.parseFloat(item[i]);
					i++;
				}
				i = 0;
				itemDataSet.setItems(items);
				itemsDataSet.add(itemDataSet);
			}

		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo!");
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return itemsDataSet;
	}

	public ArrayList<ArrayList<DataSetItem>> crossValidationSplit(ArrayList<DataSetItem> itemDataSetItems, int folds) {
		ArrayList<ArrayList<DataSetItem>> itemsDataSetSplit = new ArrayList<ArrayList<DataSetItem>>();
		ArrayList<DataSetItem> itemsDataSetCopy = new ArrayList<DataSetItem>();
		for(DataSetItem item : itemDataSetItems) {
			itemsDataSetCopy.add(item);
		}

		ArrayList<DataSetItem> fold;
		Random random = new Random();
		int index = 0;
		int foldSize = itemDataSetItems.size() / folds;
		for (int i = 0; i < folds; i++) {
			fold = new ArrayList<DataSetItem>();
			while (fold.size() < foldSize) {
				index = random.nextInt(itemsDataSetCopy.size());
				fold.add(itemsDataSetCopy.remove(index));
			}
			itemsDataSetSplit.add(fold);
		}
		return itemsDataSetSplit;
	}

	public int crossValidation(ArrayList<ArrayList<DataSetItem>> folds, int colunas) {
		int sumAccuracy = 0;

		// Splits
		for (int i = 0; i < folds.size(); i++) {
			this.dataSetTest = folds.get(i);
			this.dataSetTrain = acumulateFolds(i, folds);

			// Gerando dados para o Gini
			Gini gini = new Gini();
			gini.getDatasClass(this.dataSetTrain, colunas);
			// Iniciando arvore
			Tree tree = new Tree();
			tree.setRoot(tree.buildTree(this.dataSetTrain));
			// Testando algoritmo
			sumAccuracy += tree.test(this.dataSetTest);

		}

		return (sumAccuracy  / folds.size()) ;
	}

	private ArrayList<DataSetItem> acumulateFolds(int index, ArrayList<ArrayList<DataSetItem>> folds) {
		ArrayList<DataSetItem> train = new ArrayList<DataSetItem>();
		int i = 0;
		
		for (ArrayList<DataSetItem> fold : folds) {
			if(i != index) {
				train.addAll(fold);
			}
			i++;
		}
		return train;
	}
}
