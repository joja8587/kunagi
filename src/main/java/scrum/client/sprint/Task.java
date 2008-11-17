package scrum.client.sprint;

import java.util.Map;

import scrum.client.admin.User;
import scrum.client.project.BacklogItem;

public class Task extends GTask {

	public static final int INIT_EFFORT = 1;

	private User owner;

	// Das TaskListWidget, in dem der Task liegt
	private TaskListWidget taskListWidget = null;

	public Task(BacklogItem backlogItem) {
		setBacklogItem(backlogItem);
		setLabel("New Task");
		setEffort(INIT_EFFORT);
	}

	public Task(Map data) {
		super(data);
	}

	public void setDone() {
		setEffort(0);
	}

	public boolean isDone() {
		return getEffort() == null || getEffort() == 0;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return getLabel();
	}

	// kleiner Workaround, um an das TaskListWidget zu kommen
	public TaskListWidget getTaskListWidget() {
		return taskListWidget;
	}

	public void setTaskListWidget(TaskListWidget taskListWidget) {
		this.taskListWidget = taskListWidget;
	}

}