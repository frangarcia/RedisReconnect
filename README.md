# Redis Reconnect issues

This repo is basically showing the problems you can have use the redis client for vert.x io.vertx.redis.RedisClient that is why you should use io.vertx.redis.client.Redis instead.


- Start up redis locally in port 6379
- Run the command
```
./gradlew run
``` 
- Stop redis
- You should see some error messages for both clients 
- Start redis
- You should stop seeing error messages for the client io.vertx.redis.client.Redis but io.vertx.redis.RedisClient is not able to recover itself.
 
