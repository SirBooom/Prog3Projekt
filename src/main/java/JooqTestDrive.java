import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;


import java.sql.*;

import static org.jooq.impl.DSL.table;
import static org.jooq.impl.SQLDataType.VARCHAR;
import static com.example.generated.Tables.*;

public class JooqTestDrive {
    public static void main(String[] args) throws SQLException{


            DSLContext context = buildConnection();
            //context.deleteFrom(RECIPE).where(RECIPE.ID.equal(2)).execute();
            //insertIntoRecipe(2,"rezept2");
            insertIntoRecipe(6,"rezept6");

            System.out.println(getRecipeAsString());
            //Result<Record> result = context.select().from(RECIPE).fetch();
            //context.insertInto((RECIPE)).values(2,"rezept2").execute();


            /** context.createTableIfNotExists(EXAMPLE).
                    column("id", BIGINT.identity(true)).
                    column("Name", VARCHAR(25).nullable(false))
                    .constraints(DSL.constraint().primaryKey("id"))
                    .execute();
            var selected = context.select(EXAMPLE.ID, EXAMPLE.NAME)
                    .from(EXAMPLE)
                    .where(EXAMPLE.ID.eq(11))
                    .fetchOne();

            //context.insertInto(EXAMPLE)
            //        .values(11,"test").execute();

            System.out.println(selected);
            */

            //System.out.println(result);


    }

    /**
     *
     * @return DSLContext to the SQL-Database
     * @throws SQLException if connection to database fails
     */
    public static DSLContext buildConnection() throws SQLException{
        try {
            String jdbcUrl = "jdbc:sqlite:rezeptverwaltungsdb.db";
            Connection connection  = DriverManager.getConnection(jdbcUrl);
            return DSL.using(connection);

        } catch (SQLException e){

            throw new SQLException("Error: Connection to SQL-Database");

        }
    }

    public static boolean insertIntoRecipe(int num, String str){
        try {
            DSLContext context = buildConnection();
            context.insertInto(RECIPE).values(num, str).execute();
            return true;

        }catch (SQLException e){

            e.printStackTrace();

        }
        return false;
    }

    public static String getRecipeAsString(){
        StringBuilder str = new StringBuilder();
        try {

            DSLContext context = buildConnection();
            Result<Record> result = context.select().from(RECIPE).fetch();

                for (Record r : result) {
                    Integer id = r.getValue(RECIPE.ID);
                    String name = r.getValue(RECIPE.NAME);

                    str.append("ID: ").append(id).append(" NAME: ").append(name).append("\n");

                }
                return str.toString();

        }catch (SQLException e){

            e.printStackTrace();
            return "Error while connecting to database";

        }

    }

}
