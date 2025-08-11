
////Implement a Task Management System that manages a list of tasks. Each task has a priority, a status, and a name. The system should allow users to add tasks, remove tasks, sort tasks by priority, and filter tasks based on their status using a functional interface.
////
////Requirements:
////Task Class:
////Create a Task class with fields: name (String), priority (int), and status (an enum Status).
////Implement Comparable<Task> so tasks can be sorted based on priority (lower priority number means higher priority).
////Include appropriate constructors, getters, and setters.
////Status Enum:
////Create an enum Status with values: PENDING, IN_PROGRESS, and COMPLETED.
////Functional Interface:
////Create a functional interface TaskFilter with a method boolean filter(Task task) that is used to filter tasks based on certain conditions.
////TaskManager Class:
////Manage a collection (ArrayList) of tasks in the TaskManager class.
////Implement methods to:
////Add a task.
////Remove a task.
////Sort tasks by priority.
////Filter tasks based on status using the TaskFilter functional interface.
////Main Class:
////Create a Main class to demonstrate the system by adding tasks, sorting them by priority, and filtering based on status using a lambda expression with the TaskFilter interface.

//another question

enum Category{

}