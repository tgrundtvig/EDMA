package edma.valuedomains;

public interface IListValueDomain extends IMetaValueDomain
{
	public IMetaValueDomain getElementValueDomain();
	public Integer getMinSize();
	public Integer getMaxSize();
}
