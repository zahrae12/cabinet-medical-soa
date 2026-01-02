package ma.fsr.soa.medecin_service.controller;

import jakarta.validation.Valid;
import ma.fsr.soa.cabinetrepo.model.Medecin;
import ma.fsr.soa.medecin_service.service.MedecinService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/medecins")
public class MedecinController {

    private final MedecinService medecinService;

    public MedecinController(MedecinService medecinService) {
        this.medecinService = medecinService;
    }

    // Get all medecins
    @GetMapping
    public List<Medecin> getAllMedecins() {
        return medecinService.findAll();
    }

    // Get medecin by ID
    @GetMapping("/{id}")
    public Medecin getMedecinById(@PathVariable Long id) {
        return medecinService.findById(id);
    }

    // Create a new medecin
    @PostMapping
    public Medecin createMedecin(@Valid @RequestBody Medecin medecin) {
        return medecinService.create(medecin);
    }

    // Update an existing medecin
    @PutMapping("/{id}")
    public Medecin updateMedecin(
            @PathVariable Long id,
            @Valid @RequestBody Medecin medecin
    ) {
        return medecinService.update(id, medecin);
    }

    // Delete a medecin
    @DeleteMapping("/{id}")
    public void deleteMedecin(@PathVariable Long id) {
        medecinService.delete(id);
    }
}
