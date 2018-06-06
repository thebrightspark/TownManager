package brightspark.townmanager.util;

import java.util.List;

public class ListPage<T>
{
    private List<T> list;
    private int pageNum, pageMax, indexStart;

    public ListPage(List<T> list, int pageNum, int pageMax, int indexStart)
    {
        this.list = list;
        this.pageNum = pageNum;
        this.pageMax = pageMax;
        this.indexStart = indexStart;
    }

    public List<T> getList()
    {
        return list;
    }

    public int getPageNum()
    {
        return pageNum;
    }

    public int getPageMax()
    {
        return pageMax;
    }

    public int getIndexStart()
    {
        return indexStart;
    }
}
