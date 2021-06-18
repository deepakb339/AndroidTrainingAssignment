package org.nagarro.todolist.network;

import org.nagarro.todolist.model.Todos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/todos")
    Call<List<Todos>> getAllTodos();
}
