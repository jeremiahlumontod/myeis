package guru.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@EntityScan(basePackages = {"guru.springframework.domain"})
@SpringBootApplication
public class SpringmvcApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SpringmvcApplication.class, args);
        System.out.println("Beans*******");
        System.out.println(ctx.getBeanDefinitionCount());
        //for(String name: ctx.getBeanDefinitionNames()) {
        //    System.out.println(name);
        //}

    }
}

//public class SpringmvcApplication extends SpringBootServletInitializer {
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(SpringmvcApplication.class);
//    }
//
//    public static void main(String[] args) {
//        ApplicationContext ctx = SpringApplication.run(SpringmvcApplication.class, args);
//        System.out.println("Beans*******");
//        System.out.println(ctx.getBeanDefinitionCount());
//        //for(String name: ctx.getBeanDefinitionNames()) {
//        //    System.out.println(name);
//        //}
//
//    }
//}
