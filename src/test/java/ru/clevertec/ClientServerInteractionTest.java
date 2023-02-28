package ru.clevertec;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.*;

class ClientServerInteractionTest {

    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000, 10000, 100000})
    void checkClientRequestNumberShouldEqualsToResponseListSize(int requestNumber) {

        Client client = new Client(requestNumber);
        executeClientServerInteractionWithConcurrency(client);
        assertEquals(requestNumber, client.getResponseList().size());
    }

    private void executeClientServerInteractionWithConcurrency(Client client) {

        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        List<Entity> requestList = client.getRequestList();

        requestList.stream().forEach(entity -> {
            CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() ->
                    Server.function(entity.getField()), executorService);

            completableFuture.thenAccept(result -> client.addResponse(result));
        });

        while(executorService.getActiveCount()!=0)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}