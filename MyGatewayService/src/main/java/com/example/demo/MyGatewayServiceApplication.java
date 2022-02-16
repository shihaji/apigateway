package com.example.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class MyGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyGatewayServiceApplication.class, args);
	}
	
	@Bean
	public RouteLocator getRoute(RouteLocatorBuilder builder) {
		
		return builder.routes()
				.route(t->t.path("/getEmp/**").uri("lb://MYEUREKACLIENT"))
				.route(t->t.path("/validate/**").uri("lb://MYAUTH"))
				.route(t->t.path("/authenticate/**").uri("lb://MYAUTH"))
				.build();
		
	}
	
	@Bean
    @Autowired
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils){
        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        config.setNonSecurePort(8087);
        config.setIpAddress(ip);
        config.setPreferIpAddress(true);
        return config;
    }
	
	
	

}
