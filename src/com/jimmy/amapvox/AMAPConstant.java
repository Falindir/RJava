package com.jimmy.amapvox;

public class AMAPConstant {

    // nbre minimum de cellules échantillonnées pour appliquer le modèle glmer
    public static int seuil_echantillonnage = 10;

    // nombre max de minivoxels -1 fusionnés pour le calcul du voisinage
    public static int seuil_fusion = 0;

    public static int header_input = 6; //le nombre de ligne du header de input.vox

    //distance de voisinage (en cellules)
    public static int minivox = 5;

    // pas de discrétisation des volumes élémentaires
    public static float  EP = (float)0.01;

}