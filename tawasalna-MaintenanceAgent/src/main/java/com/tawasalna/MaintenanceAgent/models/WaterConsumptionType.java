package com.tawasalna.MaintenanceAgent.models;

public enum WaterConsumptionType {
    DOMESTIC,       // Usage domestique (e.g., Maison, Bureau)
    IRRIGATION,     // Irrigation (e.g., Agriculture, Jardinage)
    INDUSTRIAL,     // Usage industriel (e.g., Usines, Machines)
    RECREATIONAL,   // Usage récréatif (e.g., Piscines, Parcs aquatiques)
    COMMERCIAL,     // Usage commercial (e.g., Hôtels, Restaurants)
    PUBLIC,         // Usage public (e.g., Fontaines, Toilettes publiques)
    COOLING,        // Refroidissement (e.g., Tours de refroidissement)
    FIRE_SAFETY,    // Sécurité incendie (e.g., Systèmes de sprinklers)
    WASTEWATER,     // Eaux usées (e.g., Traitement des eaux usées)
    OTHER      ,     // Autre
    SURFACE,        // Eau de surface (e.g., Rivières, Lacs, Étangs)
    SOUTERRAINE,    // Eau souterraine (e.g., Nappes phréatiques, Puits)
    PLUIE,          // Eau de pluie (e.g., Collecte d'eau de pluie)
    DESSALINISATION,// Eau dessalinisée (e.g., Eau de mer traitée)
    RECYCLEE,       // Eau recyclée (e.g., Eaux usées traitées)
    GLACIERE,       // Eau glacière (e.g., Fonte des glaciers)
    RESERVOIR,      // Eau de réservoir (e.g., Barrages, Réservoirs artificiels)
    MER,            // Eau de mer (e.g., Océans, Mers)
    SOURCE,         // Eau de source (e.g., Sources naturelles)
    AUTRE           // Autre (e.g., Toute autre source d'eau)
}
