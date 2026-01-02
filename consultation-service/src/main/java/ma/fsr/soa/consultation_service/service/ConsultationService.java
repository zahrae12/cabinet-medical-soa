package ma.fsr.soa.consultation_service.service;

import ma.fsr.soa.cabinetrepo.model.Consultation;
import ma.fsr.soa.cabinetrepo.repository.ConsultationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {

    private final ConsultationRepository consultationRepository;

    public ConsultationService(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    public Consultation getConsultationById(Long id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultation introuvable : id = " + id));
    }

    public List<Consultation> getConsultationsByRendezVousId(Long rendezVousId) {
        return consultationRepository.findByRendezVousId(rendezVousId);
    }

    public Consultation createConsultation(Consultation consultation) {
        // Extract rendez-vous ID from nested object if provided
        if (consultation.getRendezVousId() == null && consultation.getRendezVous() != null) {
            consultation.setRendezVousId(consultation.getRendezVous().getId());
        }

        validateConsultation(consultation);

        return consultationRepository.save(consultation);
    }

    public Consultation updateConsultation(Long id, Consultation consultation) {
        Consultation existing = getConsultationById(id);

        // Extract rendez-vous ID from nested object if provided
        if (consultation.getRendezVousId() == null && consultation.getRendezVous() != null) {
            consultation.setRendezVousId(consultation.getRendezVous().getId());
        }

        validateConsultation(consultation);

        existing.setDateConsultation(consultation.getDateConsultation());
        existing.setRapport(consultation.getRapport());
        existing.setRendezVousId(consultation.getRendezVousId());

        return consultationRepository.save(existing);
    }

    public void deleteConsultation(Long id) {
        Consultation existing = getConsultationById(id);
        consultationRepository.delete(existing);
    }

    private void validateConsultation(Consultation consultation) {
        // Règle 1: Le rendez-vous ID doit être fourni
        Long rendezVousId = consultation.getRendezVousId();
        if (rendezVousId == null && consultation.getRendezVous() != null) {
            rendezVousId = consultation.getRendezVous().getId();
        }
        if (rendezVousId == null) {
            throw new RuntimeException("Rendez-vous introuvable.");
        }

        // Note: We don't validate if the rendez-vous exists in another service's database
        // That's the responsibility of the rendez-vous-service

        // Règle 2: La date de consultation est obligatoire
        if (consultation.getDateConsultation() == null) {
            throw new RuntimeException("La date de consultation est obligatoire.");
        }

        // Règle 3: We can't validate date >= rendez-vous date without calling the other service
        // This validation would need to be done via API call or removed in microservices architecture

        // Règle 4: Le rapport est obligatoire (au moins 10 caractères)
        if (consultation.getRapport() == null || consultation.getRapport().trim().length() < 10) {
            throw new RuntimeException("Rapport de consultation insuffisant.");
        }
    }
}