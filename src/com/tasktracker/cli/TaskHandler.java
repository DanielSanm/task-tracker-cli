package com.tasktracker.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskHandler {
	
	private static final String TASKS_JSON_FILE_NAME = "tasks.json";

	public static void add(Task newTask) {
		
		File file = new File(TASKS_JSON_FILE_NAME);

		if (!file.exists()) {
			try {
				if (!file.createNewFile()) {
					System.out.println("Failed to create file.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		StringBuilder jsonContent = readJSONFile();

		String newContent = "{\"id\": \"" + newTask.getId() + "\"," + " \"description\": \""
				+ newTask.getDescription() + "\"," + " \"status\": \"" + newTask.getStatus().toString().toLowerCase()
				+ "\"," + " \"createdAt\": \"" + newTask.getCreatedAt() + "\"," + " \"updatedAt\": \""
				+ newTask.getUpdatedAt() + "\"" + "}";
		
		if (jsonContent.length() < 1) {
			jsonContent.append("[" + newContent + "]");
		} else {
			jsonContent = new StringBuilder(jsonContent.toString().replaceAll("\\s+", " "));	// removes extra space inserted (if any) in formatting
			int arrayEndIndex = jsonContent.lastIndexOf("]");
			jsonContent.insert(arrayEndIndex, ",\n" + newContent);
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(TASKS_JSON_FILE_NAME))) {
			writer.write(jsonContent.toString());
			System.out.println("JSON file update succesful!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static StringBuilder readJSONFile() {
		StringBuilder content = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(TASKS_JSON_FILE_NAME))) {
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return content;
	}
	
	public static long getNewId() {
		String textContent = readJSONFile().toString().replaceAll("\\s+", "");
		
		int idIndex = textContent.lastIndexOf("id");
		int descIndex = textContent.lastIndexOf("description");
		
		if (!textContent.isEmpty() && idIndex != -1 && descIndex != -1) {
			String rowId = textContent.substring(idIndex, descIndex);
			Matcher matcher = Pattern.compile("\\d+").matcher(rowId);
			
			if (matcher.find()) {
				return Long.parseLong(matcher.group()) + 1; 
			}
		}
		
		return 1L;
	}
}
