package brightspark.townmanager.commands;

import brightspark.townmanager.TownManager;
import brightspark.townmanager.data.AreasData;
import brightspark.townmanager.data.Plot;
import brightspark.townmanager.util.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.LinkedList;
import java.util.List;

/**
 * Lists all of the plots owned by the player
 * Provide no arguments to list all of the sender's plots, otherwise parse the argument as a player name and find theirs
 *
 * /tm plots [playerName]
 */
public class CommandPlots extends CommandBase
{
    @Override
    public String getName()
    {
        return "plots";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return TownManager.MOD_ID + ".command.plots.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        EntityPlayer player;
        List<Plot> plots;

        switch(args.length)
        {
            case 0: //List all plots the player owns
                if(!(sender.getCommandSenderEntity() instanceof EntityPlayer))
                    throw new CommandException("Only players can use this command!");
                player = (EntityPlayer) sender.getCommandSenderEntity();
                plots = new LinkedList<>(AreasData.getPlotsForPlayer(server, player));
                break;
            case 1: //List all plots the given player owns
                player = server.getPlayerList().getPlayerByUsername(args[0]);
                if(player == null)
                    throw new CommandException("Player with username '" + args[0] + "' not found!");
                plots = new LinkedList<>(AreasData.getPlotsForPlayer(server, player));
                break;
            default:
                throw new WrongUsageException(getUsage(sender));
        }

        ITextComponent text;
        if(plots.isEmpty())
        {
            text = new TextComponentTranslation("tm.command.plots.none");
            text.getStyle().setColor(TextFormatting.GOLD);
        }
        else
        {
            plots.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
            text = Utils.createPagedListText(plots, 0, 10);
        }
        sender.sendMessage(text);
    }
}
