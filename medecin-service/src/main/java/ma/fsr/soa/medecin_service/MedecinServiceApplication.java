package ma.fsr.soa.medecin_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"ma.fsr.soa.medecin_service",
		"ma.fsr.soa.cabinetrepo"
})
@EnableJpaRepositories(basePackages = "ma.fsr.soa.cabinetrepo.repository")
public class MedecinServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(MedecinServiceApplication.class, args);
	}
}