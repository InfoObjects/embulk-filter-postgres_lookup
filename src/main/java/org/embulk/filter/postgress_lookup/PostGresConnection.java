package org.embulk.filter.postgress_lookup;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostGresConnection {

    private static Connection connection=null;

    private PostGresConnection(PostgressLookupFilterPlugin.PluginTask task) throws Exception {
        try{
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://" + task.getHost() + ":"+task.getPort()+"/"+task.getDatabase();
            connection= DriverManager.getConnection(url, task.getUserName(), task.getPassword());
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }

    }

    public static Connection getConnection(PostgressLookupFilterPlugin.PluginTask task){
        try {
            if(connection==null || connection.isClosed()){
                try {
                    new PostGresConnection(task);
                    return connection;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return connection;
    }
}
