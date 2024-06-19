package br.com.everdev.dfsappc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DFSAppCApplication {

	public static void main(String[] args) {
//		System.setProperty("java.rmi.server.hostname","192.168.0.105:1010");
		SpringApplication.run(DFSAppCApplication.class, args);
	}

}
