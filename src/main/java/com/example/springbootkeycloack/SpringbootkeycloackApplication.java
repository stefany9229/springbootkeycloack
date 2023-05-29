package com.example.springbootkeycloack;

import com.example.springbootkeycloack.paraRomper.MyClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringbootkeycloackApplication {

	public static void main(String[] args) {


		ApplicationContext context=SpringApplication.run(SpringbootkeycloackApplication.class, args);
		MyClass myClass = new MyClass();
		myClass.interactWithToken();
	}

}
