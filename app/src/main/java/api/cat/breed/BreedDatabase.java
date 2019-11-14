package api.cat.breed;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Breed.class}, version=1,exportSchema = false)
public abstract class BreedDatabase extends RoomDatabase {

    public abstract BreedDao dao();
}