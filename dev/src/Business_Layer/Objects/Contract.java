package Business_Layer.Objects;

import java.util.*;

public class Contract {
	private List<String> Conditions;
	private Map<Item, Float> itemVsPrice;
	
	public Contract (List<String> Conditions, Map<Item, Float> itemVsPrice ) {
		this.Conditions = new LinkedList<String>();
		this.itemVsPrice = new HashMap<Item, Float>();
		this.Conditions.addAll(Conditions);
		this.itemVsPrice.putAll(itemVsPrice);
		
	}
	
}
