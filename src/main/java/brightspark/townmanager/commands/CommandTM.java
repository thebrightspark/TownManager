package brightspark.townmanager.commands;

import com.google.common.collect.ImmutableList;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;
import net.minecraftforge.server.command.CommandTreeHelp;

import java.util.List;

/**
 * Base command tree for all Town Manager commands
 * TODO: Add the rest of the main command branches
 *
 * /tm >subCommand<
 */
public class CommandTM extends CommandTreeBase
{
    public CommandTM()
    {
        addSubcommand(new CommandTown());
        addSubcommand(new CommandTowns());
        addSubcommand(new CommandPlots());
        addSubcommand(new CommandTreeHelp(this));
    }

    @Override
    public String getName()
    {
        return "tm";
    }

    @Override
    public List<String> getAliases()
    {
        return ImmutableList.of("townmanager");
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "tm.commands.usage";
    }
}
