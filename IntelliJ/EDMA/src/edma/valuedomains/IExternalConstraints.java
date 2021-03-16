package edma.valuedomains;

import edma.util.Pair;

public interface IExternalConstraints
{
	public Pair<Integer, String> checkConstraints(Object value, int valueDomainIndex);
}
