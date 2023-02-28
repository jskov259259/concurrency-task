package ru.clevertec;

import java.util.concurrent.Callable;

public class Request implements Callable<Integer> {

    private Entity entity;

    public Request(Entity entity) {
        this.entity = entity;
    }

    @Override
    public Integer call() throws Exception {

        Integer field = entity.getField();
        Integer request = Server.function(field);
        return request;
    }
}
