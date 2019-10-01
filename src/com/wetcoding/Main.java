package com.wetcoding;
import com.wetcoding.datareader.DBReader;
import com.wetcoding.datareader.DataReader;
import com.wetcoding.datareader.DataReaderException;
import com.wetcoding.datareader.FileReader;
import com.wetcoding.configuration.Configuration;

import java.io.IOException;

public class Main {
    public static void main(String [] args){
        Configuration configuration=null;
        try {
            configuration = new Configuration("settings.ini");
        } catch (IOException e){
            System.out.println("Init file not found");
            System.exit(0);
        }

        try{
            String [] colNames={"index","WEIGHT"};

            //DataReader reader=new DBReader(configuration,colNames);
            DataReader reader=new FileReader(configuration,"2.csv",colNames);

            for(String name:colNames) {
                System.out.println("Values of column " + name);
                for (int i = 0; i < reader.getValuesCount(); i++) {
                    System.out.println(reader.getValue(name, i));
                }
            }

        } catch (DataReaderException e){
            System.out.println(e.getMessage());
        }
    }
}
