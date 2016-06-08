package guessthishero.myriadstudio72.com.guessthishero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Hero {

    private String m_HeroName;
    private Integer[] m_ResourcesID;
    private int randomResource;
    private Random m_Random = new Random();

    public Hero(String heroName, Integer[] resourcesID) {
        m_HeroName = heroName;
        this.m_ResourcesID = resourcesID;

        randomResource = m_ResourcesID[m_Random.nextInt(m_ResourcesID.length)];
    }

    public String getName() {
        return m_HeroName.toUpperCase();
    }

    public Integer getResource() {
        return randomResource;
    }

    /**
     * Fill the name of hero from input with random characters until the size of result will be the size of @LETTERS_NR
     *
     * @return
     */
    public ArrayList<Character> getFilledChars() {

        ArrayList<Character> result = new ArrayList<>(getUniqueChars(getName()));
        Character character;

        while (result.size() < IConst.LETTERS_NR) {
            character = IConst.m_Alphabet.get(m_Random.nextInt(IConst.m_Alphabet.size()));
            if (!result.contains(character))
                result.add(character);
        }
        Collections.shuffle(result);
        return result;
    }

    /**
     * Return inputted string without any duplicate characters and in a random order.
     *
     * @param input
     * @return
     */
    private ArrayList<Character> getUniqueChars(String input) {
        ArrayList<Character> result = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            if (result.contains(input.charAt(i)) || input.charAt(i) == '\0') continue;
            result.add(input.charAt(i));
        }
        return result;
    }
}
