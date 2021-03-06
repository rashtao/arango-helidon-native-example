
package io.helidon.examples.quickstart.mp;

import io.helidon.microprofile.server.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.spi.CDI;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

class MainTest {
    private static Server server;

    @BeforeAll
    public static void startTheServer() throws Exception {
        server = Server.create().start();
    }

    @Test
    void testVersionEndpoint() {
        Client client = ClientBuilder.newClient();

        JsonObject jsonVersion = client
                .target(getConnectionString("/version"))
                .request()
                .get(JsonObject.class);
        Assertions.assertEquals("arango", jsonVersion.getString("server"));
    }

    @AfterAll
    static void destroyClass() {
        CDI<Object> current = CDI.current();
        ((SeContainer) current).close();
    }

    private String getConnectionString(String path) {
        return "http://localhost:" + server.port() + path;
    }
}
