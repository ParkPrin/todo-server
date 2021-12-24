package org.example.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.example.model.ToDoEntity;
import org.example.model.ToDoRequest;
import org.example.model.ToDoResponse;
import org.example.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin // CORS 이슈방지
@AllArgsConstructor
@RestController
@RequestMapping("/")
public class TodoController {

	private final TodoService service;

	@PostMapping
	public ResponseEntity<ToDoResponse> create(@RequestBody ToDoRequest request) {
		System.out.println("CREATE");
		if (Objects.isNull(request.getTitle())) {
			return ResponseEntity.badRequest().build();
		}

		if (Objects.isNull(request.getOrder())) {
			request.setOrder(0L);
		}

		if (Objects.isNull(request.getCompleted())){
			request.setCompleted(false);
		}

		ToDoEntity result = this.service.add(request);
		return ResponseEntity.ok(new ToDoResponse(result));
	}

	@GetMapping("{id}")
	public ResponseEntity<ToDoResponse> readOne(@PathVariable Long id){
		System.out.println("READ ONE");
		return Objects.isNull(id)
				? ResponseEntity.badRequest().build()
				: ResponseEntity.ok(new ToDoResponse(service.searchById(id)));
	}

	@GetMapping
	public ResponseEntity<List<ToDoResponse>> readAll() {
		System.out.println("READ ALL");

		return ResponseEntity.ok(
				service.searchAll()
				.stream()
				.map(toDoEntity -> new ToDoResponse(toDoEntity))
				.collect(Collectors.toList())
		);
	}

	@PatchMapping("{id}")
	public ResponseEntity<ToDoResponse> update(@PathVariable Long id, @RequestBody ToDoRequest todoRequest){
		System.out.println("UPDATE");
		return ResponseEntity.ok(
				new ToDoResponse(
						service.updateById(id, todoRequest)
				)
		);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteOne(@PathVariable Long id){
		System.out.println("DELETE");
		if (Objects.isNull(id)) {
			return ResponseEntity.badRequest().build();
		}
		service.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<?> deleteAll(){
		System.out.println("DELETE ALL");
		service.deleteAll();
		return ResponseEntity.ok().build();
	}

}
