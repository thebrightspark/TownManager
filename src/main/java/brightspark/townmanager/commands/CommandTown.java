package brightspark.townmanager.commands;

import brightspark.townmanager.data.Area;
import brightspark.townmanager.data.AreasData;
import brightspark.townmanager.data.Town;
import brightspark.townmanager.handlers.action.Action;
import brightspark.townmanager.handlers.action.AreaAction;
import brightspark.townmanager.handlers.action.AreaHandler;
import brightspark.townmanager.util.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
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
        return "tm.command.town.usage";
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
            return "tm.command.town.create.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
        {
            if(!(sender instanceof EntityPlayerMP))
                throw new CommandException("tm.command.town.create.notPlayer");

            EntityPlayerMP player = (EntityPlayerMP) sender;
            int argsLength = args.length;
            String name;
            if(argsLength >= 7) //tm town create <name> <x1> <y1> <z1> <x2> <y2> <z2>
            {
                int[] posParts = new int[6];
                boolean parsedAllArgs = false;
                try
                {
                    for(int i = 0; i < 6; i++)
                        posParts[i] = Integer.parseInt(args[argsLength - 6 + i]);
                    parsedAllArgs = true;
                }
                catch(NumberFormatException ignored) {}

                if(parsedAllArgs)
                {
                    BlockPos pos1 = new BlockPos(posParts[0], posParts[1], posParts[2]);
                    BlockPos pos2 = new BlockPos(posParts[3], posParts[4], posParts[5]);
                    name = Utils.joinCommandArgs(args, 0, argsLength - 7);
                    boolean result = AreasData.get(sender.getEntityWorld()).addTown(new Town(name, player.getUniqueID(), new Area(pos1, pos2)));
                    if(result)
                        sender.sendMessage(new TextComponentTranslation("tm.command.town.create.success"));
                    else
                        sender.sendMessage(new TextComponentTranslation("tm.command.town.create.fail"));
                    return;
                }
            }

            //tm town create <name>
            //Create town by selecting area in-world by punching blocks
            name = Utils.joinCommandArgs(args, 0);
            AreaAction action = AreaHandler.getAction(player);
            if(action != null && action.hasAction())
            {
                //Action already in progress!
                sender.sendMessage(new TextComponentTranslation("tm.command.town.create.inProgress"));
            }
            else
            {
                //Start action
                action = new AreaAction(player);
                action.setInitData(Action.CREATE_TOWN, sender.getEntityWorld(), name);
                AreaHandler.putAction(action);
                sender.sendMessage(new TextComponentTranslation("tm.command.town.create.start"));
            }
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
            return "tm.command.town.delete.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
        {
            if(args.length == 0)
                throw new WrongUsageException(getUsage(sender));

            String name = Utils.joinCommandArgs(args, 0);
            ITextComponent text;
            if(AreasData.deleteTown(server, name))
                text = new TextComponentTranslation("tm.command.town.delete.success");
            else
                text = new TextComponentTranslation("tm.command.town.delete.failure");
            text.getStyle().setColor(TextFormatting.GOLD);
            sender.sendMessage(text);
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
            return "tm.command.town.expand.usage";
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
            return "tm.command.town.retract.usage";
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
            return "tm.command.town.members.usage";
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
            return "tm.command.town.addMember.usage";
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
            return "tm.command.town.removeMember.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
        {

        }
    }
}
