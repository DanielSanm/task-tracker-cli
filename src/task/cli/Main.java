package task.cli;

import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		if (args.length != 0) {
			TaskHandler.load();

			Map<String, Runnable> commands = new HashMap<>();

			commands.put(Task.ADD_COMMAND, () -> handleAddCommand(args));
			commands.put(Task.UPDATE_COMMAND, () -> handleUpdateCommand(args));
			commands.put(Task.DELETE_COMMAND, () -> handleDeleteCommand(args));
			commands.put(Task.MARK_IN_PROGRESS_COMMAND, () -> handleMarkInProgressCommand(args));
			commands.put(Task.MARK_DONE_COMMAND, () -> handleMarkDoneCommand(args));
			commands.put(Task.LIST_COMMAND, () -> handleListCommand(args));

			Runnable command = commands.getOrDefault(args[0], () -> System.out.println("Invalid command."));
			command.run();

			TaskHandler.save();
		}
	}

	private static void handleAddCommand(String[] args) {
		if (args.length > 1 && isValidString(args[1])) {
			long id = TaskHandler.getNewId();
			TaskHandler.add(new Task(id, args[1]));
		} else {
			System.out.println("Task description cannot be empty.");
		}
	}

	private static void handleUpdateCommand(String[] args) {
		if (args.length > 2 && isValidTaskId(args[1]) && isValidString(args[2])) {
			TaskHandler.update(Long.parseLong(args[1]), args[2]);
		} else {
			System.out.println("Task id must be a number and description cannot be empty.");
		}
	}

	private static void handleDeleteCommand(String[] args) {
		if (args.length > 1 && isValidTaskId(args[1])) {
			TaskHandler.delete(Long.parseLong(args[1]));
		} else {
			System.out.println("Task id must be a number.");
		}
	}

	private static void handleMarkInProgressCommand(String[] args) {
		if (args.length > 1 && isValidTaskId(args[1])) {
			TaskHandler.markAs(Long.parseLong(args[1]), TaskStatus.IN_PROGRESS);
		} else {
			System.out.println("Task id must be a number.");			
		}
	}

	private static void handleMarkDoneCommand(String[] args) {
		if (args.length > 1 && isValidTaskId(args[1])) {
			TaskHandler.markAs(Long.parseLong(args[1]), TaskStatus.DONE);
		} else {
			System.out.println("Task id must be a number.");			
		}
	}

	private static void handleListCommand(String[] args) {
		if (args.length > 1 && isValidTaskStatus(args[1])) {
			TaskHandler.list(args[1]);
		} else {
			TaskHandler.list();			
		}
	}

	private static boolean isValidString(String str) {
		return str != null && !str.isEmpty() && !str.isBlank();
	}

	private static boolean isValidTaskStatus(String str) {
		return str.equals(TaskStatus.TODO) || str.equals(TaskStatus.IN_PROGRESS) || str.equals(TaskStatus.DONE);
	}

	private static boolean isValidTaskId(String str) {
		if (!isValidString(str))
			return false;

		try {
			Long.parseLong(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
