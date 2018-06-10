package brightspark.townmanager.commands;

import brightspark.townmanager.data.AreasData;
import brightspark.townmanager.data.Town;
import brightspark.townmanager.util.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.LinkedList;
import java.util.List;

/**
 * Lists all of the towns on the server
 * Can provide a search filter as an argument - will return all towns containing the argument
 *
 * /tm towns [nameFilter]
 */
public class CommandTowns extends CommandBase
{
    @Override
    public String getName()
    {
        return "towns";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "tm.command.towns.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        List<Town> towns = args.length == 0 ?
                new LinkedList<>(AreasData.getAllTowns(server)) :
                new LinkedList<>(AreasData.getAllTownsContaining(server, Utils.joinCommandArgs(args, 1)));

        ITextComponent text;
        if(towns.isEmpty())
        {
            text = new TextComponentTranslation("tm.command.plots.none");
            text.getStyle().setColor(TextFormatting.GOLD);
        }
        else
        {
            towns.sort((t1, t2) -> t1.getName().compareToIgnoreCase(t2.getName()));
            text = Utils.createPagedListText(towns, 0, 10);
        }
        sender.sendMessage(text);
    }
}
