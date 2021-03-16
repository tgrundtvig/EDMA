package org.abstractica.edma.valuedomains.exceptions;

import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.impl.Constraint;

public class InvalidValueException extends RuntimeException
{
	private static final long serialVersionUID = 3703265687537828693L;
	private IMetaValueDomain domain;
	private Constraint violated;
	private String reason;

	public InvalidValueException(	IMetaValueDomain domain,
									Constraint violated,
									String reason)
	{
		super("Violation of constraint " + violated.getName() + " in value domain " + domain.getName() + ". Reason: " + reason);
		this.domain = domain;
		this.violated = violated;
		this.reason = reason;
	}

	public IMetaValueDomain getDomain()
	{
		return domain;
	}

	public Constraint getViolatedConstraint()
	{
		return violated;
	}
	
	public String getReason()
	{
		return reason;
	}
}
