package model;

import java.util.Arrays;

/**
 * @author karol
 *
 */
public class DataSetItem {


	private float items[];
	
	public float getItem(int pos) {
		return this.items[pos];
	}

	public float[] getItems() {
		return items;
	}
	
	public void setItems(float[] items) {
		this.items = items;
	}
	

	@Override
	public String toString() {
		return  Arrays.toString(items) ;
	}
	
	@Override
	public DataSetItem clone() {
		DataSetItem clone = new DataSetItem();
		clone.setItems(this.getItems());
		return clone;
	}
	
	
	

}