package com.vegcale;

public class Plant {
    private String detail;
    private int growthDifficulty;
    private HarvestMonth mHarvestMonth;
    private String hoursOfLight;
    private PlantName mPlantName;
    private SeedingMonth mSeedingMonth;
    private String wateringAmount;
    private String wateringFrequency;

    public Plant() {}

    public Plant(
            String detail,
            int growthDifficulty,
            HarvestMonth mHarvestMonth,
            String hoursOfLight,
            PlantName mPlantName,
            SeedingMonth mSeedingMonth,
            String wateringAmount,
            String wateringFrequency
    ) {
        this.detail = detail;
        this.growthDifficulty = growthDifficulty;
        this.mHarvestMonth = mHarvestMonth;
        this.hoursOfLight = hoursOfLight;
        this.mPlantName = mPlantName;
        this.mSeedingMonth = mSeedingMonth;
        this.wateringAmount = wateringAmount;
        this.wateringFrequency = wateringFrequency;
    }

    public String getDetail() {
        return detail;
    }

    public int getGrowthDifficulty() {
        return growthDifficulty;
    }

    public HarvestMonth getHarvestMonth() {
        return mHarvestMonth;
    }

    public String getHoursOfLight() {
        return hoursOfLight;
    }

    public PlantName getPlantName() {
        return mPlantName;
    }

    public SeedingMonth getSeedingMonth() {
        return mSeedingMonth;
    }

    public String getWateringAmount() {
        return wateringAmount;
    }

    public String getWateringFrequency() {
        return wateringFrequency;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setGrowthDifficulty(int growthDifficulty) {
        this.growthDifficulty = growthDifficulty;
    }

    public void setHarvestMonth(HarvestMonth mHarvestMonth) {
        this.mHarvestMonth = mHarvestMonth;
    }

    public void setHoursOfLight(String hoursOfLight) {
        this.hoursOfLight = hoursOfLight;
    }

    public void setPlantName(PlantName mPlantName) {
        this.mPlantName = mPlantName;
    }

    public void setSeedingMonth(SeedingMonth mSeedingMonth) {
        this.mSeedingMonth = mSeedingMonth;
    }

    public void setWateringAmount(String wateringAmount) {
        this.wateringAmount = wateringAmount;
    }

    public void setWateringFrequency(String wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    private class HarvestMonth {
        private String beginningMonthOfSeeding;
        private String endMonthOfSeeding;

        public String getBeginningMonthOfSeeding() {
            return beginningMonthOfSeeding;
        }

        public String getEndMonthOfSeeding() {
            return endMonthOfSeeding;
        }
    }

    private class PlantName {
        private String english;
        private String japanese;

        public String getEnglish() {
            return english;
        }

        public String getJapanese() {
            return japanese;
        }
    }

    private class SeedingMonth {
        private String beginningMonthOfSeeding;
        private String endMonthOfSeeding;

        public String getBeginningMonthOfSeeding() {
            return beginningMonthOfSeeding;
        }

        public String getEndMonthOfSeeding() {
            return endMonthOfSeeding;
        }
    }
}
