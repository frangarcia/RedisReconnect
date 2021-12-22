package com.example.starter;

import io.vertx.core.Vertx
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.JsonObject
import io.vertx.redis.RedisClient
import io.vertx.redis.client.*
import io.vertx.redis.RedisOptions
import io.vertx.core.AsyncResult

public class MainVerticle extends AbstractVerticle {

  static Vertx vertx
  RedisClient redis
  Redis redis2
  static Map redisConfig = [auth:null,select:null,host:"localhost",port:6379,masterName:"master",reconnectAttempts:10, reconnectInterval:1000]

  MainVerticle() {
    vertx = Vertx.vertx()
    try {
      redis = RedisClient.create(vertx, new RedisOptions(JsonObject.mapFrom(redisConfig)))
      redis2 = Redis.createClient(vertx, new io.vertx.redis.client.RedisOptions(JsonObject.mapFrom(redisConfig)))
    } catch (Exception e) {
      println e
    }
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.setPeriodic(1000, {
      try {
        redis?.ping({ AsyncResult<String> ar ->
          if (ar.succeeded()) {
            println "Redis = ${ar.result()}"
          } else {
            println "Using Redis failed"
          }
        })
      } catch (Exception e) {
        println "Using Redis threw an exception ${e}"
      }

      try {
        redis2?.send(Request.cmd(Command.PING), { AsyncResult<Response> ar ->
          if (ar.succeeded()) {
            println "Redis2 = ${ar.result()}"
          } else {
            println "Using Redis2 failed"
          }
        })
      } catch (Exception e) {
        println "Using Redis2 threw an exception ${e}"
      }
    })
  }
}
