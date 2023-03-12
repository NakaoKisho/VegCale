package com.vegcale.data;

import java.util.ArrayList;

public class Article {
    private ArrayList<String> paragraph;
    private ArrayList<String> paragraph_image_url;
    private ArrayList<String> tag;
    private String title;
    private String title_image;

    public Article() {
    }

    public ArrayList<String> getParagraph() {
        return paragraph;
    }

    public ArrayList<String> getParagraph_image_url() {
        return paragraph_image_url;
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle_image() {
        return title_image;
    }
}
