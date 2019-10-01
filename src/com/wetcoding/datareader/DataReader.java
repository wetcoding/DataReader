package com.wetcoding.datareader;

import com.wetcoding.configuration.Configuration;
import com.wetcoding.configuration.ConfigurationException;
import com.wetcoding.resources.StringResources;

import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактный класс для реализации
 * функций чтения из различных источников
 */
public abstract class DataReader {

    /**Список столбцов*/
    List<DataColumn> dataColumns=new ArrayList<>();


    /**
     * Конструктор класса
     * @param configuration - настройки
     * @param columns - имена стоблцов
     * @throws DataReaderException
     */
    public DataReader(Configuration configuration,  String ... columns) throws DataReaderException{
        if(configuration==null){
            throw new DataReaderException(StringResources.CFG_NO_FILE);
        }
        for(int i=0;i<columns.length;i++){
            String designation=null;
            try {
                designation=configuration.get(columns[i]);
            } catch (ConfigurationException e){
                System.out.println(e.getMessage()+" designation set as "+columns[i]);
            }
            if(designation!=null){
                dataColumns.add(new DataColumn(columns[i],designation));
            } else {
                dataColumns.add(new DataColumn(columns[i]));
            }
        }
    }


    /**
     * Метод для получения количества значений
     * @return - возвращает количество строк в столбцах
     */
    public int getValuesCount() {
        if(dataColumns.size()>0){
            return dataColumns.get(0).getSize();
        }
        return 0;
    }


    /**
     * Получение значения из определенного столбца
     * @param column - имя столбца
     * @param index - номер строки
     * @return - значение
     */
    public float getValue(String column, int index){
        for(DataColumn d:dataColumns){
            if(d.getName().equals(column)){
                return d.getValue(index);
            }
        }

        return 0;
    }
}
