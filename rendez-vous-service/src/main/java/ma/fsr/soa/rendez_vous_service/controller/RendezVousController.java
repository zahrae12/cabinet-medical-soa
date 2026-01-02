package ma.fsr.soa.rendez_vous_service.controller;

import ma.fsr.soa.cabinetrepo.model.RendezVous;
import ma.fsr.soa.rendez_vous_service.service.RendezVousService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/internal/api/v1/rendezvous")
public class RendezVousController {

    private final RendezVousService rdvService;

    public RendezVousController(RendezVousService rdvService) {
        this.rdvService = rdvService;
    }

    @GetMapping
    public ResponseEntity<List<RendezVous>> getAllRendezVous() {
        return ResponseEntity.ok(rdvService.getAllRendezVous());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RendezVous> getRendezVousById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(rdvService.getRendezVousById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<RendezVous>> getRendezVousByPatient(@PathVariable Long id) {
        return ResponseEntity.ok(rdvService.getRendezVousByPatientId(id));
    }

    @GetMapping("/medecin/{id}")
    public ResponseEntity<List<RendezVous>> getRendezVousByMedecin(@PathVariable Long id) {
        return ResponseEntity.ok(rdvService.getRendezVousByMedecinId(id));
    }

    @PostMapping
    public ResponseEntity<?> createRendezVous(@RequestBody RendezVous rdv) {
        try {
            RendezVous created = rdvService.createRendezVous(rdv);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            // Log the error to console
            System.err.println("❌ Error creating rendez-vous: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRendezVous(@PathVariable Long id, @RequestBody RendezVous rdv) {
        try {
            System.out.println("🔄 Updating rendez-vous ID: " + id);
            System.out.println("   Date: " + rdv.getDateRdv());
            System.out.println("   Status: " + rdv.getStatut());
            System.out.println("   Patient: " + rdv.getPatient());
            System.out.println("   Medecin: " + rdv.getMedecin());

            RendezVous updated = rdvService.updateRendezVous(id, rdv);

            System.out.println("✅ Successfully updated");
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            // Log the error to console
            System.err.println("❌ Error updating rendez-vous: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.err.println("❌ Unexpected error: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur serveur: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<?> updateStatut(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String statutStr = body.get("statut");
            RendezVous.StatutRendezvous statut = RendezVous.StatutRendezvous.valueOf(statutStr);
            return ResponseEntity.ok(rdvService.updateStatut(id, statut));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Statut invalide. Valeurs possibles : PLANIFIE, ANNULE, TERMINE."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        try {
            rdvService.deleteRendezVous(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}