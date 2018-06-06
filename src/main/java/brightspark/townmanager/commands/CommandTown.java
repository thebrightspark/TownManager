package brightspark.townmanager.commands;

import brightspark.townmanager.TownManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeBase;
import net.minecraftforge.server.command.CommandTreeHelp;

/**
 * Base command branch for "town" commands
 * TODO: Finish 'town' sub-commands
 *
 * /tm town >subCommand<
 */
public class CommandTown extends CommandTreeBase
{
    public CommandTown()
    {
        addSubcommand(new CreateCommand());
        addSubcommand(new DeleteCommand());
        addSubcommand(new ExpandCommand());
        addSubcommand(new RetractCommand());
        addSubcommand(new ListCommand());
        addSubcommand(new MembersCommand());
        addSubcommand(new AddMemberCommand());
        addSubcommand(new RemoveMemberCommand());
        addSubcommand(new CommandTreeHelp(this));
    }

    @Override
    public String getName()
    {
        return "town";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return TownManager.MOD_ID + ".command.town.usage";
    }

    private static class CreateCommand extends CommandBase
    {
        @Override
        public String getName()
        {
            return "create";
        }

        @Override
        public String getUsage(ICommandSender sender)
        {
            return TownManager.MOD_ID + ".command.town.create.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
        {

        }
    }

    private static class DeleteCommand extends CommandBase
    {
        @Override
        public String getName()
        {
            return "delete";
        }

        @Override
        public String getUsage(ICommandSender sender)
        {
            return TownManager.MOD_ID + ".command.town.delete.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
        {

        }
    }

    private static class ExpandCommand extends CommandBase
    {
        @Override
        public String getName()
        {
            return "expand";
        }

        @Override
        public String getUsage(ICommandSender sender)
        {
            return TownManager.MOD_ID + ".command.town.expand.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
        {

        }
    }

    private static class RetractCommand extends CommandBase
    {
        @Override
        public String getName()
        {
            return "retract";
        }

        @Override
        public String getUsage(ICommandSender sender)
        {
            return TownManager.MOD_ID + ".command.town.retract.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
        {

        }
    }

    private static class ListCommand extends CommandBase
    {
        @Override
        public String getName()
        {
            return "list";
        }

        @Override
        public String getUsage(ICommandSender sender)
        {
            return TownManager.MOD_ID + ".command.town.list.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
        {

        }
    }

    private static class MembersCommand extends CommandBase
    {
        @Override
        public String getName()
        {
            return "members";
        }

        @Override
        public String getUsage(ICommandSender sender)
        {
            return TownManager.MOD_ID + ".command.town.members.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
        {

        }
    }

    private static class AddMemberCommand extends CommandBase
    {
        @Override
        public String getName()
        {
            return "addMember";
        }

        @Override
        public String getUsage(ICommandSender sender)
        {
            return TownManager.MOD_ID + ".command.town.addMember.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
        {

        }
    }

    private static class RemoveMemberCommand extends CommandBase
    {
        @Override
        public String getName()
        {
            return "removeMember";
        }

        @Override
        public String getUsage(ICommandSender sender)
        {
            return TownManager.MOD_ID + ".command.town.removeMember.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
        {

        }
    }
}
