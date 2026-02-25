package com.CasoEstudio1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "reunion")
public class Reunion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "El título no puede estar vacío.")
    @Size(max = 150, message = "El título no puede tener más de 150 caracteres.")
    private String titulo;

    @Column(nullable = false)
    @NotNull(message = "La fecha no puede estar vacía.")
    private LocalDate fecha;

    @Column(nullable = false, length = 10)
    @NotBlank(message = "La hora no puede estar vacía.")
    private String hora;

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;
}