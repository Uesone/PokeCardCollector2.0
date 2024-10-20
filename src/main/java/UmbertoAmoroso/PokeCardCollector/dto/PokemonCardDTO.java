package UmbertoAmoroso.PokeCardCollector.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PokemonCardDTO {
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

    // Aggiungi il campo quantity
    private int quantity;
}

@Getter
@Setter
class AttackDTO {
    private String name;
    private String[] cost;
    private int convertedEnergyCost;
    private String damage;
    private String text;
}

@Getter
@Setter
class WeaknessDTO {
    private String type;
    private String value;
}

@Getter
@Setter
class SetDTO {
    private String id;
    private String name;
    private String series;
    private int printedTotal;
    private int total;
    private LegalitiesDTO legalities;
    private String ptcgoCode;
    private String releaseDate;
    private String updatedAt;
    private ImagesDTO images;
}

@Getter
@Setter
class LegalitiesDTO {
    private String unlimited;
    private String expanded;
}

@Getter
@Setter
class ImagesDTO {
    private String small;
    private String large;
}

@Getter
@Setter
class TcgPlayerDTO {
    private String url;
    private PricesDTO prices;
}

@Getter
@Setter
class PricesDTO {
    private HolofoilDTO holofoil;
}

@Getter
@Setter
class HolofoilDTO {
    private double low;
    private double mid;
    private double high;
    private double market;
    private double directLow;
}
