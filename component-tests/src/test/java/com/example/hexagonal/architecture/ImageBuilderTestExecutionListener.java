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

import java.io.File;
import java.util.Arrays;

import static java.util.Collections.singletonList;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Order(HIGHEST_PRECEDENCE)
public class ImageBuilderTestExecutionListener implements SpringApplicationRunListener {

    private final boolean buildImage;

    public ImageBuilderTestExecutionListener(SpringApplication application, String[] args) {
        buildImage = Arrays.asList(args).contains("build.image=true");
    }

    @SneakyThrows
    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        //        https://www.jetbrains.com/help/idea/maven-support.html#maven2_install

//        https://youtrack.jetbrains.com/issue/IDEA-258757
        if (buildImage) {
            Invoker invoker = new DefaultInvoker();

            InvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile(new File("../pom.xml"));
            request.setGoals(singletonList("clean package -DskipTests=true"));

            var result = invoker.execute(request);

            if (result.getExitCode() != 0) {
                if (result.getExecutionException() != null) {
                    throw new IllegalStateException("Build failed. Exist code: " + result.getExitCode(), result.getExecutionException());
                } else {
                    throw new IllegalStateException("Build failed. Exist code: " + result.getExitCode());
                }
            }
        }
    }
}
