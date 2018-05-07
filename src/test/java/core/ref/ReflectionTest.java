package core.ref;

import next.model.Question;
import next.model.User;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        Arrays.stream(clazz.getFields()).forEach(field -> logger.debug(field.getName()));
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> logger.debug(field.getName()));

        Arrays.stream(clazz.getMethods()).forEach(method -> logger.debug(method.getName()));
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> logger.debug(method.getName()));

        Arrays.stream(clazz.getConstructors()).forEach(constructor -> logger.debug(constructor.getName()));
        Arrays.stream(clazz.getDeclaredConstructors()).forEach(constructor -> logger.debug(constructor.getName()));


    }

    @Test
    public void newInstanceWithConstructorArgs() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        Arrays.stream(clazz.getConstructors()).map(constructor -> {
            try {
                return constructor.newInstance("12","34", "56", "78");
            } catch (Exception e){
            	return null;
			}
        }).forEach(o -> {
        	User user = (User)o;
        	logger.debug(user.getUserId());
			logger.debug(user.getPassword());
			logger.debug(user.getEmail());
			logger.debug(user.getName());
		});
    }

    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student = new Student();

        Arrays.stream(clazz.getDeclaredFields()).filter(field -> Modifier.isPrivate(field.getModifiers()))
			.peek(field -> field.setAccessible(true)).forEach(field ->{
				try {
					if (field.getType() == String.class) {
						field.set(student, "TEST");

					} else {
						field.setInt(student, 12);
					}
				}catch (Exception e){}
		});

        logger.debug(student.getName());
		logger.debug(String.format("%d", student.getAge()));
		Assert.assertThat(student.getAge(), CoreMatchers.equalTo(12));
		Assert.assertThat(student.getName(), CoreMatchers.equalTo("TEST"));

    }
}
