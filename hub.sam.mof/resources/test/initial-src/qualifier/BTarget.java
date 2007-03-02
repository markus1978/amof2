package qualifier;


/**
 * <b>BTarget</b>, superClass = {qualifier.Target}
 */
public interface BTarget extends qualifier.Target
{

    /**
     * <b>asource</b>, multiplicity=(0,1)
     */
    public qualifier.ASource getAsource();

    public void setAsource(qualifier.ASource value);

}

