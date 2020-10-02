package com.innopolis.innometrics.authserver.entitiy;

import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer profileId;

    @Column(name = "email")
    private String userEmail;

    @Column
    private String macAddress;

    @Column
    private Double averageCpu;

    @Column
    private Double averageGpu;

    @Column
    private Double averageMemory;

    @Column
    private Double averageStorage;

    @Column
    private String mainVendor;

    @Column
    private String cpuVendor;

    @Column
    private String cpuModel;

    @Column
    private String gpuVendor;

    @Column
    private String gpuModel;

    @Column
    private String memoryVendor;

    @Column
    private int memoryCounter;

    @Column
    private String memoryModel;

    @Column
    private String storageVendor;

    @Column
    private String storageModel;

    @Column
    private int storageCounter;



}
