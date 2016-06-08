package guessthishero.myriadstudio72.com.guessthishero;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Content extends Activity {

    InterstitialAd m_InterstitialAd;
    RewardedVideoAd m_VideoAd;
    boolean m_IsVideoAdReady;
    final Object m_Lock = new Object();

    Integer m_Life = null;
    Integer m_Money = null;
    List<Hero> m_Heroes = null;
    Integer m_HeroID = null;
    Integer m_Achievement = null;

    SharedPreferences m_SharedSettings;
    SharedPreferences.Editor m_SharedEditor;

    Map<IConst.enumButtons, ImageButton> m_Buttons = new HashMap<>();
    Map<IConst.enumText, TextView> m_Text = new HashMap<>();
    TextView m_TextLetter[] = new TextView[14];
    ImageButton m_ButtonLetter[] = new ImageButton[14];

    MediaPlayer m_MediaPlayer = new MediaPlayer();
    Random m_Random = new Random();

    /**
     * Get coins for watching video ad.
     *
     * @return
     */
    int getVideoAdBonusMoney() {
        return m_Random.nextInt(IConst.MAX_VIDEO_REWARD - IConst.MIN_VIDEO_REWARD) + IConst.MIN_VIDEO_REWARD;
    }

    /**
     * Get achievement and update ui.
     */
    void updateAchievement() {
        float result;
        if (m_Heroes.size() != 0)
            result = ((float) m_Heroes.size() / (float) IConst.HEROES_NUMBERS) * 100f;
        else
            result = 0f;

        m_Achievement = (int) (100f - result);
        m_Text.get(IConst.enumText.BOOK).setText(m_Achievement + "%");
    }

    /**
     * Return a random hero.
     */
    void getRandomHero() {
        m_HeroID = m_Random.nextInt(m_Heroes.size());
    }

    /**
     * List all heroes into @m_Heroes.
     */
    void initHeroes() {
        m_Heroes = new ArrayList<>();
        m_Heroes.add(new Hero("Abaddon", new Integer[]{R.raw.abaddon_1, R.raw.abaddon_2, R.raw.abaddon_3}));
        m_Heroes.add(new Hero("Alchemist", new Integer[]{R.raw.alchimist_1, R.raw.alchimist_2, R.raw.alchimist_3}));
        m_Heroes.add(new Hero("Ancient Apparition", new Integer[]{R.raw.ancient_1, R.raw.ancient_2, R.raw.ancient_3}));
        m_Heroes.add(new Hero("Anti-Mage", new Integer[]{R.raw.antimage_1, R.raw.antimage_2, R.raw.antimage_3}));
        m_Heroes.add(new Hero("Axe", new Integer[]{R.raw.axe_1, R.raw.axe_2, R.raw.axe_3}));
        m_Heroes.add(new Hero("Bane", new Integer[]{R.raw.bane_1, R.raw.bane_2, R.raw.bane_3}));
        m_Heroes.add(new Hero("Batrider", new Integer[]{R.raw.batrider_1, R.raw.batrider_2, R.raw.batrider_3}));
        m_Heroes.add(new Hero("Beastmaster", new Integer[]{R.raw.beastmaster_1, R.raw.beastmaster_2, R.raw.beastmaster_3}));
        m_Heroes.add(new Hero("Bloodseeker", new Integer[]{R.raw.bloodseeker_1, R.raw.bloodseeker_2, R.raw.bloodseeker_3}));
        m_Heroes.add(new Hero("Bounty Hunter", new Integer[]{R.raw.bounty_1, R.raw.bounty_2, R.raw.bounty_3}));
        m_Heroes.add(new Hero("Brewmaster", new Integer[]{R.raw.brewmaster_1, R.raw.brewmaster_2, R.raw.brewmaster_3}));
        m_Heroes.add(new Hero("Bristleback", new Integer[]{R.raw.bristleback_1, R.raw.bristleback_2, R.raw.bristleback_3}));
        m_Heroes.add(new Hero("Broodmother", new Integer[]{R.raw.broodmother_1, R.raw.broodmother_2, R.raw.broodmother_3}));
        m_Heroes.add(new Hero("Centaur Warrunner", new Integer[]{R.raw.centaur_1, R.raw.centaur_2, R.raw.centaur_3}));
        m_Heroes.add(new Hero("Chaos Knight", new Integer[]{R.raw.chaos_1, R.raw.chaos_2, R.raw.chaos_3}));
        m_Heroes.add(new Hero("Chen", new Integer[]{R.raw.chen_1, R.raw.chen_2, R.raw.chen_3}));
        m_Heroes.add(new Hero("Clinkz", new Integer[]{R.raw.clinkz_1, R.raw.clinkz_2, R.raw.clinkz_3}));
        m_Heroes.add(new Hero("Clockwerk", new Integer[]{R.raw.clockwerk_1, R.raw.clockwerk_2, R.raw.clockwerk_3}));
        m_Heroes.add(new Hero("Crystal Maiden", new Integer[]{R.raw.crystal_1, R.raw.crystal_2, R.raw.crystal_3}));
        m_Heroes.add(new Hero("Dark Seer", new Integer[]{R.raw.darkseer_1, R.raw.darkseer_2, R.raw.darkseer_3}));
        m_Heroes.add(new Hero("Dazzle", new Integer[]{R.raw.dazzle_1, R.raw.dazzle_2, R.raw.dazzle_3}));
        m_Heroes.add(new Hero("Death Prophet", new Integer[]{R.raw.death_1, R.raw.death_2, R.raw.death_3}));
        m_Heroes.add(new Hero("Disruptor", new Integer[]{R.raw.disruptor_1, R.raw.disruptor_2, R.raw.disruptor_3}));
        m_Heroes.add(new Hero("Doom", new Integer[]{R.raw.doom_1, R.raw.doom_2, R.raw.doom_3}));
        m_Heroes.add(new Hero("Dragon Knight", new Integer[]{R.raw.dragon_1, R.raw.dragon_2, R.raw.dragon_3}));
        m_Heroes.add(new Hero("Drow Ranger", new Integer[]{R.raw.drow_1, R.raw.drow_2, R.raw.drow_3}));
        m_Heroes.add(new Hero("Earth Spirit", new Integer[]{R.raw.earthspirit_1, R.raw.earthspirit_2, R.raw.earthspirit_3}));
        m_Heroes.add(new Hero("Earthshaker", new Integer[]{R.raw.earthshaker_1, R.raw.earthshaker_2, R.raw.earthshaker_3}));
        m_Heroes.add(new Hero("Elder Titan", new Integer[]{R.raw.elder_1, R.raw.elder_2, R.raw.elder_3}));
        m_Heroes.add(new Hero("Ember Spirit", new Integer[]{R.raw.ember_1, R.raw.ember_2, R.raw.ember_3}));
        m_Heroes.add(new Hero("Enchantress", new Integer[]{R.raw.enchantress_1, R.raw.enchantress_2, R.raw.enchantress_3}));
        m_Heroes.add(new Hero("Enigma", new Integer[]{R.raw.enigma_1, R.raw.enigma_2, R.raw.enigma_3}));
        m_Heroes.add(new Hero("Faceless Void", new Integer[]{R.raw.void_1, R.raw.void_2, R.raw.void_3}));
        m_Heroes.add(new Hero("Gyrocopter", new Integer[]{R.raw.gyrocopter_1, R.raw.gyrocopter_2, R.raw.gyrocopter_3}));
        m_Heroes.add(new Hero("Huskar", new Integer[]{R.raw.huskar_1, R.raw.huskar_2, R.raw.huskar_3}));
        m_Heroes.add(new Hero("Invoker", new Integer[]{R.raw.invoker_1, R.raw.invoker_2, R.raw.invoker_3}));
        m_Heroes.add(new Hero("Jakiro", new Integer[]{R.raw.jakiro_1, R.raw.jakiro_2, R.raw.jakiro_3}));
        m_Heroes.add(new Hero("Juggernaut", new Integer[]{R.raw.juggernaut_1, R.raw.juggernaut_2, R.raw.juggernaut_3}));
        m_Heroes.add(new Hero("Keeper of the Light", new Integer[]{R.raw.keeper_1, R.raw.keeper_2, R.raw.keeper_3}));
        m_Heroes.add(new Hero("Kunkka", new Integer[]{R.raw.kunkka_1, R.raw.kunkka_2, R.raw.kunkka_3}));
        m_Heroes.add(new Hero("Legion Commander", new Integer[]{R.raw.legion_1, R.raw.legion_2, R.raw.legion_3}));
        m_Heroes.add(new Hero("Leshrac", new Integer[]{R.raw.lesharc_1, R.raw.lesharc_2, R.raw.lesharc_3}));
        m_Heroes.add(new Hero("Lich", new Integer[]{R.raw.lich_1, R.raw.lich_2, R.raw.lich_3}));
        m_Heroes.add(new Hero("Lifestealer", new Integer[]{R.raw.lifestealer_1, R.raw.lifestealer_2, R.raw.lifestealer_3}));
        m_Heroes.add(new Hero("Lina", new Integer[]{R.raw.lina_1, R.raw.lina_2, R.raw.lina_3}));
        m_Heroes.add(new Hero("Lion", new Integer[]{R.raw.lion_1, R.raw.lion_2, R.raw.lion_3}));
        m_Heroes.add(new Hero("Lone Druid", new Integer[]{R.raw.lone_1, R.raw.lone_2, R.raw.lone_3}));
        m_Heroes.add(new Hero("Luna", new Integer[]{R.raw.luna_1, R.raw.luna_2, R.raw.luna_3}));
        m_Heroes.add(new Hero("Lycan", new Integer[]{R.raw.lycan_1, R.raw.lycan_2, R.raw.lycan_3}));
        m_Heroes.add(new Hero("Magnus", new Integer[]{R.raw.magnus_1, R.raw.magnus_2, R.raw.magnus_3}));
        m_Heroes.add(new Hero("Medusa", new Integer[]{R.raw.medusa_1, R.raw.medusa_2, R.raw.medusa_3}));
        m_Heroes.add(new Hero("Meepo", new Integer[]{R.raw.meepo_1, R.raw.meepo_2, R.raw.meepo_3}));
        m_Heroes.add(new Hero("Mirana", new Integer[]{R.raw.mirana_1, R.raw.mirana_2, R.raw.mirana_3}));
        m_Heroes.add(new Hero("Morphling", new Integer[]{R.raw.morphling_1, R.raw.morphling_2, R.raw.morphling_3}));
        m_Heroes.add(new Hero("Naga Siren", new Integer[]{R.raw.naga_1, R.raw.naga_2, R.raw.naga_3}));
        m_Heroes.add(new Hero("Nature's Prophet", new Integer[]{R.raw.nature_1, R.raw.nature_2, R.raw.nature_3}));
        m_Heroes.add(new Hero("Necrophos", new Integer[]{R.raw.necro_1, R.raw.necro_2, R.raw.necro_3}));
        m_Heroes.add(new Hero("Night Stalker", new Integer[]{R.raw.nightstalker_1, R.raw.nightstalker_2, R.raw.nightstalker_3}));
        m_Heroes.add(new Hero("Nyx Assassin", new Integer[]{R.raw.nyx_1, R.raw.nyx_2, R.raw.nyx_3}));
        m_Heroes.add(new Hero("Ogre Magi", new Integer[]{R.raw.ogre_1, R.raw.ogre_2, R.raw.ogre_3}));
        m_Heroes.add(new Hero("Omniknight", new Integer[]{R.raw.omni_1, R.raw.omni_2, R.raw.omni_3}));
        m_Heroes.add(new Hero("Oracle", new Integer[]{R.raw.oracle_1, R.raw.oracle_2, R.raw.oracle_3}));
        m_Heroes.add(new Hero("Outworld Devourer", new Integer[]{R.raw.outworld_1, R.raw.outworld_2, R.raw.outworld_3}));
        m_Heroes.add(new Hero("Phantom Assassin", new Integer[]{R.raw.phassassin_1, R.raw.phassassin_2, R.raw.phassassin_3}));
        m_Heroes.add(new Hero("Phantom Lancer", new Integer[]{R.raw.phlancer_1, R.raw.phlancer_2, R.raw.phlancer_3}));
        m_Heroes.add(new Hero("Puck", new Integer[]{R.raw.puck_1, R.raw.puck_2, R.raw.puck_3}));
        m_Heroes.add(new Hero("Pudge", new Integer[]{R.raw.pudge_1, R.raw.pudge_2, R.raw.pudge_3}));
        m_Heroes.add(new Hero("Pugna", new Integer[]{R.raw.pugna_1, R.raw.pugna_2, R.raw.pugna_3}));
        m_Heroes.add(new Hero("Queen of Pain", new Integer[]{R.raw.queen_1, R.raw.queen_2, R.raw.queen_3}));
        m_Heroes.add(new Hero("Razor", new Integer[]{R.raw.razor_1, R.raw.razor_2, R.raw.razor_3}));
        m_Heroes.add(new Hero("Riki", new Integer[]{R.raw.riki_1, R.raw.riki_2, R.raw.riki_3}));
        m_Heroes.add(new Hero("Rubick", new Integer[]{R.raw.rubick_1, R.raw.rubick_2, R.raw.rubick_3}));
        m_Heroes.add(new Hero("Sand King", new Integer[]{R.raw.sandking_1, R.raw.sandking_2, R.raw.sandking_3}));
        m_Heroes.add(new Hero("Shadow Demon", new Integer[]{R.raw.shadowdemon_1, R.raw.shadowdemon_2, R.raw.shadowdemon_3}));
        m_Heroes.add(new Hero("Shadow Fiend", new Integer[]{R.raw.shadowfiend_1, R.raw.shadowfiend_2, R.raw.shadowfiend_3}));
        m_Heroes.add(new Hero("Shadow Shaman", new Integer[]{R.raw.shadowshaman_1, R.raw.shadowshaman_2, R.raw.shadowshaman_3}));
        m_Heroes.add(new Hero("Skywrath Mage", new Integer[]{R.raw.skywrath_1, R.raw.skywrath_2, R.raw.skywrath_3}));
        m_Heroes.add(new Hero("Slardar", new Integer[]{R.raw.slardar_1, R.raw.slardar_2, R.raw.slardar_3}));
        m_Heroes.add(new Hero("Slark", new Integer[]{R.raw.slark_1, R.raw.slark_2, R.raw.slark_3}));
        m_Heroes.add(new Hero("Sniper", new Integer[]{R.raw.sniper_1, R.raw.sniper_2, R.raw.sniper_3}));
        m_Heroes.add(new Hero("Spectre", new Integer[]{R.raw.spectre_1, R.raw.spectre_2, R.raw.spectre_3}));
        m_Heroes.add(new Hero("Spirit Breaker", new Integer[]{R.raw.spiritbreaker_1, R.raw.spiritbreaker_2, R.raw.spiritbreaker_3}));
        m_Heroes.add(new Hero("Storm Spirit", new Integer[]{R.raw.storm_1, R.raw.storm_2, R.raw.storm_3}));
        m_Heroes.add(new Hero("Sven", new Integer[]{R.raw.sven_1, R.raw.sven_2, R.raw.sven_3}));
        m_Heroes.add(new Hero("Techies", new Integer[]{R.raw.techies_1, R.raw.techies_2, R.raw.techies_3}));
        m_Heroes.add(new Hero("Templar Assassin", new Integer[]{R.raw.templar_1, R.raw.templar_2, R.raw.templar_3}));
        m_Heroes.add(new Hero("Terrorblade", new Integer[]{R.raw.terrorbalde_1, R.raw.terrorbalde_2, R.raw.terrorbalde_3}));
        m_Heroes.add(new Hero("Tidehunter", new Integer[]{R.raw.tidehunter_1, R.raw.tidehunter_2, R.raw.tidehunter_3}));
        m_Heroes.add(new Hero("Timbersaw", new Integer[]{R.raw.timbersaw_1, R.raw.timbersaw_2, R.raw.timbersaw_3}));
        m_Heroes.add(new Hero("Tinker", new Integer[]{R.raw.tinker_1, R.raw.tinker_2, R.raw.tinker_3}));
        m_Heroes.add(new Hero("Tiny", new Integer[]{R.raw.tiny_1, R.raw.tiny_2, R.raw.tiny_3}));
        m_Heroes.add(new Hero("Treant Protector", new Integer[]{R.raw.treant_1, R.raw.treant_2, R.raw.treant_3}));
        m_Heroes.add(new Hero("Troll Warlord", new Integer[]{R.raw.troll_1, R.raw.troll_2, R.raw.troll_3}));
        m_Heroes.add(new Hero("Tusk", new Integer[]{R.raw.tusk_1, R.raw.tusk_2, R.raw.tusk_3}));
        m_Heroes.add(new Hero("Undying", new Integer[]{R.raw.undying_1, R.raw.undying_2, R.raw.undying_3}));
        m_Heroes.add(new Hero("Ursa", new Integer[]{R.raw.ursa_1, R.raw.ursa_2, R.raw.ursa_3}));
        m_Heroes.add(new Hero("Vengeful Spirit", new Integer[]{R.raw.vengeful_1, R.raw.vengeful_2, R.raw.vengeful_3}));
        m_Heroes.add(new Hero("Venomancer", new Integer[]{R.raw.venomancer_1, R.raw.venomancer_2, R.raw.venomancer_3}));
        m_Heroes.add(new Hero("Viper", new Integer[]{R.raw.viper_1, R.raw.viper_2, R.raw.viper_3}));
        m_Heroes.add(new Hero("Visage", new Integer[]{R.raw.visage_1, R.raw.visage_2, R.raw.visage_3}));
        m_Heroes.add(new Hero("Warlock", new Integer[]{R.raw.warlock_1, R.raw.warlock_2, R.raw.warlock_3}));
        m_Heroes.add(new Hero("Weaver", new Integer[]{R.raw.weaver_1, R.raw.weaver_2, R.raw.weaver_3}));
        m_Heroes.add(new Hero("Windranger", new Integer[]{R.raw.windranger_1, R.raw.windranger_2, R.raw.windranger_3}));
        m_Heroes.add(new Hero("Winter Wyvern", new Integer[]{R.raw.winter_1, R.raw.winter_2, R.raw.winter_3}));
        m_Heroes.add(new Hero("Witch Doctor", new Integer[]{R.raw.witchdoctor_1, R.raw.witchdoctor_2, R.raw.witchdoctor_3}));
        m_Heroes.add(new Hero("Wraith King", new Integer[]{R.raw.wraithking_1, R.raw.wraithking_2, R.raw.wraithking_3}));
        m_Heroes.add(new Hero("Zeus", new Integer[]{R.raw.zeus_1, R.raw.zeus_2, R.raw.zeus_3}));
    }

    /**
     * Save game info to shared external storage.
     *
     * @param input - object to be updated.
     */
    void updateShared(IConst.enumShared input) {

        switch (input) {
            case HEROES:
                Gson gson = new Gson();
                String json = gson.toJson(m_Heroes);
                m_SharedEditor.putString(IConst.SHARED_HEROES, json);
                m_SharedEditor.commit();
                break;

            case HERO_ID:
                m_SharedEditor.putInt(IConst.SHARED_HERO_ID, m_HeroID);
                m_SharedEditor.commit();
                break;

            case MONEY:
                m_SharedEditor.putInt(IConst.SHARED_MONEY, m_Money);
                m_SharedEditor.commit();
                break;

            case LIFE:
                m_SharedEditor.putInt(IConst.SHARED_LIFE, m_Life);
                m_SharedEditor.commit();
                break;

            default:
                throw new IllegalArgumentException("Enum missing");
        }


    }

    /**
     * Get game info from shared external storage.
     *
     * @param input - object how get info.
     * @return
     */
    <T> T getShared(IConst.enumShared input) {

        switch (input) {
            case HEROES:
                List<Hero> heroesList = null;
                if (m_SharedSettings.contains(IConst.SHARED_HEROES)) {
                    String json = m_SharedSettings.getString(IConst.SHARED_HEROES, null);
                    Gson gson = new Gson();
                    Hero[] heroesArray = gson.fromJson(json,
                            Hero[].class);

                    heroesList = Arrays.asList(heroesArray);
                    heroesList = new ArrayList<>(heroesList);
                }
                return (T) heroesList;

            case HERO_ID:
                return (T) Integer.valueOf(m_SharedSettings.getInt(IConst.SHARED_HERO_ID, -1));

            case MONEY:
                return (T) Integer.valueOf(m_SharedSettings.getInt(IConst.SHARED_MONEY, -1));

            case LIFE:
                return (T) Integer.valueOf(m_SharedSettings.getInt(IConst.SHARED_LIFE, -1));

            default:
                throw new IllegalArgumentException("Enum missing");
        }
    }

    /**
     * Modify @m_Money value and save it to external storage.
     *
     * @param value - value to be modified.
     */
    void changeMoney(int value) {
        m_Money += value;
        if (m_Money < 0)
            m_Money = 0;
        updateShared(IConst.enumShared.MONEY);
        m_Text.get(IConst.enumText.MONEY).setText(String.valueOf(m_Money));
    }

    /**
     * Modify @m_Life value and save it to external storage.
     *
     * @param value - value to be modified.
     */
    void changeLife(int value) {
        m_Life += value;
        updateShared(IConst.enumShared.LIFE);
        m_Text.get(IConst.enumText.LIFE).setText("x" + m_Life);
    }

    /**
     * Fill letter buttons with characters from specified hero name and the rest with random characters.
     */
    void fillLetterButtons() {
        ArrayList<Character> name = m_Heroes.get(m_HeroID).getFilledChars();

        for (int i = 0; i < 14; i++) {
            m_TextLetter[i].setText(String.valueOf(name.get(i)));
        }
    }

    /**
     * Init everything, get saves from external storage and update ui.
     */
    void init() {
        m_SharedSettings = getSharedPreferences(IConst.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        m_SharedEditor = m_SharedSettings.edit();

        if (m_Heroes == null) {
            List<Hero> heroes = (ArrayList<Hero>) getShared(IConst.enumShared.HEROES);

            if (heroes == null) {
                initHeroes();
                updateShared(IConst.enumShared.HEROES);
            } else
                m_Heroes = heroes;
        }

        if (m_HeroID == null) {
            int heroID = getShared(IConst.enumShared.HERO_ID);

            if (heroID == -1) {
                getRandomHero();
                updateShared(IConst.enumShared.HERO_ID);
            } else {
                m_HeroID = heroID;
            }
        }

        if (m_Life == null) {
            int life = getShared(IConst.enumShared.LIFE);

            if (life == -1) {
                m_Life = IConst.MAX_LIFE;
                updateShared(IConst.enumShared.LIFE);
            } else
                m_Life = life;
        }

        if (m_Money == null) {
            int money = getShared(IConst.enumShared.MONEY);

            if (money == -1) {
                m_Money = 0;
                updateShared(IConst.enumShared.MONEY);
            } else
                m_Money = money;
        }

        int resID;

        for (int i = 0; i < 14; i++) {
            resID = getResources().getIdentifier("buttonLetter" + (i + 1), "id", getPackageName());
            m_ButtonLetter[i] = (ImageButton) findViewById(resID);

            resID = getResources().getIdentifier("textLetter" + (i + 1), "id", getPackageName());
            m_TextLetter[i] = (TextView) findViewById(resID);
        }

        m_Text.put(IConst.enumText.BOX_TYPE, (TextView) findViewById(R.id.textBoxType));
        m_Text.put(IConst.enumText.BOOK, (TextView) findViewById(R.id.textBook));
        m_Text.put(IConst.enumText.LIFE, (TextView) findViewById(R.id.textBottle));
        m_Text.put(IConst.enumText.MONEY, (TextView) findViewById(R.id.textMoney));

        m_Buttons.put(IConst.enumButtons.START, (ImageButton) findViewById(R.id.buttonStart));
        m_Buttons.put(IConst.enumButtons.REMOVE, (ImageButton) findViewById(R.id.buttonRemove));
        m_Buttons.put(IConst.enumButtons.DONE, (ImageButton) findViewById(R.id.buttonDone));
        m_Buttons.put(IConst.enumButtons.HINT, (ImageButton) findViewById(R.id.buttonHint));
        m_Buttons.put(IConst.enumButtons.REWARD, (ImageButton) findViewById(R.id.buttonReward));
        m_Buttons.put(IConst.enumButtons.BACK, (ImageButton) findViewById(R.id.buttonBack));

        m_Buttons.get(IConst.enumButtons.START).setOnClickListener(handleStartButton);
        m_Buttons.get(IConst.enumButtons.REMOVE).setOnClickListener(handleRemoveButton);
        m_Buttons.get(IConst.enumButtons.DONE).setOnClickListener(handleDoneButton);
        m_Buttons.get(IConst.enumButtons.HINT).setOnClickListener(handleHintButton);
        m_Buttons.get(IConst.enumButtons.REWARD).setOnClickListener(handleRewardButton);
        m_Buttons.get(IConst.enumButtons.BACK).setOnClickListener(handleBackButton);

        for (int i = 0; i < 14; i++)
            m_ButtonLetter[i].setOnClickListener(handleLetterButtons);

    }

    /**
     * Load video ad.
     */
    void loadRewardedVideoAd() {
        synchronized (m_Lock) {
            if (!m_IsVideoAdReady && !m_VideoAd.isLoaded()) {
                m_IsVideoAdReady = true;
                Bundle extras = new Bundle();
                extras.putBoolean("_noRefresh", true);
                AdRequest adRequest = new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build();

//                AdRequest adRequest = new AdRequest.Builder()
//                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                        .build();


                m_VideoAd.loadAd(IConst.AD_REWARD_ID, adRequest);
            }
        }
    }

    /**
     * Load interstitial ad.
     */
    void loadInterstitialAd() {
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .build();
        Bundle extras = new Bundle();
        extras.putBoolean("_noRefresh", true);
        AdRequest adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build();

        m_InterstitialAd.loadAd(adRequest);
    }

    /**
     * Init ads.
     */
    void initAds() {
        MobileAds.initialize(Content.this, IConst.APP_ID);

        m_InterstitialAd = new InterstitialAd(Content.this);
        m_InterstitialAd.setAdUnitId(IConst.AD_INTERSTITIAL_ID);
        m_InterstitialAd.setAdListener(handleInterstitialAd);
        loadInterstitialAd();

        m_VideoAd = MobileAds.getRewardedVideoAdInstance(this);
        m_VideoAd.setRewardedVideoAdListener(handleRewardAd);
        loadRewardedVideoAd();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.content);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initAds();
        init();
        m_Text.get(IConst.enumText.LIFE).setText("x" + m_Life);
        m_Text.get(IConst.enumText.MONEY).setText(String.valueOf(m_Money));
        updateAchievement();
        m_Text.get(IConst.enumText.BOX_TYPE).setText("");
        fillLetterButtons();
    }

    View.OnClickListener handleStartButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (m_Achievement == 100) {
                m_Life = IConst.MAX_LIFE;
                initHeroes();
                updateShared(IConst.enumShared.HEROES);
                getRandomHero();
                updateShared(IConst.enumShared.HERO_ID);
                updateAchievement();
                m_Text.get(IConst.enumText.BOX_TYPE).setText("");
                fillLetterButtons();
            }

            if (!m_MediaPlayer.isPlaying()) {
                m_MediaPlayer = MediaPlayer.create(Content.this, m_Heroes.get(m_HeroID).getResource());
                m_MediaPlayer.setVolume(0.1f, 0.1f);
                m_MediaPlayer.start();
            }
        }
    };

    View.OnClickListener handleRemoveButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int length = m_Text.get(IConst.enumText.BOX_TYPE).getText().toString().length();
            if (length > 0) {
                String removeLastChar = m_Text.get(IConst.enumText.BOX_TYPE).getText().toString().substring(0, length - 1);
                m_Text.get(IConst.enumText.BOX_TYPE).setText(removeLastChar);
            }
        }
    };

    View.OnClickListener handleHintButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (m_Achievement == 100)
                return;

            if (m_Money - IConst.HINT_VALUE < 0) {
                Toast.makeText(Content.this, IConst.MSG_NO_GOLD, Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(Content.this, "-" + IConst.HINT_VALUE + " gold", Toast.LENGTH_SHORT).show();
            changeMoney(-IConst.HINT_VALUE);

            String box = m_Text.get(IConst.enumText.BOX_TYPE).getText().toString();
            String heroName = m_Heroes.get(m_HeroID).getName();

            if (box.length() > heroName.length() || !box.equals(heroName.substring(0, box.length())))
                m_Text.get(IConst.enumText.BOX_TYPE).setText(heroName.substring(0, 1));
            else if (box.length() < heroName.length() && box.equals(heroName.substring(0, box.length())))
                m_Text.get(IConst.enumText.BOX_TYPE).setText(box + heroName.substring(box.length(), box.length() + 1));
        }
    };

    View.OnClickListener handleDoneButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (m_Achievement == 100)
                return;

            String box = m_Text.get(IConst.enumText.BOX_TYPE).getText().toString();
            String hero = m_Heroes.get(m_HeroID).getName();

            if (box.equals(hero)) {
                Toast.makeText(Content.this, IConst.MSG_CORRECT, Toast.LENGTH_SHORT).show();
                m_Heroes.remove(m_HeroID.intValue());
                updateAchievement();
                changeMoney(IConst.MONEY_BONUS);
                m_Text.get(IConst.enumText.BOX_TYPE).setText("");

                if (m_Achievement == 100) {
                    Toast.makeText(Content.this, IConst.MSG_WIN_GAME, Toast.LENGTH_LONG).show();
                    return;
                }

                updateShared(IConst.enumShared.HEROES);
                getRandomHero();
                updateShared(IConst.enumShared.HERO_ID);
                fillLetterButtons();

            } else {
                changeLife(-1);

                if (m_Life <= 0) {
                    if (m_InterstitialAd.isLoaded())
                        m_InterstitialAd.show();

                    changeLife(IConst.MAX_LIFE);
                    initHeroes();
                    updateShared(IConst.enumShared.HEROES);
                    getRandomHero();
                    updateShared(IConst.enumShared.HERO_ID);
                    updateAchievement();
                    m_Text.get(IConst.enumText.BOX_TYPE).setText("");
                    fillLetterButtons();
                }
                Toast.makeText(Content.this, IConst.MSG_INCORRECT, Toast.LENGTH_SHORT).show();
            }
        }
    };

    View.OnClickListener handleLetterButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonLetter1:
                    m_Text.get(IConst.enumText.BOX_TYPE).setText(m_Text.get(IConst.enumText.BOX_TYPE).getText().toString() + m_TextLetter[0].getText().toString());
                    break;
                case R.id.buttonLetter2:
                    m_Text.get(IConst.enumText.BOX_TYPE).setText(m_Text.get(IConst.enumText.BOX_TYPE).getText().toString() + m_TextLetter[1].getText().toString());
                    break;
                case R.id.buttonLetter3:
                    m_Text.get(IConst.enumText.BOX_TYPE).setText(m_Text.get(IConst.enumText.BOX_TYPE).getText().toString() + m_TextLetter[2].getText().toString());
                    break;
                case R.id.buttonLetter4:
                    m_Text.get(IConst.enumText.BOX_TYPE).setText(m_Text.get(IConst.enumText.BOX_TYPE).getText().toString() + m_TextLetter[3].getText().toString());
                    break;
                case R.id.buttonLetter5:
                    m_Text.get(IConst.enumText.BOX_TYPE).setText(m_Text.get(IConst.enumText.BOX_TYPE).getText().toString() + m_TextLetter[4].getText().toString());
                    break;
                case R.id.buttonLetter6:
                    m_Text.get(IConst.enumText.BOX_TYPE).setText(m_Text.get(IConst.enumText.BOX_TYPE).getText().toString() + m_TextLetter[5].getText().toString());
                    break;
                case R.id.buttonLetter7:
                    m_Text.get(IConst.enumText.BOX_TYPE).setText(m_Text.get(IConst.enumText.BOX_TYPE).getText().toString() + m_TextLetter[6].getText().toString());
                    break;
                case R.id.buttonLetter8:
                    m_Text.get(IConst.enumText.BOX_TYPE).setText(m_Text.get(IConst.enumText.BOX_TYPE).getText().toString() + m_TextLetter[7].getText().toString());
                    break;
                case R.id.buttonLetter9:
                    m_Text.get(IConst.enumText.BOX_TYPE).setText(m_Text.get(IConst.enumText.BOX_TYPE).getText().toString() + m_TextLetter[8].getText().toString());
                    break;
                case R.id.buttonLetter10:
                    m_Text.get(IConst.enumText.BOX_TYPE).setText(m_Text.get(IConst.enumText.BOX_TYPE).getText().toString() + m_TextLetter[9].getText().toString());
                    break;
                case R.id.buttonLetter11:
                    m_Text.get(IConst.enumText.BOX_TYPE).setText(m_Text.get(IConst.enumText.BOX_TYPE).getText().toString() + m_TextLetter[10].getText().toString());
                    break;
                case R.id.buttonLetter12:
                    m_Text.get(IConst.enumText.BOX_TYPE).setText(m_Text.get(IConst.enumText.BOX_TYPE).getText().toString() + m_TextLetter[11].getText().toString());
                    break;
                case R.id.buttonLetter13:
                    m_Text.get(IConst.enumText.BOX_TYPE).setText(m_Text.get(IConst.enumText.BOX_TYPE).getText().toString() + m_TextLetter[12].getText().toString());
                    break;
                case R.id.buttonLetter14:
                    m_Text.get(IConst.enumText.BOX_TYPE).setText(m_Text.get(IConst.enumText.BOX_TYPE).getText().toString() + m_TextLetter[13].getText().toString());
                    break;

                default:
                    break;
            }
        }
    };

    RewardedVideoAdListener handleRewardAd = new RewardedVideoAdListener() {
        @Override
        public void onRewardedVideoAdLeftApplication() {
        }

        @Override
        public void onRewardedVideoAdClosed() {
            loadRewardedVideoAd();
            int bonus = getVideoAdBonusMoney();
            m_Money += bonus;
            Toast.makeText(Content.this, "+" + bonus + " gold", Toast.LENGTH_SHORT).show();
            updateShared(IConst.enumShared.MONEY);
            m_Text.get(IConst.enumText.MONEY).setText(String.valueOf(m_Money));
        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int errorCode) {
            m_IsVideoAdReady = false;
        }

        @Override
        public void onRewardedVideoAdLoaded() {
            m_IsVideoAdReady = false;
        }

        @Override
        public void onRewardedVideoAdOpened() {
        }

        @Override
        public void onRewarded(RewardItem reward) {

        }

        @Override
        public void onRewardedVideoStarted() {
        }
    };

    AdListener handleInterstitialAd = new AdListener() {
        @Override
        public void onAdClosed() {
            super.onAdClosed();
            loadInterstitialAd();
        }

        @Override
        public void onAdFailedToLoad(int i) {
            super.onAdFailedToLoad(i);
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
        }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
        }
    };

    View.OnClickListener handleBackButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    };

    View.OnClickListener handleRewardButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (m_VideoAd.isLoaded()) {
                m_VideoAd.show();
            } else
                Toast.makeText(Content.this, IConst.MSG_NOT_READY, Toast.LENGTH_SHORT).show();
        }
    };
}


