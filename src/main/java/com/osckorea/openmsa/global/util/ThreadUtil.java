package com.osckorea.openmsa.global.util;

import com.osckorea.openmsa.global.payload.RequestHeaderPayload;

public class ThreadUtil {

    public static ThreadLocal<RequestHeaderPayload> threadLocalRequestHeaderPayload = new ThreadLocal<RequestHeaderPayload>();
	public static InheritableThreadLocal<RequestHeaderPayload> inheritableThreadLocalRequestHeaderPayload = new InheritableThreadLocal<RequestHeaderPayload>();

}
