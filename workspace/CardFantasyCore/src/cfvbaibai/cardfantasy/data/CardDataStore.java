package cfvbaibai.cardfantasy.data;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

public class CardDataStore {
    private Map <String, CardData> cardMap;

    private CardDataStore() {
        cardMap = new HashMap <String, CardData>();
    }

    public static CardDataStore loadDefault() {
        CardDataStore store = new CardDataStore();

        URL url = CardDataStore.class.getClassLoader().getResource("cfvbaibai/cardfantasy/data/CardData.xml");
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(url);
            @SuppressWarnings("unchecked")
            List<Node> cardNodes = doc.selectNodes("/Cards/Card");
            for (Node cardNode : cardNodes) {
                CardData cardData = new CardData();
                cardData.setId(Integer.parseInt(cardNode.valueOf("@id")));
                cardData.setName(cardNode.valueOf("@name"));
                cardData.setRace(Race.valueOf(cardNode.valueOf("@race")));
                cardData.setSummonSpeed(Integer.parseInt(cardNode.valueOf("@speed")));
                cardData.setStar(Integer.parseInt(cardNode.valueOf("@star")));
                cardData.setDeckCost(Integer.parseInt(cardNode.valueOf("@cost")));
                cardData.setBaseAT(Integer.parseInt(cardNode.valueOf("@at")));
                cardData.setBaseHP(Integer.parseInt(cardNode.valueOf("@hp")));
                cardData.setIncrAT(Integer.parseInt(cardNode.valueOf("@incrAT")));
                cardData.setIncrHP(Integer.parseInt(cardNode.valueOf("@incrHP")));
                
                // TODO: Load features.
                store.addCard(cardData);
            }
            return store;
        } catch (DocumentException e) {
            throw new CardFantasyRuntimeException("Cannot load card info XML.", e);
        }
    }

    private void addCard(CardData cardData) {
        this.cardMap.put(cardData.getName(), cardData);
    }
    
    public CardData getCardInfo(String name) {
        if (this.cardMap.containsKey(name)) {
            return this.cardMap.get(name);
        } else {
            return null;
        }
    }
}
