package api.cat.breed;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BreedDao {
    @Query("SELECT * FROM Breed")
      List<Breed> getBreeds();

    @Insert
     void coll(Breed... breed);


}
