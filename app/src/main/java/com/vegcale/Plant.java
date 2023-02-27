package com.vegcale;

public class Plant {
    private String detail;
    private String growth_difficulty;
    private harvest_month harvest_month;
    private String hours_of_light;
    private String image_url;
    private String name;
    private seeding_month seeding_month;
    private String watering_amount;
    private String watering_frequency;
    private ph ph;

    public Plant() {
    }

    public String getDetail() {
        return detail;
    }

    public String getGrowth_Difficulty() {
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

    public String getName() {
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

    public ph getPh() {
        return ph;
    }

    public static class harvest_month {
        private int from;
        private int to;

        public harvest_month() {
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }
    }

    public static class seeding_month {
        private int from;
        private int to;

        public seeding_month() {
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }
    }

    public static class ph {
        private int from;
        private int to;

        public ph() {
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }
    }
}
