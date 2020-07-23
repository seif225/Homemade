package com.example.graduiation.ui.DataStructuresAndAlgos;

import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.FoodSearchModel;
import com.example.graduiation.ui.Data.UserParentModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FoodSort {

    //buuble sort Algo------------------------------------------------------------------
    //ascending

    public static void BubblesortArrayListDescending(List<FoodModel> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                if (Integer.parseInt(arrayList.get(j).getPrice()) > Integer.parseInt(arrayList.get(i).getPrice())) {
                    FoodModel temp = arrayList.get(j);
                    arrayList.set(j, arrayList.get(i));
                    arrayList.set(i, temp);

                }
            }

        }
    }

    //descending
    public static void BubblesortArrayList(List<FoodModel> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                if (Integer.parseInt(arrayList.get(j).getPrice()) < Integer.parseInt(arrayList.get(i).getPrice())) {
                    FoodModel temp = arrayList.get(j);
                    arrayList.set(j, arrayList.get(i));
                    arrayList.set(i, temp);

                }
            }

        }
    }
//------------------------------------------------------------------------------------

//merge sort algo
    //ascending

    private static List<FoodSearchModel> Ascaux;

    public static void AscendingMergeSort(List<FoodSearchModel> a) {
        Ascaux = new ArrayList<>(a.size());
        Ascaux.addAll(a);
        AscendingMergeSort(a, 0, a.size() - 1);
    }

    private static void AscendingMergeSort(List<FoodSearchModel> a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        AscendingMergeSort(a, lo, mid);
        AscendingMergeSort(a, mid + 1, hi);
        AscendingMerge(a, lo, mid, hi);
    }

    private static void AscendingMerge(List<FoodSearchModel> a, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) Ascaux.set(k, a.get(k));
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a.set(k, Ascaux.get(j++));
            else if (j > hi) a.set(k, Ascaux.get(i++));
            else if (Integer.parseInt(Ascaux.get(j).getPrice()) < Integer.parseInt(Ascaux.get(i).getPrice()))
                a.set(k, Ascaux.get(j++));
            else a.set(k, Ascaux.get(i++));
        }
    }
//-----------------------------------------------------------------------------------------
    //Descending mergesort

    private static ArrayList<FoodSearchModel> Desaux;
    private static ArrayList<UserParentModel> DesauxRate;

    public static void DesMergeSort(List<FoodSearchModel> a) {
        Desaux = new ArrayList<>(a.size());
        Desaux.addAll(a);
        DesMergeSort(a, 0, a.size() - 1);
    }

    private static void DesMergeSort(List<FoodSearchModel> a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        DesMergeSort(a, lo, mid);
        DesMergeSort(a, mid + 1, hi);
        DesMerge(a, lo, mid, hi);
    }

    private static void DesMerge(List<FoodSearchModel> a, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) Desaux.set(k, a.get(k));
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a.set(k, Desaux.get(j++));
            else if (j > hi) a.set(k, Desaux.get(i++));
            else if (Integer.parseInt(Desaux.get(j).getPrice()) > Integer.parseInt(Desaux.get(i).getPrice()))
                a.set(k, Desaux.get(j++));
            else a.set(k, Desaux.get(i++));
        }
    }



    //------------------------------------------------------
    public static void DesBubbleSortByRate(List<UserParentModel> a){
        for (int i = 0; i <a.size() ; i++) {
            for (int j = i+1; j <a.size() ; j++) {
                if(a.get(i).getRate()<a.get(j).getRate()){
                    UserParentModel temp = a.get(j);
                    a.set(j, a.get(i));
                    a.set(i, temp);

                }
            }
        }
    }
   //----------------------------------------------------------------------------
   public static void DesMergeSortByRate(List<UserParentModel> a) {
       DesauxRate = new ArrayList<>(a.size());
       DesauxRate.addAll(a);
       DesMergeSortByRate(a, 0, a.size() - 1);
   }

    private static void DesMergeSortByRate(List<UserParentModel> a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        DesMergeSortByRate(a, lo, mid);
        DesMergeSortByRate(a, mid + 1, hi);
        DesMergeByRate(a, lo, mid, hi);
    }

    private static void DesMergeByRate(List<UserParentModel> a, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) DesauxRate.set(k, a.get(k));
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a.set(k, DesauxRate.get(j++));
            else if (j > hi) a.set(k, DesauxRate.get(i++));
            else if (DesauxRate.get(j).getRate() > DesauxRate.get(i).getRate())
                a.set(k, DesauxRate.get(j++));
            else a.set(k, DesauxRate.get(i++));
        }
    }


    //---------------------------------------------------------------------------
}
