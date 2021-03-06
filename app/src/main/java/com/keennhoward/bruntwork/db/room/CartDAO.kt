package com.keennhoward.bruntwork.db.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartProductModel: CartProductModel)

    @Delete
    suspend fun delete(cartProductModel: CartProductModel)

    @Query("SELECT * FROM Cart")
    fun getCart(): LiveData<List<CartProductModel>>

    @Query("DELETE FROM Cart")
    suspend fun clearCart()

}