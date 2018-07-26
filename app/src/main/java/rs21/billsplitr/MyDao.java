package rs21.billsplitr;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import rs21.billsplitr.POJOs.Bill;

@Dao
public interface MyDao {

    @Query("SELECT * FROM MyBills")
    List<Bill> getAllBills();

    @Query("SELECT * FROM MyBills WHERE id = :id")
    Bill billWithId(int id);

    @Insert
    void insertBill(Bill bill);

    @Update
    void updateBill(Bill bill);

    @Delete
    void deleteBill(Bill bill);
}
