package task.cli;

import java.time.LocalDateTime;

public class Task {
	
	public static final String ADD_COMMAND = "add";
	public static final String UPDATE_COMMAND = "update";
	public static final String DELETE_COMMAND = "delete";
	public static final String MARK_IN_PROGRESS_COMMAND = "mark-in-progress";
	public static final String MARK_DONE_COMMAND = "mark-done";
	public static final String LIST_COMMAND = "list";

	private long id;
	private String description;
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Task(long id, String description) {
		this.id = id;
		this.description = description;		
		status = TaskStatus.TODO;
		createdAt = LocalDateTime.now();
		updatedAt = createdAt;
	}

	public Task(long id, String description, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.description = description;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return """
				{
				    "id": "%d",
				    "description": "%s",
				    "status": "%s",
				    "createdAt": "%s",
				    "updatedAt": "%s"
				},
				""".formatted(id, description, status, createdAt, updatedAt);
	}

}
