package com.wetcoding.datareader;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для пердставления столбца
 */
class DataColumn {
    /**Имя столбца в программе*/
    private String name;
    /** Фактическое имя стобца в таблице*/
    private String designation;
    /** Номер столбца в csv файлах*/
    private int index;


    private List<Float> values=new ArrayList<>();

    public DataColumn(String name,String designation){
        this.designation=designation;
        this.name=name;
        this.index=-1;
    }

    public DataColumn(String name){
        this(name,name);
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public String getName(){
        return this.name;
    }

    public String getDesignation(){
        return designation!=null?designation:name;
    }

    public void addValue(float v){
        values.add(v);
    }

    public int getSize(){
        return values.size();
    }

    public float getValue(int index){
        if(index<values.size()){
            return values.get(index);
        }

        System.out.println("Array index of bound!");
        return 0;
    }
}
