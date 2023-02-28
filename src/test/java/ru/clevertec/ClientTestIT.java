package ru.clevertec;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientTestIT {

    @Test
    void checkServerShouldReturnExpectedValue() {

        Client client = new Client(1);
        Entity entity = client.getRequestList().get(0);
        client.addResponse(Server.function(entity.getField()));
        assertEquals(90, client.getResponseList().get(0));
    }

    @Test
    void checkServerShouldReturnExpectedResponseQuantity() {

        Client client = new Client(5);
        List<Entity> requestList = client.getRequestList();
        requestList.stream().forEach(entity -> {
            client.addResponse(Server.function(entity.getField()));
        });
        assertEquals(5, client.getResponseList().size());
    }
}