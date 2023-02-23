package com.vegcale;

public class Plant {
    private String detail;
    private int growth_difficulty;
    private harvest_month harvest_month;
    private String hours_of_light;
    private String image_url;
    private name name;
    private seeding_month seeding_month;
    private String watering_amount;
    private String watering_frequency;

    public Plant() {
    }

    public Plant(
            String detail,
            int growth_difficulty,
            harvest_month harvest_month,
            String hours_of_light,
            String image_url,
            name name,
            seeding_month seeding_month,
            String watering_amount,
            String watering_frequency
    ) {
        this.detail = detail;
        this.growth_difficulty = growth_difficulty;
        this.harvest_month = harvest_month;
        this.hours_of_light = hours_of_light;
        this.image_url = image_url;
        this.name = name;
        this.seeding_month = seeding_month;
        this.watering_amount = watering_amount;
        this.watering_frequency = watering_frequency;
    }

    public String getDetail() {
        return detail;
    }

    public int getGrowth_Difficulty() {
        return growth_difficulty;
    }

    public harvest_month getHarvest_Month() {
        return harvest_month;
    }

    public String getHours_Of_Light() {
        return hours_of_light;
    }

    public String getImage_Url() {
        return image_url;
    }

    public name getName() {
        return name;
    }

    public seeding_month getSeeding_Month() {
        return seeding_month;
    }

    public String getWatering_Amount() {
        return watering_amount;
    }

    public String getWatering_Frequency() {
        return watering_frequency;
    }

    public static class harvest_month {
        private String from;
        private String to;

        public harvest_month() {
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }
    }

    public static class name {
        private String English;
        private String Japanese;

        public name() {
        }

        public String getEnglish() {
            return English;
        }

        public String getJapanese() {
            return Japanese;
        }
    }

    public static class seeding_month {
        private String from;
        private String to;

        public seeding_month() {
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }
    }
}
