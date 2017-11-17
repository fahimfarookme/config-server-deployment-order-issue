package me.fahimfarook.ms.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.fahimfarook.ms.config.Config;

@PropertySource("classpath:microservice.properties")
@SpringBootApplication
public class MicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceApplication.class, args);
	}
	
	@Bean
	public Config config() {
		return new Config();
	}
}

interface Api {

	@RequestMapping("/config-prop/date")
	String configProp();
	
	@RequestMapping("/env-prop/date")
	String envProp();
	
	@RequestMapping("/value-prop/date")
	String valueProp();
}

@RefreshScope
@RestController
class Resource implements Api {
	
	@Value("${prop.date}")
	private String prop;
	
	@Autowired
	private Environment env;

	@Autowired
	private Config config;

	@Override
	public String configProp() {
		return this.config.getDate();
	}

	@Override
	public String envProp() {
		return this.env.getProperty("prop.date");
	}

	@Override
	public String valueProp() {
		return prop;
	}
}
