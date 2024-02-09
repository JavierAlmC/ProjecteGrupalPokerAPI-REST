package com.grup.model.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity
public class DeckDb {
    private final List<String> suites = List.of("hearts", "diamonds", "clubs", "spades");
    private final List<Integer> values = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
    private List<CardDb> cards;

    public DeckDb() {
        List<CardDb> newCards = List.of();
        for (String suite : suites) {
            for (Integer value : values) {
                newCards.add(new CardDb(suite, value));
            }
        }
        Collections.shuffle(newCards);
        this.cards.clear();
        this.cards.addAll(newCards);
    }

    public List<CardDb> giveCards(Integer nCards, List<CardDb> cards) {
        List<CardDb> givenCards = List.of();
        givenCards = new ArrayList<>(cards.subList(0, nCards));
        cards.subList(0, nCards).clear(); // Eliminar cartas dadas de la baraja
        return givenCards;
    }
}
