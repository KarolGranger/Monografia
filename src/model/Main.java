package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		String nameFile = "C:\\Users\\karol\\eclipse-workspace\\Monografia\\src\\model\\banknote.csv"; 
		int colunas = 5;
		int nFolds = 4;
		File file = new File();	
		//Lendo dataset e setando dados na classe
		ArrayList<DataSetItem> itemsDataSetFull = new ArrayList<DataSetItem>();
		itemsDataSetFull = file.fileParser(nameFile, colunas);
		
		//Dividindo dataset in 5 folds
		ArrayList<ArrayList<DataSetItem>> folds;
		folds = file.crossValidationSplit(itemsDataSetFull, nFolds);
			
		//Treinando modelo com folders -1
		int mediaAccuracy = 0;
		mediaAccuracy = file.crossValidation(folds, colunas);
		System.out.println(mediaAccuracy);
		
	}
}
