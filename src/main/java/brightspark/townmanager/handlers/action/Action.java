package brightspark.townmanager.handlers.action;

public enum Action
{
    CREATE_PLOT("plot"),
    CREATE_TOWN("town");

    private final String lang;

    Action(String lang)
    {
        this.lang = lang;
    }

    public String langName()
    {
        return lang;
    }
}
