package ru.clevertec;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Client {

    private List<Entity> requestList = new ArrayList<>();

    private List<Integer> responseList = new ArrayList<>();

    public void addResponse(Integer response) {
        responseList.add(response);
    }

    public static void main(String[] args) {

        Client client = new Client();
        client.initializeRequestList();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Entity> requestList = client.getRequestList();
        requestList.stream().forEach(entity -> {
            Future<Integer> future = executorService.submit(new Request(entity));
            try {
                client.addResponse(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        client.responseList.stream().forEach(System.out::println);
        System.out.println(client.responseList.size());
    }

    private void initializeRequestList() {
        for (int i=0; i< 100; i++) requestList.add(new Entity());
    }

    public List<Entity> getRequestList() {
        return requestList;
    }

}
