package concurrency;

import java.util.StringJoiner;

class TheThread extends Thread {
    private Integer counter = 1;

    // public void start() {
    //     System.out.println(this.getName() + " started");
    //     run();
    // }
    
    public TheThread() {}
    public TheThread(String name) { 
        super.setName(name); 
        StringJoiner joiner = new StringJoiner(",", "[", "]")
            .add("ID: " + this.getId())
            .add("Name: " + this.getName())
            .add("Priority: " + this.getPriority())
            .add("Group: " + this.getThreadGroup().getName())
            .add("State: " + this.getState().name())
            .add("Counter: " + this.counter);

        System.out.println("Thread Created:  " + joiner.toString());
    }

    public TheThread(ThreadGroup group, String name) { 
        super(group, name); 
        StringJoiner joiner = new StringJoiner(",", "[", "]")
            .add("ID: " + this.getId())
            .add("Name: " + this.getName())
            .add("Priority: " + this.getPriority())
            .add("Group: " + this.getThreadGroup().getName())
            .add("State: " + this.getState().name())
            .add("Counter: " + this.counter);

        System.out.println("Thread Created:  " + joiner.toString());
    }

    public TheThread(String name, Integer counter) { 
        super.setName(name); 
        this.counter = counter;
        StringJoiner joiner = new StringJoiner(",", "[", "]")
            .add("ID: " + this.getId())
            .add("Name: " + this.getName())
            .add("Priority: " + this.getPriority())
            .add("Group: " + this.getThreadGroup().getName())
            .add("State: " + this.getState().name())
            .add("Counter: " + this.counter);

        System.out.println("Thread Created:  " + joiner.toString());
    }
    
    public TheThread(ThreadGroup group, String name, Integer counter) { 
        super(group, name); 
        this.counter = counter;
        StringJoiner joiner = new StringJoiner(",", "[", "]")
        .add("ID: " + this.getId())
        .add("Name: " + this.getName())
        .add("Priority: " + this.getPriority())
        .add("Group: " + this.getThreadGroup().getName())
        .add("State: " + this.getState().name())
        .add("Counter: " + this.counter);

        System.out.println("Thread Created:  " + joiner.toString());
    }

    @Override
    public void run() {
        System.out.println("Thread:[" + this.getName() + "] is started");
        for(int i = 1; i <= counter; i++) {
            StringJoiner joiner = new StringJoiner(",", "[", "]")
                .add("ID: " + this.getId())
                .add("Name: " + this.getName())
                .add("Priority: " + this.getPriority())
                .add("Group: " + this.getThreadGroup().getName())
                .add("State: " + this.getState().name());

            System.out.println(joiner.toString());

            try {
                sleep(1000);
            } catch(InterruptedException e) {
                System.out.println(this.getName() + " thrown error: " + e.getMessage());
            }
            System.out.println("Thread:[" + this.getName() + "] is exiting");
        }
    }
}
