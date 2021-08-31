package com.example.hexagonal.architecture.adapter.persistence;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.spring.api.DBRider;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.github.database.rider.core.api.configuration.Orthography.LOWERCASE;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest
@TestPropertySource("classpath:/persistence-test.properties")
@Transactional
@DBUnit(
        cacheConnection = false,
        dataTypeFactoryClass = PostgresqlDataTypeFactory.class,
        caseInsensitiveStrategy = LOWERCASE
)
@DBRider
@Tag("integration")
public @interface PersistenceTest {
}
