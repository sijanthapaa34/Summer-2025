package Practice;

import java.util.ArrayList;
import java.util.Collections;

//Implement a Task Management System that manages a list of tasks. Each task has a priority, a status, and a name. The system should allow users to add tasks, remove tasks, sort tasks by priority, and filter tasks based on their status using a functional interface.
//
//Requirements:
//Task Class:
//Create a Task class with fields: name (String), priority (int), and status (an enum Status).
//Implement Comparable<Task> so tasks can be sorted based on priority (lower priority number means higher priority).
//Include appropriate constructors, getters, and setters.
//Status Enum:
//Create an enum Status with values: PENDING, IN_PROGRESS, and COMPLETED.
//Functional Interface:
//Create a functional interface TaskFilter with a method boolean filter(Task task) that is used to filter tasks based on certain conditions.
//TaskManager Class:
//Manage a collection (ArrayList) of tasks in the TaskManager class.
//Implement methods to:
//Add a task.
//Remove a task.
//Sort tasks by priority.
//Filter tasks based on status using the TaskFilter functional interface.
//Main Class:
//Create a Main class to demonstrate the system by adding tasks, sorting them by priority, and filtering based on status using a lambda expression with the TaskFilter interface.
enum Status{
    PENDING , IN_PROGRESS, COMPLETED;
        }
interface TaskFilter {
    boolean filter(Task task);
}

class Task implements Comparable<Task>{
    String name;
    int priority;
    Status status;

    public Task(Task task) {
        this.name = task.name;
        this.priority = task.priority;
        this.status = task.status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                '}';
    }

    @Override
    public int compareTo(Task that) {
        if(this.priority >that.priority){
            return 1;
        }
        else{
            return -1;
        }
    }
}
class TaskManager {
    ArrayList<Task> t = new ArrayList<Task>();

    public void addTask(Task task) {
//        Task obj = new Task(task);
//        t.add(obj);
        t.add(new Task(task));
    }

    public void removeTask(Task task) {
        t.remove(task);
    }

    public void sort() {
        Collections.sort(t);
//        TaskFilter tf = new TaskFilter() {
////            @Override
////            public boolean filter(Task task) {
////                if(ta)return false;
////            }
////        };
    }
}

public class Main {
}
