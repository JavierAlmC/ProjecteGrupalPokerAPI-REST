package com.grup.pokerdaw.api_rest_pokerdaw.model.db;
import java.util.ArrayList;
import java.util.HashMap;
public class Player {
    private int money;
    private ArrayList<String> cards;
    private int playerDeal;
    private HashMap<String, Integer> playerScore;
    private String playerState;

    public Player() {
        this.money = 1000;
        this.cards = new ArrayList<>();
        this.playerDeal = 0;
        this.playerScore = new HashMap<>();
    }

    public int deal(int amount) {
        if (amount == this.money) {
            this.money = 0;
            this.playerState = "AllIn";
            this.playerDeal += amount;
            return amount;
        } else if (amount < this.money) {
            this.money -= amount;
            this.playerDeal += amount;
            return amount;
        }
        return 0; // In case the amount is not valid
    }

    public void resetDeal() {
        this.playerDeal = 0;
    }

    // Getters and setters for private fields if needed
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public void setCards(ArrayList<String> cards) {
        this.cards = cards;
    }

    public int getPlayerDeal() {
        return playerDeal;
    }

    public void setPlayerDeal(int playerDeal) {
        this.playerDeal = playerDeal;
    }

    public HashMap<String, Integer> getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(HashMap<String, Integer> playerScore) {
        this.playerScore = playerScore;
    }

    public String getPlayerState() {
        return playerState;
    }

    public void setPlayerState(String playerState) {
        this.playerState = playerState;
    }
}
