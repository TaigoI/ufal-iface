package br.ufal.ic.prog2.Model.DAO;

import br.ufal.ic.prog2.Model.Bean.Feed;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BaseStorage {

    protected final Map<String, Feed> memoryDatabase;

    protected final Integer ID_LENGTH = 6;
    protected final String ID_PREFIX = "G";

    BaseStorage(){
        memoryDatabase = new HashMap<>();
    }

    protected String generateId(){
        Random random = new Random();
        String output = this.ID_PREFIX+"+";
        for (int j = 0; j < this.ID_LENGTH; j++){
            char base = random.nextBoolean() ? 'A' : 'a';
            output = output.concat(String.valueOf((char) (base + random.nextInt(26))));
        }
        return output;
    }
}
