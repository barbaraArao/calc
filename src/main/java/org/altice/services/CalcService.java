package org.altice.services;

import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CalcService implements ICalcService {

    private final List<BigInteger> localCache = new ArrayList<>();
    private static final int CACHE_THRESHOLD = 100_000;

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

        for (int i = localCache.size(); i <= n; i++) {
            BigInteger next = localCache.get(i - 4).add(localCache.get(i - 3));
            localCache.add(next);
        }

        return localCache.get(n);
    }
}
