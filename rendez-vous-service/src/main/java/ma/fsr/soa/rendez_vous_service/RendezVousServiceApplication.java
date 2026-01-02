package ma.fsr.soa.rendez_vous_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"ma.fsr.soa.rendez_vous_service",
		"ma.fsr.soa.cabinetrepo"
})
@EnableJpaRepositories(basePackages = "ma.fsr.soa.cabinetrepo.repository")
public class RendezVousServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RendezVousServiceApplication.class, args);
	}

}
