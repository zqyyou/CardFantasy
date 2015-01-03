package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class BloodPaint {
    public static void apply(Skill cardFeature, SkillResolver resolver, CardInfo attacker, Player defender, int victimCount) throws HeroDieSignal {
        int damage = cardFeature.getImpact();
        List <CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
            defender.getField().toList(), victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, cardFeature, true);
        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, cardFeature, damage);
            if (!result.isAttackable()) {
                continue;
            }
            damage = result.getDamage(); 
            ui.attackCard(attacker, victim, cardFeature, damage);
            boolean cardDead = resolver.applyDamage(victim, damage).cardDead;
            ui.healCard(attacker, attacker, cardFeature, damage);
            resolver.applyDamage(attacker, -damage);
            if (cardDead) {
                resolver.resolveDeathFeature(attacker, victim, cardFeature);
            }
        }
    }
}