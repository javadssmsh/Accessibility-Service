package ir.javadsh.challenge.db.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ReportLogDao {

    @Query("SELECT * FROM reportlog")
    public List<ReportLog> getAllLogs();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void saveLog(ReportLog reportLog);

    @Query("DELETE FROM reportlog")
    public void deleteAll();

    @Delete
    public void deleteLog(ReportLog reportLog);
}
