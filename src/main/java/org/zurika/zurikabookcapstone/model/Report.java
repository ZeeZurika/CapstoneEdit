package org.zurika.zurikabookcapstone.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reportType;
    private String generatedBy;
    private LocalDateTime generatedAt;
    private String reportData;

    public Report(String reportType, String generatedBy,
                  LocalDateTime generatedAt, String reportData) {
        this.reportType = reportType;
        this.generatedBy = generatedBy;
        this.generatedAt = generatedAt;
        this.reportData = reportData;
    }}
