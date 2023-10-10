package com.masprogtechs.park.api.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_customers_has_slots")
@EntityListeners(AuditingEntityListener.class)
public class CustomerSlot implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "number_receipt", nullable = false, unique = true, length = 15)
   private String receipt; // recibo

    @Column(name = "plate", nullable = false, length = 11)
    private String plate; // placa de carro

    @Column(name = "make", nullable = false, length = 45)
    private String make; // marca de carro

    @Column(name = "model", nullable = false, length = 45)
    private String model; // modelo

    @Column(name = "color", nullable = false, length = 45)
    private String color; // cor

    @Column(name = "input_data", nullable = false)
    private LocalDateTime inputData; // data de entrada

    @Column(name = "output_data")
    private LocalDateTime outputData; // data de entrada

    @Column(name = "charge", columnDefinition = "decimal(7,2)")
    private BigDecimal charge; // valor

    @Column(name = "discount", columnDefinition = "decimal(7,2)")
    private BigDecimal discount; // desconto

    @ManyToOne
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "id_slot", nullable = false)
    private Slot slot;

    @CreatedDate
    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_At")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_By")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_By")
    private String updatedBy;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
