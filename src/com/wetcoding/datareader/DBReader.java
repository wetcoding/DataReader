package com.wetcoding.datareader;

import configuration.Configuration;
import configuration.ConfigurationException;
import resources.StringResources;

import java.sql.*;

public class DBReader extends DataReader {
    public DBReader(Configuration configuration,  String ... columns) throws DataReaderException{
        super(configuration,columns);

        String ip,port,dbName,table,user,password;
        try {
            ip=configuration.get("ip");
            port=configuration.get("port");
            dbName=configuration.get("dbName");
            table=configuration.get("table");
            user=configuration.get("user");
            password=configuration.get("password");
        } catch (ConfigurationException e){
            throw new DataReaderException(e.getMessage());
        }
        Connection connection=connect(ip,port,dbName,user,password);
        if(connection!=null){
            PreparedStatement preparedStatement=null;
            ResultSet rs=null;
            try {
                StringBuffer query=new StringBuffer("SELECT ");
                for(int i=0;i<dataColumns.size();i++){
                    query.append(dataColumns.get(i).getDesignation());
                    if(i<dataColumns.size()-1){
                        query.append(",");
                    }
                }
                query.append(" FROM ");
                query.append(table);
                query.append(" ORDER BY ?;");
                preparedStatement=connection.prepareStatement(query.toString());
                preparedStatement.setString(1, dataColumns.get(0).getDesignation());
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    for(DataColumn d:dataColumns){
                        d.addValue(rs.getFloat(d.getDesignation()));
                    }
                }
            } catch (SQLException e) {
                throw new DataReaderException(StringResources.DB_READ_ERROR);
            } finally {
                try{
                    if(rs!=null)
                        rs.close();
                    if(preparedStatement!=null)
                        preparedStatement.close();
                    if(connection!=null)
                        connection.close();

                } catch (SQLException e){
                    throw  new DataReaderException(StringResources.DB_CLOSE_ERROR);
                }
            }
        }



    }

    private Connection connect(String ip, String port, String dbName, String user, String password) throws DataReaderException {
        try {
            StringBuffer url=new StringBuffer();
            url.append("jdbc:mysql://")
                .append(ip)
                .append(":")
                .append(port)
                .append("/")
                .append(dbName)
                .append("?verifyServerCertificate=false & useSSL=false & allowPublicKeyRetrieval=true & requireSSL=false & useLegacyDatetimeCode=false & amp & serverTimezone=UTC");

            return DriverManager.getConnection(url.toString(), user, password);
        } catch (SQLException e){
            throw new DataReaderException(StringResources.DB_CONNECTION_ERROR);
        }
    }
}
