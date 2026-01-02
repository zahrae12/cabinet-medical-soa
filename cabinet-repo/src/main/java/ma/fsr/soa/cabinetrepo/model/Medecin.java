package ma.fsr.soa.cabinetrepo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medecins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medecin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du médecin est obligatoire.")
    @Column(nullable = false)
    private String nom;

    @NotBlank(message = "L'email du médecin est obligatoire.")
    @Email(message = "Email du médecin invalide.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "La spécialité du médecin est obligatoire.")
    @Column(nullable = false)
    private String specialite;

    private String telephone;
    private String adresse;
}