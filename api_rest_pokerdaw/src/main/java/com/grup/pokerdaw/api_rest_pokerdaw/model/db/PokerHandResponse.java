package com.grup.pokerdaw.api_rest_pokerdaw.model.db;

public class PokerHandResponse {
    private String name;
    private int score;
    private Card highCard;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Card getHighCard() {
        return highCard;
    }

    public void setHighCard(Card highCard) {
        this.highCard = highCard;
    }
}
