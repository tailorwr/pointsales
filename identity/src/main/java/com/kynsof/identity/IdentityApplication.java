package com.kynsof.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
//public class IdentityApplication implements CommandLineRunner {
//
////    @Autowired
////    private ModuleServiceImpl moduleServiceImpl;
////
////    @Autowired
////    private CustomerServiceImpl customerServiceImpl;
////
////    @Autowired
////    private UserSystemServiceImpl userServiceImpl;
//
//    public static void main(String[] args) {
//        SpringApplication.run(IdentityApplication.class, args);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//    }
//
////    @Override
////    public void run(String... args) throws Exception {
////        this.moduleServiceImpl.updateDelete();
////        this.customerServiceImpl.updateDelete();
////        this.userServiceImpl.updateDelete();
////    }
//
//}
public class IdentityApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdentityApplication.class, args);
    }


}