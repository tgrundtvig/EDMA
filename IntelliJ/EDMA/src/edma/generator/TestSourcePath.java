package edma.generator;

public class TestSourcePath implements ISourcePath
{
	private String relPath;
	
	public TestSourcePath(String relPath)
	{
		this.relPath = relPath;
	}

	@Override
	public String getSourcePath()
	{
		String tobiasPath = "C:/Users/tgrun/Desktop/Development/EDMA/IntelliJProject/EDMA/";
		return tobiasPath + relPath;
	}

}
