package org.abstractica.edma.compiler.ast;

import java.util.Collection;

public interface IAttributeContainer
{
	public void addAttribute(AttributeNode att);
	public void addAttributes(Collection<AttributeNode> atts);
	public Collection<AttributeNode> getAttributes();
	public AttributeNode getAttribute(int i);
	public String getValueDomainName();
	public AttributeNode getAttribute(String att);
}
