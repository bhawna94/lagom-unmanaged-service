package edu.knoldus.external.impl.test;

import edu.knoldus.external.api.Information;
import edu.knoldus.external.impl.HelloServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class HelloServiceimplTest {

    private static MockExternalService mockExternalService = new MockExternalService();
    private static HelloServiceImpl helloService ;
    //= new HelloServiceImpl(mockExternalService);

    @BeforeClass
    public static void initializer() {
        helloService = new HelloServiceImpl(mockExternalService);
    }

    @Test
    public void callGetMethod() throws Exception {
        Information info = helloService.getInformation().invoke()
                .toCompletableFuture()
                .get(5, TimeUnit.SECONDS);


        assertEquals(Information.builder()
                .userId(1)
                .id(1)
                .body("DEF").title("ABC").build(),info);
    }
}
