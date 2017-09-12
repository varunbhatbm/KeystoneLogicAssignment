package com.keystonelogic.assignment;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

class UserData {
	Date timestamp;
	String name;
	Set<String> tags;
	Long value;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public UserData(Date timestamp, String name, Set<String> tags, Long value) {
		super();
		this.timestamp = timestamp;
		this.name = name;
		this.tags = tags;
		this.value = value;
	}

	@Override
	public String toString() {
		return "{Name:" + name + " Time:" + timestamp + " Tags: " + tags + " Value: " + value + "}";
	}

}

public class MaxValueFinder {
	HashMap<String, Long> maxValuePerUser = new HashMap<>();

	public static void main(String[] args) {
		List<UserData> userDataList = new LinkedList<UserData>();
		Set<String> tags = new HashSet();
		tags.add("Movie");
		tags.add("Cricket");
		tags.add("Cycling");
		userDataList.add(new UserData(new Date(System.currentTimeMillis()-2000), "Suraj", tags, 200L));
		tags.clear();

		tags.add("Meditation");
		tags.add("Yoga");
		tags.add("Dancing");
		userDataList.add(new UserData(new Date(System.currentTimeMillis()-4000), "Vyas", tags, 300L));

		tags.add("Jumping");
		tags.add("Parkour");
		tags.add("Programming");
		userDataList.add(new UserData(new Date(), "Mithesh", tags, 400L));

		System.out.println("User Data is " + userDataList);

		MaxValueFinder maxValueFinder = new MaxValueFinder();
		
		//Here enter the timeDealyForSearch value . example: 5 
		maxValueFinder.getUserTrendsWithTags(userDataList,5,null);
		maxValueFinder.printData();
	}

	public void getUserTrends(List<UserData> userDatList, Integer timeDelayToSearchInSeconds) {

		userDatList.stream().filter((u) -> {
			long currentTime = System.currentTimeMillis();
			long userTime = u.getTimestamp().getTime();
			return (currentTime - userTime) < (timeDelayToSearchInSeconds*1000);
		}).forEach((u) -> {
			Long userValue = u.getValue();
			Long maxValue = maxValuePerUser.get(u.getName());
			if (maxValue == null) {
				maxValuePerUser.put(u.getName(), u.getValue());
			} else if (maxValue < u.getValue()) {
				maxValuePerUser.put(u.getName(), u.getValue());
			}
		});

	}

	public void getUserTrendsWithTags(List<UserData> userDatList, Integer timeDelayToSearchInSeconds, Set<String> searchTags) {

		if (searchTags == null) {
			getUserTrends(userDatList, timeDelayToSearchInSeconds);
		} else {
			userDatList.stream().filter((u) -> {
				long currentTime = System.currentTimeMillis();
				long userTime = u.getTimestamp().getTime();
				return (currentTime - userTime) < (timeDelayToSearchInSeconds*1000);
			}).filter((u) -> {
				return u.getTags().containsAll(searchTags);
			}).forEach((u) -> {
				Long userValue = u.getValue();
				Long maxValue = maxValuePerUser.get(u.getName());
				if (maxValue == null) {
					maxValuePerUser.put(u.getName(), u.getValue());
				} else if (maxValue < u.getValue()) {
					maxValuePerUser.put(u.getName(), u.getValue());
				}
			});
		}
	}

	public void printData() {
		System.out.println("Max Values of users is " + maxValuePerUser);
	}

}
