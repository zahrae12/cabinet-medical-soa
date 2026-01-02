package ma.fsr.soa.patient_service.service;

import ma.fsr.soa.cabinetrepo.model.Patient;
import ma.fsr.soa.cabinetrepo.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient introuvable : id = " + id));
    }

    public Patient create(Patient patient) {
        validate(patient);
        return patientRepository.save(patient);
    }

    public Patient update(Long id, Patient patient) {
        Patient existing = findById(id);
        validate(patient);
        existing.setNom(patient.getNom());
        existing.setTelephone(patient.getTelephone());
        existing.setDateNaissance(patient.getDateNaissance());
        return patientRepository.save(existing);
    }

    public void delete(Long id) {
        Patient existing = findById(id);
        patientRepository.delete(existing);
    }

    private void validate(Patient patient) {
        if (patient.getNom() == null || patient.getNom().isBlank()) {
            throw new RuntimeException("Le nom du patient est obligatoire.");
        }
        if (patient.getTelephone() == null || patient.getTelephone().isBlank()) {
            throw new RuntimeException("Le téléphone du patient est obligatoire.");
        }
        if (patient.getDateNaissance() != null && patient.getDateNaissance().isAfter(LocalDate.now())) {
            throw new RuntimeException("La date de naissance ne peut pas être future");
        }
    }
}
