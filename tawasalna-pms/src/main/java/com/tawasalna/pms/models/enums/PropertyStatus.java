package com.tawasalna.pms.models.enums;

public enum PropertyStatus {
    FOR_SALE, // sur le marché et disponible à l'achat.
    UNDER_OFFER, //il y a déjà une offre en cours d'examen ou de négociation par le propriétaire
    SOLD, // a été vendue et n'est plus disponible sur le marché.
    FOR_RENT, // disponible pour la location.
    RENTED, // est actuellement louée à un locataire.
    PENDING,// pour une propriété qui est sur le point d'être mise sur le marché mais qui nécessite encore quelques préparatifs ou qui est en attente de documentation.
    SUSPENDED, //Indique que la propriété était précédemment en vente ou en location mais que l'agent immobilier a temporairement retiré l'annonce pour diverses raisons.
    WITHDRAWN, //a été retirée du marché de manière permanente.
    OFF_MARKET, //qui n'est pas actuellement sur le marché, mais qui peut être disponible pour des transactions privées ou spéciales.
    UNDER_CONSTRUCTION // Pour les propriétés qui sont en cours de construction ou de développement.
}