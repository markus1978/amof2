package qualifier;


/**
 * <b>ATarget</b>, superClass = {qualifier.Target}
 */
public interface ATarget extends qualifier.Target
{

    /**
     * <b>asource</b>, multiplicity=(0,1)
     */
    public qualifier.ASource getAsource();

    public void setAsource(qualifier.ASource value);

}

