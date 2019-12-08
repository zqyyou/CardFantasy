package cfvbaibai.cardfantasy;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.Board;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Phase;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.Rule;
import cfvbaibai.cardfantasy.engine.RuneInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.game.PveGameResult;

public abstract class GameUI {

    private Board board;
    private Rule rule;

    protected GameUI() {
    }

    public void gameStarted(Board board, Rule rule) {
        this.board = board;
        this.rule = rule;
        this.gameStarted();
    }

    protected Board getBoard() {
        return this.board;
    }

    protected Rule getRule() {
        return this.rule;
    }
    
    protected int getPlayerNumber(Player hero) {
        for (int i = 0; i < this.board.getPlayerCount(); ++i) {
            Player player = this.board.getPlayer(i);
            if (player.getId().equals(hero.getId())) {
                return i;
            }
        }
        return -1;
    }

    public abstract void gameStarted();

    public abstract void playerAdded(Player player, int playerNumber);

    public abstract void errorHappened(CardFantasyRuntimeException e);

    public abstract void phaseChanged(Player player, Phase previous, Phase current);

    public abstract void playerChanged(Player previousPlayer, Player nextPlayer);

    public abstract void gameEnded(GameResult result);

    public abstract void cardDrawed(Player drawer, CardInfo card);

    public abstract void cantDrawDeckEmpty(Player drawer);

    public abstract void cantDrawHandFull(Player drawer);

    public abstract void summonCard(Player player, CardInfo card);

    public abstract void roundStarted(Player player, int round);

    public abstract void roundEnded(Player player, int round);

    public abstract void attackCard(EntityInfo attacker, CardInfo defender, Skill cardSkill, int damage);

    public abstract void cardDead(CardInfo deadCard);

    public abstract void attackHero(EntityInfo attacker, Player hero, Skill cardSkill, int damage);

    public abstract void useSkill(EntityInfo caster, List<? extends EntityInfo> targets, Skill skill, boolean bingo);

    public abstract void protect(EntityInfo protector, EntityInfo attacker, EntityInfo protectee,
            Skill attackSkill, Skill protectSkill);

    public void useSkill(EntityInfo caster, EntityInfo target, Skill skill, boolean bingo) {
        List<EntityInfo> victims = new ArrayList<EntityInfo>();
        victims.add(target);
        useSkill(caster, victims, skill, bingo);
    }

    public abstract void useSkillToHero(EntityInfo caster, Player targetHero, Skill skill);

    public abstract void addCardStatus(EntityInfo attacker, CardInfo victim, Skill cardSkill, CardStatusItem item);

    public abstract void removeCardStatus(CardInfo card, CardStatusType type);

    public abstract void battleBegins();

    public abstract void attackBlocked(EntityInfo attacker, CardInfo defender, Skill atSkill, Skill dfSkill);

    public abstract void adjustAT(EntityInfo source, CardInfo target, int adjAT, Skill cardSkill);

    public void adjustHP(EntityInfo source, CardInfo target, int adjHP, Skill cardSkill) {
        List<CardInfo> targets = new ArrayList<CardInfo>();
        targets.add(target);
        adjustHP(source, targets, adjHP, cardSkill);
    }
    
    public abstract void adjustHP(EntityInfo source, List<? extends CardInfo> targets, int adjHP, Skill cardSkill);

    public abstract void blockDamage(EntityInfo protector, EntityInfo attacker, EntityInfo defender,
            Skill cardSkill, int originalDamage, int actualDamage);

    public abstract void healBlocked(EntityInfo healer, CardInfo healee, Skill cardSkill, Skill blockerSkill);

    public abstract void debuffDamage(CardInfo card, CardStatusItem item, int effect);

    public abstract void cannotAction(CardInfo card);

    public abstract void recoverAT(CardInfo card, SkillType cause, int recoveredAT);

    public abstract void healCard(EntityInfo healer, CardInfo healee, Skill cardSkill, int healHP);

    public abstract void healHero(EntityInfo healer, Player healee, Skill cardSkill, int healHP);

    public abstract void loseAdjustATEffect(CardInfo ally, SkillEffect effect);

    public abstract void loseAdjustHPEffect(CardInfo ally, SkillEffect effect);

    public abstract void cardToDeck(Player player, CardInfo card);

    public abstract void cardToHand(Player player, CardInfo card);

    public abstract void cardToGrave(Player player, CardInfo card);
    
    public abstract void cardToOutField(Player player, CardInfo card);
    
    public abstract void blockStatus(EntityInfo attacker, EntityInfo defender, Skill cardSkill, CardStatusItem item);

    public abstract void blockSkill(EntityInfo attacker, EntityInfo defender, Skill cardSkill,
            Skill attackSkill);

    public abstract void returnCard(EntityInfo attacker, CardInfo defender, Skill cardSkill);


    public abstract void disableBlock(CardInfo attacker, EntityInfo defender, Skill attackSkill, Skill blockSkill);

    public abstract void confused(CardInfo card);

    public abstract void softened(CardInfo card);

    public abstract void roll100(int dice, int rate);

    public abstract void useSkill(EntityInfo caster, Skill skill, boolean bingo);

    public abstract void killCard(EntityInfo attacker, CardInfo victim, Skill cardSkill);

    public abstract void activateRune(RuneInfo rune);

    public abstract void deactivateRune(RuneInfo rune);

    public abstract void updateRuneEnergy(RuneInfo rune);

    public abstract void compactField(Field field);

    public abstract void showMessage(String string);

    public abstract void stageCreated();

    public abstract void cardActionBegins(CardInfo card);
    
    public abstract void cardActionEnds(CardInfo card);
    
    public abstract void mapStageResult(PveGameResult result);
    
    public abstract void increaseSummonDelay(CardInfo card, int offset);

    public abstract void unbend(CardInfo card, CardStatusItem statusItem);
}
