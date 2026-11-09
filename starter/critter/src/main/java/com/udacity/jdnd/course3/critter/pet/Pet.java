package com.udacity.jdnd.course3.critter.pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="Pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    private String petName;

    private LocalDate petDob;

    @Column(length = 500)
    private String petNote;

    @Enumerated(EnumType.STRING)
    private PetType petType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public LocalDate getPetDob() {
        return petDob;
    }

    public void setPetDob(LocalDate petDob) {
        this.petDob = petDob;
    }

    public String getPetNote() {
        return petNote;
    }

    public void setPetNote(String petNote) {
        this.petNote = petNote;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
