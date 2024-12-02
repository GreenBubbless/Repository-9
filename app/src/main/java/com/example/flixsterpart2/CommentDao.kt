import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.flixsterpart2.CommentEntity

@Dao
interface CommentDao {
    @Insert
    suspend fun insert(comment: CommentEntity)

    @Query("SELECT * FROM comments WHERE movieId = :movieId")
    suspend fun getCommentsByMovieId(movieId: String): List<CommentEntity>

    @Delete
    suspend fun delete(comment: CommentEntity)
}
