package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoResponse {

	private Long id;
	private String title;
	private Long order;
	private Boolean completed;

	private final static String BASE_URL = "http://localhost:8080/";

	private String url;

	public ToDoResponse(ToDoEntity toDoEntity){
		this.id = toDoEntity.getId();
		this.title = toDoEntity.getTitle();
		this.order = toDoEntity.getOrder();
		this.completed = toDoEntity.getCompleted();
		this.url = BASE_URL + this.id;
	}
}
