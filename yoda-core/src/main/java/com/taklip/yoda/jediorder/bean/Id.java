package com.taklip.yoda.jediorder.bean;

import java.io.Serializable;

public class Id implements Serializable {
	private static final long serialVersionUID = 6870931236218221183L;

	private long version;
	private long time;
	private long machine;
	private long sequence;

	public Id() {
	}

	public Id(long version, long time, long machine, long sequence) {
		super();
		this.version = version;
		this.time = time;
		this.machine = machine;
		this.sequence = sequence;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getMachine() {
		return machine;
	}

	public void setMachine(long machine) {
		this.machine = machine;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("[");
		sb.append("version=").append(version).append(",");
		sb.append("time=").append(time).append(",");
		sb.append("machine=").append(machine).append(",");
		sb.append("seq=").append(sequence).append("]");

		return sb.toString();
	}
}