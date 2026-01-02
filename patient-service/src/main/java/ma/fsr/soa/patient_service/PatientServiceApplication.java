package ma.fsr.soa.patient_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"ma.fsr.soa.patient_service",
		"ma.fsr.soa.cabinetrepo"
})
@EnableJpaRepositories(basePackages = "ma.fsr.soa.cabinetrepo.repository")
public class PatientServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PatientServiceApplication.class, args);
	}
}