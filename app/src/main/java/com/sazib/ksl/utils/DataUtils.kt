package com.sazib.ksl.utils

import com.sazib.ksl.data.db._task.Task
import com.sazib.ksl.data.db._user.User
import com.sazib.ksl.data.db.post_code.PostalDetails
import com.sazib.ksl.ui.test.TestModel
import com.sazib.ksl.ui.todo.edit_task.model.TaskTypeModel

object DataUtils {

  fun getTestData(): List<TestModel> = arrayListOf(
    TestModel( "Dhanmondi", "Dhanmondi Model Dhaka sdfas sdafjlkgasd ajfasdf afs"),
    TestModel( "Dhanmondi", "Dhanmondi Model Dhaka wasd"),
    TestModel( "Dhanmondi", "Dhanmondi Mode asdf sdf asd afeegjkuklwtob gweflv l Dhaka"),
    TestModel( "Dhanmondi", "Dhanmondisd asdij asoplqgwihnb ff;l',vbkrdmamfldk ko;g, Model  asdDhaka"),
    TestModel( "Dhanmondi", "Dhanmondi Model Dhaka"),
  )

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

  fun getTaskType(): List<TaskTypeModel> = arrayListOf(
      TaskTypeModel("Work", "7"),
      TaskTypeModel("Personal", "1"),
      TaskTypeModel("Shopping", "2"),
      TaskTypeModel("Shopping", "2"),
      TaskTypeModel("Shopping", "2"),
      TaskTypeModel("Shopping", "2"),
      TaskTypeModel("Shopping", "2"),
      TaskTypeModel("Shopping", "2"),
      TaskTypeModel("Health", "3")
  )

  fun getTaskList(): List<Task> = arrayListOf(
      Task(null, "Daily UI Challenge", "7.30", "8.30", "", "", "", "Create To Do List"),
      Task(null, "Daily UI Challenge", "7.30", "8.30", "", "", "", "Create To Do List"),
      Task(null, "Daily UI Challenge", "7.30", "8.30", "", "", "", "Create To Do List"),
      Task(null, "Daily UI Challenge", "7.30", "8.30", "", "", "", "Create To Do List"),
      Task(null, "Daily UI Challenge", "7.30", "8.30", "", "", "", "Create To Do List"),
  )
}
