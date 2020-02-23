package com.example.graduiation.ui.DataStructuresAndAlgos;

import com.example.graduiation.ui.Data.FoodModel;

import java.util.ArrayList;
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

    private static List<FoodModel> Ascaux;

    public static void AscendingMergeSort(List<FoodModel> a) {
        Ascaux = new ArrayList<>(a.size());
        Ascaux.addAll(a);
        AscendingMergeSort(a, 0, a.size() - 1);
    }

    private static void AscendingMergeSort(List<FoodModel> a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        AscendingMergeSort(a, lo, mid);
        AscendingMergeSort(a, mid + 1, hi);
        AscendingMerge(a, lo, mid, hi);
    }

    private static void AscendingMerge(List<FoodModel> a, int lo, int mid, int hi) {
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

    private static ArrayList<FoodModel> Desaux;

    public static void DesMergeSort(List<FoodModel> a) {
        Desaux = new ArrayList<>(a.size());
        Desaux.addAll(a);
        DesMergeSort(a, 0, a.size() - 1);
    }

    private static void DesMergeSort(List<FoodModel> a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        DesMergeSort(a, lo, mid);
        DesMergeSort(a, mid + 1, hi);
        DesMerge(a, lo, mid, hi);
    }

    private static void DesMerge(List<FoodModel> a, int lo, int mid, int hi) {
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

}
