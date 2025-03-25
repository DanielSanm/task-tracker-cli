# Task Tracker CLI

Task Tracker is a command line interface for task management, allowing you to create lists with several tasks with different statuses. You can add, update and delete them as needed.

## Installation

1. In the directory of your choice clone or download this repository: 
```powershell
git clone https://github.com/DanielSanm/task-tracker-cli.git
```

2. Navigate to the project directory and compile:
```powershell
cd task-tracker-cli
javac -d bin src\*.java
```

## Usage

```powershell
# Adding a new task
java task.cli.Main add "Buy groceries"

# Updating and deleting tasks
java task.cli.Main update 1 "Buy groceries and cook dinner"
java task.cli.Main delete 1

# Marking a task as in progress or done
java task.cli.Main mark-in-progress 1
java task.cli.Main mark-done 1

# Listing all tasks
java task.cli.Main list

# Listing tasks by status
java task.cli.Main list done
java task.cli.Main list todo
java task.cli.Main list in-progress
```