package sg.nus.edu.iss.vttp5a_ssf_2022_assessment.repo;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import sg.nus.edu.iss.vttp5a_ssf_2022_assessment.util.Utility;

@Repository
public class MapRepo {
    @Autowired
    @Qualifier(Utility.TEMPLATE01)
    RedisTemplate<String, String> redisTemplate;

    HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    public void create(String redisKey, String hashKey, String hashValue){
        hashOperations.put(redisKey, hashKey, hashValue);
    }

    public String get(String redisKey, String hashKey){
        return hashOperations.get(redisKey, hashKey);
    }

    public long delete(String redisKey, String hashKey){
        return hashOperations.delete(redisKey, hashKey);
    }

    public boolean keyExists(String redisKey, String hashKey){
        return hashOperations.hasKey(redisKey, hashKey);
    }

    public Map<String, String> getEntries(String redisKey){
        return hashOperations.entries(redisKey);
    }

    public Set<String> getKeys(String redisKey){
        return hashOperations.keys(redisKey);
    }

    public long size(String redisKey){
        return hashOperations.size(redisKey);
    }
}
