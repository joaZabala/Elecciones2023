package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "result_detail")
public class ResultDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private int orden;
    private String nombre;
    private int votos;
    private double porcentaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id", foreignKey = @ForeignKey(name = "fk_result_detail_result"))
    private ResultEntity result;
}
