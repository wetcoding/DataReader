package com.wetcoding.datareader;

import com.wetcoding.configuration.Configuration;


import java.io.FileNotFoundException;
import java.io.IOException;
import com.opencsv.CSVReader;
import com.wetcoding.resources.StringResources;

/**
 * Класс для чтения данных из файла
 */
public class FileReader extends DataReader {


    public FileReader(Configuration configuration, String file, String ... columns) throws DataReaderException{

        super(configuration,columns);

        try {
            CSVReader reader=new CSVReader(new java.io.FileReader(file),';');
            String[] csvLine;
            boolean firstLine=true;
            String[] annotations;
            while((csvLine = reader.readNext()) != null){

                //Разборка аннотации
                if(firstLine){
                    firstLine=false;
                    annotations= new String[csvLine.length];
                    for(int i=0;i<annotations.length;i++){
                        annotations[i]=new String(csvLine[i]);
                        for(DataColumn d:dataColumns){
                            if(d.getDesignation().equals(annotations[i])){
                                d.setIndex(i);
                                System.out.println("find column <"+d.getName()+"> as <"+d.getDesignation()+","+i+">");
                            }
                        }
                    }
                    //Проверяем все ли колонки есть
                    for(DataColumn d:dataColumns){
                        if(d.getIndex()==-1){
                            throw new DataReaderException(StringResources.CSV_NO_SUCH_COLUMN+d.getDesignation());
                        }
                    }
                    continue;
                }

                //Заносим данные из csv строки в соответствующую DataColumn
                for(int i=0;i<csvLine.length;i++){
                    for(DataColumn d:dataColumns){
                        if(d.getIndex()==i){
                            d.addValue(Float.valueOf(csvLine[i]));
                        }
                    }
                }

            }
        } catch (NumberFormatException e){
            throw new DataReaderException(StringResources.CSV_READ_ERROR);
        } catch (FileNotFoundException e){
            throw new DataReaderException(StringResources.CSV_FILE_NOT_FOUND);
        } catch (IOException e){
            throw new DataReaderException(StringResources.CSV_READ_ERROR);
        }
    }


}
