package com.univocity.articles.jmh.params;

public enum BufferSize {
	_1K(10), _4K(12), _8K(13), _16K(14), _32K(15), _64K(16), _128K(17), _1M(20), _4M(22);
	
	BufferSize(int n) {
		this.size = 1 << n;
	}
	
	public final int size;
	
}
