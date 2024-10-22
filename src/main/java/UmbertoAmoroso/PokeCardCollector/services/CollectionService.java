package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.dto.CardDTO;
import UmbertoAmoroso.PokeCardCollector.dto.CollectionDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Collection;
import UmbertoAmoroso.PokeCardCollector.entities.CollectionCard;
import UmbertoAmoroso.PokeCardCollector.entities.User;
import UmbertoAmoroso.PokeCardCollector.repositories.CollectionCardRepository;
import UmbertoAmoroso.PokeCardCollector.repositories.CollectionRepository;
import UmbertoAmoroso.PokeCardCollector.repositories.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    public List<CollectionDTO> getUserCollections(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return collectionRepository.findByUser(user).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public CollectionDTO createCollection(Long userId, String collectionName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Collection collection = new Collection();
        collection.setName(collectionName);
        collection.setUser(user);
        Collection savedCollection = collectionRepository.save(collection);
        return mapToDTO(savedCollection);
    }

    public void addCardToCollection(Long collectionId, String cardId, String imageUrl) {
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new IllegalArgumentException("Collection not found"));

        CollectionCard collectionCard = new CollectionCard();
        collectionCard.setCardId(cardId); // Usa l'ID della carta dall'API
        collectionCard.setImageUrl(imageUrl); // Imposta l'URL dell'immagine
        collectionCard.setCollection(collection); // Collega la carta alla collezione
        collectionCardRepository.save(collectionCard); // Salva nel database
    }

    public void deleteCollection(Long collectionId) {
        collectionRepository.deleteById(collectionId);
    }

    public void deleteCardFromCollection(Long collectionId, String cardId) {
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new IllegalArgumentException("Collection not found"));
        List<CollectionCard> cards = collectionCardRepository.findByCollection(collection);
        CollectionCard cardToRemove = cards.stream()
                .filter(card -> card.getCardId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Card not found in collection"));
        collectionCardRepository.delete(cardToRemove);
    }

    private CollectionDTO mapToDTO(Collection collection) {
        CollectionDTO dto = new CollectionDTO();
        dto.setId(collection.getId());
        dto.setName(collection.getName());

        // Gestione della lista delle carte, se è null viene impostata come lista vuota
        dto.setCards(
                Optional.ofNullable(collection.getCards())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(this::mapCardToDTO)
                        .collect(Collectors.toList())
        );

        return dto;
    }


    private CardDTO mapCardToDTO(CollectionCard card) {
        CardDTO dto = new CardDTO();
        dto.setId(card.getId());
        dto.setCardId(card.getCardId());
        dto.setImageUrl(card.getImageUrl());
        return dto;
    }
}
