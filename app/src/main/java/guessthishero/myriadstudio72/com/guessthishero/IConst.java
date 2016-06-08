package guessthishero.myriadstudio72.com.guessthishero;

import java.util.ArrayList;
import java.util.Arrays;


public interface IConst {
    enum enumButtons {
        START,
        DONE,
        REMOVE,
        HINT,
        REWARD,
        BACK
    }

    enum enumText {
        MONEY,
        BOX_TYPE,
        BOOK,
        LIFE
    }

    enum enumShared{
        HEROES,
        HERO_ID,
        MONEY,
        LIFE
    }

    ArrayList<Character> m_Alphabet =
            new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                    'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));

    /**
     * You need to put your own admob id instead of 'x'.
     */
    String AD_REWARD_ID = "ca-app-pub-xxxxxxxxxxxxxxxxxxxxxxxxxxx";
    String AD_INTERSTITIAL_ID = "ca-app-pub-xxxxxxxxxxxxxxxxxxxxxxxxxxx";
    String APP_ID = "ca-app-pub-xxxxxxxxxxxxxxxxxxxxxxxxxxx";


    String SHARED_PREFERENCES_NAME = "PRODUCT_APP";
    String SHARED_HEROES = "heroes";
    String SHARED_LIFE = "life";
    String SHARED_MONEY = "money";
    String SHARED_HERO_ID = "heroID";

    String MSG_CORRECT = "Correct!";
    String MSG_INCORRECT = "Incorrect!";
    String MSG_NO_GOLD = "Not enough gold!";
    String MSG_NOT_READY = "Ad is not ready!";
    String MSG_WIN_GAME = "Congratulations, you won the game!";

    int HEROES_NUMBERS = 107;
    int MAX_LIFE = 3;
    int MONEY_BONUS = 10;
    int HINT_VALUE = 15;
    int LETTERS_NR = 14;
    int MIN_VIDEO_REWARD = 5;
    int MAX_VIDEO_REWARD = 20;
}
