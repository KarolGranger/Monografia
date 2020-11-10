package model;

import java.util.ArrayList;

public class Gini {
	private static ArrayList<Integer> values_uniques;
	private ArrayList<Integer> labels;
	private int depth;

	

	public ArrayList<Integer> getValues_uniques() {
		return values_uniques;
	}

	public ArrayList<Integer> getLabels() {
		return labels;
	}

	public void getDatasClass(ArrayList<DataSetItem> itemsDataSet, int colunas) {
		// Getting unique values of classes
		ArrayList<Integer> uniquesClasses = new ArrayList<>();
		ArrayList<Integer> classes = new ArrayList<>();
		for (DataSetItem item : itemsDataSet) {
			Integer classe = new Integer((int) item.getItems()[colunas - 1]);
			classes.add(classe);
			if (!uniquesClasses.contains(classe)) {
				uniquesClasses.add(classe);
			}
		}
		this.labels = classes;
		this.values_uniques = uniquesClasses;
	}

	public Node findBestSplit(ArrayList<DataSetItem> itemsDataSet) {
		depth++;
		int column = 0;
		double giniLeft = 0, giniRight = 0, mediaGini = 0, bestGini = 1;
		float  bestAttribute = 0;
		ArrayList<DataSetItem> bestLeftBranch  = new ArrayList<>();
		ArrayList<DataSetItem> bestRightBranch  = new ArrayList<>();
		for (DataSetItem item : itemsDataSet) {
			for (int i = 0; i < item.getItems().length-1; i++) {
				ArrayList<DataSetItem> leftBranch = new ArrayList<>();
				ArrayList<DataSetItem> rightBranch = new ArrayList<>();
				float attribute = item.getItems()[i];
				
				// Getting the item and build two Arrays based in the attribute
				for (DataSetItem item2 : itemsDataSet) {
					if (attribute <= item2.getItems()[i]) {
						leftBranch.add(item2);
					} else {
						rightBranch.add(item2);
					}
				}
				// Send branch for gini calc			
				giniLeft = giniIndex(leftBranch);			
				giniRight = giniIndex(rightBranch);
				
				//Media by node
				mediaGini = (giniLeft * leftBranch.size()) + (giniRight * rightBranch.size());
				mediaGini = mediaGini / (leftBranch.size() + rightBranch.size());
				if(mediaGini < bestGini) {
					bestGini = mediaGini;
					bestLeftBranch = leftBranch;
					bestRightBranch = rightBranch;
					bestAttribute = attribute;
					column = i;
				}
			}
		}

			Node node = new Node();
			node.setLeft_branch(bestLeftBranch);
			node.setRight_branch(bestRightBranch);
			node.setAttribute(bestAttribute);
			node.setColumn(column);
			node.setDepth(depth);
			return node;
		
	}

	public double giniIndex(ArrayList<DataSetItem> branch) {
		int proporcoes[] = {0,0};
		double proporcao = 0, total = 1;
		// Avoiding division by 0
		if (branch.size() == 0) {
			return 0;
		} else {
			for (DataSetItem item : branch) {
				if (Gini.values_uniques.get(0) == item.getItem(4)) {
					proporcoes[0]++;
				} else {
					proporcoes[1]++;
				}
			}
			for (int i = 0; i < proporcoes.length; i++) {
				double j = proporcoes[i];
				proporcao = j / branch.size();
				total -= Math.pow(proporcao, 2);
			}
			return total;
		}
	}
	
	

}
