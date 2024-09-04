package com.tasktracker.cli;

import java.time.LocalDateTime;

public class Task {
	
	public static final String ADD_COMMAND = "add";

	private long id;
	private String description;
	private TaskStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Task(long id, String description) {
		this.id = id;
		this.description = description;		
		status = TaskStatus.TODO;
		createdAt = LocalDateTime.now();
		updatedAt = createdAt;
	}

	public long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskStatus getStatus() {
		return status;
	}
	
	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
