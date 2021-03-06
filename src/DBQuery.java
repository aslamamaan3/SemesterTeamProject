import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.sql.ResultSetMetaData;


public class DBQuery
{
    private static String tableName = "Music";
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;
    private static String dbURL = "jdbc:derby:codejava/webdb1;create=true";
    
    public static void main(String[] args) {
       createConnection();
       //insertSong("C:\\Hello.mp3");
      // insertSong("C:\\Doc\\Top.mp3");
      // insertSong("C:\\Doc\\Down.mp3");
       deleteSong("C:\\Doc\\Down.mp3");
       selectSongs();
       //truncateTable();
       shutdown();
    }
    
    
    /*Creates connection to derby*/
    private static void createConnection()
    {
    	try {
            // connect method #1 - embedded driver
            
             conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                System.out.println("Connected to database #1");
            }
       
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    /*creates table only if table does not exists*/
    private static void createTable()
    {
        try
        {
            stmt = conn.createStatement();
            stmt.execute("CREATE TABLE Music("
            		+ "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
            		+ "File varchar(255),"
            		+ "Title varchar(255),"
            		+ "InsertDate DATE not null with default current DATE"
            		+ ")");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    
    /*truncates table*/
    private static void truncateTable()
    {
        try
        {
            stmt = conn.createStatement();
            stmt.execute("TRUNCATE TABLE Music");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    
    /*deleted a selected song from table*/
    private static void deleteSong(String FileName)
    {
    	String Title;
        try
        {
            stmt = conn.createStatement();
            Title =FileName.substring(FileName.lastIndexOf('\\')+1, FileName.length());
            System.out.println("The Title is "+Title);
            stmt.execute("DELETE FROM Music WHERE File="+"'"+FileName+"'");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    
    /*inserts a song into table*/
    private static void insertSong(String FileName)
    {
    	String Title;
        try
        {
            stmt = conn.createStatement();
            Title =FileName.substring(FileName.lastIndexOf('\\')+1, FileName.length());
            System.out.println("The Title is "+Title);
            stmt.execute("insert into Music values(default,"+"'"+FileName+"'"+",'"+Title+"',default)");
            //stmt.execute("insert into " + tableName + " values ("+
              //      id + ",'" +FileName+ ",'" + Title + "')");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    
    /*selects all songs from table*/
    private static void selectSongs()
    {
        try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from " + tableName);
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t");  
            }

            System.out.println("\n-------------------------------------------------");

            while(results.next())
            {
              //  int id = results.getInt(1);
                String ID = results.getString(1);
                String FILE = results.getString(2);
                String TITLE = results.getString(3);
                String DATE = results.getString(4);
                System.out.println(ID + "\t\t" + FILE + "\t\t" +TITLE+"\t\t"+DATE+"\t\t");
            }
            results.close();
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    /*closes database connection*/
    private static void shutdown()
    {
        try
        {
            if (stmt != null)
            {
                stmt.close();
            }
            if (conn != null)
            {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                conn.close();
            }           
        }
        catch (SQLException sqlExcept)
        {
            
        }

    }
    
    
    
}