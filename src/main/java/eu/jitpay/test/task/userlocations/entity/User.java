package eu.jitpay.test.task.userlocations.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
@Accessors(chain = true)
public class User {
    @Id
    private UUID id;
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    @Version
    private Long version;
}
