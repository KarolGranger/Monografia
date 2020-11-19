package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class Tree {
	Node root;
	int childrens = 0;
	int acertos = 0;
	int erros = 0;
	
	public Node buildTree(ArrayList<DataSetItem> branch) {
		childrens++;
		Gini gini = new Gini();
 		Node node = gini.findBestSplit(branch);
		if(node.getLeft_branch().size() == 0 || node.getRight_branch().size() == 0) {
			int classe;
			if(node.getLeft_branch().size() < node.getRight_branch().size()) {
				classe = (int) createLeaf(node.getRight_branch());
			}
			else {
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
		}
		else {
			int classe = (int) createLeaf(node.getLeft_branch());
			Node leaf = new Node(true, classe);
			node.setLeftSon(leaf);
		}
		if (node.getRight_branch().size() > 1) {
			Node tempNode = buildTree(node.getRight_branch());
			node.setRightSon(tempNode);
		}else{
			int classe = (int) createLeaf(node.getRight_branch());
			Node leaf = new Node(true, classe);
			node.setRightSon(leaf);
		}
		return node;
	}

	public boolean isLeaf(ArrayList<DataSetItem> branch) {
		return branch.size() == 0;
	}
	
	private float createLeaf(ArrayList<DataSetItem> branch) {
		Map<Object, Long> counts = branch.stream()
				.collect(Collectors.groupingBy(e -> e.getItem(4), Collectors.counting()));
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

	public int test(ArrayList<DataSetItem> datasetTest) {
		Node node;
		for (DataSetItem item : datasetTest) {
			node = root;
 			while(node.getLeftSon() != null || node.getRightSon() != null) {
				if (item.getItem(node.getColumn()) > node.getAttribute()) {
					node = node.getRightSon();
					if(node.isLeaf()) {
						if(item.getItem(4) == node.getClasse()) {
							acertos++;
						}
						else {
							erros++;
						}
					}
				} else {
					node = node.getLeftSon();
					if(node.isLeaf()) {
						if(item.getItem(4) == node.getClasse()) {
							acertos++;
						}
						else {
							erros++;
						}
					}
				}
			}
		}
		int acuracia = (int) Math.ceil(((float) acertos / datasetTest.size())*100);
		return acuracia;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
}
