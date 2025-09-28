package org.altice.services;
import java.math.BigInteger;

import io.smallrye.mutiny.converters.uni.ToCompletableFuture;

public interface ICalcService {
    BigInteger calc(int n);
}
