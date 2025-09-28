package org.altice.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import redis.RedisCacheService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CalcService implements ICalcService {

    private final List<BigInteger> localCache = new ArrayList<>();
    private static final int CACHE_THRESHOLD = 100_000;

    @Inject
    RedisCacheService redisCache;

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

        if (n >= CACHE_THRESHOLD) {
            String cached = redisCache.get("calc:" + n);
            if (cached != null) {
                return new BigInteger(cached);
            }
        }

        for (int i = localCache.size(); i <= n; i++) {
            BigInteger next = localCache.get(i - 4).add(localCache.get(i - 3));
            localCache.add(next);
        }

        BigInteger result = localCache.get(n);

        if (n >= CACHE_THRESHOLD) {
            redisCache.set("calc:" + n, result.toString());
        }

        return result;
    }
}
