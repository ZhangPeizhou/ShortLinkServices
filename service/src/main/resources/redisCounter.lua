local id = redis.call('GET', KEYS[1])
if not id then
    redis.call('SET', KEYS[1],0)
end
id = redis.call('INCR', KEYS[1])
return id