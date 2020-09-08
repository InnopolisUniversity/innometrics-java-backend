package com.innopolis.innometrics.authserver.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProfileRequest implements Serializable {

    private Integer profileId;

    private String userEmail;

    private String macAddress;

    private Double averageCpu;

    private Double averageGpu;

    private Double averageMemory;

    private Double averageStorage;

    private String mainVendor;

    private String cpuVendor;

    private String cpuModel;

    private String gpuVendor;

    private String gpuModel;

    private String memoryVendor;

    private int memoryCounter;

    private String memoryModel;

    private String storageVendor;

    private String storageModel;

    private int storageCounter;

    public ProfileRequest(String userEmail, String macAddress, Double averageCpu,
                          Double averageGpu, Double averageMemory, Double averageStorage, String mainVendor,
                          String cpuVendor, String cpuModel, String gpuVendor, String gpuModel,
                          String memoryVendor, int memoryCounter, String memoryModel, String storageVendor,
                          String storageModel, int storageCounter) {
        //this.profileId = profileId;
        this.userEmail = userEmail;
        this.macAddress = macAddress;
        this.averageCpu = averageCpu;
        this.averageGpu = averageGpu;
        this.averageMemory = averageMemory;
        this.averageStorage = averageStorage;
        this.mainVendor = mainVendor;
        this.cpuVendor = cpuVendor;
        this.cpuModel = cpuModel;
        this.gpuVendor = gpuVendor;
        this.gpuModel = gpuModel;
        this.memoryVendor = memoryVendor;
        this.memoryCounter = memoryCounter;
        this.memoryModel = memoryModel;
        this.storageVendor = storageVendor;
        this.storageModel = storageModel;
        this.storageCounter = storageCounter;
    }

    public ProfileRequest(){}

//    public int getProfileId() {
//        return profileId;
//    }
//
//    public void setProfileId(int profileId) {
//        this.profileId = profileId;
//    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Double getAverageCpu() {
        return averageCpu;
    }

    public void setAverageCpu(Double averageCpu) {
        this.averageCpu = averageCpu;
    }

    public Double getAverageGpu() {
        return averageGpu;
    }

    public void setAverageGpu(Double averageGpu) {
        this.averageGpu = averageGpu;
    }

    public Double getAverageMemory() {
        return averageMemory;
    }

    public void setAverageMemory(Double averageMemory) {
        this.averageMemory = averageMemory;
    }

    public Double getAverageStorage() {
        return averageStorage;
    }

    public void setAverageStorage(Double averageStorage) {
        this.averageStorage = averageStorage;
    }

    public String getMainVendor() {
        return mainVendor;
    }

    public void setMainVendor(String mainVendor) {
        this.mainVendor = mainVendor;
    }

    public String getCpuVendor() {
        return cpuVendor;
    }

    public void setCpuVendor(String cpuVendor) {
        this.cpuVendor = cpuVendor;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public String getGpuVendor() {
        return gpuVendor;
    }

    public void setGpuVendor(String gpuVendor) {
        this.gpuVendor = gpuVendor;
    }

    public String getGpuModel() {
        return gpuModel;
    }

    public void setGpuModel(String gpuModel) {
        this.gpuModel = gpuModel;
    }

    public String getMemoryVendor() {
        return memoryVendor;
    }

    public void setMemoryVendor(String memoryVendor) {
        this.memoryVendor = memoryVendor;
    }

    public int getMemoryCounter() {
        return memoryCounter;
    }

    public void setMemoryCounter(int memoryCounter) {
        this.memoryCounter = memoryCounter;
    }

    public String getMemoryModel() {
        return memoryModel;
    }

    public void setMemoryModel(String memoryModel) {
        this.memoryModel = memoryModel;
    }

    public String getStorageVendor() {
        return storageVendor;
    }

    public void setStorageVendor(String storageVendor) {
        this.storageVendor = storageVendor;
    }

    public String getStorageModel() {
        return storageModel;
    }

    public void setStorageModel(String storageModel) {
        this.storageModel = storageModel;
    }

    public int getStorageCounter() {
        return storageCounter;
    }

    public void setStorageCounter(int storageCounter) {
        this.storageCounter = storageCounter;
    }
}
