package brightspark.townmanager.data;

import java.util.UUID;

public class AreaAction
{
    private UUID uuid;
    private Action currentAction = Action.NONE;

    public AreaAction(UUID uuid)
    {
        this.uuid = uuid;
    }

    public Action getCurrentAction()
    {
        return currentAction;
    }

    public void setCurrentAction(Action currentAction)
    {
        this.currentAction = currentAction;
        if(currentAction == Action.COMPLETE)
            AreaHandler.removeAction(uuid);
    }
}
