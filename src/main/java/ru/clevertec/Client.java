package ru.clevertec;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Client {

    private int requestQuantity;

    private List<Entity> requestList = new ArrayList<>();
    private List<Integer> responseList = new ArrayList<>();
    private Lock lock = new ReentrantLock();

    public Client() {
        requestQuantity = 10;
        initializeRequestList();
    }

    public Client(int requestQuantity) {
        this.requestQuantity = requestQuantity;
        initializeRequestList();
    }

    public void addResponse(Integer response) {
        lock.lock();
        responseList.add(response);
        lock.unlock();
    }

    private void initializeRequestList() {

        IntStream.rangeClosed(1, requestQuantity).mapToObj(i -> new Entity())
                            .forEach(entity -> requestList.add(entity));
    }

    public List<Entity> getRequestList() {
        return requestList;
    }

    public List<Integer> getResponseList() {
        return responseList;
    }
}
