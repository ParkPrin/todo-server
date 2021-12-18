package org.example.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.example.model.ToDoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToDoRepositoryTest {

	@Autowired
	ToDoRepository toDoRepository;

	@Test
	public void ToDoList의_데이터를_생성한다(){
		ToDoEntity toDoEntity = ToDoEntity.builder()
				.title("안녕 Todo")
				.order(1L)
				.completed(false)
				.build();
		ToDoEntity toDoEntitySaveResult = toDoRepository.save(toDoEntity);
		assertEquals(toDoEntity, toDoEntitySaveResult);
	}

}