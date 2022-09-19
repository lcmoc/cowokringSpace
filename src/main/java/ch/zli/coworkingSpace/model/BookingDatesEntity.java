package ch.zli.coworkingSpace.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.security.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "BOOKINGDATES")
public class BookingDatesEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    UUID id = UUID.randomUUID();

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "whole_day", nullable = false)
    private boolean wholeDay;

    @Column(name = "booked", nullable = false)
    private boolean booked;

    @Column(name = "user_id", nullable = false)
    private int user_id;

}
