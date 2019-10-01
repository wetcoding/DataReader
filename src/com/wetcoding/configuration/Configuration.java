package com.wetcoding.configuration;

import com.wetcoding.resources.StringResources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для работы с настройками
 */
public class Configuration {
    Map<String,String> cfgMap=new HashMap();
    private final int MAX_ROW_COUNT=100;
    private final int MAX_ROW_LENGTH=100;


    public Configuration(String fileName) throws IOException{
        cfgMap=new HashMap<String,String>();
        try {
            File file=new File(fileName);
            BufferedReader br=new BufferedReader(new FileReader(file));
            String st;
            int size=0;

            System.out.println("Ini: ");
            while((st = br.readLine()) != null && size<MAX_ROW_COUNT) {
                String [] kv= parseLine(st);
                if(kv!=null){
                    cfgMap.put(kv[0],kv[1]);
                }
                size++;
            }

            br.close();

        }
        catch (IOException e){
            throw e;
        }
    }

    /**
     * Получение значения конфигурации по ключу
     * @param key - ключ
     * @return - значение
     * @throws ConfigurationException
     */
    public String get(String key) throws ConfigurationException{
        if(cfgMap.containsKey(key))
            return cfgMap.get(key);

        throw new ConfigurationException(StringResources.CFG_NO_SUCH_FIELD+key);
    }


    /**
     * Разбор строки на "ключ"-"значение"
     * @param line - исходная строка
     * @return - массив из 2х элементов или null
     */
    private String[] parseLine(String line){
        if(line.startsWith("//"))
            return null;
        if(line.length()>MAX_ROW_LENGTH)
            return null;
        if(line.contains("="))
        {
            line=line.replaceAll("\\s","");
            String [] kv=line.split("=");
            if (kv.length==2)
            {
                System.out.println(kv[0]+" : "+kv[1]);
                return kv;
            }
        }

        return null;
    }
}
