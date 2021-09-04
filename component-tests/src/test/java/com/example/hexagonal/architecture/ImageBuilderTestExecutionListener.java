package com.example.hexagonal.architecture;

import lombok.SneakyThrows;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.TestContext;

import java.io.File;

import static java.util.Collections.singletonList;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Order(HIGHEST_PRECEDENCE)
public class ImageBuilderTestExecutionListener implements SpringApplicationRunListener  /*TestExecutionListener*/ {

    public ImageBuilderTestExecutionListener(SpringApplication application, String[] args) {
    }

    @SneakyThrows
    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        //        https://www.jetbrains.com/help/idea/maven-support.html#maven2_install

//        https://youtrack.jetbrains.com/issue/IDEA-258757

        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File("../pom.xml"));
        request.setGoals(singletonList("clean package -DskipTests=true"));

        Invoker invoker = new DefaultInvoker();
        invoker.execute(request);
    }

    //    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        if (testContext.getAttribute("IMAGE_CREATED") == null) {
            //        https://www.jetbrains.com/help/idea/maven-support.html#maven2_install

//        https://youtrack.jetbrains.com/issue/IDEA-258757

            InvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile(new File("../pom.xml"));
            request.setGoals(singletonList("clean package -DskipTests=true"));

            Invoker invoker = new DefaultInvoker();
            invoker.execute(request);

            testContext.setAttribute("IMAGE_CREATED", true);
        }
    }
}
