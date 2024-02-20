package com.grup.pokerdaw.api_rest_pokerdaw.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.Card;
import com.grup.pokerdaw.api_rest_pokerdaw.model.db.PokerHandRequest;
import com.grup.pokerdaw.api_rest_pokerdaw.model.db.PokerHandResponse;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.impl.HandResolverService;

@RestController
public class PokerHandController {

    @PostMapping("/resolveHand")
    public PokerHandResponse resolveHand(@RequestBody PokerHandRequest request) {
        ArrayList<Card> hand = new ArrayList<>(request.getHand());

        HashMap<String, Object> result = HandResolverService.handResolver(hand);

        PokerHandResponse response = new PokerHandResponse();
        response.setName((String) result.get("name"));
        response.setScore((int) result.get("score"));
        response.setHighCard((Card) result.get("highCard"));

        return response;
    }

     @PostMapping("/resolverMano")
    public ResponseEntity<HashMap<String, Object>> resolverMano(@RequestBody ArrayList<Card> hand) {
        try {
            HashMap<String, Object> result = HandResolverService.handResolver(hand);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
    
}

