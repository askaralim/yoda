package com.taklip.yoda.jediorder.bean;

public class IdMeta {
	//|Version|Time|Machine Id|Sequence|
	//|*0*|*1-41*|*42-51*|*52-63*|
	private final byte versionBits;
	private final byte timeBits;
	private final byte machineBits;
	private final byte sequenceBits;

	public IdMeta(byte versionBits, byte timeBits, byte machineBits, byte sequenceBits) {
		super();
		this.versionBits = versionBits;
		this.timeBits = timeBits;
		this.machineBits = machineBits;
		this.sequenceBits = sequenceBits;
	}

	public byte getVersionBits() {
		return versionBits;
	}

	public long getVersionBitsMask() {
		return -1L ^ -1L << versionBits;
	}

	public byte getTimeBits() {
		return timeBits;
	}

	public long getTimeBitsStartPos() {
		return versionBits;
	}

	public long getTimeBitsMask() {
		return -1L ^ -1L << timeBits;
	}

	public byte getMachineBits() {
		return machineBits;
	}

	public long getMachineBitsStartPos() {
		return versionBits + timeBits;
	}

	public long getMachineBitsMask() {
		return -1L ^ -1L << machineBits;
	}

	public byte getSequenceBits() {
		return sequenceBits;
	}

	public long getSequenceBitsStartPos() {
		return versionBits + timeBits + machineBits;
	}

	public long getSequenceBitsMask() {
		return -1L ^ -1L << sequenceBits;
	}
}