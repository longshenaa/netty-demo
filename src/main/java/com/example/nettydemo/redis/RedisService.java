package com.example.nettydemo.redis;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public void setString(String key, String value, long timeout) {
        setObject(key, value, timeout);
    }
    public void setList(String key, List<String> values) {
        setObject(key, values, 0L);
    }
    public void setHash(String key, Map<String, String> values) {
        setObject(key, values, 0L);
    }
    public void setObject(String key, Object value, long timeout) {
        if (StringUtils.isEmpty(key) || value == null) {
            return;
        }
        if (value instanceof String) {
            stringRedisTemplate.opsForValue().set(key, (String) value, timeout);
            return;
        }
        if (value instanceof List) {
            for (String cell : (List<String>)value) {
                stringRedisTemplate.opsForList().leftPush(key, cell);
            }
            return;
        }
        if (value instanceof Map) {
            for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet()) {
                stringRedisTemplate.opsForHash().put(key, entry.getKey(), entry.getValue());
            }
            return;
        }
    }
    public String getStr(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
