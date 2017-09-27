package com.jdglazer.igrd;

public abstract class IGRDCommonDTO {
	
	protected Class childClass;
	
	protected IGRDCommonDTO( Class child ) {
		childClass = child;
	}
}
