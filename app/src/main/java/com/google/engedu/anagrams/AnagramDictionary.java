package com.google.engedu.anagrams;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import static android.R.id.list;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private int wordlength = DEFAULT_WORD_LENGTH;
    private Random random = new Random();
    private int size, size1;
    HashSet<String> wordset = new HashSet<>();
    HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    HashMap<Integer,ArrayList<String>> sizeToWords=new HashMap<>();
    ArrayList<String> wordlist = new ArrayList<String>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordset.add(word);
            wordlist.add(word);
            String sortedword = sortLetters(word);
            ArrayList<String> wordMapList=new ArrayList<>();
            if (sizeToWords.containsKey(word.length())) {
                wordMapList = sizeToWords.get(word.length());
                wordMapList.add(word);
                sizeToWords.put(word.length(), wordMapList);
            } else {
                ArrayList<String> newWordList = new ArrayList<>();
                newWordList.add(word);
                sizeToWords.put(word.length(), newWordList);
            }
            ArrayList<String> sortedList = new ArrayList<>();
            if (!lettersToWord.containsKey(sortedword)) {
                sortedList.add(word);
                lettersToWord.put(sortedword, sortedList);
            } else {

                sortedList = lettersToWord.get(sortedword);
                sortedList.add(word);
                lettersToWord.put(sortedword, sortedList);
            }
        }
        size = wordlist.size();

    }


    public boolean isGoodWord(String word, String base) {

        if (wordset.contains(word) && !(base.contains(word)))
            return true;
        else
            return false;

    }


    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> resultlist = new ArrayList<>();
        for (char ch = 'a'; ch <= 'z'; ch++) {
            String anagram = word + ch;
            String sortedAnagram = sortLetters(anagram);
            if (lettersToWord.containsKey(sortedAnagram)) {
                temp = lettersToWord.get(sortedAnagram);
            }
            for (int i = 0; i < temp.size(); i++) {
                if (!(temp.get(i).contains(word))) {
                    resultlist.add(temp.get(i));
                }
            }

        }
        return resultlist;
    }

    public String sortLetters(String s) {
        String sorted;
        sorted = "";
        int i, j;
        char temp;
        char ch[] = new char[s.length()];
        for (i = 0; i < s.length(); i++)
            ch[i] = s.charAt(i);
        for (i = 0; i < s.length() - 1; i++) {
            for (j = 0; j < (s.length() - i - 1); j++) {
                if (ch[j] > ch[j + 1]) {
                    temp = ch[j];
                    ch[j] = ch[j + 1];
                    ch[j + 1] = temp;
                }
            }

        }
        for (i = 0; i < s.length(); i++)
            sorted = sorted + ch[i];
        return (sorted);
    }


    public String pickGoodStarterWord() {
        int randomnumber;
        String starterword;
        do {
            randomnumber = random.nextInt(sizeToWords.get(wordlength).size());
            starterword = sizeToWords.get(wordlength).get(randomnumber);
        } while (getAnagramsWithOneMoreLetter(starterword).size() <= MIN_NUM_ANAGRAMS);

        if (wordlength < MAX_WORD_LENGTH) {
            wordlength++;
        }

        return starterword;
    }

}
