package br.ufal.ic.prog2.Model.DAO;

import br.ufal.ic.prog2.Model.Bean.Feed;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BaseStorage<T> {

    protected Map<String, T> memoryDatabase;

    protected final Integer ID_LENGTH = 5;

    BaseStorage(){
        memoryDatabase = new HashMap<>();
    }

    protected String generateId(String prefix){
        return generateId(prefix, ID_LENGTH);
    }

    protected String generateId(String prefix, Integer length){
        Random random = new Random();
        String output = prefix+"+";
        for (int j = 0; j < length; j++){
            char base = random.nextBoolean() ? 'A' : 'a';
            output = output.concat(String.valueOf((char) (base + random.nextInt(26))));
        }
        return output;
    }

    public Map<String, T> getMemoryDatabase() {
        return memoryDatabase;
    }
}
