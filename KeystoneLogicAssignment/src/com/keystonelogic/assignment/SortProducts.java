package com.keystonelogic.assignment;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.json.*;

public class SortProducts {
	HashMap<String, JSONArray> categoryProductsMap = new HashMap<>();

	// Create Product set which has JSONArrays as element
	Set<JSONArray> productSet = new TreeSet<>(new Comparator<JSONArray>() {

		@Override
		public int compare(JSONArray o1, JSONArray o2) {
			int firstArrayTotalCost = 0;
			for (int i = 0; i < o1.length(); i++) {
				firstArrayTotalCost += o1.getJSONObject(i).getInt("price");
			}

			int secondArrayTotalCost = 0;
			for (int i = 0; i < o2.length(); i++) {
				secondArrayTotalCost += o2.getJSONObject(i).getInt("price");
			}
			return firstArrayTotalCost - secondArrayTotalCost;
		}
	});

	public static void main(String[] args) {
		// HashMap<String,JSONArray>

		String inputString = "[{\"productId\": '1234', \"type\": 'Shirt', \"category\": 3, \"price\": 40}, "
				+ "{\"productId\": '2341', \"type\": 'Pants', \"category\": 3a, \"price\": 15}, "
				+ "{\"productId\": '123', \"type\": 'Shoe', \"category\": 1, \"price\": 20}, "
				+ "{\"productId\": '125', \"type\": 'Bag', \"category\": 2, \"price\": 100}, "
				+ "{\"productId\": '126', \"type\": 'Lace', \"category\": 23a, \"price\": 10}, "
				+ "{\"productId\": '127', \"type\": 'WoodenHorse', \"category\": 5, \"price\": 100000}, "
				+ "{\"productId\": '234', \"type\": 'Socks', \"category\": 1a, \"price\": 5}]";

		SortProducts sp = new SortProducts();
		sp.parseJSONData(inputString);
		sp.printProductSetSortedOnCost();
	}

	public void parseJSONData(String inputString) {
		JSONArray inputJSONArray = new JSONArray(inputString);

		System.out.println("JSONArray is " + inputJSONArray);

		// Seggregate the Product details as per the category
		for (int i = 0; i < inputJSONArray.length(); i++) {
			JSONObject jsonObject = inputJSONArray.getJSONObject(i);
			try {

				String categoryString = (String) String.valueOf(jsonObject.get("category"));

				String[] splitStirng = categoryString.split("[a-b]");
				// System.out.println("Spring STring is "+Arrays.toString(splitStirng));

				String mapKey = splitStirng[0];

				if (categoryProductsMap.get(mapKey) != null) {
					JSONArray productArray = categoryProductsMap.get(mapKey);
					productArray.put(jsonObject);
					categoryProductsMap.put(mapKey, productArray);
				} else {
					JSONArray productArray = new JSONArray();
					productArray.put(jsonObject);
					categoryProductsMap.put(mapKey, productArray);
				}
			} catch (Exception e) {
				System.err.println("Error parsing data ["+jsonObject.toString()+"] .. moving onto next one..");
			}

		}

		Set<String> keySet = categoryProductsMap.keySet();
		Iterator<String> keySetIter = keySet.iterator();

		// Insert individual product JSONArrays into the product set
		while (keySetIter.hasNext()) {
			JSONArray jsonArray = categoryProductsMap.get(keySetIter.next());
			productSet.add(jsonArray);
		}

	}

	public void printProductSetSortedOnCost() {
		System.out.println("ProductSet: ");
		System.out.println(productSet);
	}

}
