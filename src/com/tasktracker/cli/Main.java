package com.tasktracker.cli;

public class Main {

	public static void main(String[] args) {
		if (args.length != 0) {
			switch (args[0]) {
			case Task.ADD_COMMAND:
				long id = TaskHandler.getNewId();
				TaskHandler.add(new Task(id, args[1]));
				break;
			}
		}
	}

}
