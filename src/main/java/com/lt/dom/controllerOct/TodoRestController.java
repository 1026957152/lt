package com.lt.dom.controllerOct;

import com.lt.dom.domain.CouponTemplatePojo;
import com.lt.dom.oct.Invitation;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.Todo;
import com.lt.dom.repository.InvitationsRepository;
import com.lt.dom.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class TodoRestController {




    @Autowired
    private TodoRepository todoRepository;

    @GetMapping(produces = "application/json")
    public List<Todo> listTodos(@PathVariable String SUPPLIER_ID) {
        List<Todo> todos = todoRepository.findAll();
        return todos;
    }


    @PostMapping(value = "/{TODO_ID}/mark_as_done", produces = "application/json")
    public Todo 接受邀请(@PathVariable long TODO_ID, @RequestBody CouponTemplatePojo  pojo) {

        Example<Invitation> example = Example.of(Invitation.from("Fred", "Bloggs", null));
        //Optional<Passenger> actual = repository.findOne(example);
        Optional<Todo> optionalTodo = todoRepository.findById(TODO_ID);

        if(optionalTodo.isPresent()) {
            return optionalTodo.get();
        }else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }



    }


    @PostMapping(value = "/mark_as_done", produces = "application/json")
    public List<Todo> 接受邀请( @RequestBody CouponTemplatePojo  pojo) {


        List<Todo> todos = todoRepository.findAll();
        if(todos.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }
        return todos;



    }
}