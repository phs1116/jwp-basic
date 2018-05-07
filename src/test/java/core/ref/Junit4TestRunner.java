package core.ref;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Junit4Test junit4Test =  clazz.newInstance();
        Arrays.stream(clazz.getMethods()).filter(method -> method.isAnnotationPresent(MyTest.class))
            .forEach(method -> invokeMethod(junit4Test, method));

    }
    private void invokeMethod(Junit4Test junit4Test, Method method) {
        try {
            method.invoke(junit4Test);
        } catch (Exception e){ }
    }
}
