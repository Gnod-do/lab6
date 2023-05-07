package main.commands;

public abstract class AbstractCommand implements Command{
    private String name;

    private String usage;

    private String description;

    public AbstractCommand(String name, String usage, String description) {
        this.name = name;
        this.usage  = usage;
        this.description = description;
    }

    /**
     * @return Name and usage way of the command.
     */

    public String getName() {return name;}

    /**
     * @return Usage of the command.
     */

    @Override
    public String getUsage() {
        return usage;
    }

    /**
     * @return Description of the command.
     */

    public String geDescription() {return description;}

    @Override
    public String toString() {return name + " (" + description + " )";}

    @Override
    public int hashCode() {return name.hashCode() + description.hashCode();}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return  true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return  false;
        AbstractCommand other = (AbstractCommand) obj;
        return name.equals(other.name) && description.equals(other.description);
    }


}
