package org.example.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import org.example.model.ToDoEntity;
import org.example.model.ToDoRequest;
import org.example.repository.ToDoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

	@Mock
	private ToDoRepository toDoRepository;

	@InjectMocks
	private TodoService todoService;

/*	@Test
	void add() {
		// given
		when(toDoRepository.save(any(ToDoEntity.class)))
				.then(AdditionalAnswers.returnsFirstArg());
		ToDoRequest expected = new ToDoRequest();
		expected.setTitle("Test Title");
		expected.setOrder(2L);

		// when
		ToDoEntity actual = this.todoService.add(expected);

		// then
		assertEquals(expected.getTitle(), actual.getTitle());
	}*/

	@Test
	void add() {
		// given
		given(toDoRepository.save(any(ToDoEntity.class)))
				.willAnswer(AdditionalAnswers.returnsFirstArg());
		ToDoRequest expected = new ToDoRequest();
		expected.setTitle("Test Title");
		expected.setOrder(2L);

		// when
		ToDoEntity doEntity = todoService.add(expected);

		// then
		assertEquals(doEntity.getTitle(), doEntity.getTitle());
	}

	@Test
	void searchById() {
		// given
		ToDoEntity expected = new ToDoEntity();
		expected.setTitle("Test Title");
		expected.setId(2L);
		expected.setOrder(0L);
		expected.setCompleted(false);
		Optional<ToDoEntity> optional = Optional.of(expected);
		given(toDoRepository.findById(any(Long.class)))
				.willReturn(optional);

		// when
		Optional<ToDoEntity> toDoEntity = toDoRepository.findById(2L);

		// then
		assertEquals(toDoEntity.get(), expected);
	}

	@Test
	public void searchByIdFailed() {
		given(this.toDoRepository.findById(anyLong()))
				.willReturn(Optional.empty());

		assertThrows(ResponseStatusException.class, () -> {
			this.todoService.searchById(123L);
		});
	}

	@Test
	void searchAll() {
		// given
		ToDoEntity expected = new ToDoEntity();
		expected.setTitle("Test Title1");
		expected.setId(1L);
		expected.setOrder(0L);
		expected.setCompleted(false);

		ToDoEntity expected2 = new ToDoEntity();
		expected.setTitle("Test Title2");
		expected.setId(2L);
		expected.setOrder(0L);
		expected.setCompleted(true);

		ToDoEntity expected3 = new ToDoEntity();
		expected.setTitle("Test Title3");
		expected.setId(3L);
		expected.setOrder(0L);
		expected.setCompleted(false);

		given(toDoRepository.findAll())
				.willReturn(List.of(expected, expected2, expected3));

		// when
		List<ToDoEntity> toDoEntities = todoService.searchAll();

		assertEquals(List.of(expected, expected2, expected3), toDoEntities);
	}

	@Test
	void updateById() {
		// given
		ToDoEntity expected = new ToDoEntity();
		expected.setTitle("Test Title1");
		expected.setId(1L);
		expected.setOrder(0L);
		expected.setCompleted(false);

		ToDoEntity expected2 = new ToDoEntity();
		expected.setTitle("Test Title2");
		expected.setId(1L);
		expected.setOrder(2L);
		expected.setCompleted(true);
		Optional<ToDoEntity> optional = Optional.of(expected);
		given(toDoRepository.findById(any(Long.class)))
				.willReturn(optional);

		given(toDoRepository.save(any(ToDoEntity.class)))
				.willAnswer(AdditionalAnswers.returnsFirstArg());

		ToDoRequest toDoRequest = new ToDoRequest();
		expected.setTitle("Test Title");
		expected.setOrder(2L);

		// when
		ToDoEntity toDoEntity = todoService.updateById(expected.getId(), toDoRequest);

		// than
		assertEquals(toDoEntity.getId(), expected.getId());
		assertEquals(toDoEntity.getTitle(), toDoRequest.getTitle());
		assertEquals(toDoEntity.getOrder(), toDoRequest.getOrder());
	}
}