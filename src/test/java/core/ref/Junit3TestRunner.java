package core.ref;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Junit3Test junit3Test = clazz.newInstance();
        Arrays.stream(clazz.getMethods())
            .filter(method -> method.getName().startsWith("test"))
            .forEach(method -> invokeMethod(junit3Test, method));
    }

    private void invokeMethod(Junit3Test junit3Test, Method method) {
        try {
            method.invoke(junit3Test);
        } catch (Exception e){ }
    }
}
