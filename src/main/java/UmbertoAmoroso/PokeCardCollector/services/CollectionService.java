package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.dto.CardDTO;
import UmbertoAmoroso.PokeCardCollector.dto.CollectionDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Collection;
import UmbertoAmoroso.PokeCardCollector.entities.CollectionCard;
import UmbertoAmoroso.PokeCardCollector.entities.User;
import UmbertoAmoroso.PokeCardCollector.repositories.CollectionCardRepository;
import UmbertoAmoroso.PokeCardCollector.repositories.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CollectionCardRepository collectionCardRepository;

    // Restituisce le collezioni di un utente
    public List<CollectionDTO> getUserCollections(Long userId) {
        List<Collection> collections = collectionRepository.findByUser_Id(userId);
        return collections.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Crea una nuova collezione per l'utente autenticato
    public CollectionDTO createCollection(Long userId, String collectionName) {
        Collection collection = new Collection();
        collection.setName(collectionName);
        collection.setUser(new User(userId));  // Associa l'utente alla collezione
        Collection savedCollection = collectionRepository.save(collection);
        return mapToDTO(savedCollection);
    }

    // Aggiunge una carta a una collezione specifica
    public void addCardToCollection(Long userId, Long collectionId, String cardId, String imageUrl) {
        Collection collection = collectionRepository.findByIdAndUser_Id(collectionId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Collection not found for the user"));

        CollectionCard collectionCard = new CollectionCard();
        collectionCard.setCardId(cardId);
        collectionCard.setImageUrl(imageUrl);
        collectionCard.setCollection(collection);
        collectionCardRepository.save(collectionCard);
    }

    // Elimina una collezione
    public void deleteCollection(Long userId, Long collectionId) {
        Collection collection = collectionRepository.findByIdAndUser_Id(collectionId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Collection not found for the user"));
        collectionRepository.delete(collection);
    }

    // Rimuove una carta da una collezione specifica
    public void deleteCardFromCollection(Long userId, Long collectionId, String cardId) {
        Collection collection = collectionRepository.findByIdAndUser_Id(collectionId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Collection not found for the user"));
        List<CollectionCard> cards = collectionCardRepository.findByCollection(collection);
        CollectionCard cardToRemove = cards.stream()
                .filter(card -> card.getCardId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Card not found in collection"));
        collectionCardRepository.delete(cardToRemove);
    }

    // Mappa Collection in CollectionDTO
    private CollectionDTO mapToDTO(Collection collection) {
        CollectionDTO dto = new CollectionDTO();
        dto.setId(collection.getId());
        dto.setName(collection.getName());

        dto.setCards(
                Optional.ofNullable(collection.getCards())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(this::mapCardToDTO)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    // Mappa CollectionCard in CardDTO
    private CardDTO mapCardToDTO(CollectionCard card) {
        CardDTO dto = new CardDTO();
        dto.setId(card.getId());
        dto.setCardId(card.getCardId());
        dto.setImageUrl(card.getImageUrl());
        return dto;
    }
}
