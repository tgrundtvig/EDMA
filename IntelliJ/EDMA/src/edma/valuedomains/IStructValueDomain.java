package edma.valuedomains;

public interface IStructValueDomain extends IMetaValueDomain
{
	public int getNumberOfFields();
	public IStructField getField(int i);
}
