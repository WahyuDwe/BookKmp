package com.dwe.bookkmp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.dwe.bookkmp.domain.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)

    @Transaction
    @Query("SELECT * FROM book WHERE id = :bookId")
    suspend fun getBook(bookId: Int): Book

    @Transaction
    @Query("SELECT * FROM book WHERE id = :bookId")
    fun getBookByIdFlow(bookId: Int): Flow<Book?>

    @Transaction
    @Query("SELECT * FROM book ORDER BY isFavorite DESC")
    fun readAllBooksSortedByFavorite(): Flow<List<Book>>

    @Transaction
    @Query("SELECT * FROM book")
    fun readAllBooks(): Flow<List<Book>>

    @Query("UPDATE book SET isFavorite = :isFavorite WHERE id = :bookId")
    suspend fun setFavorite(isFavorite: Boolean, bookId: Int)

    @Transaction
    @Query("DELETE FROM book WHERE id = :bookId")
    suspend fun deleteBookId(bookId: Int)
}