package ch.zli.coworkingSpace.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity(name = "BOOKINGDATES")
public class BookingDatesEntity {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "whole_day", nullable = false)
    private boolean wholeDay;

    @Column(name = "booked", nullable = false)
    private boolean booked;

    @Column(name = "user_id", nullable = false)
    private int user_id;

}
