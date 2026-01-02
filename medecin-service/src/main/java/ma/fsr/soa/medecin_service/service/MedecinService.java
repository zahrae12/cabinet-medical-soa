package ma.fsr.soa.medecin_service.service;

import ma.fsr.soa.cabinetrepo.model.Medecin;
import ma.fsr.soa.cabinetrepo.repository.MedecinRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedecinService {

    private final MedecinRepository medecinRepository;

    public MedecinService(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    public List<Medecin> findAll() {
        return medecinRepository.findAll();
    }

    public Medecin findById(Long id) {
        return medecinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médecin introuvable : id = " + id));
    }

    public Medecin create(Medecin medecin) {
        validate(medecin);
        return medecinRepository.save(medecin);
    }

    public Medecin update(Long id, Medecin medecin) {
        Medecin existing = findById(id);
        validate(medecin);
        existing.setNom(medecin.getNom());
        existing.setEmail(medecin.getEmail());
        existing.setSpecialite(medecin.getSpecialite());
        return medecinRepository.save(existing);
    }

    public void delete(Long id) {
        Medecin existing = findById(id);
        medecinRepository.delete(existing);
    }

    private void validate(Medecin medecin) {
        if (medecin.getNom() == null || medecin.getNom().isBlank()) {
            throw new RuntimeException("Le nom du médecin est obligatoire.");
        }
        if (medecin.getEmail() == null || medecin.getEmail().isBlank()) {
            throw new RuntimeException("L’email du médecin est obligatoire.");
        }
        if (!medecin.getEmail().contains("@")) {
            throw new RuntimeException("Email du médecin invalide.");
        }
        if (medecin.getSpecialite() == null || medecin.getSpecialite().isBlank()) {
            throw new RuntimeException("La spécialité du médecin est obligatoire.");
        }
    }
}
