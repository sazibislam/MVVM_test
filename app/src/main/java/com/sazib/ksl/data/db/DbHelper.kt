package com.sazib.ksl.data.db

import com.sazib.ksl.data.db._task.Task
import com.sazib.ksl.data.db._user.User
import com.sazib.ksl.data.db.loan.Loan
import com.sazib.ksl.data.db.post_code.PostalDetails
import com.sazib.ksl.data.db.user_details.UserDetails
import io.reactivex.Observable

interface DbHelper {

    suspend fun checkUser(user: User): Observable<List<User>>
    suspend fun checkUserExist(user: User): Observable<List<User>>
    suspend fun insertUser(user: List<User>): Observable<Boolean>

    suspend fun loadTaskAll(): Observable<List<Task>>
    suspend fun insertTaskList(task: List<Task>): Observable<Boolean>
    suspend fun deleteTaskList(task: List<Task>): Observable<List<Task>>

    suspend fun insertUserDetails(userDetails: List<UserDetails>): Observable<Boolean>
    suspend fun loadUserAll(): Observable<List<UserDetails>>

    suspend fun insertPostalDetails(postalDetails: List<PostalDetails>): Observable<Boolean>
    suspend fun loadPostalDetailsAll(): Observable<List<PostalDetails>>

    suspend fun loadLoanAll(): Observable<List<Loan>>
    suspend fun insertLoanList(loan: List<Loan>): Observable<Boolean>
    suspend fun deleteLoanList(loan: List<Loan>): Observable<List<Loan>>
}