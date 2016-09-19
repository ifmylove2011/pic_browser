package com.xter.picbrowser.event;

/**
 * Created by XTER on 2016/9/19.
 */
public class StateEvent {
	boolean state;

	public StateEvent(boolean state) {
		this.state = state;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
}
