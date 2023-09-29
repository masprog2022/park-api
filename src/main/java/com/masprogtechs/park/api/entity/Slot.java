package com.masprogtechs.park.api.entity;

import com.masprogtechs.park.api.enums.StatusSlot;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_slots")
@EntityListeners(AuditingEntityListener.class)
public class Slot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 4)
    private String code;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusSlot status;


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
