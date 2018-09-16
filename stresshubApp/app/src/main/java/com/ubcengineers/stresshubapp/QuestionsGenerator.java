package com.ubcengineers.stresshubapp;

import java.util.ArrayList;
import java.util.Random;

public class QuestionsGenerator {
    private String[] questions = {"Have you felt like you've had low energy recently?",
            "Have your levels of energy felt lower than usual lately?",
            "Have you been experiencing frequent migraines or headaches recently?",
            "Have you been experiencing nausea, or have frequent stomach pain?",
            "Have you experienced any odd bowel movements, such as diarrhea or constipation?",
            "Have you been experiencing an increase in aches or tenseness of muscles?",
            "Have you had trouble sleeping lately?",
            "Have you experienced nightmares lately?",
            "Have you had the same nightmare many times?",
            "Have you experienced an above average amount of colds or infections?",
            "Have you lost sexual desire or sexual ability?",
            "Have you felt nervous or anxious recently?",
            "Have your ears been ringing?",
            "Have you had abnormally sweaty hands or feet?",
            "Have you experienced difficulty swallowing, or a dry mouth?",
            "Have you found yourself grinding your teeth, or clenching your jaw frequently?",
            "Have you been worried more than usual?",
            "Have you found your thoughts racing?",
            "Have you been forgetful or disorganized recently?",
            "Have you found it hard to focus recently?",
            "Have you been oddly negative or pessimistic recently?",
            "Have you lost appetite, or ate more than usual recently?",
            "Have you been avoiding responsibilities recently?",
            "Have you used drugs, such as cigarettes and alcohol more than normal?"
    };

    public String[] getQuestions() {
        String[] randomQuestions = new String[4];
        ArrayList<Integer> indexesUsed = new ArrayList<>();
        indexesUsed.add(randomNumberNotInArray(indexesUsed));
        indexesUsed.add(randomNumberNotInArray(indexesUsed));
        indexesUsed.add(randomNumberNotInArray(indexesUsed));
        indexesUsed.add(randomNumberNotInArray(indexesUsed));
        randomQuestions[0] = questions[indexesUsed.get(0)];
        randomQuestions[1] = questions[indexesUsed.get(1)];
        randomQuestions[2] = questions[indexesUsed.get(2)];
        randomQuestions[3] = questions[indexesUsed.get(3)];
        return randomQuestions;
    }

    private int randomNumberNotInArray(ArrayList<Integer> indexesUsed) {

        Random random = new Random();
        int randomIndex = random.nextInt(questions.length);
        while (indexesUsed.contains(randomIndex)) {
            randomIndex = random.nextInt(questions.length);
        }
        return randomIndex;
    }
}
