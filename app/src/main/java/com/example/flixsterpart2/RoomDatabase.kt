import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flixsterpart2.CommentEntity

@Database(entities = [CommentEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commentDao(): CommentDao
}
