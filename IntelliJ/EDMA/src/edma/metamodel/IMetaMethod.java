package edma.metamodel;


public interface IMetaMethod
{
	public String getName();
	public int getArrayPosition();
	public String getDescription();
	public int getNumberOfInputParameters();
	public IMetaMethodParameter getInputParameter(int i);
	public int getNumberOfOutputParameters();
	public IMetaMethodParameter getOutputParameter(int i);
	public int getNumberOfErrorCodes();
	public IMetaErrorCode getErrorCode(int i);
}
