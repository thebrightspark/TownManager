package brightspark.townmanager.util;

import brightspark.townmanager.data.AreaBase;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class Utils
{
    /**
     * Returns a sub list representing a "page" of the given list - useful for display purposes when listing values
     * @param list The list to get a page of
     * @param page The requested page number
     * @param maxPerPage The max amount of entries on a page
     * @param <T> The type of values in the list
     * @return An object holding the sub list, actual page number and the start index of the page from the original list
     */
    public static <T> ListPage<T> getPageOfList(List<T> list, int page, int maxPerPage)
    {
        int listSize = list.size();
        int pageMax = listSize / maxPerPage;
        //We reduce the given page number by 1, because we calculate starting from page 0, but is shown to start from page 1.
        if(page > 0) page--;
        if(page * maxPerPage > listSize) page = pageMax;

        //Work out the range to display on the page
        int min = page * maxPerPage;
        int max = min + maxPerPage;
        if(listSize < max) max = listSize;

        List<T> listPage = list.subList(min, max);
        return new ListPage<>(listPage, page, pageMax, min);
    }

    /**
     * Creates an ITextComponent to display a list page in the chat
     * @param list The list to get a page of
     * @param page The requested page number
     * @param maxPerPage The max amount of entries on a page
     * @param <T> The type of values in the list (must implement Named)
     * @return The ITextComponent
     */
    public static <T extends AreaBase> ITextComponent createPagedListText(List<T> list, int page, int maxPerPage)
    {
        return createPagedListText(getPageOfList(list, page, maxPerPage));
    }

    /**
     * Creates an ITextComponent to display a list page in the chat
     * @param page The ListPage object from getPageOfList
     * @param <T> The type of values in the list (must implement Named)
     * @return The ITextComponent
     */
    public static <T extends AreaBase> ITextComponent createPagedListText(ListPage<T> page)
    {
        ITextComponent component = new TextComponentString(TextFormatting.YELLOW + " ========== ");
        ITextComponent title = new TextComponentTranslation("tm.command.generic.pageTitle",
                page.getPageNum() + 1, page.getPageMax() + 1);
        title.getStyle().setColor(TextFormatting.GOLD);
        component.appendSibling(title);
        component.appendText(TextFormatting.YELLOW + " ==========");
        List<T> list = page.getList();
        int indexStart = page.getIndexStart();
        for(int i = 0; i < list.size(); i++)
            component.appendText("\n  " + (indexStart + i) + ". " + list.get(i).getName());
        return component;
    }

    /**
     * Joins together the given Strings in the array into one String where each value is separated by a space
     * @param args Command arguments
     * @param start Index to start from (go till the end of the array)
     * @return The combined String
     */
    public static String joinCommandArgs(String[] args, int start)
    {
        return joinCommandArgs(args, start, args.length);
    }

    /**
     * Joins together the given Strings in the array into one String where each value is separated by a space
     * @param args Command arguments
     * @param start Index to start from (go till the end of the array)
     * @param end Index to end at
     * @return The combined String
     */
    public static String joinCommandArgs(String[] args, int start, int end)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = start; i < end; i++)
            sb.append(args[i]);
        return sb.toString();
    }
}
