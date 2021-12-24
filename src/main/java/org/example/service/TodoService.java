package org.example.service;

import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.example.model.ToDoEntity;
import org.example.model.ToDoRequest;
import org.example.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class TodoService {

	private final ToDoRepository toDoRepository;

	/**
	 * 1	todo 리스트 목록에 아이템을 추가
	 * 2	todo  리스트 목록 중 특정 아이템을 조회
	 * 3	todo 리스트 전체 목록을 조회
	 * 4	todo 리스트 목록 중 특정 아이템을 수정
	 * 5	todo 리스트 목록 중 특정 아이템을 삭제
	 * 6	todo 리스트 전체 목록을 삭제
	 */

	public ToDoEntity add(ToDoRequest request){
		ToDoEntity toDoEntity = new ToDoEntity();
		toDoEntity.setTitle(request.getTitle());
		toDoEntity.setOrder(request.getOrder());
		toDoEntity.setCompleted(request.getCompleted());
		return toDoRepository.save(toDoEntity);
	}

	public ToDoEntity searchById(Long id){
		return toDoRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

	}

	public List<ToDoEntity> searchAll(){
		return toDoRepository.findAll();
	}

	public  ToDoEntity updateById(Long id, ToDoRequest request){
		ToDoEntity toDoEntity = searchById(id);
		if (Objects.nonNull(toDoEntity.getTitle())){
			toDoEntity.setTitle(request.getTitle());
		}
		if (Objects.nonNull(toDoEntity.getOrder())){
			toDoEntity.setOrder(request.getOrder());
		}
		if (Objects.nonNull(toDoEntity.getCompleted())){
			toDoEntity.setCompleted(request.getCompleted());
		}
		System.out.println(toDoEntity);
		return this.toDoRepository.save(toDoEntity);
	}

	public void deleteById(Long id){
		toDoRepository.deleteById(id);
	}

	public void deleteAll(){
		toDoRepository.deleteAll();
	}
}
