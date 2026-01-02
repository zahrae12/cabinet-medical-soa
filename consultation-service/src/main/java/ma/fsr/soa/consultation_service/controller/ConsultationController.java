package ma.fsr.soa.consultation_service.controller;

import ma.fsr.soa.cabinetrepo.model.Consultation;
import ma.fsr.soa.consultation_service.service.ConsultationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/internal/api/v1/consultations")
public class ConsultationController {

    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping
    public ResponseEntity<List<Consultation>> getAllConsultations() {
        return ResponseEntity.ok(consultationService.getAllConsultations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(consultationService.getConsultationById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rendezvous/{id}")
    public ResponseEntity<List<Consultation>> getConsultationsByRendezVous(@PathVariable Long id) {
        return ResponseEntity.ok(consultationService.getConsultationsByRendezVousId(id));
    }

    @PostMapping
    public ResponseEntity<?> createConsultation(@RequestBody Consultation consultation) {
        try {
            Consultation created = consultationService.createConsultation(consultation);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateConsultation(@PathVariable Long id, @RequestBody Consultation consultation) {
        try {
            return ResponseEntity.ok(consultationService.updateConsultation(id, consultation));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long id) {
        try {
            consultationService.deleteConsultation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}