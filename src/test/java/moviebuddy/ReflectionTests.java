package moviebuddy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTests {

    @Test
    void objectCreateAndMethodCall() throws Exception {
        // Reflection 미사용
        Duck duck = new Duck();
        duck.quack();

        // Reflection 사용
        Class<?> duckClass = Class.forName("moviebuddy.ReflectionTests$Duck");
        Object duckObj = duckClass.getDeclaredConstructor().newInstance();
        Method quackMethod = duckObj.getClass().getDeclaredMethod("quack", new Class<?>[0]);
        quackMethod.invoke(duckObj);
    }

    static class Duck {

        void quack() {
            System.out.println("꽥꽥");
        }
        
    }

}
