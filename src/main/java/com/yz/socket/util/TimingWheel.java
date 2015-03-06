package com.yz.socket.util;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

public class TimingWheel {

	private static final HashedWheelTimer timer = new HashedWheelTimer();
	static int i=0;
	public static Timeout newTimeout(TimerTask task, long delay, TimeUnit unit) {
		return timer.newTimeout(task, delay, unit);
	}

	
}
