package com.keystonelogic.assignment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class WordCounter {
	List<String> uniqueWordList = new LinkedList<String>();
	List<String> duplicateWordsList = new LinkedList<String>();

	public static void main(String[] args) {
		List<String> lines = new LinkedList<String>();
		lines.add("This is fun!! This is good");
		lines.add("A beautiful sunday morning..");

		WordCounter wc = new WordCounter();

		wc.countWordsForBook(lines);
		wc.printData();

	}

	public void countWordsForBook(List<String> lines) {
		for (String line : lines) {
			countWordsForLine(line);
		}
	}

	public void printData() {
		System.out.println("Unique Words are " + uniqueWordList);

		System.out.println("Duplicate Words are " + duplicateWordsList);
	}

	public void countWordsForLine(String line) {
		String[] words = line.split(" ");

		HashMap<String, Integer> wordCountMap = new HashMap<>();

		for (int i = 0; i < words.length; i++) {
			Integer wordCount = 0;
			if ((wordCount = wordCountMap.get(words[i])) != null) {
				wordCountMap.put(words[i], wordCount + 1);
			} else {
				wordCountMap.put(words[i], 1);
			}
		}

		uniqueWordList.addAll(wordCountMap.keySet().stream().collect(Collectors.toList()));

		duplicateWordsList.addAll(wordCountMap.entrySet().stream().filter((e) -> e.getValue() > 1).map(e -> e.getKey())
				.collect(Collectors.toList()));
	}

}
