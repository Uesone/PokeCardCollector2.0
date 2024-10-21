package UmbertoAmoroso.PokeCardCollector.controllers;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PokemonCardDTO {
    private String id;
    private String name;
    private String supertype;
    private List<String> subtypes;
    private String hp;
    private List<String> types;
    private String evolvesFrom;
    private List<String> evolvesTo;
    private List<Ability> abilities;
    private List<Attack> attacks;
    private List<Weakness> weaknesses;
    private List<String> retreatCost;
    private int convertedRetreatCost;
    private SetDetail set;
    private String artist;
    private String rarity;
    private String flavorText;
    private List<Integer> nationalPokedexNumbers;
    private Map<String, Legalities> legalities;
    private Map<String, Images> images;
    private TcgPlayer tcgplayer;
    private CardMarket cardmarket;

    @Getter
    @Setter
    public static class Ability {
        private String name;
        private String text;
        private String type;
    }

    @Getter
    @Setter
    public static class Attack {
        private String name;
        private List<String> cost;
        private String text;
        private String damage;
        private int convertedEnergyCost;
    }

    @Getter
    @Setter
    public static class Weakness {
        private String type;
        private String value;
    }

    @Getter
    @Setter
    public static class SetDetail {
        private String id;
        private String name;
        private String series;
        private int printedTotal;
        private int total;
        private Map<String, String> legalities;
        private String ptcgoCode;
        private String releaseDate;
        private String updatedAt;
        private Images images;
    }

    @Getter
    @Setter
    public static class Images {
        private String small;
        private String large;
    }

    @Getter
    @Setter
    public static class TcgPlayer {
        private String url;
        private String updatedAt;
        private Prices prices;

        @Getter
        @Setter
        public static class Prices {
            private PriceDetail normal;
            private PriceDetail holofoil;
            private PriceDetail reverseHolofoil;
            private PriceDetail firstEditionHolofoil;

            @Getter
            @Setter
            public static class PriceDetail {
                private double low;
                private double mid;
                private double high;
                private double market;
                private double directLow;
            }
        }
    }

    @Getter
    @Setter
    public static class CardMarket {
        private String url;
        private String updatedAt;
        private Prices prices;

        @Getter
        @Setter
        public static class Prices {
            private double averageSellPrice;
            private double lowPrice;
            private double trendPrice;
            private double germanProLow;
            private double suggestedPrice;
            private double reverseHoloSell;
            private double reverseHoloLow;
            private double reverseHoloTrend;
        }
    }

    @Getter
    @Setter
    public static class Legalities {
        private String standard;
        private String expanded;
        private String unlimited;
    }
}