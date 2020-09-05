package demo.spring.boot.demospringboot.util;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.common.web.util
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/4/7     melvin                 Created
 */
public class MapUtil {

    public static class Builder<K, V> {
        private ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();

        public Builder<K, V> e(K key, V value) {
            if (value != null) {
                builder.put(key, value);
            }
            return this;
        }

        public Map<K, V> build() {
            return builder.build();
        }
    }

    public static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    public static <K, V> Map<K, V> $(K k1, V v1) {
        return create(e(k1, v1));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2) {
        return create(e(k1, v1), e(k2, v2));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16,
            K k17, V v17) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16), e(k17, v17));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16,
            K k17, V v17,
            K k18, V v18) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16), e(k17, v17), e(k18, v18));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16,
            K k17, V v17,
            K k18, V v18,
            K k19, V v19) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16), e(k17, v17), e(k18, v18), e(k19, v19));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16,
            K k17, V v17,
            K k18, V v18,
            K k19, V v19,
            K k20, V v20) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16), e(k17, v17), e(k18, v18), e(k19, v19), e(k20, v20));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16,
            K k17, V v17,
            K k18, V v18,
            K k19, V v19,
            K k20, V v20,
            K k21, V v21) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16), e(k17, v17), e(k18, v18), e(k19, v19), e(k20, v20),
                e(k21, v21));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16,
            K k17, V v17,
            K k18, V v18,
            K k19, V v19,
            K k20, V v20,
            K k21, V v21,
            K k22, V v22) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16), e(k17, v17), e(k18, v18), e(k19, v19), e(k20, v20),
                e(k21, v21), e(k22, v22));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16,
            K k17, V v17,
            K k18, V v18,
            K k19, V v19,
            K k20, V v20,
            K k21, V v21,
            K k22, V v22,
            K k23, V v23) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16), e(k17, v17), e(k18, v18), e(k19, v19), e(k20, v20),
                e(k21, v21), e(k22, v22), e(k23, v23));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16,
            K k17, V v17,
            K k18, V v18,
            K k19, V v19,
            K k20, V v20,
            K k21, V v21,
            K k22, V v22,
            K k23, V v23,
            K k24, V v24) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16), e(k17, v17), e(k18, v18), e(k19, v19), e(k20, v20),
                e(k21, v21), e(k22, v22), e(k23, v23), e(k24, v24));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16,
            K k17, V v17,
            K k18, V v18,
            K k19, V v19,
            K k20, V v20,
            K k21, V v21,
            K k22, V v22,
            K k23, V v23,
            K k24, V v24,
            K k25, V v25) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16), e(k17, v17), e(k18, v18), e(k19, v19), e(k20, v20),
                e(k21, v21), e(k22, v22), e(k23, v23), e(k24, v24), e(k25, v25));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16,
            K k17, V v17,
            K k18, V v18,
            K k19, V v19,
            K k20, V v20,
            K k21, V v21,
            K k22, V v22,
            K k23, V v23,
            K k24, V v24,
            K k25, V v25,
            K k26, V v26) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16), e(k17, v17), e(k18, v18), e(k19, v19), e(k20, v20),
                e(k21, v21), e(k22, v22), e(k23, v23), e(k24, v24), e(k25, v25),
                e(k26, v26));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16,
            K k17, V v17,
            K k18, V v18,
            K k19, V v19,
            K k20, V v20,
            K k21, V v21,
            K k22, V v22,
            K k23, V v23,
            K k24, V v24,
            K k25, V v25,
            K k26, V v26,
            K k27, V v27) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16), e(k17, v17), e(k18, v18), e(k19, v19), e(k20, v20),
                e(k21, v21), e(k22, v22), e(k23, v23), e(k24, v24), e(k25, v25),
                e(k26, v26), e(k27, v27));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16,
            K k17, V v17,
            K k18, V v18,
            K k19, V v19,
            K k20, V v20,
            K k21, V v21,
            K k22, V v22,
            K k23, V v23,
            K k24, V v24,
            K k25, V v25,
            K k26, V v26,
            K k27, V v27,
            K k28, V v28) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16), e(k17, v17), e(k18, v18), e(k19, v19), e(k20, v20),
                e(k21, v21), e(k22, v22), e(k23, v23), e(k24, v24), e(k25, v25),
                e(k26, v26), e(k27, v27), e(k28, v28));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16,
            K k17, V v17,
            K k18, V v18,
            K k19, V v19,
            K k20, V v20,
            K k21, V v21,
            K k22, V v22,
            K k23, V v23,
            K k24, V v24,
            K k25, V v25,
            K k26, V v26,
            K k27, V v27,
            K k28, V v28,
            K k29, V v29) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16), e(k17, v17), e(k18, v18), e(k19, v19), e(k20, v20),
                e(k21, v21), e(k22, v22), e(k23, v23), e(k24, v24), e(k25, v25),
                e(k26, v26), e(k27, v27), e(k28, v28), e(k29, v29));
    }

    public static <K, V> Map<K, V> $(
            K k1, V v1,
            K k2, V v2,
            K k3, V v3,
            K k4, V v4,
            K k5, V v5,
            K k6, V v6,
            K k7, V v7,
            K k8, V v8,
            K k9, V v9,
            K k10, V v10,
            K k11, V v11,
            K k12, V v12,
            K k13, V v13,
            K k14, V v14,
            K k15, V v15,
            K k16, V v16,
            K k17, V v17,
            K k18, V v18,
            K k19, V v19,
            K k20, V v20,
            K k21, V v21,
            K k22, V v22,
            K k23, V v23,
            K k24, V v24,
            K k25, V v25,
            K k26, V v26,
            K k27, V v27,
            K k28, V v28,
            K k29, V v29,
            K k30, V v30) {
        return create(
                e(k1, v1), e(k2, v2), e(k3, v3), e(k4, v4), e(k5, v5),
                e(k6, v6), e(k7, v7), e(k8, v8), e(k9, v9), e(k10, v10),
                e(k11, v11), e(k12, v12), e(k13, v13), e(k14, v14), e(k15, v15),
                e(k16, v16), e(k17, v17), e(k18, v18), e(k19, v19), e(k20, v20),
                e(k21, v21), e(k22, v22), e(k23, v23), e(k24, v24), e(k25, v25),
                e(k26, v26), e(k27, v27), e(k28, v28), e(k29, v29), e(k30, v30));
    }

    @SafeVarargs
    public static <K, V> HashMap<K, V> h(Entry<K, V>... entries) {
        HashMap<K, V> hashMap = new HashMap<>();
        for (Entry<K, V> entry : entries) {
            if (entry.getValue() == null)   continue;
            hashMap.put(entry.getKey(), entry.getValue());
        }
        return hashMap;
    }

    public static <K, V> Entry<K, V> e(K key, V value) {
        return new Entry<K, V>(key, value);
    }

    public static <K, V> Map<K, V> $() {
        return ImmutableMap.of();
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static <K, V> Map<K, V> $(Entry<K, V>... entries) {
        return create(entries);
    }

    public static <K, V> Map<K, V> merge(Map<K, V>... maps) {
        ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
        for (Map<K, V> m : maps) {
            for (Map.Entry<K, V> entry : m.entrySet()) {
                builder.put(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    private static <K, V> Map<K, V> create(Entry<K, V>... entries) {
        ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
        for (Entry<K, V> entry : entries) {
            if (entry.getValue() == null)   continue;
            builder.put(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
}
