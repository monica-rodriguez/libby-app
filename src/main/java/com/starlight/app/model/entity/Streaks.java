package com.starlight.app.model.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.time.LocalDate;

@Entity
@Table(
        name = "streaks",
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "dateRead"})
)
@Data
@NoArgsConstructor
public class Streaks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookId")
    private Books book;

    @Column(name = "dateRead", nullable = false)
    private LocalDate date;

    private Integer pagesRead;

    public Streaks(User user, Books book, LocalDate date) {
        this.book = book;
        this.date = date;
    }
}
