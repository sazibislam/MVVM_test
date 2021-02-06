package com.sazib.ksl.utils

import com.sazib.ksl.data.db._user.User
import com.sazib.ksl.data.db.post_code.PostalDetails

object DataUtils {

  fun getPostalDetails(): List<PostalDetails> = arrayListOf(
      PostalDetails(1, 1200, "Dhanmondi", "Dhanmondi Model", "Dhaka"),
      PostalDetails(2, 1214, "Agargoan", "Agargoan Model", "Dhaka"),
      PostalDetails(3, 1330, "Dhamrai", "Dhamrai Model", "Dhaka"),
      PostalDetails(4, 1200, "Dhanmondi", "Dhanmondi Model", "Dhaka")
  )

  fun getDummyUser(): List<User> = arrayListOf(
      User(null, "sazib", "sazibislam1@gmail.com", "sazib", true),
      User(null, "saiful", "sazibislam1@gmail.com", "sazibBoss", false)
  )
}
