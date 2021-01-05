package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DecisionTreeSequential {
	Node root;
	private List<Float[]> train = new ArrayList<>();
	private List<Float[]> test = new ArrayList<>();
	float acertos = 0, erros = 0;
	private static float accurate = 0;
	
	public DecisionTreeSequential(List<List<Float[]>> folds, int index) {
		crossValidation(folds, index);
		this.root = buildTree(this.train);
		accurate = getAccurate() + test();
	}

	private void crossValidation(List<List<Float[]>> folds, int index) {
		// Splitting dataset of train and test
		this.test = folds.get(index);
		this.train = acumulateFolds(index, folds);
	}

	private List<Float[]> acumulateFolds(int index, List<List<Float[]>> folds) {
		List<Float[]> train = new ArrayList<Float[]>();
		int i = 0;
		for (List<Float[]> fold : folds) {
			if (i != index) {
				train.addAll(fold);
			}
			i++;
		}
		return train;
	}

	private Node buildTree(List<Float[]> branch) {
		Gini gini = new Gini();
		Node node = gini.giniIndex(branch);
		if (node.getLeft_branch().size() == 0 || node.getRight_branch().size() == 0) {
			int classe;
			if (node.getLeft_branch().size() < node.getRight_branch().size()) {
				classe = (int) createLeaf(node.getRight_branch());
			} else {
				classe = (int) createLeaf(node.getLeft_branch());
			}
			Node leaf = new Node(true, classe);
			node.setRightSon(leaf);
			node.setLeftSon(leaf);
			return node;
		}
		if (node.getLeft_branch().size() > 1) {
			Node tempLeft = buildTree(node.getLeft_branch());
			node.setLeftSon(tempLeft);
		} else {
			int classe = (int) createLeaf(node.getLeft_branch());
			Node leaf = new Node(true, classe);
			node.setLeftSon(leaf);
		}
		if (node.getRight_branch().size() > 1) {
			Node tempNode = buildTree(node.getRight_branch());
			node.setRightSon(tempNode);
		} else {
			int classe = (int) createLeaf(node.getRight_branch());
			Node leaf = new Node(true, classe);
			node.setRightSon(leaf);
		}
		return node;
	}

	private float createLeaf(List<Float[]> branch) {
		Map<Object, Long> counts = branch.stream().collect(Collectors.groupingBy(e -> e[4], Collectors.counting()));
		Long mostOccurrence = 0l;
		float index = 0;
		for (Map.Entry<Object, Long> entry : counts.entrySet()) {
			if (entry.getValue() > mostOccurrence) {
				mostOccurrence = entry.getValue();
				index = (float) entry.getKey();
			}
		}
		return index;
	}

	private float test() {
		Node node;
		for (Float[] item : test) {
			node = this.root;
			while (node.getLeftSon() != null || node.getRightSon() != null) {
				if (item[node.getColumn()] > node.getAttribute()) {
					node = node.getRightSon();
					if (node.isLeaf()) {
						if (item[4] == node.getClasse()) {
							acertos++;
						} else {
							erros++;
						}
					}
				} else {
					node = node.getLeftSon();
					if (node.isLeaf()) {
						if (item[4] == node.getClasse()) {
							acertos++;
						} else {
							erros++;
						}
					}
				}
			}
		}
		float acuracia =  (acertos / this.test.size()) * 100;
		return acuracia;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public List<Float[]> getTrain() {
		return train;
	}

	public List<Float[]> getTest() {
		return test;
	}

	public static float getAccurate() {
		return accurate;
	}

}
