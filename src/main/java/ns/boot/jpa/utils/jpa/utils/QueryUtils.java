package ns.boot.jpa.utils.jpa.utils;


import ns.boot.jpa.utils.jpa.function.ExceptionFunction;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author zn
 */
public class QueryUtils {
    public enum StringEnums{upper,lower}

    public static boolean isNullOrEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return "".equals(o.toString()) || "null".equals(o.toString());
        }
        return false;
    }

    public static String getQueryFilterName(String name) {
        String[] names = name.split("\\.");
        StringBuilder temp = new StringBuilder(names[0]);
        if (names.length > 1) {
            temp.append(Character.toUpperCase(names[1].charAt(0))).append(names[1].substring(1)).toString();
        }
        return temp.toString();
    }

    public static Object getValue(String field, Object object) {
        try {
//			field.setAccessible(true);
            Method getMethod = new PropertyDescriptor(field, object.getClass()).getReadMethod();
            return getMethod.invoke(object);
        } catch (Exception e) {
            System.out.println("getvalue error");
        }
        return null;
    }

    public static List<Field> getAllFields(Class object, List list) {
        if (object.getSuperclass() == null) {
            return list;
        } else {
            list.addAll(Arrays.asList(object.getDeclaredFields()));
            return getAllFields(object.getSuperclass(), list);
        }
    }

    public static Object getClassTypeValue(Class<?> typeClass, List<Object> list) {
        Object value = null;
        if (list != null) {
            value = list.get(0);
        }
        if (typeClass == int.class || value instanceof Integer) {
            if (null == value) {
                return null;
            }
            return Integer.parseInt(value.toString());
        } else if (typeClass == short.class) {
            if (null == value) {
                return 0;
            }
            return Short.valueOf(value.toString());
        } else if (typeClass == byte.class) {
            if (null == value) {
                return 0;
            }
            return Byte.valueOf(value.toString());
        } else if (typeClass == double.class) {
            if (null == value) {
                return 0;
            }
            return Double.valueOf(value.toString());
        } else if (typeClass == long.class) {
            if (null == value) {
                return 0;
            }
            return Long.valueOf(value.toString());
        } else if (typeClass == String.class) {
            if (null == value) {
                return "";
            }
            return value.toString();
        } else if (typeClass == boolean.class) {
            if (null == value) {
                return true;
            }
            return value;
        } else if (typeClass == BigDecimal.class) {
            if (null == value) {
                return new BigDecimal(0);
            }
            return new BigDecimal(value + "");
        } else if (typeClass == List.class) {
            return list;
        } else if (typeClass.getSuperclass() == Enum.class) {
            for (int i = 0; i < typeClass.getEnumConstants().length; i++) {
                if (typeClass.getEnumConstants()[i].toString().equals(value)) {
                    return typeClass.getEnumConstants()[i];
                }
            }
            return typeClass.getEnumConstants()[0];
        } else {
            return typeClass.cast(value);
        }
        //enums
    }

    public static boolean isEmpty(Object o) {
        if (o == null){
            return true;
        }else if (o instanceof String && (((String) o).isEmpty() || "".equals(o))) {
            return true;
        }else if (o instanceof Integer && Integer.parseInt((String) o) == 0) {
            return true;
        }else {
            return false;
        }
    }

    public static String changeFirstChar(String str, StringEnums enums) {
        char[] cs=str.toCharArray();
        if ((cs[0] > 'A' && cs[0] < 'Z') || (cs[0] > 'a' && cs[0] < 'z' )) {
            cs[0] = (char) (enums == StringEnums.upper ? cs[0] - 32 : cs[0] + 32);
        }
        return String.valueOf(cs);
    }

    public static <T, R> Function<T, R> wrapException(ExceptionFunction<T, R> exceptionFunction) {
        return t -> {
            try {
                return exceptionFunction.apply();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

}