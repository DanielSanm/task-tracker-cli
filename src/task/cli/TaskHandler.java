package task.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class TaskHandler {

	private static final String TASKS_JSON_FILE_PATH = System.getProperty("user.dir") + File.separator + "tasks.json";

	private static final Map<Long, Task> tasks = new LinkedHashMap<>();

	public static void load() {

		File file = new File(TASKS_JSON_FILE_PATH);

		if (!file.exists()) {
			try {
				if (!file.createNewFile()) {
					System.out.println("Failed to create file.");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}

		StringBuilder content = readJSONFile();

		while (true) {
			int startIndex = content.indexOf("{");
			int idIndex = content.indexOf("\"id\":");
			int descIndex = content.indexOf("\"description\":");
			int statusIndex = content.indexOf("\"status\":");
			int createdIndex = content.indexOf("\"createdAt\":");
			int updatedIndex = content.indexOf("\"updatedAt\":");
			int endIndex = content.indexOf("}");

			if (startIndex == -1 || idIndex == -1 || descIndex == -1 || statusIndex == -1 || createdIndex == -1
					|| updatedIndex == -1 || endIndex == -1)
				return;

			BiFunction<Integer, Integer, String> formatValue = (startStr, endStr) -> content.substring(startStr, endStr)
					.replaceAll("[\",]", "").trim();

			long idValue = Long.parseLong(formatValue.apply(idIndex + 5, descIndex));
			String descValue = formatValue.apply(descIndex + 14, statusIndex);
			String statusValue = formatValue.apply(statusIndex + 9, createdIndex);
			LocalDateTime createdValue = LocalDateTime.parse(formatValue.apply(createdIndex + 12, updatedIndex));
			LocalDateTime updatedValue = LocalDateTime.parse(formatValue.apply(updatedIndex + 12, endIndex));

			Task task = new Task(idValue, descValue, statusValue, createdValue, updatedValue);

			tasks.put(idValue, task);

			content.delete(startIndex, endIndex + 1);
		}
	}

	public static long getNewId() {
		return !tasks.isEmpty() ? Collections.max(tasks.keySet()) + 1 : 1;
	}

	public static void add(Task newTask) {
		tasks.put(newTask.getId(), newTask);
		System.out.println("Task added successfully (ID: " + newTask.getId() + ")");
	}

	public static void update(long idTask, String newDesc) {
		Task task = tasks.get(idTask);

		if (task == null) {
			System.out.println("The index cannot be found.");
			return;
		}

		task.setDescription(newDesc);
		tasks.put(idTask, task);
		System.out.println("Task updated successfully (ID: " + idTask + ")");
	}

	public static void delete(long idTask) {
		if (tasks.remove(idTask) == null) {
			System.out.println("The index cannot be found.");
			return;
		}
		
		System.out.println("Task deleted (ID: " + idTask + ")");
	}

	public static void markAs(long idTask, String status) {
		Task task = tasks.get(idTask);

		if (task == null) {
			System.out.println("The index cannot be found.");
			return;
		}

		task.setStatus(status);
		tasks.put(idTask, task);
		System.out.println("Task marked with \"" + status + "\" (ID: " + idTask + ")");
	}

	public static void list() {
		tasks.entrySet().forEach(System.out::println);
	}

	public static void list(String filter) {
		tasks.entrySet().stream().filter(task -> task.getValue().getStatus().equals(filter))
				.forEach(System.out::println);
	}

	public static void save() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(TASKS_JSON_FILE_PATH))) {
			StringBuilder content = new StringBuilder();
			content.append("[\n");
			for (Map.Entry<Long, Task> task : tasks.entrySet()) {
				Task taskObj = task.getValue();
				String newContent = """
							{
						  		"id": "%d",
						  		"description": "%s",
						  		"status": "%s",
						  		"createdAt": "%s",
						  		"updatedAt": "%s"
							},
						""".formatted(taskObj.getId(), taskObj.getDescription(), taskObj.getStatus(),
						taskObj.getCreatedAt(), taskObj.getUpdatedAt());

				content.append(newContent);
			}

			int lastComma = content.lastIndexOf(",");
			if (lastComma != -1)
				content.delete(lastComma, lastComma + 1);

			content.append("]");

			writer.write(content.toString());
			System.out.println("JSON file saved succesful!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static StringBuilder readJSONFile() {
		StringBuilder content = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(TASKS_JSON_FILE_PATH))) {
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

}
