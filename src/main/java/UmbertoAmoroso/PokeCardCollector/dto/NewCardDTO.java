package UmbertoAmoroso.PokeCardCollector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCardDTO {
        private String apiId;
        private boolean holo;
        private int quantity;
        private String condition;

        // Getter per apiId, holo, quantity, e condition
        public String getApiId() {
                return apiId;
        }

        public boolean isHolo() {
                return holo;
        }

        public int getQuantity() {
                return quantity;
        }

        public String getCondition() {
                return condition;
        }

}
