package task.cli;

public class Main {

	public static void main(String[] args) {
		if (args.length != 0) {
			TaskHandler.load();
			
			switch (args[0]) {
			case Task.ADD_COMMAND:
				if (args.length > 1 && isValidString(args[1])) {
					long id = TaskHandler.getNewId();
					TaskHandler.add(new Task(id, args[1]));
					break;
				}
				System.out.println("Task description cannot be empty.");
				break;
				
			case Task.UPDATE_COMMAND:
				if (args.length > 2 && isNumber(args[1]) && isValidString(args[2])) {
					TaskHandler.update(Long.parseLong(args[1]), args[2]);
					break;
				}
				System.out.println("Task id must be a number and description cannot be empty.");
				break;
				
			case Task.DELETE_COMMAND:
				if (args.length > 1 && isNumber(args[1])) {
					TaskHandler.delete(Long.parseLong(args[1]));
					break;
				}
				System.out.println("Task id must be a number.");
				break;
				
			case Task.MARK_IN_PROGRESS_COMMAND:
				if (args.length > 1 && isNumber(args[1])) {
					TaskHandler.markAs(Long.parseLong(args[1]), TaskStatus.IN_PROGRESS);
					break;
				}
				System.out.println("Task id must be a number.");
				break;
				
			case Task.MARK_DONE_COMMAND:
				if (args.length > 1 && isNumber(args[1])) {
					TaskHandler.markAs(Long.parseLong(args[1]), TaskStatus.DONE);
					break;
				}
				System.out.println("Task id must be a number.");
				break;
				
			case Task.LIST_COMMAND:
				if (args.length > 1 && isValidStatus(args[1])) {
					TaskHandler.list(args[1]);
					break;
				}
				TaskHandler.list();
				break;
			}
			
			TaskHandler.save();
		}
	}
	
	private static boolean isValidStatus(String str) {
		return str.equals(TaskStatus.TODO) || str.equals(TaskStatus.IN_PROGRESS) || str.equals(TaskStatus.DONE);
	}
	
	private static boolean isValidString(String str) {
		return !str.isEmpty() || !str.isBlank();
	}
	
	private static boolean isNumber(String str) {
		if (!isValidString(str)) return false;
		
		try {
			Long.parseLong(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
