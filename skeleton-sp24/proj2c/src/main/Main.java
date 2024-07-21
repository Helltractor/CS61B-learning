package main;

import browser.NgordnetServer;
import handler.HistoryHandler;
import handler.HistoryTextHandler;
import handler.HyponymsHandler;
import wordnet.WordNet;
import ngrams.NGramMap;
import org.slf4j.LoggerFactory;

public class Main {
    
    static {
        LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
    }
    
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        
        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        String synsetFile = "./data/wordnet/synsets.txt";
        String hyponymFile = "./data/wordnet/hyponyms.txt";
        
        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNet wng = new WordNet(synsetFile, hyponymFile);
        
        hns.startUp();
        hns.register("history", new HistoryHandler(ngm));
        hns.register("historytext", new HistoryTextHandler(ngm));
//        hns.register("history", new DummyHistoryHandler());
//        hns.register("historytext", new DummyHistoryTextHandler());
        hns.register("hyponyms", new HyponymsHandler(ngm, wng));
        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
