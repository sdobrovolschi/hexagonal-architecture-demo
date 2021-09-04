package com.example.hexagonal.architecture;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest(webEnvironment = NONE)
@TestPropertySource("classpath:/component-tests.properties")
//@TestExecutionListeners(value = ImageBuilderTestExecutionListener.class, mergeMode = MERGE_WITH_DEFAULTS)
@Tag("component")
public @interface ComponentTest {
}
