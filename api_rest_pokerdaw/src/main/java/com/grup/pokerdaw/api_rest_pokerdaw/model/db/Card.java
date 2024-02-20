package com.grup.pokerdaw.api_rest_pokerdaw.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Card {
    //private static final List<String> suites = List.of("hearts", "diamonds", "clubs", "spades");
    //private static final List<Integer> values = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
    public String suit;
    public int value;

    /*public List<Card> giveCards(Integer nCards, List<Card> cards) {
        List<Card> givenCards = List.of();
        givenCards = new ArrayList<>(cards.subList(0, nCards));
        cards.subList(0, nCards).clear(); // Eliminar cartas dadas de la baraja
        return givenCards;
    }*/
    
    /*List<Card> newCards = List.of();
        for (String suite : suites) {
            for (Integer value : values) {
                newCards.add(new Card(suite, value));
            }
        }
        Collections.shuffle(newCards);
        this.cards.clear();
        this.cards.addAll(newCards);*/
}
