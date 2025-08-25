package Internship;

// Command interface
interface Command {
    void execute();
    void undo();
}

// Receiver
class Light {
    void turnOn() {
        System.out.println("Light is ON");
    }
    void turnOff() {
        System.out.println("Light is OFF");
    }
}

// Concrete Commands
class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }
    public void execute() {
        light.turnOn();
    }
    public void undo() {
        light.turnOff();
    }
}

class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }
    public void execute() {
        light.turnOff();
    }
    public void undo() {
        light.turnOn();
    }
}

// Invoker
class RemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }

    public void pressUndo() {
        command.undo();
    }
}

// Client
public class CommandPatternDemo {
    public static void main(String[] args) {

        //Receiver class
        Light light = new Light();

        //here we are encapsulating the concrete command class
        //stores the reference of the receiver class which allows us to perform action in queue and allows us to undo/redo
        Command lightOn = new LightOnCommand(light);
        Command lightOff = new LightOffCommand(light);

        //"command interface object"with concrete command instance same light obj


        //invoker it is invoking the command;
        // it doesnt know what command (turn off/ turn on)to execute it knows only command(press button /press undo)
        RemoteControl remote = new RemoteControl();

        remote.setCommand(lightOn);
        remote.pressButton();
        remote.pressUndo();

        remote.setCommand(lightOff);
        remote.pressButton();
        remote.pressUndo();
    }
}

