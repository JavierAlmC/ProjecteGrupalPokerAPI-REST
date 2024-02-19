package com.grup.pokerdaw.api_rest_pokerdaw.srv.impl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.Card;

public class HandResolverService {

    // Clase interna para representar una carta
   

    // Método para ordenar la mano por valor ascendente
    public static ArrayList<Card> sortHandByValue(ArrayList<Card> hand) {
        ArrayList<Card> copyHand = arrangeCardValues(new ArrayList<>(hand));
        Collections.sort(copyHand, Comparator.comparingInt(a -> a.value));
        return copyHand;
    }

    // Método para ordenar la mano por valor descendente
    public static ArrayList<Card> sortHandByValueDescendant(ArrayList<Card> hand) {
        ArrayList<Card> copyHand = arrangeCardValues(new ArrayList<>(hand));
        Collections.sort(copyHand, (a, b) -> b.value - a.value);
        return copyHand;
    }

    // Método para ordenar la mano por palo
    public static ArrayList<Card> sortHandBySuit(ArrayList<Card> hand) {
        ArrayList<Card> copyHand = new ArrayList<>(hand);
        Collections.sort(copyHand, Comparator.comparing(a -> a.suit));
        return copyHand;
    }

    // Método para convertir los valores de las cartas
    public static ArrayList<Card> arrangeCardValues(ArrayList<Card> hand) {
        ArrayList<Card> copyHand = new ArrayList<>(hand);
        for (Card card : copyHand) {
            if (card.value >= 2 && card.value <= 10) {
                continue; // Los valores numéricos ya están en el formato correcto
            } else {
                switch (card.value) {
                    case 65: // 'A'
                        card.value = 14;
                        break;
                    case 74: // 'J'
                        card.value = 11;
                        break;
                    case 81: // 'Q'
                        card.value = 12;
                        break;
                    case 75: // 'K'
                        card.value = 13;
                        break;
                }
            }
        }
        return copyHand;
    }

    // Método para verificar si hay una escalera
    public static boolean isStraight(ArrayList<Card> hand) {
        ArrayList<Card> sortedHand = sortHandByValue(hand);
        ArrayList<Card> straightCards = new ArrayList<>();
        for (int i = 0; i < sortedHand.size() - 1; i++) {
            if (sortedHand.get(i).value == sortedHand.get(i + 1).value - 1) {
                straightCards.add(sortedHand.get(i));
            } else if (sortedHand.get(i).value - sortedHand.get(i + 1).value != 0) {
                straightCards.clear();
            }
        }
        if (sortedHand.get(sortedHand.size() - 2).value == sortedHand.get(sortedHand.size() - 1).value - 1) {
            straightCards.add(sortedHand.get(sortedHand.size() - 1));
        }
        return straightCards.size() >= 5;
    }

    // Método para verificar si hay cartas del mismo palo
    public static ArrayList<Card> isSameSuit(ArrayList<Card> hand) {
        ArrayList<Card> sortedHand = sortHandBySuit(hand);
        HashMap<String, ArrayList<Card>> suitCounter = new HashMap<>();
        suitCounter.put("hearts", new ArrayList<>());
        suitCounter.put("spades", new ArrayList<>());
        suitCounter.put("diamonds", new ArrayList<>());
        suitCounter.put("clubs", new ArrayList<>());
        for (Card card : sortedHand) {
            suitCounter.get(card.suit).add(card);
        }
        for (ArrayList<Card> cards : suitCounter.values()) {
            if (cards.size() >= 5) {
                return cards;
            }
        }
        return new ArrayList<>();
    }

    // Método para verificar si hay una escalera real
    public static boolean isRoyalFlush(ArrayList<Card> sameSuitCards) {
        for (Card card : sameSuitCards) {
            if (card.value == 10 || card.value == 14) {
                return true;
            }
        }
        return false;
    }

