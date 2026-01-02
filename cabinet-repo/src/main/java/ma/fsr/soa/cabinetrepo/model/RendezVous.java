package ma.fsr.soa.cabinetrepo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "rendezvous")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_rdv", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateRdv;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutRendezvous statut;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "medecin_id", nullable = false)
    private Long medecinId;

    @Transient
    private Patient patient;

    @Transient
    private Medecin medecin;

    public enum StatutRendezvous {
        PLANIFIE,
        CONFIRME,
        ANNULE,
        TERMINE
    }

    @PrePersist
    public void prePersist() {
        if (this.statut == null) {
            this.statut = StatutRendezvous.PLANIFIE;
        }
    }
}