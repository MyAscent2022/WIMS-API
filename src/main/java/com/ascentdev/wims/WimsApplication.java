package com.ascentdev.wims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;



//-- FOR WAR FILE

//@SpringBootApplication
//@PropertySource({"file:C:\\wms_paircargo\\conf\\config.properties"})
//public class WimsApplication extends SpringBootServletInitializer{
//
//  @Override
//  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//    return application.sources(WimsApplication.class);
//  }
//
//  public static void main(String[] args) {
//    SpringApplication.run(WimsApplication.class, args);
//  }
//
//}



// -- FOR JAR FILE

@SpringBootApplication
@PropertySource({"file:C:\\wms_paircargo\\conf\\config.properties"})
//@PropertySource({"file:C:\\wms_paircargo\\conf\\configV2.properties"})
public class WimsApplication {

  public static void main(String[] args) {
    SpringApplication.run(WimsApplication.class, args);
  }

} 
