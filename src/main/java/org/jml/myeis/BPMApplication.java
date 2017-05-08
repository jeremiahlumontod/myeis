package org.jml.myeis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@EntityScan(basePackages = {"org.jml.myeis.domain"})
@SpringBootApplication
public class BPMApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BPMApplication.class, args);
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
//@Bean //activate during initial creation of schema or new user needs to be entered
//InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {
//
//    return new InitializingBean() {
//        public void afterPropertiesSet() throws Exception {
//
//            Group group = identityService.newGroup("user");
//            group.setName("users");
//            group.setType("security-role");
//            identityService.saveGroup(group);
//
//            User admin = identityService.newUser("admin");
//            admin.setPassword("admin");
//            identityService.saveUser(admin);
//
//        }
//    };
//}

//}
