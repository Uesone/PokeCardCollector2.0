package UmbertoAmoroso.PokeCardCollector.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PokemonCardData {
    private String id;
    private String name;
    private String supertype;
    private String[] subtypes;
    private String hp;
    private String[] types;
    private String[] evolvesTo;
    private String[] rules;
    private AttackDTO[] attacks;
    private WeaknessDTO[] weaknesses;
    private String[] retreatCost;
    private int convertedRetreatCost;
    private SetDTO set;
    private String number;
    private String artist;
    private String rarity;
    private int[] nationalPokedexNumbers;
    private LegalitiesDTO legalities;
    private ImagesDTO images;
    private TcgPlayerDTO tcgplayer;
}
