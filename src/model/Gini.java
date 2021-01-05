package model;

import java.util.ArrayList;
import java.util.List;

public class Gini {
	private static List<Integer> values_uniques;
	private List<Integer> labels;
	
	
	public Node giniIndex(List<Float[]> itemsDataSet) {
		int column = 0;
		double mediaGini = 0, bestGini = 1;
		float bestAttribute = 0;
		List<Float[]> bestLeftBranch = new ArrayList<>();
		List<Float[]> bestRightBranch = new ArrayList<>();
		for (Float[] item : itemsDataSet) {
			for (int i = 0; i < item.length - 1; i++) {
				List<Float[]> leftBranch = new ArrayList<>();
				List<Float[]> rightBranch = new ArrayList<>();
				float attribute = item[i];
				// Getting the item and build two Arrays based in the attribute
				for (Float[] item2 : itemsDataSet) {
					if (attribute <= item2[i]) {
						leftBranch.add(item2);
					} else {
						rightBranch.add(item2);
					}
				}
				// Media by node
				mediaGini = (calculateProportions(leftBranch) * leftBranch.size()) / (leftBranch.size())
						+ (calculateProportions(rightBranch) * rightBranch.size() / rightBranch.size());
				if (mediaGini < bestGini) {
					bestGini = mediaGini;
					bestLeftBranch = leftBranch;
					bestRightBranch = rightBranch;
					bestAttribute = attribute;
					column = i;
				}
			}
		}
		Node node = new Node(bestLeftBranch, bestRightBranch, bestAttribute, column);
		return node;
	}

	public double calculateProportions(List<Float[]> branch) {
		int proporcoes[] = { 0, 0 };
		double proporcao = 0, total = 1;
		// Avoiding division by 0
		if (branch.size() == 0) {
			return 0;
		} else {
			for (Float[] item : branch) {
				if (0 == item[4]) {
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
	
	public List<Integer> getValues_uniques() {
		return values_uniques;
	}

	public List<Integer> getLabels() {
		return labels;
	}
	
}
