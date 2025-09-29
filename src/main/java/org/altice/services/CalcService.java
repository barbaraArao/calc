package org.altice.services;

import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CalcService implements ICalcService {

    private final List<BigInteger> localCache = new ArrayList<>();
    private static final int CACHE_THRESHOLD = 300_000;

    public CalcService() {
        localCache.add(BigInteger.ZERO);
        localCache.add(BigInteger.ONE);
        localCache.add(BigInteger.ZERO);
        localCache.add(BigInteger.ONE);
    }

    @Override
    public synchronized BigInteger calc(int n) {
        if (n < localCache.size()) {
            return localCache.get(n);
        }

        for (int i = localCache.size(); i <= Math.min(n, CACHE_THRESHOLD); i++) {
            BigInteger next = localCache.get(i - 4).add(localCache.get(i - 3));
            localCache.add(next);
        }

        // se maior que 300k:
        if (n > CACHE_THRESHOLD) {
            int size = localCache.size();
            BigInteger v0 = localCache.get(size - 4);
            BigInteger v1 = localCache.get(size - 3);
            BigInteger v2 = localCache.get(size - 2);
            BigInteger v3 = localCache.get(size - 1);

            for (int i = size; i <= n; i++) {
                BigInteger next = v0.add(v1); // i-4 + i-3
                v0 = v1;
                v1 = v2;
                v2 = v3;
                v3 = next;
            }

            return v3;
        }

        return localCache.get(n);
    }
}
