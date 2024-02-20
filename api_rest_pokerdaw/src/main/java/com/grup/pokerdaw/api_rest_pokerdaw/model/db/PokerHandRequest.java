package com.grup.pokerdaw.api_rest_pokerdaw.model.db;

import java.util.ArrayList;

public class PokerHandRequest {
    private ArrayList<Card> hand;

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }
}
