package ma.fsr.soa.rendez_vous_service.service;

import ma.fsr.soa.cabinetrepo.model.RendezVous;
import ma.fsr.soa.cabinetrepo.repository.RendezVousRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;

    public RendezVousService(RendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }

    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    public RendezVous getRendezVousById(Long id) {
        return rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable : id = " + id));
    }

    public List<RendezVous> getRendezVousByPatientId(Long patientId) {
        return rendezVousRepository.findByPatientId(patientId);
    }

    public List<RendezVous> getRendezVousByMedecinId(Long medecinId) {
        return rendezVousRepository.findByMedecinId(medecinId);
    }

    public RendezVous createRendezVous(RendezVous rdv) {
        validateRendezVous(rdv);

        // Extract patient ID from nested object if provided
        if (rdv.getPatientId() == null && rdv.getPatient() != null) {
            rdv.setPatientId(rdv.getPatient().getId());
        }

        // Extract medecin ID from nested object if provided
        if (rdv.getMedecinId() == null && rdv.getMedecin() != null) {
            rdv.setMedecinId(rdv.getMedecin().getId());
        }

        // Set default status: PLANIFIE
        if (rdv.getStatut() == null) {
            rdv.setStatut(RendezVous.StatutRendezvous.PLANIFIE);
        }

        return rendezVousRepository.save(rdv);
    }

    public RendezVous updateRendezVous(Long id, RendezVous rdv) {
        RendezVous existing = getRendezVousById(id);

        // Extract patient ID from nested object BEFORE validation
        if (rdv.getPatientId() == null && rdv.getPatient() != null) {
            rdv.setPatientId(rdv.getPatient().getId());
        }

        // Extract medecin ID from nested object BEFORE validation
        if (rdv.getMedecinId() == null && rdv.getMedecin() != null) {
            rdv.setMedecinId(rdv.getMedecin().getId());
        }

        // NOW validate
        validateRendezVous(rdv);

        // Update date
        existing.setDateRdv(rdv.getDateRdv());

        // Update status
        if (rdv.getStatut() != null) {
            validateStatut(rdv.getStatut());
            existing.setStatut(rdv.getStatut());
        }

        // Update patient ID
        if (rdv.getPatientId() != null) {
            existing.setPatientId(rdv.getPatientId());
        }

        // Update medecin ID
        if (rdv.getMedecinId() != null) {
            existing.setMedecinId(rdv.getMedecinId());
        }

        return rendezVousRepository.save(existing);
    }

    public RendezVous updateStatut(Long id, RendezVous.StatutRendezvous statut) {
        RendezVous existing = getRendezVousById(id);
        validateStatut(statut);
        existing.setStatut(statut);
        return rendezVousRepository.save(existing);
    }

    public void deleteRendezVous(Long id) {
        RendezVous existing = getRendezVousById(id);
        rendezVousRepository.delete(existing);
    }

    private void validateRendezVous(RendezVous rdv) {
        // Règle 1: La date du rendez-vous doit être postérieure à la date actuelle
        if (rdv.getDateRdv() == null) {
            throw new RuntimeException("La date du rendez-vous est obligatoire.");
        }
        if (rdv.getDateRdv().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("La date du rendez-vous doit être future.");
        }

        // Règle 2 & 3: Patient and Medecin IDs must be provided
        Long patientId = rdv.getPatientId();
        if (patientId == null && rdv.getPatient() != null) {
            patientId = rdv.getPatient().getId();
        }
        if (patientId == null) {
            throw new RuntimeException("Patient introuvable.");
        }

        Long medecinId = rdv.getMedecinId();
        if (medecinId == null && rdv.getMedecin() != null) {
            medecinId = rdv.getMedecin().getId();
        }
        if (medecinId == null) {
            throw new RuntimeException("Médecin introuvable.");
        }

        // Règle 4: Validate statut if provided
        if (rdv.getStatut() != null) {
            validateStatut(rdv.getStatut());
        }
    }

    private void validateStatut(RendezVous.StatutRendezvous statut) {
        // Règle 4: Statuts autorisés uniquement : PLANIFIE, ANNULE, TERMINE
        if (statut != RendezVous.StatutRendezvous.PLANIFIE &&
                statut != RendezVous.StatutRendezvous.CONFIRME &&
                statut != RendezVous.StatutRendezvous.ANNULE &&
                statut != RendezVous.StatutRendezvous.TERMINE) {
            throw new RuntimeException("Statut invalide. Valeurs possibles : PLANIFIE, ANNULE, TERMINE.");
        }
    }
}