    // Método para encontrar cartas iguales en la mano
    public static HashMap<String, Object> equalCardsFinder(ArrayList<Card> hand) {
        ArrayList<Card> copyHand = arrangeCardValues(new ArrayList<>(hand));
        HashMap<Integer, ArrayList<Card>> pairsFound = new HashMap<>();
        for (Card card : copyHand) {
            if (pairsFound.containsKey(card.value)) {
                pairsFound.get(card.value).add(card);
            } else {
                pairsFound.put(card.value, new ArrayList<>(Collections.singletonList(card)));
            }
        }
        ArrayList<ArrayList<Card>> equalCards = new ArrayList<>(pairsFound.values());
        Collections.sort(equalCards, (a, b) -> b.size() - a.size());
        if (equalCards.get(0).size() == 4) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("name", "Four of a Kind");
            result.put("score", 8);
            result.put("highCard", equalCards.get(0).get(0));
            return result;
        }
        if (equalCards.get(0).size() == 3) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("name", "Three of a Kind");
            result.put("score", 4);
            result.put("highCard", highestCardFinder(equalCards.get(0)));
            return result;
        }
        if (equalCards.get(0).size() == 2 && equalCards.size() >= 2 && equalCards.get(1).size() >= 2) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("name", "Full House");
            result.put("score", 7);
            result.put("highCard", equalCards.get(0).get(0));
            return result;
        }
        if (equalCards.get(0).size() == 2 && equalCards.size() == 2) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("name", "Two Pair");
            result.put("score", 3);
            result.put("highCard", equalCards.get(0).get(0));
            return result;
        }
        if (equalCards.get(0).size() == 2) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("name", "Pair");
            result.put("score", 2);
            result.put("highCard", highestCardFinder(equalCards.get(0)));
            return result;
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", "High Card");
        result.put("score", 1);
        result.put("highCard", highestCardFinder(hand));
        return result;
    }

    // Método para encontrar la carta de mayor valor en la mano
    public static Card highestCardFinder(ArrayList<Card> hand) {
        Card highestCard = hand.get(0);
        for (Card card : hand) {
            if (card.value > highestCard

.value) {
                highestCard = card;
            }
        }
        return highestCard;
    }

    // Método principal para resolver la mano
    public static HashMap<String, Object> handResolver(ArrayList<Card> hand) {
        ArrayList<Card> sameSuitCards = isSameSuit(hand);
        boolean checkForStraight = isStraight(hand);

        // Escala Real, Escala de color, Color
        if (sameSuitCards.size() >= 5) {
            // Escala Real, Escala de color
            if (checkForStraight) {
                // Escala Real
                if (isRoyalFlush(sameSuitCards)) {
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("name", "Royal Flush");
                    result.put("score", 10);
                    result.put("highCard", new Card("", 14));
                    return result;
                } else {
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("name", "Straight Flush");
                    result.put("score", 9);
                    result.put("highCard", highestCardFinder(hand));
                    return result;
                }
            } else {
                HashMap<String, Object> result = new HashMap<>();
                result.put("name", "Flush");
                result.put("score", 6);
                result.put("highCard", sameSuitCards.get(sameSuitCards.size() - 1));
                return result;
            }
        }

        // Escala
        if (checkForStraight) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("name", "Straight");
            result.put("score", 5);
            result.put("highCard", highestCardFinder(hand));
            return result;
        }

        // Poker, Full, Trio, Doble Parella, Parella
        return equalCardsFinder(hand);
    }

    // Método principal de prueba
    public static void main(String[] args) {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card("hearts", 14));
        hand.add(new Card("hearts", 13));
        hand.add(new Card("hearts", 12));
        hand.add(new Card("hearts", 11));
        hand.add(new Card("hearts", 10));

        HashMap<String, Object> result = handResolver(hand);
        System.out.println("Hand Name: " + result.get("name"));
        System.out.println("Hand Score: " + result.get("score"));
        System.out.println("High Card: " + ((Card) result.get("highCard")).value + " of " + ((Card) result.get("highCard")).suit);
    }
}
