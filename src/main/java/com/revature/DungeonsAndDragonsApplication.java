package com.revature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy //enables use of @Aspect annocation
public class DungeonsAndDragonsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DungeonsAndDragonsApplication.class, args);
	}

}
