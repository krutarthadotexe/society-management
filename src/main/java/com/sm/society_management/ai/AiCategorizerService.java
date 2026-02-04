package com.sm.society_management.ai;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AiCategorizerService {

    // Stopwords
    private static final Set<String> STOPWORDS = Set.of(
            "is","the","a","an","in","at","on","from","to","and","of","for"
    );

    // Category reference documents
    private static final Map<String, String> CATEGORY_DOCS = Map.of(
            "Plumbing",    "water leak pipe tap bathroom ceiling drainage flush",
            "Electricity", "power light wiring voltage switch socket outage fan",
            "Cleaning",    "garbage dirty sweep mop waste hygiene smell",
            "Security",    "theft suspicious gate guard lock broken safety",
            "Noise",       "noise loud music construction disturb night",
            "General",     "complaint request issue service help"
    );

    // Priority keyword sets
    private static final Set<String> HIGH_WORDS = Set.of(
            "fire","danger","shock","emergency","injury","broken lock","theft"
    );

    private static final Set<String> MEDIUM_WORDS = Set.of(
            "leak","not working","dirty","noise","delay","problem"
    );

    // ============================
    public JSONObject categorize(String title, String description) {

        String text = (title + " " + description).toLowerCase();

        List<String> tokens = tokenize(text);

        // Compute similarity scores
        String bestCategory = "General";
        int bestScore = 0;

        for(String category : CATEGORY_DOCS.keySet()) {
            int score = similarity(tokens, CATEGORY_DOCS.get(category));
            if(score > bestScore) {
                bestScore = score;
                bestCategory = category;
            }
        }

        // Determine priority
        String priority = detectPriority(tokens);

        JSONObject result = new JSONObject();
        result.put("category", bestCategory);
        result.put("priority", priority);

        return result;
    }

    // ============================
    private List<String> tokenize(String text){
        String[] words = text.replaceAll("[^a-z ]","").split("\\s+");
        List<String> tokens = new ArrayList<>();

        for(String w : words){
            if(w.length()>2 && !STOPWORDS.contains(w)){
                tokens.add(w);
            }
        }
        return tokens;
    }

    // ============================
    private int similarity(List<String> tokens, String referenceDoc){
        int score = 0;
        String ref = referenceDoc.toLowerCase();
        for(String token : tokens){
            if(ref.contains(token)){
                score++;
            }
        }
        return score;
    }

    // ============================
    private String detectPriority(List<String> tokens){

        for(String t : tokens){
            if(HIGH_WORDS.contains(t)) return "HIGH";
        }
        for(String t : tokens){
            if(MEDIUM_WORDS.contains(t)) return "MEDIUM";
        }
        return "LOW";
    }
}