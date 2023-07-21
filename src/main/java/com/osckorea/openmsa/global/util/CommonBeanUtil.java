package com.osckorea.openmsa.global.util;

import com.osckorea.openmsa.global.core.ApplicationContextProvider;

public class CommonBeanUtil {

   public static Object getBean(String beanName) {
      return ApplicationContextProvider.getContext().getBean(beanName);

   }

   public static <T> Object getBean(Class<T> clz) {
      return ApplicationContextProvider.getContext().getBean(clz);

   }

}
