package model;

import java.util.List;

/**
 * @author karol
 *
 */
public class Node {
	private List<Float[]> left_branch;
	private List<Float[]> right_branch;
	private Node leftSon;
	private Node rightSon;
	private float attribute;
	private int column;
	private boolean isLeaf = false;
	int classe = -1; 
	
	public Node(boolean isLeaf, int classe) {
		this.isLeaf = isLeaf;
		this.classe = classe;
	}
	

	public Node(List<Float[]> left_branch, List<Float[]> right_branch, float attribute, int column) {
		super();
		this.left_branch = left_branch;
		this.right_branch = right_branch;
		this.attribute = attribute;
		this.column = column;
	}

	public float getAttribute() {
		return attribute;
	}
	public void setAttribute(float attribute) {
		this.attribute = attribute;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getClasse() {
		return classe;
	}
	public void setClasse(int classe) {
		this.classe = classe;
	}
	public List<Float[]> getLeft_branch() {
 		return left_branch;
	}
	public void setLeft_branch(List<Float[]> left_branch) {
		this.left_branch = left_branch;
	}
	public List<Float[]> getRight_branch() {
		return right_branch;
	}
	public void setRight_branch(List<Float[]> right_branch) {
		this.right_branch = right_branch;
	}
	@Override
	public String toString() {
		return "Left:" + left_branch.size() + " Right:=" + right_branch.size() + 
				"\n  Attribute:" + attribute + ", Column=" + column;
	}
	public Node getLeftSon() {
		return leftSon;
	}
	public void setLeftSon(Node leftSon) {
		this.leftSon = leftSon;
	}
	public Node getRightSon() {
		return rightSon;
	}
	public void setRightSon(Node rightSon) {
		this.rightSon = rightSon;
	}
	
	public String getText() {
		int tamEsquerda = 0;
		int tamDireita = 0;
		if(this.left_branch.size() > 0) {
			tamEsquerda = this.left_branch.size();
		}

		if(this.right_branch.size() > 0) {
			tamEsquerda = this.right_branch.size();
		}

		return tamEsquerda + tamDireita + "";
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
}
