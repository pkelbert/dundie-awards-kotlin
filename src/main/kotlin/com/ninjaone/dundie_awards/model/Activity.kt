package com.ninjaone.dundie_awards.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "activities")
class Activity(

    @Column(name = "occurred_at")
    var occurredAt: LocalDateTime,

    @Column(name = "event")
    var event: String

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
