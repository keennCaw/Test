package com.keennhoward.bruntwork.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartProductModel: CartProductModel)

    @Delete
    suspend fun delete(cartProductModel: CartProductModel)

    @Query("SELECT * FROM Cart")
    fun getCart(): LiveData<CartProductModel>

    @Query("DELETE FROM Cart")
    suspend fun clearCart()

}