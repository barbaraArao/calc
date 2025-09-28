package redis;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RedisCacheService {

    private final ValueCommands<String, String> commands;

    public RedisCacheService(RedisDataSource ds) {
        this.commands = ds.value(String.class);
    }

    public void set(String key, String value) {
        commands.set(key, value);
    }

    public String get(String key) {
        return commands.get(key);
    }
}
