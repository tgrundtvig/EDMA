package org.abstractica.edma.valuedomains;

import org.abstractica.edma.util.Pair;

public interface IExternalConstraints
{
	public Pair<Integer, String> checkConstraints(Object value, int valueDomainIndex);
}
