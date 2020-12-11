package model;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class File {

	public static ArrayList<Float[]> fileParser(String nameFile) {
		ArrayList<Float[]> dataset = new ArrayList<>();
		try (Scanner reader = new Scanner(Paths.get("src\\model\\" + nameFile))) {
			while (reader.hasNext()) {
				String line[] = reader.nextLine().split(",");
				Float[] values = new Float[line.length];
				for (int i = 0; i < values.length; i++)
					values[i] = Float.parseFloat(line[i]);
				dataset.add(values);
			}
		} catch (Exception e) {
			System.err.println("Erro ao ler o arquivo! " + e);
		}
        return dataset;
	}

	public static List<List<Float[]>> splitFolds(ArrayList<Float[]> items, int folds) {
		int nFolds = items.size() / folds;
		List<List<Float[]>> itemsSplit = new ArrayList<>();
		List<Float[]> split ;
		int index = 0, temp = 0;
		for (int i = 0; i < folds; i++) {
			split = new ArrayList<Float[]>(nFolds);
			temp += nFolds;
			split = items.subList(index, temp);
			index = temp;
			itemsSplit.add(split);
		}
		return itemsSplit;
	}
}
