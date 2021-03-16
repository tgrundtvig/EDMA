package edma.compiler.ast;

import java.util.ArrayList;

public class WorldNode extends AstNode
{
	private ArrayList<AValueDomainNode> valueDomains;
	private ArrayList<DataModelNode> dataModels;
	
	public WorldNode(String fileName)
	{
		super(fileName, 1);
		valueDomains = new ArrayList<AValueDomainNode>();
		dataModels = new ArrayList<DataModelNode>();
	}
	
	public DataModelNode getDataModelByName(String name)
	{
		for (DataModelNode dmNode : dataModels)
		{
			if (dmNode.getName().equals(name))
				return dmNode;
		}
		return null;
	}

	public ArrayList<DataModelNode> getDataModels()
	{
		return dataModels;
	}

	public ArrayList<AValueDomainNode> getValueDomains()
	{
		return valueDomains;
	}

	public void addValueDomain(AValueDomainNode n)
	{
		valueDomains.add(n);
	}

	public void addDataModel(DataModelNode dm)
	{
		dataModels.add(dm);
	}

	public void print(String tabs)
	{
		for(AValueDomainNode vd : valueDomains)
		{
			vd.print(tabs);
		}
		for(DataModelNode dm : dataModels)
		{
			dm.print(tabs);
		}
	}
}
