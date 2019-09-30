package com.wetcoding.datareader;

import configuration.Configuration;
import configuration.ConfigurationException;
import resources.StringResources;

import java.util.ArrayList;
import java.util.List;

public abstract class DataReader {
    List<DataColumn> dataColumns=new ArrayList<>();


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


    public int getValuesCount() {
        if(dataColumns.size()>0){
            return dataColumns.get(0).getSize();
        }

        return 0;
    }


    public float getValue(String column, int index){
        for(DataColumn d:dataColumns){
            if(d.getName().equals(column)){
                return d.getValue(index);
            }
        }

        return 0;
    }
}
