package com.univocity.articles.jmh.params;

public enum BufferSize {
	D2K(2000), D4K(4000), _1K(1<< 10), _2K(1<< 11), _4K(1<<12), _8K(1<<13), _16K(1<<14), _32K(1<<15), _64K(1<<16), _128K(1<<17), _256K(1<<18), _512K(1<<19), _1M(1<<20), _4M(1<<22);
	
	BufferSize(int n) {
		this.size = n;
	}
	
	public final int size;
	
}
